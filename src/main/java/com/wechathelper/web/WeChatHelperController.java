package com.wechathelper.web;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

import com.wechathelper.WechathelperApplication;
import com.wechathelper.constant.ErrorCode;
import com.wechathelper.model.*;
import com.wechathelper.repository.AutoReplyTextRepository;
import com.wechathelper.repository.AutoReplyToUserRepository;
import com.wechathelper.repository.TextMessageTaskRepository;
import com.wechathelper.repository.UserRepository;
import com.wechathelper.security.JwtTokenUtil;
import com.wechathelper.service.JwtUserDetailsServiceImpl;
import com.wechathelper.service.TextMessageTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.wechathelper.constant.Constant;

import javax.validation.Valid;

import static org.hibernate.validator.internal.util.CollectionHelper.asSet;


@RestController
@RequestMapping(value="/wechathelper")
public class WeChatHelperController {

	@Autowired
	private TextMessageTaskService textMessageTaskService;

	@Autowired
	private TextMessageTaskRepository textMessageTaskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AutoReplyTextRepository autoReplyTextRepository;

	@Autowired
	private AutoReplyToUserRepository autoReplyToUserRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping(value="/")
	public String index() {
		return "this is Wechat Helper";
	}
	
	@GetMapping(value="/qrcode")
	public ResponseEntity<?> getQRCode(@RequestParam("username") String username) {
		try {
			Socket client = new Socket("127.0.0.1", Constant.SOCKET_TO_WECHAT_SERVER_PORT);
			client.setSoTimeout(5000);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(username);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			String imgDir = in.readUTF();
			client.close();
			return new ResponseEntity<String>(imgDir,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			ErrorModel errorModel = new ErrorModel(ErrorCode.ENTITY_ALREADY_EXIST,"ENTITY_ALREADY_EXIST","",e.toString());
			return new ResponseEntity<ErrorModel>(errorModel,HttpStatus.OK);
		}
		
	}

	@PostMapping(value = "/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		final Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(username, userDetails);
		final String token = jwtTokenUtil.generateToken(map);
		return token;

	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@Valid User user) {
		User userExists = userRepository.findByUsername(user.getUsername());
		if (userExists != null){
			ErrorModel errorModel = new ErrorModel(ErrorCode.ENTITY_ALREADY_EXIST,"ENTITY_ALREADY_EXIST","",null);
			return new ResponseEntity<ErrorModel>(errorModel,HttpStatus.CONFLICT);
		}

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		final String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		Role role = new Role("USER");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);

		userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value="/status")
	public ResponseEntity<?> wechatStatus(@RequestParam("username") String username) {
		if (WechathelperApplication.getWechatService().getWechatMap().containsKey(username)) {
			User user = userRepository.findByUsername(username);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}

		return new ResponseEntity<Integer>(1, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/create-text-message-task")
	public ResponseEntity<?> createTextMessageTask(@Validated @RequestBody TextMessageTask textMessageTask){

		if (textMessageTaskService.createTextMessageTask(textMessageTask) == 1){
			return new ResponseEntity<TextMessageTask>(textMessageTask, HttpStatus.ACCEPTED);
		}else{
			ErrorModel errorModel = new ErrorModel(ErrorCode.ENTITY_ALREADY_EXIST,"ENTITY_ALREADY_EXIST","",null);
			return new ResponseEntity<ErrorModel>(errorModel,HttpStatus.CONFLICT);
		}

	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/text-message-task-list")
	public ResponseEntity<?> getTextMessageTaskList(@RequestParam(value = "wechatId") String wechatId){
		List<TextMessageTask> list = textMessageTaskService.getTextMessageTasksListByUserId(wechatId);

		return  new ResponseEntity<List<TextMessageTask>>(list,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/text-message-task")
	public ResponseEntity<?> updateTextMessageTaskList(@Validated TextMessageTask textMessageTask){
		textMessageTaskRepository.save(textMessageTask);
		return  new ResponseEntity<TextMessageTask>(textMessageTask,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/auto-reply-text-message")
	public ResponseEntity<?> updateAutoReplyTestMessage(@Validated AutoReplyMessage autoReplyMessega){
		AutoReplyMessage check = autoReplyTextRepository.findByWechatId(autoReplyMessega.getWechatId());

		if (check != null){
			autoReplyTextRepository.save(check);
			return  new ResponseEntity<AutoReplyMessage>(check,HttpStatus.OK);
		}

		autoReplyTextRepository.save(autoReplyMessega);
		return  new ResponseEntity<AutoReplyMessage>(autoReplyMessega,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/user_profile")
	public ResponseEntity<?> updateUserProfile(@Validated User user){
		userRepository.save(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/auto-reply-custom-message")
	public ResponseEntity<?> updateAutoReplyCustomMessage(@RequestParam(value = "wechat_id") String wechatId,
														  @RequestParam(value = "custom_message") String message,
														  @RequestParam(value = "reply_to_wechat_id") String toWechatId){

			AutoReplyToUser autoReplyToUserCheck = autoReplyToUserRepository.findByWechatIdAndAndReplyToWechatId(wechatId,toWechatId);
			if (autoReplyToUserCheck==null){
				AutoReplyToUser autoReplyToUser = new AutoReplyToUser(
					toWechatId,wechatId,true,false,message
				);

				autoReplyToUserRepository.save(autoReplyToUser);
				WechathelperApplication.getWechatService().getRobotByWechatId(wechatId).setChatWithTuring(false);
				return new ResponseEntity<>(autoReplyToUser,HttpStatus.OK);
			}else{
				autoReplyToUserCheck.setCustomMessage(message);
				autoReplyToUserRepository.save(autoReplyToUserCheck);
				WechathelperApplication.getWechatService().getRobotByWechatId(wechatId).setChatWithTuring(false);
				return new ResponseEntity<>(autoReplyToUserCheck,HttpStatus.OK);
			}

	}

}
