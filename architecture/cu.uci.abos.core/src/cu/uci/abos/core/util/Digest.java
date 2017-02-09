package cu.uci.abos.core.util;

import java.security.MessageDigest;

public class Digest {

	public static final String digest(String str, String algorithm) {
		if (str == null)
			return str;
		try {
			MessageDigest md5 = MessageDigest.getInstance(algorithm);
			md5.reset();
			md5.update(str.getBytes());
			return convert(md5.digest());
		} catch (Exception e) {
			return null;
		}
	}

	private static final String convert(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(convertDigit(bytes[i] >> 4));
			sb.append(convertDigit(bytes[i] & 0xf));
		}
		return sb.toString();
	}

	private static final char convertDigit(int value) {
		value &= 0xf;
		if (value >= 10)
			return (char) ((value - 10) + 97);
		else
			return (char) (value + 48);
	}
}