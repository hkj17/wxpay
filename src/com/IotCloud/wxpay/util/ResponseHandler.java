package com.IotCloud.wxpay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class ResponseHandler {
	private String content;
	private int type;
	private Map<String, String> paramMap;
	private boolean isParamMapSet;

	public ResponseHandler(String content, int type) throws Exception {
		this.content = content;
		this.type = type;
		this.paramMap = new TreeMap<String, String>();
		this.isParamMapSet = false;
	}

	public String getParamValue(String key) {
		if(isParamMapSet) {
			return paramMap.get(key);
		}else {
			return "";
		}
	}
	
	public String getAllParamValues() {
		if(isParamMapSet) {
			return paramMap.toString();
		}else {
			return "";
		}
	}
	
	public void getAllParameters(){
		if(type == 0) {
			getAllJsonParams();
		}else if(type == 1) {
			getAllXmlParams();
		}else {
			
		}
	}
	
	private void getAllJsonParams() {
		JSONObject jsonObject = JSONObject.fromObject(content);
		Iterator iter = jsonObject.keys();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			paramMap.put(key, jsonObject.getString(key));
		}
		isParamMapSet = true;
	}
	
	private void getAllXmlParams() {
		int startIndex = content.indexOf("<xml>") + "<xml>".length();
		int endIndex = content.indexOf("</xml>");
		String body = content.substring(startIndex, endIndex).trim();
		String regEx = "<(\\w+)>(.*)</\\w+>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(body);
		while (m.find()) {
			String key = m.group(1);
			String value = m.group(2);
			String regEx2 = "<!\\[CDATA\\[(.*)\\]\\]>";
			Pattern p2 = Pattern.compile(regEx2);
			Matcher m2 = p2.matcher(value);
			if(m2.find()) {
				paramMap.put(key, m2.group(1));
			}else {
				paramMap.put(key, value);
			}
		}  
		isParamMapSet = true;
	}

	public static void main(String[] args) throws Exception {
		String xml = "<![CDATA[CNY]]>";
		
	    // 正则表达式规则
	    //String regEx = "<(\\w+)>(.*)</\\w+>"; // "<attach>(.*)</attach>";
		String regEx = "<!\\[CDATA\\[(.*)\\]\\]>";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(xml);
	    while (matcher.find()) {  
	    	System.out.println(matcher.group(1));
	    }  

	}
}
