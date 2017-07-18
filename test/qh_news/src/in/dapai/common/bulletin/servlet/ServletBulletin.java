package in.dapai.common.bulletin.servlet;

import in.dapai.common.bulletin.dao.DBHelper;
import in.dapai.common.bulletin.model.Bulletin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import sun.util.logging.resources.logging;

public class ServletBulletin extends HttpServlet {
	private static final Log log = LogFactory.getLog(ServletBulletin.class);

	public ServletBulletin() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public static void uptateMap() {
		DBHelper.init();
		mapBulletIdToListIndex = new HashMap<Integer, Integer>();
		List<Bulletin> list = DBHelper.getAllBulletin();
		listAll = list;
		for (int i = 0, n = list.size(); i < n; i++) {
			Bulletin b = list.get(i);
			Set<String> keySet = new HashSet<String>();
			try {
				if (b.getFilter() == null || b.getFilter().length() == 0 || b.getFilter().equals("[]")) {
					keySet.add("all");
				} else {
					JSONArray ja = new JSONArray(b.getFilter());
					for (int j = 0, m = ja.length(); j < m; j++) {
						JSONObject jo = ja.getJSONObject(j);
						String strKey = "";
						String appId = jo.optString("a");
						if (!appId.equals(""))
							strKey = "a:" + appId;
						String version = jo.optString("v");
						if (!version.equals(""))
							strKey = strKey + ",v:" + version;
						String channel = jo.optString("c");
						if (!channel.equals(""))
							strKey = strKey + ",c:" + channel;
						keySet.add(strKey);
					}
				}
				b.setKeySet(keySet);
				log.debug("鍏憡id:" + b.getId() + "keySet: " + keySet);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mapBulletIdToListIndex.put(b.getId(), i);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String appId = request.getParameter("appid");// 搴旂敤id
		String version = request.getParameter("version");// 鐗堟湰
		String channel = request.getParameter("channel");// 娓犻亾
		String sizeStr = request.getParameter("size");// 澶氬皯
		String curStr = request.getParameter("cur");// 褰撳墠
		log.debug("璇锋眰鏁版嵁: appid:" + appId + " version: " + version + " channel: " + channel + "  size:" + sizeStr + " cur: " + curStr);
		int size = 4;
		if (sizeStr != null) {
			size = Integer.parseInt(sizeStr);
			if (size <= 0 || size > 20)
				size = 4;
		}
		StringBuffer sb = null;
		List<Bulletin> res = null;
		int cur = 0;
		if (curStr == null) { // 涓嶅簲璇ュ嚭鐜板湪搴旂敤涓�杩欎釜搴旇浣滀负涓�釜json鏂囨湰鎻愪緵鍑哄幓
			cur = listAll.size() - 1;
		} else {
			// 鍚戝悗鐨勭炕椤�
			if (curStr != null) {
				cur = Integer.parseInt(curStr);
				if (mapBulletIdToListIndex.containsKey(cur))
					cur = mapBulletIdToListIndex.get(cur);
				else
					cur = 0;
				cur--;// 涓嶅寘鍚紶鍏ョ殑杩欎釜
			}
		}

		int count = 0;
		if (cur >= 0 && cur < listAll.size()) {
			res = new ArrayList<Bulletin>();
			for (int i = cur; i >= 0; i--) {
				Bulletin b = listAll.get(i);
				if (isMatch(b, appId, version, channel)) {
					res.add(b);
					count++;
				}
				if (count == size)
					break;
			}

			if (res == null) {
				sb = new StringBuffer("{code:0,msg:'鑾峰彇澶辫触'}");
			}
		} else {
			// cur 瓒呭嚭姝ｅ父鑼冨洿
			sb = new StringBuffer("{code:2,msg:'娌℃湁鏇村浜�}");
		}
		if (res != null) {
			sb = new StringBuffer("{code:1,list:[");
			for (int i = 0, n = res.size(); i < n; i++) {
				to(res.get(i), sb);
				if (i != n - 1)
					sb.append(",");
			}
			sb.append("]}");
		}
		out.print(sb.toString());
		out.flush();
		out.close();
	}

	private boolean isMatch(Bulletin b, String appId, String version, String channel) {
		Set<String> keySet = b.getKeySet();
		if (keySet.contains("all"))
			return true;
		if (keySet.contains("a:" + appId))
			return true;
		if (keySet.contains("a:" + appId + ",v:" + version))
			return true;
		if (keySet.contains("a:" + appId + ",c:" + channel))
			return true;
		if (keySet.contains("a:" + appId + ",v:" + version + ",c:" + channel))
			return true;
		return false;
	}
	 

	private void to(Bulletin b, StringBuffer sb) {
		int index = b.getContent().indexOf("http://");
		String content=b.getContent();
		//鐐虫涓嶈兘璁块棶杞箟鍚庣殑缃戝潃(鏂滄潬琚浆)
		//String content = null;
//		if (index != -1) {
//			try {
//				content = URLEncoder.encode(b.getContent().substring(+7), "UTF-8");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			content = "http://" + content;
//		} else
//			content = b.getContent();
		sb.append("{id:" + b.getId() + ",title:'" + b.getTitle() + "',content:'" + content + "',time:" + b.getTime()+"000" + ",tag:'" + b.getTag() + "'}");
	}

	public static Map<Integer, Integer> mapBulletIdToListIndex;

	public static List<Bulletin> listAll;

	public void init() throws ServletException {
		// DBHelper.init();
		uptateMap();
	}
}
