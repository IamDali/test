package in.dapai.ttwqp.server.dao;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.OperationFuture;

public class DBCacheMem extends IDBCache {
	public DBCacheMem() {
		if (mc == null) {
			try {
				// 指定验证机制，推荐PLAIN，
				// 部分客户端存在协议BUG，只能使用PLAIN协议(PlainCallbackHandler)
				AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler("be7d4113992e11e3", "abcthm3b10_")); // 7779_da27
				// 用户名，密码
				mc = new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(Protocol.BINARY) // 指定使用Binary协议
						.setOpTimeout(1000)// 设置超时时间为100ms
						.setAuthDescriptor(ad).build(), AddrUtil.getAddresses("be7d4113992e11e3.m.cnhzalicm10pub001.ocs.aliyuncs.com:11211")); // 访问地址
				// OperationFuture future = mc.set("Hello", 0, "OCS"); //
				// 异步接口，注意！返回的是Future
				// future.get(); // future.get() 确保之前(mc.set())操作已经结束
				// System.out.println(mc.get("Hello"));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// if (mc != null)
				// mc.shutdown(); // 关闭，释放资源
			}
		}
	}

	public Object get(String key) {
		return mc.get(key);
	}

	public void put(String key, Object obj) {
		mc.set(key, 0, obj);
	}

	MemcachedClient mc;

	public void put(String key, int exp, Object obj) {
		mc.set(key, exp, obj);
	}

	public void close() {
		if(mc!=null){
			mc.shutdown();
		}
	}

}
