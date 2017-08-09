package com.IotCloud.wxpay.util;

import java.security.MessageDigest;

public class MessageDigestUtil {

	private static String byteArrayToHexString(byte b[]) {
		char buf[] = new char[b.length * 2];
		for (int i = 0; i < buf.length;) {
			byte byte0 = b[i / 2];
			buf[i++] = hexDigits[byte0 >>> 4 & 0xf];
			buf[i++] = hexDigits[byte0 & 0xf];
		}
		return new String(buf);
	}

	private static String getMessageDigestString(String method, String origin, String charsetname) {
		if (CommonUtil.isNullOrEmpty(origin)) {
			return null;
		}

		try {
			MessageDigest md = MessageDigest.getInstance(method);
			if (CommonUtil.isNullOrEmpty(charsetname))
				return byteArrayToHexString(md.digest(origin.getBytes()));
			else
				return byteArrayToHexString(md.digest(origin.getBytes(charsetname)));
		} catch (Exception exception) {
			return null;
		}
	}
	
	public static String MD5Encode(String origin, String charsetname) {
		return getMessageDigestString("MD5", origin, charsetname);
	}

	public static String SHA1Encode(String origin, String charsetname) {
		return getMessageDigestString("SHA1", origin, charsetname);
	}

	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

}
