package com.IotCloud.wxpay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.IotCloud.wxpay.client.TenpayHttpClient;
import com.IotCloud.wxpay.constant.ParameterKeys;
import com.IotCloud.wxpay.constant.ParameterValues;

public class UnifiedOrderService extends RequestHandler {
	public UnifiedOrderService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		requestUrl = ParameterValues.URL_UNIFIED_ORDER;
		setAppId(ParameterValues.APP_ID);
	}
	
	public void setAppId(String appId) {
		super.setParameter(ParameterKeys.APP_ID, appId);
	}
	
	public void setMchId(String mchId) {
		super.setParameter(ParameterKeys.MCH_ID, mchId);
	}
	
	public void setDeviceInfo(String deviceInfo) {
		super.setParameter("device_info", deviceInfo);
	}
	
	public void setNonce(String nonce) {
		super.setParameter(ParameterKeys.NONCE_STR, nonce);
	}
	
	public void setBody(String body) {
		super.setParameter("body", body);
	}
	
	public void setDetail(String detail) {
		super.setParameter("detail", detail);
	}
	
	public void setAttach(String attach) {
		super.setParameter("attach", attach);
	}
	
	public void setTradeNo(String tradeNo) {
		super.setParameter(ParameterKeys.TRADE_NO, tradeNo);
	}
	
	public void setFeeType(String feeType) {
		super.setParameter("fee_type", feeType);
	}
	
	public void setTotalFee(String totalFee) {
		super.setParameter("total_fee", totalFee);
	}
	
	public void setIp(String ip) {
		super.setParameter("spbill_create_ip", ip);
	}
	
	public void setNotifyUrl(String notifyUrl) {
		super.setParameter("notify_url", notifyUrl);
	}
	
	public void setTradeType(String tradeType) {
		super.setParameter("trade_type", tradeType);
	}
	
	public void setProductId(String productId) {
		super.setParameter("product_id", productId);
	}
	
	public void setOpenId(String openId) {
		super.setParameter("openid", openId);
	}
	
	public String getPrepayId() {
		String xml = super.getXmlBody();
		System.out.println(xml);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		if (httpClient.callHttpPost(requestUrl, xml)) {
			resContent = httpClient.getResContent();
		}
		return resContent;
	}

	@Override
	public boolean isRequestValid() {
		return true;
	}
}
