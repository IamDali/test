package in.dapai.ttwqp.server.dao;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class DBCacheBaeRedis2 extends IDBCache {
	Jedis jedis;
	static String host = "redis.duapp.com";
	static int port = 80;
	static String username = "NO73yF7DGF2PIoPSlMBABRy8";// app key
	static String password = "TZdldbK5Y8TOCaEyzWyLAO3s61L5WHkf";// secret
	static String databaseName = "JspofarQlnOQGslSWAPI";

	public DBCacheBaeRedis2() {
		connect();
		/* 至此连接已完全建立，就可对当前数据库进行相应的操作了 */
	}

	private void connect() {
		if (jedis != null) {
			jedis.disconnect();
			jedis = null;
		}
		jedis = new Jedis(host, port);
		jedis.connect();
		jedis.auth(username + "-" + password + "-" + databaseName);
	}

	public Object get(String key) {
		try {
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			return jedis.get(key);
		}
	}

	@Override
	public String flushDB() {
		try {
			return jedis.flushDB();
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			try {
				return jedis.flushDB();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}

	}

	@Override
	public Set<String> keys(String pattern) {
		try {
			return jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			try {
				return jedis.keys(pattern);
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public Long del(String... keys) {
		try {
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			try {
				return jedis.del(keys);
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	public void put(String key, Object obj) {
		try {
			jedis.set(key, obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			jedis.set(key, obj.toString());
		}
	}

	public void put(String key, int seconds, Object obj) {
		try {
			jedis.set(key, obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			connect();
			jedis.set(key, obj.toString());
		}
	}

	public void close() {
		if (jedis != null)
			jedis.shutdown();
	}

}
