package com.bwf.duobao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String encrypt(String s){
		byte[] btinput=s.getBytes();
		try {
			MessageDigest mdinst=MessageDigest.getInstance("MD5");
			
			mdinst.update(btinput);
			byte[] md=mdinst.digest();
			
			StringBuffer sb=new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val=((int)md[i])&0xff;
				if(val<16){
					sb.append("0");
					sb.append(Integer.toHexString(val));
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
