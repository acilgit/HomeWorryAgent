package com.android.housingonitoringagent.homeworryagent.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

	private static MessageDigest messageDigest = getMessageDigest();

	public static MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

			return null;
		}
	}

	public static String md5Encrypt(){
		String md5;//md5����ֵ
		String result;//���ռ��ܽ��
		long l = System.currentTimeMillis();//��ǰ������
		long lon = l;
		char[] chars = new char[7];//��ĸ�ַ�����
		String string = "";//7����ĸ���ַ���
		String str = "abcdefghijklmnopqrstuvwxyz";//26����ĸ���������������ĸ�ַ�
		for (int i = 0; i < chars.length; i++) {
			chars[i] = str.charAt((int)(Math.random()*26));
			string += chars[i];
			l += (int)chars[i];
		}
		md5 = EncryptUtils.encryptMD5(String.valueOf(l));
		result = md5 + string + lon;
		return result;
	}

	public static String MD5(byte[] source) {
		char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
		try {
			messageDigest.update(source);
			byte tmp[] = messageDigest.digest();          // MD5 的计算结果是一个 128 位的长整数，

			char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0;                       // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {   // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i];            // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];        // 取字节中低 4 位的数字转换
			}

			return new String(str);
		} catch(Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}
