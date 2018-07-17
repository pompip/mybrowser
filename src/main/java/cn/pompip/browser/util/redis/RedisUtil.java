package cn.pompip.browser.util.redis;


import cn.pompip.browser.util.json.JSONUtil;

import java.util.Map;

public class RedisUtil {
	
	public static ShardedRedis getShardedRedis(int uid)
	{
		return new ShardedRedis(uid);
	}
	
	public static ShardedRedis getShardedRedis(String uid)
	{
		return new ShardedRedis(Integer.parseInt(uid));
	}
	
	public static LogRedis getLogRedis()
	{
		return new LogRedis();
	}
	
	public static CacheRedis getCacheRedis()
	{
		return new CacheRedis();
	}
	
	public static void rouletteHorn(int uid ,String content)
	{
		Map playerMap=getShardedRedis(uid).hmget("hu:"+uid, "nickName","sex","vlevel");
		playerMap.put("to_uid", uid);
		playerMap.put("cmd", 9006);
		playerMap.put("type", 2);
		playerMap.put("name", "系统");
		playerMap.put("body", content);
		playerMap.put("tag", "");
		
		getLogRedis().publish("speaker", JSONUtil.toJson(playerMap));
	}
	
	public static void horn(int uid,int addrmb,Map map) {
		int[] uids=new int[1];
		uids[0]=uid;
		map.put("to_uid", uids);
		map.put("addrmb", addrmb);
		map.put("cmd", 9007);
		getLogRedis().publish("speaker", JSONUtil.toJson(map));
	}
	
	public static void horn(String uid ,String content)
	{
		Map playerMap=getShardedRedis(uid).hmget("hu:"+uid, "nickName","sex","vlevel");
		playerMap.put("to_uid", uid);
		playerMap.put("cmd", 9006);
		playerMap.put("type", 1);
		playerMap.put("name", "系统");
		playerMap.put("body", content);
		playerMap.put("tag", "");
		getLogRedis().publish("speaker", JSONUtil.toJson(playerMap));
	}
	
	public static String getConfigValue(String key)
	{
		return getCacheRedis().hget("sys:server", key);
	}
	
	public static void main(String[] args) {

		
		Map playerMap=getShardedRedis(50065019).hmget("hu:"+50065019, "nickName","sex","vlevel");
		playerMap.put("uid", 50065019);
		playerMap.put("cmd", 4006);
		playerMap.put("name", "系统");
		playerMap.put("sex", Integer.parseInt(playerMap.get("sex").toString()));
		playerMap.put("str", "再来打老虎果断充值648元成长为VIP6,可以领取VIP6尊贵会员礼包，众人投来羡慕的目光，土豪我们做朋友吧！");
		playerMap.put("tag", "");
		getLogRedis().publish("zjh_speaker", JSONUtil.toJson(playerMap));
		
		//System.out.println(50057050+" "+RedisUtil.getShardedRedis(50057050).del("50057050"));
		/*int[] sss={50057050,50006950,50006320,50050022,50016472,50028473,50013154,50005704,50052384,50010755,50008165,50051496,50041296,50053066,50006856,50051156,50041917,50056657,50007687,50016017,50013997,50050408,50027239};
		for (int i : sss) {
			System.out.println(i+" "+RedisUtil.getShardedRedis(i).del(i));
		}*/
		/*for (int i = 0; i < 10; i++) {
		Set<String> keys=RedisUtil.getShardedRedis(i).keys("hu:*");
		for (String key:keys) {
				if(RedisUtil.getShardedRedis(i).hget(key, "tree_exp")==null)
				{
					RedisUtil.getShardedRedis(i).hset(key, "tree_exp", "0");
				}
			
				{
					RedisUtil.getShardedRedis(i).hset(key, "tree_last_award_time", "0");
				}
				System.out.println(RedisUtil.getShardedRedis(i).hget(key, "name"));
		}
	}*/
		
	}
}
