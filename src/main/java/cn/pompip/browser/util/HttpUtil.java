package cn.pompip.browser.util;


import okhttp3.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


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
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public static void writeFileToDisk(String fileUrl, String filePath) throws Exception {
        URL url = new URL(fileUrl);
        InputStream inStream = url.openStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
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
        Request request = new Request.Builder().get().url(url)
                .header("Content-Type", "text/html;charset=utf-8").build();
        ResponseBody body = httpClient.newCall(request).execute().body();
        return body.string();
    }

    public static String get(String url, Map<String, Object> params) throws Exception {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        params.forEach((key, value) -> builder.addQueryParameter(key, value.toString()));
        Request request = new Request.Builder().get().url(builder.build())
                .header("Content-Type", "text/html;charset=utf-8")
                .build();
        ResponseBody body = httpClient.newCall(request).execute().body();
        return body.string();
    }

    public static void get(String url, Map<String, Object> params, Callback callback) {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        params.forEach((key, value) -> builder.addQueryParameter(key, value.toString()));
        Request request = new Request.Builder().get().url(builder.build())
                .header("Content-Type", "text/html;charset=utf-8")
                .build();
        httpClient.newCall(request).enqueue(callback);

    }

    public static String post(String url, Map<String, String> params) throws Exception {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        params.forEach(bodyBuilder::add);

        Request build = new Request.Builder()
                .post(bodyBuilder.build())
                .url(HttpUrl.get(url).newBuilder().build())
                .build();
        Response execute = httpClient.newCall(build).execute();

        return execute.body().string();


    }


}
