//package in.dapai.ttwqp.server.dao;
//
//import com.baidu.bae.api.factory.BaeFactory;
//import com.baidu.bae.api.memcache.BaeCache;
//
//public class DBCacheBaeMem implements IDBCache {
//	static String cacheId = "DMdvpLjkZnqAXmzryQKv";
//
//	public static String memcacheAddr = "cache.duapp.com:20243";
//	static String user = "NO73yF7DGF2PIoPSlMBABRy8";
//	static String password = "TZdldbK5Y8TOCaEyzWyLAO3s61L5WHkf";
//	BaeCache memcache;
//
//	public DBCacheBaeMem() {
//		memcache = BaeFactory.getBaeCache(cacheId, memcacheAddr, user, password);
//		memcache.setConnectTimeout(10);
//	}
//
//	public void close() {
//
//	}
//
//	public Object get(String key) {
//		return memcache.get(key);
//	}
//
//	public void put(String key, Object obj) {
//		memcache.set(key, obj);
//	}
//
//	public void put(String key, int exp, Object obj) {
//		memcache.set(key, obj, exp);
//	}
//}
