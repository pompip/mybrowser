package cn.pompip.browser.util;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUtil  {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
//
//
//	/***
//	 * 图片上传到cdn
//	 * @param file
//	 * @return
//	 * @throws ClientProtocolException
//	 * @throws IOException
//	 */
//	public static String uploadFile(File file) throws ClientProtocolException, IOException
//	{
//		String fileName=null;
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(RedisUtil.getConfigValue("image_upload_url"));
//		FileBody bin = new FileBody(file);
//		// 以浏览器兼容模式运行，防止文件名乱码。
//		HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//				.addPart("file", bin).build();
//		httpPost.setEntity(reqEntity);
//		CloseableHttpResponse response = httpClient.execute(httpPost);
//		// 获取响应对象
//		HttpEntity resEntity = response.getEntity();
//		if (resEntity != null) {
//			String responseStr=EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
//			JSONObject jsonObject = JSONObject.fromObject(responseStr);
//			String code = jsonObject.getString("code");
//			if("1".equals(code))
//			{
//				fileName = jsonObject.getString("data").substring(36);
//			}
//		}
//		// 销毁
//		EntityUtils.consume(resEntity);
//		response.close();
//		return fileName;
//	}
	
	@SuppressWarnings("rawtypes")
	public static Map getParameterMap(HttpServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}
	
	public static void writeFileToDisk(String fileUrl,String filePath) throws Exception {
		URL url = new URL(fileUrl);  
        InputStream inStream = url.openStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }
        inStream.close();  
        byte[] btImg = outStream.toByteArray();
        File file = new File(filePath);  
        FileOutputStream fops = new FileOutputStream(file);  
        fops.write(btImg);  
        fops.flush();  
        fops.close();  
	}
	private static OkHttpClient httpClient = new OkHttpClient.Builder().build();
	
	public static String get(String url) throws Exception {
		Request request = new Request.Builder().get().url(url).header("Content-Type","text/html;charset=utf-8").build();
		ResponseBody body = httpClient.newCall(request).execute().body();
		if (body!=null){
			return  body.string();
		}else {
			throw new Exception();
		}

//		HttpClient client = new HttpClient();
//		// 设置代理服务器地址和端口
//		// client.getHostConfiguration().setProxy( "172.26.184.189", 80 );
//		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//		HttpMethod method = new GetMethod(url);
//		// 这里设置字符编码，避免乱码
//		method.setRequestHeader("Content-Type", "text/html;charset=utf-8");
//		client.executeMethod(method);
//		// 释放连接
//		String res=new String(method.getResponseBody(),"utf-8");
//		method.releaseConnection();
//		return res;

	}
	
	public static String post(String serverUrl, String data, int timeout) throws Exception
	{
		BufferedReader reader;
		OutputStreamWriter wr;
		StringBuilder responseBuilder = null;
		reader = null;
		wr = null;
		URL url = new URL(serverUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		conn.setConnectTimeout(timeout);
		wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();
		
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		responseBuilder = new StringBuilder();
		for(String line = null; (line = reader.readLine()) != null;)
			responseBuilder.append((new StringBuilder()).append(line).append("\n").toString());
		String res = responseBuilder.toString();
		logger.debug(responseBuilder.toString());
		
		if(wr != null)
			try
		{
				wr.close();
		}
		catch(IOException e2)
		{
			logger.error("close error", e2);
		}
		if(reader != null)
			try
		{
				reader.close();
		}
		catch(IOException e3)
		{
			logger.error("close error", e3);
		}
		if(reader != null)
			try
		{
				reader.close();
		}
		catch(IOException e5)
		{
			logger.error("close error", e5);
		}
		return res;
	}
	
	public static boolean isNotEmpty(String value){
		return !StringUtils.isEmpty(value) && !StringUtils.isEmpty(value.trim());
	} 
	
	public static boolean isNotEmpty(String... values){
		for (String string : values) {
			if(isNotEmpty(string)){
				return false;
			}
		}
		return true;
	}
	

}
