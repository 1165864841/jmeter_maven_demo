package jmeterLib;

import redis.clients.jedis.Jedis;


/**
 * 
 * TODO：redis缓存读取数据
 *
 * @author Joe-Tester
 * @time 2021年5月28日
 * @file HandleRedis.java
 */
public class HandleRedis {

	private Jedis redis;

	/**
	 * 定义一个获取redis-key的值的方法
	 * 
	 * @param serverip
	 * @param port
	 * @param passwd
	 * @param dbnum
	 * @param key
	 * @return
	 */
	public String getValueOfKey(String serverip, int port, String passwd,
			int dbnum, String key) {
		// 创建redis连接
		redis = new Jedis(serverip, port);
		// 密码认证
		redis.auth(passwd);
		// 选择db
		redis.select(dbnum);
		// redis.flushDB();选择redis数据库之后清除缓存，flushAll()清除redis所有缓存
		// getkey的值
		String v = redis.get(key);
		// 关闭连接
		if (v == null) {
			redis.close();
			return "redis查询为空";
		} else {
			redis.close();
		}
		// 并返回值
		return v;
	}

	public static void main(String[] args) {
		HandleRedis res = new HandleRedis();
		String red = res.getValueOfKey("47.107.254.16", 6016, "hcpdev0327", 10,
				"phone:13800013800");
		System.out.println(red);
		@SuppressWarnings("unused")
		int[] ask={1,2,3,4};
	}
}
