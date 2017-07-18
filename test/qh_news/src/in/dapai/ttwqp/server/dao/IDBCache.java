package in.dapai.ttwqp.server.dao;

import java.util.Set;

public abstract class IDBCache {
	public static final String startKeyApk = "apk_";
	public static final String startKeyPatch = "patch_";
	public static final String startKeyHttpNews = "http_news_";// 新闻的http请求缓存

	public abstract void put(String key, Object obj);

	public abstract Object get(String key);

	public abstract void put(String key, int exp, Object obj);

	public abstract void close();

	public   Set<String> keys(String pattern){return null;}
	public Long del(String... keys){
		return null;
	}
	public String flushDB(){
		return null;
	}
}
