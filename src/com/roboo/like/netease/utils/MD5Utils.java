package com.roboo.like.netease.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils
{
	private static final String HASH_ALGORITHM = "MD5";
	private static final int RADIX = 10 + 26; // 10 digits + 26 letters
	public static String generate(String url)
	{
		byte[] md5 = getMD5(url.getBytes());
		BigInteger bi = new BigInteger(md5).abs();
		return bi.toString(RADIX);
	}

	private static byte[] getMD5(byte[] data)
	{
		byte[] hash = null;
		try
		{
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		}
		catch (NoSuchAlgorithmException e)
		{

		}
		return hash;
	}
}
