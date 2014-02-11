package com.wss.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	public enum EncryptionType {
		MD2, MD5, SHA1, SHA256, SHA384, SHA512;
		public String toString(){
			String t = super.toString();
			if(t.indexOf("SHA")==0) return "SHA-" + t.substring(3);
			return t;			
		}
	}

	public static String encode(byte[] data, EncryptionType Type){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(Type.toString());
			md.update(data);
			byte[] newByte = md.digest();
			return HexEncoding.encode(newByte).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public static String encode(String data, EncryptionType Type){
		return encode(data.getBytes(), Type);
	}
}