package com.wechathelper.robot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.wechathelper.constant.Constant;

import io.github.biezhi.wechat.api.constant.Config;

public class WechatService {
	
	private ServiceWechatBot serviceWechatBot;
	private HashMap<String , MyWechatBot> wechatMap;
	private ServerSocket serverSocket;
	private Socket socket;
	
	public void initServiceWechat() {
		try {
			serverSocket = new ServerSocket(Constant.SOCKET_TO_WECHAT_SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String newWechatBot() {
		MyWechatBot bot = new MyWechatBot(Config.me().autoLogin(true).showTerminal(true));
		bot.start();
		String qRCodePath = bot.config().assetsDir();

		return qRCodePath;
	}
	
	public MyWechatBot getRobotByUsername (String username) {
		MyWechatBot bot = wechatMap.get(username);
		return bot;
	}
	
	public void runServerWechat() {
		serviceWechatBot = new ServiceWechatBot(Config.me().autoLogin(true).showTerminal(true));
		serviceWechatBot.start();
	}
	
	public void runWechatServiceServer(){
		while (true) {
			try {
				socket = serverSocket.accept();
				DataInputStream input = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				String clientInputInt = input.readUTF();
				if (clientInputInt.length() > 0) {
					MyWechatBot neWechatBot = new MyWechatBot(Config.me().autoLogin(true).showTerminal(true));
					Thread startWechatHandler = new Thread(new Runnable() {
						
						@Override
						public void run() {
							neWechatBot.start();
							wechatMap.put(clientInputInt, neWechatBot);
						}
					});
					
					startWechatHandler.start();
					
					TimeUnit.SECONDS.sleep(1);
					out.writeUTF(neWechatBot.config().assetsDir()+"/qrcode.png");
					out.close();
					input.close();
				}
				
				
			} catch (IOException | NullPointerException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket!=null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						socket = null;
						e.printStackTrace();
					}
				}
			}
		}
	}

	public HashMap<String, MyWechatBot> getWechatMap() {
		return wechatMap;
	}
}
