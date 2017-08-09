package com.IotCloud.wxpay.service;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.IotCloud.wxpay.constant.ParameterKeys;
import com.IotCloud.wxpay.constant.ParameterValues;
import com.IotCloud.wxpay.util.CommonUtil;
import com.IotCloud.wxpay.util.MessageDigestUtil;

/**
 * 请求处理类 请求处理类继承此类，重写createSign方法即可。
 * 
 * @author
 *
 */
public abstract class RequestHandler {

	/** 网关url地址 */
	protected String requestUrl;

	/** 密钥 */
	private final String key;

	/** 请求的参数 */
	private SortedMap<String, String> parameters;

	/** debug信息 */
	private String debugInfo;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.key = ParameterValues.APP_KEY;
		this.parameters = new TreeMap<String, String>();
		this.debugInfo = "";
	}

	/**
	 * 初始化函数。
	 */
	public void init() {
		// nothing to do
	}

	/**
	 * 获取密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @param parameterValue
	 *            参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if (null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}

	/**
	 * 返回所有的参数
	 * 
	 * @return SortedMap
	 */
	public SortedMap<String, String> getAllParameters() {
		return this.parameters;
	}

	/**
	 * 获取debug信息
	 */
	public String getDebugInfo() {
		return debugInfo;
	}
	
	public void setSign(String sign) {
		this.setParameter(ParameterKeys.SIGN, sign);
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public void setSign() {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = this.parameters.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (!CommonUtil.isNullOrEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());

		String enc = request.getCharacterEncoding();
		String sign = MessageDigestUtil.MD5Encode(sb.toString(), enc).toUpperCase();

		this.setParameter(ParameterKeys.SIGN, sign);

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign);

	}

	public void setSignType(String signType) {
		setParameter(ParameterKeys.SIGN_TYPE, signType);
	}

	protected String getXmlBody() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>\r\n");
		Set<Entry<String, String>> es = parameters.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append("<" + k + ">" + v + "</" + k + ">" + "\r\n");

		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	public abstract boolean isRequestValid();
}
