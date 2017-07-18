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

public class PcCenterUpdate extends BaseServlet {
	private static final Log log = LogFactory.getLog(PcCenterUpdate.class);
	final int chaoshi = 5 * 60;
	public static String url = "http://ddz.dapai.in:8005/updateLoginScore";// updateLoginScore
	public static String keySign = "VYdHcG5Yutt6XlF78TgnTUsIc6Y";// 与粒仔通信的密钥

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		//String cmd = req.getParameter(Keys.cmd);
		PrintWriter out = resp.getWriter();
		out.print("{v:'v2313',u:'http://192.168.1.232:8081/a.exe'}");
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
