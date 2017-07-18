package in.dapai.ttwqp.server.servlet;

import in.dapai.common.constant.Keys;
import in.dapai.ttwqp.server.dao.DBCacheBaeRedis2;
import in.dapai.ttwqp.server.dao.DBCacheHashMap;
import in.dapai.ttwqp.server.dao.DBHelper;
import in.dapai.ttwqp.server.dao.IDBCache;
import in.dapai.ttwqp.server.model.App;
import in.dapai.ttwqp.server.model.Group;
import in.dapai.ttwqp.server.model.Patch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static in.dapai.ttwqp.server.dao.IDBCache.startKeyApk;
import static in.dapai.ttwqp.server.dao.IDBCache.startKeyPatch;

public class DBServlet extends BaseServlet {

	public DBServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//String cmd = request.getParameter(Keys.cmd);
		String cmd = request.getParameter(Keys.getImgCmd);
		String outStr = "updateDBcache  Ok~!鍒犻櫎  cmd:" + cmd;
		if (isNull(cmd)) {
			updateMap();
		} else {
			if (cmd.equals("help")) {
				outStr = "娓呴櫎澶╁ぉ鐜╂鐗屾柊闂荤殑http璇锋眰缂撳瓨 cmd涓�ttwqpnews    娓呴櫎鎵�湁redis缂撳瓨(鎵�湁鏈嶅姟鐨勯兘浼氭竻闄よ皑鎱庝娇鐢�  cmd涓�clearall";
			} else if (cmd.equals("ttwqpnews")) {

				IDBCache dbCache = new DBCacheBaeRedis2();
				Set<String> keys = dbCache.keys(IDBCache.startKeyHttpNews + "*");
				if (keys != null) {
					for (String key : keys) {
						dbCache.del(key);
					}
					outStr = "娓呴櫎澶╁ぉ鐜╂鐗屾柊闂籬ttp缂撳瓨 keys闀垮害:" + keys.size();
				} else {
					outStr = "娓呴櫎澶╁ぉ鐜╂鐗屾柊闂籬ttp缂撳瓨 keys涓虹┖";
				}

			} else if (cmd.equals("clearall")) {
				IDBCache dbCache = new DBCacheBaeRedis2();
				String result = dbCache.flushDB();
				outStr = "娓呴櫎redis鎵�湁key杩斿洖: " + result;
			}
		}
		out.append(outStr);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public static IDBCache DBcache;

	public static void updateMap() {
		DBcache = new DBCacheHashMap();
		// app
		List<Group> list = DBHelper.getGroup();
		App tmp;
		for (int i = 0, n = list.size(); i < n; i++) {
			Group group = list.get(i);
			List<App> apps = group.getList();
			for (int j = 0, m = apps.size(); j < m; j++) {
				tmp = apps.get(j);
				DBcache.put(startKeyApk + tmp.getId(), tmp);
			}
		}
		// 琛ヤ竵
		List<Patch> listpatch = DBHelper.getPatchs();
		if (listpatch != null) {
			Patch tmpPatch;
			for (int i = 0, n = listpatch.size(); i < n; i++) {
				tmpPatch = listpatch.get(i);
				DBcache.put(startKeyPatch + tmpPatch.getMd5Old(), tmpPatch);
			}
		}
	}

	public void init() throws ServletException {
		super.init();
		updateMap();
	}

}
