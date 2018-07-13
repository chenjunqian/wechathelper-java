package com.wechathelper.robot;

import com.alibaba.fastjson.JSON;
import com.wechathelper.WechathelperApplication;
import com.wechathelper.model.*;
import com.wechathelper.repository.*;
import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MyWechatBot extends WeChatBot{
	
	private boolean auto_reply = false;

	private boolean isChatWithTuring = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TextMessageTaskRepository textMessageTaskRepository;

	@Autowired
	private AutoReplyTextRepository autoReplyTextRepository;

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private AutoReplyToUserRepository autoReplyToUserRepository;

	private AutoReplyMessage autoReplyMessage;

	private TextMessageTask textMessageTask;

	public MyWechatBot(Config config) {
		super(config);
		// TODO Auto-generated constructor stub
		init();
	}

	public boolean isAuto_reply() {
		return auto_reply;
	}


	public void setAuto_reply(boolean auto_reply) {
		this.auto_reply = auto_reply;
	}

	public boolean isChatWithTuring() {
		return isChatWithTuring;
	}

	public void setChatWithTuring(boolean chatWithTuring) {
		isChatWithTuring = chatWithTuring;
	}



	private void init(){
		User user = userRepository.findByUsername(this.session().getUserName());
		setAuto_reply(user.isAutoReply());
		setChatWithTuring(user.isChatWithTuring());
		autoSendMessage();
	}
	
	
	@Bind(accountType = AccountType.TYPE_FRIEND)
	public void friendMessage(WeChatMessage message) {
		ChatMessage chatMessage = new ChatMessage(
				MyWechatBot.this.session().getUserName(),
				message.getId(),
				message.getText()
		);
		chatMessageRepository.save(chatMessage);

		AutoReplyToUser autoReplyToUser = autoReplyToUserRepository.findByWechatIdAndAndReplyToWechatId(this.session().getUserName(),message.getFromUserName());

		if (StringUtils.isNotEmpty(message.getName()) && autoReplyToUser!=null){
			this.sendMsg(message.getFromUserName(), autoReplyToUser.getCustomMessage());
		}else if(StringUtils.isNotEmpty(message.getName()) && auto_reply ){
			autoReplyMessage = autoReplyTextRepository.findByWechatId(this.session().getUserName());
	        this.sendMsg(message.getFromUserName(), "自动回复: " + autoReplyMessage.getMessage());
	    } else if (StringUtils.isNotEmpty(message.getName())  && isChatWithTuring){
			this.sendMsg(message.getFromUserName(), chatWithTuringRobot(message.getFromUserName(),message.getText()));
		}
	}


	/**
	 * 自动定时发送消息
	 */
	public void autoSendMessage(){

		WechathelperApplication.getScheduledExecutorService().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				int minute = LocalDateTime.now().getMinute();
				int hour = LocalDateTime.now().getHour();
				textMessageTask = textMessageTaskRepository.findByTaskTimeHourAndTaskTimeHour(minute,hour);
				if (textMessageTask!=null){
					MyWechatBot.this.sendMsg(
							textMessageTask.getToUsername(),
							textMessageTask.getMessage()
					);
				}
			}
		},0, 10,TimeUnit.SECONDS);

	}

	/**
	 *
	 * @param userId
	 * @param massage
	 * @return String 机器人回复的消息
	 * 与图灵机器人聊天
	 */
	private String chatWithTuringRobot(String userId, String massage){
		String apiKey = "3b55c27711f04c1a9cbef28e9d43da88";
		String turingUrl = "http://www.tuling123.com/openapi/api"+"?key="+apiKey+"&info="+massage;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
		params.add("key",apiKey);
		params.add("info",massage);
		params.add("loc","桂林");
		params.add("userid","@WSXZAQ!");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(turingUrl,HttpMethod.GET,null,String.class);

		Map<String,String> responseMap = (Map<String, String>) JSON.parse(response.getBody());
		String responseText = responseMap.get("text");

		return responseText;
	}


}
