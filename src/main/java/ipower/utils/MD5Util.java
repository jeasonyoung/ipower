package ipower.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类.
 * @author 杨勇.
 * @since 2013-11-29.
 * */
public final class MD5Util {
	/**
	 * 16进制数组。
	 * */
	public final static char HexDigits [] ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	/**
	 * 将字节数组转换为16进制字符串。
	 * @param data
	 * 	字节数组。
	 * @return 16进制字符串。
	 * */
	public final static String ConvertHex(byte[] data){
		if(data == null || data.length == 0) return null;
		int len = data.length;
		char[] results = new char[len * 2];
		int k = 0;
		for(int i = 0; i < len; i++){
			results[k++] = HexDigits[(data[i] >>> 4) & 0xf];
			results[k++] = HexDigits[data[i] & 0xf];
		}
		return new String(results);
	}
	
	/**
	 * 将字符串md5加密. 
	 * @param source
	 * 	字符串。
	 * @return 密文.
	 * */
	public final static String MD5(String source){
		try {
			byte[] input = source.getBytes(Charset.forName("UTF-8"));
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input);
			byte[] result = digest.digest();
			return ConvertHex(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 对输入流进行md5加密。
	 * @param stream
	 * 	输入流。
	 * @return 密文。
	 * */
	public final static String MD5(InputStream stream){
		if(stream == null) return null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if(stream.markSupported()){
				stream.reset();
			}
			byte[] buf = new byte[1024];
			int len = -1;
			while((len = stream.read(buf, 0, buf.length)) > 0){
				digest.update(buf, 0, len);
			}
			byte[] result = digest.digest();
			return ConvertHex(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}