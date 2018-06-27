package com.wechathelper.constant;

public class Constant {
	
	/**
	 * 连接到微信服务socket端口
	 **/
	public static final int SOCKET_TO_WECHAT_SERVER_PORT = 20006;
	
	/**
	 * 客户端向微信服务发起登录
	 **/
	public static final int SOCKET_TO_WECHAT_SERVER_LOGIN_WECHAT = 1;

	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 24*60*60;
	public static final String SIGNING_KEY = "wechathelper";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}
