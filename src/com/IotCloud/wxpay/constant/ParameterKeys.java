package com.IotCloud.wxpay.constant;

public class ParameterKeys {
	//服务器的请求常量
	public final static String APP_ID = "appid";
	public final static String APP_SECRET = "appsecret";
	public final static String MCH_ID = "mch_id";
	public final static String NONCE_STR = "nonce_str";
	public final static String TRADE_NO = "out_trade_no";
	public final static String SIGN = "sign";
	public final static String SIGN_TYPE = "sign_type";
	
	//微信的回应常量
	public final static String ACCESS_TOKEN = "access_token";//access_token常量值
	public final static String ERRORCODE = "err_code";//用来判断access_token是否失效的值
	public final static String PREPAY_ID = "prepay_id";//预支付id
	public final static String TRANSACTION_ID = "transaction_id";
}
