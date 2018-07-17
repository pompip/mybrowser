//package cn.pompip.browser.util;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
///**
// * Rest客户端
// * @author xiejiong
// *
// */
//public class RestClient {
//	private ObjectMapper mapper = new ObjectMapper();
//	private ObjectMapper xmlmapper = new XmlMapper();
//	private RestTemplate client;
//	private RestTemplate jsonClient;
//
//	/**
//	 * 从Rest服务端获取对象，不支持泛型
//	 * @param url
//	 * @param clazz
//	 * @param urlVariables
//	 * @return
//	 */
//	public <T> T getForObject(String url, Class<T> clazz, Map<String, ?> urlVariables){
//		T obj = jsonClient.getForObject(url, clazz, urlVariables);
//		try {
//			return obj;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 从Rest服务端获取对象，不支持泛型
//	 * @param url
//	 * @param clazz
//	 * @param urlVariables
//	 * @return
//	 */
//	public <T> T getForObject(String url, Class<T> clazz, Object... urlVariables){
//		T obj = jsonClient.getForObject(url, clazz, urlVariables);
//		try {
//			return obj;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 从Rest服务端获取对象，支持泛型
//	 * @param url
//	 * @param valueType
//	 * @param urlVariables
//	 * @return
//	 */
//	public <T> T getForObject(String url, TypeReference<T> valueType, Map<String, ?> urlVariables){
//		String json = client.getForObject(url, String.class, urlVariables);
//		try {
//			return mapper.readValue(json, valueType);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 从Rest服务端获取对象，支持泛型
//	 * @param url
//	 * @param valueType
//	 * @param urlVariables
//	 * @return
//	 */
//	public <T> T getForObject(String url, TypeReference<T> valueType, Object... urlVariables){
//		String json = client.getForObject(url, String.class, urlVariables);
//		try {
//			return mapper.readValue(json, valueType);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public RestTemplate getClient() {
//		return client;
//	}
//
//	public void setClient(RestTemplate client) {
//		this.client = client;
//	}
//
//	public RestTemplate getJsonClient() {
//		return jsonClient;
//	}
//
//	public void setJsonClient(RestTemplate jsonClient) {
//		this.jsonClient = jsonClient;
//	}
//}
