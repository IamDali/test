package in.dapai.ttwqp.server.servlet;

import in.dapai.common.util.UtilsCommon;
import in.dapai.common.constant.Keys;
import in.dapai.ttwqp.server.dao.DBCacheBaeRedis2;
import in.dapai.ttwqp.server.dao.DBCacheHashMap;
import in.dapai.ttwqp.server.dao.IDBCache;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Gift extends BaseServlet {
	private static final Log log = LogFactory.getLog(Gift.class);
	final int chaoshi = 5 * 60;
	public static String url = "http://ddz.dapai.in:8005/updateLoginScore";// updateLoginScore
	public static String keySign = "VYdHcG5Yutt6XlF78TgnTUsIc6Y";// 与粒仔通信的密钥

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String cs = req.getCharacterEncoding();
		System.out.println(cs);
		// req.setCharacterEncoding("UTF-8");
		//String cmd = req.getParameter(Keys.cmd);
		String cmd = req.getParameter(Keys.getImgCmd);
		String userName = req.getParameter(Keys.userName);
		String pwd = req.getParameter(Keys.password);
		PrintWriter out = resp.getWriter();
		if (cmd.equals("open")) {
			Object lastTimeObj = dbCache.get(KeyStartOpenLastTime + userName);
			int lastTime = 0;
			if (lastTimeObj != null)
				lastTime = Integer.parseInt(lastTimeObj.toString());
			int curTime = (int) (System.currentTimeMillis() / 1000);
			int timeC = curTime - lastTime;
			if (timeC > chaoshi) {// 5 * 60
				dbCache.put(KeyStartOpenLastTime + userName, chaoshi, curTime + "");
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", userName);
				params.put("time", curTime + "000");
				//params.put("sign", UtilsCommon.authorization(keySign + userName + curTime + "000").toLowerCase());
				String result = http(url, params);
				out.print(result);
				log.info("获取的userName: " + userName + "  查询到的时间:" + lastTime + "  时间差:" + timeC + "   请求     结果:" + result);
			} else {
				String result = "{status:0,msg:'请勿频繁领取'}";
				out.print(result);
				log.info("获取的userName: " + userName + "  查询到的时间:" + lastTime + "  时间差:" + timeC + "   忽略  返回:" + result);
				// 忽略
			}
		}
		out.flush();
		out.close();
	}

	final String KeyStartOpenLastTime = "openlasttime";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void init() throws ServletException {
		super.init();
		try {
			dbCache = new DBCacheBaeRedis2();
			log.info("创建DBCacheBaeRedis2缓存");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("创建DBCacheBaeRedis2数据缓存失败, 将使用 DBCacheHashMap做数据缓存");
			dbCache = new DBCacheHashMap();
		}
	};

	IDBCache dbCache;

}
