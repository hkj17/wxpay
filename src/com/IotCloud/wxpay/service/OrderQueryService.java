package com.IotCloud.wxpay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.IotCloud.wxpay.client.TenpayHttpClient;
import com.IotCloud.wxpay.constant.ParameterKeys;
import com.IotCloud.wxpay.constant.ParameterValues;
import com.IotCloud.wxpay.util.CommonUtil;
import com.IotCloud.wxpay.util.ResponseHandler;

public class OrderQueryService extends RequestHandler {

	public OrderQueryService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		requestUrl = ParameterValues.URL_ORDER_QUERY;
		setAppId(ParameterValues.APP_ID);
	}

	public void setAppId(String appId) {
		super.setParameter(ParameterKeys.APP_ID, appId);
	}

	public void setMchId(String mchId) {
		super.setParameter(ParameterKeys.MCH_ID, mchId);
	}

	public void setNonce(String nonce) {
		super.setParameter(ParameterKeys.NONCE_STR, nonce);
	}

	public void setTradeNo(String tradeNo) {
		super.setParameter(ParameterKeys.TRADE_NO, tradeNo);
	}

	public void setTransactionId(String transactionId) {
		super.setParameter(ParameterKeys.TRANSACTION_ID, transactionId);
	}
	
	public String getOrderInfo() throws Exception {
		String xml = super.getXmlBody();
		System.out.println(xml);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		if (httpClient.callHttpPost(requestUrl, xml)) {
			resContent = httpClient.getResContent();
			ResponseHandler responseHandler = new ResponseHandler(resContent, 1);
			responseHandler.getAllParameters();
			return responseHandler.getAllParamValues();
		}else {
			return "";
		}
	}

	@Override
	public boolean isRequestValid() {
		Map<String, String> map = getAllParameters();
		if (CommonUtil.isNullOrEmpty(map, ParameterKeys.APP_ID)) {
			return false;
		}
		if (CommonUtil.isNullOrEmpty(map, ParameterKeys.MCH_ID)) {
			return false;
		}
		if (CommonUtil.isNullOrEmpty(map, ParameterKeys.TRANSACTION_ID)
				&& CommonUtil.isNullOrEmpty(map, ParameterKeys.TRADE_NO)) {
			return false;
		}
		if (CommonUtil.isNullOrEmpty(map, ParameterKeys.NONCE_STR)) {
			return false;
		}
		if (CommonUtil.isNullOrEmpty(map, ParameterKeys.SIGN)) {
			return false;
		}
		return true;
	}

}
