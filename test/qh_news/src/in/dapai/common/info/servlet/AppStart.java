package in.dapai.common.info.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import in.dapai.common.info.dao.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class AppStart extends HttpServlet {
	private static final Log log = LogFactory.getLog(AppStart.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String device_id = req.getParameter("device_id");
		String mac = req.getParameter("mac");
		String data = req.getParameter("data");
		String userName = req.getParameter("username");
		log.debug("device_id: " + device_id);
		log.debug("mac: " + mac);
		log.debug("data: " + data);
		log.debug("username: " + userName);
		try {
			// [{"count":1,"pkg":"in.dapai.wlh","vc":"3"},{"count":1,"pkg":"in.dapai.ysz","vc":"51"},{"count":2,"pkg":"com.foxconn.ffkj","vc":"42"}]
			JSONArray ja = new JSONArray(data);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String pkg = jo.getString("pkg");
				String vc = jo.getString("vc");
				int countAdd = jo.getInt("count");
				DBHelper.insterOrUpdate(device_id, mac, userName, "start-" + pkg, pkg, vc, countAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		DBHelper.init();
	}
}
