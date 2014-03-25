package ipower.test;

import java.io.IOException;

import ipower.utils.MD5Util;

public class MD5Test {
	
	public static void main(String[] args) throws IOException{
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<BODY><SECTION><NAME>CORPNAME</NAME><VALUE>华为技术有限公司</VALUE></SECTION><SECTION><NAME>CORPTELE</NAME><VALUE>28780808</VALUE></SECTION></BODY>";
	
		System.out.println("data:\r\n" + data);
		String value = MD5Util.MD5(data);
		System.out.println("value:"+ value);
		System.in.read();
	}
}