package cn.pompip.browser.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RedisPropertiesFile
{
	private static String fileName = "redis.properties";
    private static HashMap hm = null;
    public static String getValue(String name)
    {  
    	String line = "";
    	String returnValue = "";
    	if (hm != null && hm.containsKey(name))
    	{
    		returnValue = (String)hm.get(name);
    		return returnValue;
    	}
    	try
		{
			InputStream file = RedisPropertiesFile.class.getClassLoader()
					.getResourceAsStream(fileName);
			InputStreamReader file1 = new InputStreamReader(file);
			BufferedReader br = new BufferedReader(file1);
			while ((line = br.readLine()) != null)
			{
				int index = line.indexOf("=");
				if (index == -1)
				{
					continue;
				}
				String key = line.substring(0, index).trim();
				String value = line.substring(index + 1).trim();
				if (hm == null)
				{
					hm = new HashMap();
				}
				hm.put(key, value);
			}
			br.close();
			file1.close();
			file.close();		
		}
    	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return (String)hm.get(name);
    }
    
    /**
     * 取属性文件信息
     * @param name	属性名称
     * @param fn 文件名
     * @return 属性值
     * @author suqing 2014-02-26
     */
    public static String getValue(String name, String fn)
    {  
    	return _getValue(name, fn, null);
    }
    
    /**
     * 取属性文件信息，属性文件为UTF-8编码
     * @param name	属性名称
     * @param fn 文件名
     * @return 属性值
     * @author suqing 2014-02-26
     */
    public static String getValue2(String name, String fn)
    {  
    	return _getValue(name, fn, "UTF-8");
    }
    
    /**
     * 取属性文件信息
     * @param name	属性名称
     * @param fn 文件名
     * @param encoding 属性文件编码
     * @return 属性值
     * @author suqing 2014-02-26
     */
    private static String _getValue(String name, String fn, String encoding)
    {  
    	String line = "";
    	String returnValue = "";
    	if (hm != null && hm.containsKey(name))
    	{
    		returnValue = (String)hm.get(name);
    		return returnValue;
    	}
    	try
    	{
    		InputStream file = RedisPropertiesFile.class.getClassLoader()
    		.getResourceAsStream(fn);
    		InputStreamReader file1=null;
    		if(encoding==null){
    			file1 = new InputStreamReader(file);
    		}else{
    			file1 = new InputStreamReader(file, encoding);
    		}
    		BufferedReader br = new BufferedReader(file1);
    		while ((line = br.readLine()) != null)
    		{
    			int index = line.indexOf("=");
    			if (index == -1)
    			{
    				continue;
    			}
    			String key = line.substring(0, index).trim();
    			String value = line.substring(index + 1).trim();
    			if (hm == null)
    			{
    				hm = new HashMap();
    			}
    			hm.put(key, value);
    		}
    		br.close();
    		file1.close();
    		file.close();		
    	}
    	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return (String)hm.get(name);
    }
    
    /**
     * for test
     * @param args
     * @throws Exception
     */
    public static void main(String []args)throws Exception
    {
    	String a =RedisPropertiesFile.getValue("TMS_DEBUG");
    	System.out.println(a);
    }
}
