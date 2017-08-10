package com.IotCloud.wxpay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.IotCloud.wxpay.service.AccessTokenService;
import com.IotCloud.wxpay.service.OrderQueryService;
import com.IotCloud.wxpay.service.UnifiedOrderService;

@RestController
public class AccessTokenController {

	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		AccessTokenService accessTokenService = new AccessTokenService(request, response);
		String token = "";
		try {
			if (accessTokenService.isRequestValid()) {
				token = accessTokenService.getAccessToken();
			}
			res.put("token", token);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unifiedOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		UnifiedOrderService unifiedOrderService = new UnifiedOrderService(request, response);
		String prepayId = "";
		unifiedOrderService.setBody("test");
		unifiedOrderService.setTotalFee("1");
		unifiedOrderService.setMchId("10000100");
		unifiedOrderService.setIp(request.getRemoteAddr());
		unifiedOrderService.setDeviceInfo("WEB");
		unifiedOrderService.setNonce("TEST");
		unifiedOrderService.setNotifyUrl("127.0.0.1");
		unifiedOrderService.setTradeNo("123456789");
		unifiedOrderService.setTradeType("MWEB");
		unifiedOrderService.setSign();
		System.out.println(unifiedOrderService.getDebugInfo());
		// ...
		if (unifiedOrderService.isRequestValid()) {
			prepayId = unifiedOrderService.getPrepayId();
		}
		res.put("prepayId", prepayId);
		return res;
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		OrderQueryService orderQueryService = new OrderQueryService(request, response);
		String orderInfo = "";
		orderQueryService.setAppId("wx2421b1c4370ec43b");
		orderQueryService.setMchId("10000100");
		orderQueryService.setNonce("ec2316275641faa3aacf3cc599e8730f");
		orderQueryService.setTransactionId("1008450740201411110005820873");
		orderQueryService.setSign("FDD167FAA73459FD921B144BAF4F4CA2");
		System.out.println(orderQueryService.getDebugInfo());
		try {
			if(orderQueryService.isRequestValid()) {
				orderInfo = orderQueryService.getOrderInfo();
			}
			res.put("orderInfo", orderInfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
