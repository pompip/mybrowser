package cn.pompip.browser.util.Cache;

import java.util.HashMap;
import java.util.Map;

/***
 * 
 * @author xiejiong
 *
 */
public class LocalCache {
	 //缓存Map  
    private static Map<String,CacheContent> map = new HashMap<>();  
    private static  LocalCache localCache = null;  
  
    private LocalCache(){  
    }  
  
    public Object getLocalCache(String key) {  
        CacheContent cc = map.get(key);  
  
        if(null == cc) {  
            return null;  
        }  
  
        long currentTime = System.currentTimeMillis();  
  
        if(cc.getCacheMillis() > 0 && currentTime - cc.getCreateTime() > cc.getCacheMillis()) {  
            //超过缓存过期时间,返回null  
            map.remove(key);  
            return null;  
        } else {  
            return cc.getElement();  
        }  
    }  
  
    public void setLocalCache(String key,int value,int cacheMillis) {  
        long currentTime = System.currentTimeMillis();  
        CacheContent cc = new CacheContent(cacheMillis,value,currentTime);  
        map.put(key,cc);  
    }  
    
    public void removeLocalCache(String key) {  
    	map.remove(key);  
    }  
    
  
    public static LocalCache getInStance(){  
    	if(localCache==null)
    	{
    		localCache = new LocalCache();  
    	}
        return localCache;  
    }  
  
  
    class CacheContent{  
        // 缓存生效时间  
        private  int cacheMillis;  
        // 缓存对象  
        private Object element;  
        // 缓存创建时间  
        private long createTime ;
        public CacheContent(int cacheMillis,Object element,long createTime)
        {
        	this.cacheMillis = cacheMillis;
        	this.element = element;
        	this.createTime =createTime;
        }
		public int getCacheMillis() {
			return cacheMillis;
		}
		public void setCacheMillis(int cacheMillis) {
			this.cacheMillis = cacheMillis;
		}
		public long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
		public Object getElement() {
			return element;
		}
		public void setElement(Object element) {
			this.element = element;
		}
		
    }  
}
