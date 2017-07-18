package in.dapai.ttwqp.server.dao;

//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.exceptions.JedisConnectionException;
//
//public class DBCacheBaeRedis implements IDBCache {
//	static String host = "redis.duapp.com";
//	static int port = 80;
//	static String username = "NO73yF7DGF2PIoPSlMBABRy8";// app key
//	static String password = "TZdldbK5Y8TOCaEyzWyLAO3s61L5WHkf";// secret
//	static String databaseName = "JspofarQlnOQGslSWAPI";
//
//	static JedisPool pool;
//	static {
//		
//
//	}
//
//	public DBCacheBaeRedis() {
//		 
//		/* 鑷虫杩炴帴宸插畬鍏ㄥ缓绔嬶紝灏卞彲瀵瑰綋鍓嶆暟鎹簱杩涜鐩稿簲鐨勬搷浣滀簡 */
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxActive(10);// 涓嶇煡閬撹繖涓缃槸涓嶆槸鎺у埗鏈�ぇ杩炴帴鏁伴噺鐨勫鏋滄槸 ,閭ｄ箞bae瑙勫畾鏈�ぇ涓�0
//		config.setMaxIdle(5);
//		// config.setMinIdle(10);
//		config.setMaxWait(1000L);
//		config.setTestOnBorrow(true);
//		config.setTestOnReturn(true);
//		pool = new JedisPool(config, host, port);
//	}
//
//	public Object get(String key) { 
//		Object result = null;
//		Jedis jee = getJedis();
//		if (jee != null) {
//			try {
//				jee.auth(username + "-" + password + "-" + databaseName);
//				// stationList=jee.lrange("trps:lineinfo", 0, -1);
//				result = jee.get(key);
//			} catch (Exception e) {
//				pool.returnBrokenResource(jee);
//			} finally {
//				if (null != jee) {
//					pool.returnResource(jee);
//				}
//			}
//		}
//
//		return result;
//	}
//
//	public void put(String key, Object obj) {
//		// jedis.setex(key, 0, obj.toString());
//
//		Jedis jee = getJedis();
//		if (jee != null) {
//			
//			try {
//				jee.auth(username + "-" + password + "-" + databaseName);
//				jee.set(key, obj.toString());
//			} catch (Exception e) {
//				pool.returnBrokenResource(jee);
//			} finally {
//				if (null != jee) {
//					pool.returnResource(jee);
//				}
//			}
//		}
//	}
//
//	public void put(String key, int seconds, Object obj) {
//		// jedis.setex(key, seconds, obj.toString());
//		Jedis jee = getJedis();
//		if (jee != null) {
//			try {
//				jee.auth(username + "-" + password + "-" + databaseName);
//				jee.set(key, obj.toString());
//			} catch (Exception e) {
//				pool.returnBrokenResource(jee);
//			} finally {
//				if (null != jee) {
//					pool.returnResource(jee);
//				}
//			}
//		}
//
//	}
//
//	public void close() {
//		 
//	}
//
//	// static Jedis jedis = null; //new Jedis(redisaddr, redisport);
//	private Jedis getJedis() {
//		Jedis jee = null;
//		try {
//			jee = pool.getResource();
//		} catch (JedisConnectionException e) {
//			pool.returnBrokenResource(jee);
//		}
//		return jee;
//	}
//
//}
