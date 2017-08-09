package com.IotCloud.wxpay.constant;

public class ParameterValues {
	/**
	 * 商家可以考虑读取配置文件
	 */
	
	//初始化
	public final static String APP_ID = "wxabe6785e709a35f1";//微信开发平台应用id
	public final static String APP_SECRET = "89e1037085a3025bd8eb8b82bb766bb3";//应用对应的凭证
	//应用对应的密钥
	public static String APP_KEY = "L8LrMqqeGRxST5reouB0K66CaYAWpqhAVsq7ggKkxHCOastWksvuX1uvmvQclxaHoYd3ElNBrNO2DHnnzgfVG9Qs473M3DTOZug5er46FhuGofumV8H2FVR9qkjSlC5K";
	//public static String PARTNER = "1900000109";//财付通商户号
	//public static String PARTNER_KEY = "8934e7d15453e97507ef794cf7b0519d";//商户号对应的密钥
	public final static String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";//获取access_token对应的url
	public final static String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String URL_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
	public final static String EXPIRE_ERRCODE = "42001";//access_token失效后请求返回的errcode
	public final static String FAIL_ERRCODE = "40001";//重复获取导致上一次获取的access_token失效,返回错误码
	public static String SIGN_METHOD_SHA1 = "SHA1";//签名算法常量值
	public static String SIGN_METHOD_MD5 = "MD5";//签名算法常量值
}
