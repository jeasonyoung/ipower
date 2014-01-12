package ipower.wrappers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;


/**
 * 上传请求包装类。
 * @author young.
 * @since 2013-08-18
 * */
public class RequestParseWrapper extends JakartaMultiPartRequest {
	
	@Override
	public void parse(HttpServletRequest servletRequest,String saveDir) throws IOException
	{
		
	}
}