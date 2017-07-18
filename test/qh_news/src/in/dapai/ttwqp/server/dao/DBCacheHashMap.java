package in.dapai.ttwqp.server.dao;

import java.util.HashMap;
import java.util.Map;

public class DBCacheHashMap extends IDBCache {
	private Map<String, Object> map = new HashMap<String, Object>();

	public Object get(String key) {
		return map.get(key);
	}

	public void put(String key, Object obj) {
		map.put(key, obj);
	}

	public void put(String key, int exp, Object obj) {
		put(key, obj);
	}

	public void close() {
		
	}
}
