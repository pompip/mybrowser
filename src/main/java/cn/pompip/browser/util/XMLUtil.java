//package cn.pompip.browser.util;
//
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class XMLUtil {
//	public static Object toObject(String xml, Class clazz) {
//		ObjectMapper xmlmapper = new XmlMapper();
//		Object obj = null;
//		try {
//			xmlmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			obj = xmlmapper.readValue(xml, clazz);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}
//
//	public static List<?> toObjects(String xml, Class clazz) throws InstantiationException, IllegalAccessException {
//		ArrayList result = new ArrayList();
//		ArrayList ls = (ArrayList) toObject(xml, ArrayList.class);
//		if (null != ls) {
//			Field[] fields = clazz.getDeclaredFields();
//			for(Object o : ls){
//				Object obj = clazz.newInstance();
//				Map map = (Map) o;
//				for(Field f : fields){
//					if(null!=map.get(f.getName())){
//						f.setAccessible(true);
//						f.set(obj, map.get(f.getName())); // 这边会报错，需要处理类型
//					}
//				}
//				result.add(obj);
//			}
//		}
//		return result;
//	}
//
//	public static void main(String[] args) {
//		/*String xml = "<Areas><Area><code>GD</code><name>广东</name><enable>true</enable><id>1</id><parent/></Area><Area><code>GZ</code><name>广州</name><enable>true</enable><id>2</id><parent><code>GD</code><name>广东</name><enable>true</enable><id>0</id></parent></Area><Area><code>SZ</code><name>深圳</name><enable>true</enable><id>3</id><parent><code>GD</code><name>广东</name><enable>true</enable><id>0</id></parent></Area><Area><code>FJ</code><name>福建</name><enable>true</enable><id>4</id><parent/></Area><Area><code>FZ</code><name>福州</name><enable>true</enable><id>5</id><parent><code>FJ</code><name>福建</name><enable>true</enable><id>0</id></parent></Area><Area><code>XM</code><name>厦门</name><enable>true</enable><id>6</id><parent><code>FJ</code><name>福建</name><enable>true</enable><id>0</id></parent></Area></Areas>";
//		List ls=null;
//		try {
//			ls = toObjects(xml, AreaBean.class);
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(null!=ls ? ls.get(0):null);
//
//		String bjXML = toXML(null);
//		System.out.println(bjXML);
//		AreaBean bj = (AreaBean) toObject(bjXML, AreaBean.class);
//		System.out.println(bj.getName());*/
//	}
//
//	public static String toXML(Object obj) {
//		XmlMapper xml = new XmlMapper();
//
//		try {
//			if (obj instanceof List || obj instanceof Map) {
//				return xml.writeValueAsString(obj);
//			} else {
//				StringWriter sw = new StringWriter();
//				xml.writeValue(sw, obj);
//				return sw.toString();
//			}
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
