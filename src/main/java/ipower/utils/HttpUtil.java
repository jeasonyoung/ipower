package ipower.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
/**
 * HTTP工具类。
 * @author yangyong.
 * @since 2014-03-01.
 * */
public final class HttpUtil {
	/**
	 * 发起http请求获取反馈。
	 * @param connection
	 * 	http链接对象。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(HttpURLConnection connection, String method,String data) throws IOException{
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		//设置请求方式(GET/POST)
		connection.setRequestMethod(method);
		if(method.equalsIgnoreCase("GET")){
			connection.connect();
		}
		//当有数据需要提交时
		if(data != null && !data.trim().isEmpty()){
			OutputStream outputStream = connection.getOutputStream();
			//注意编码格式，防止中文乱码
			outputStream.write(data.getBytes("UTF-8"));
			outputStream.close();
		}
		//将返回的输入流转换成字符串
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		StringBuffer buffer = new StringBuffer();
		String out = null;
		while((out = bufferedReader.readLine()) != null){
			buffer.append(out);
		}
		//释放资源
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		connection.disconnect();
		
		return buffer.toString();
	}
	/**
	 * 发起https请求获取反馈。
	 * @param x509TrustManager
	 * 	SSL证书。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * */
	public static String sendRequest(X509TrustManager x509TrustManager,String url, String method,String data){
		try {
			if(x509TrustManager == null)
				return sendRequest(url, method, data);
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {x509TrustManager};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象。
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL uri = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection)uri.openConnection();
			connection.setSSLSocketFactory(ssf);
			
			return sendRequest(connection, method, data);
		}catch(ConnectException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发起https请求获取反馈。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(String url, String method,String data) throws IOException{
		URL uri = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)uri.openConnection();
		return sendRequest(connection, method, data);
	}
}