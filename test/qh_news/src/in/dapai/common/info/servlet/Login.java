package in.dapai.common.info.servlet;

import in.dapai.common.bulletin.servlet.ServletBulletin;
//import in.dapai.common.constant.Keys;
import in.dapai.common.info.dao.DBHelper;
import in.dapai.common.info.model.LoginInfo;
import in.dapai.util.HttpUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Login extends HttpServlet {
	private static final Log log = LogFactory.getLog(Login.class);

	public static void main(String[] args) {
		System.out.println(0.1 * 1e6);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userName = req.getParameter("username");
		userName = getStr(userName);
		String md5Apk = req.getParameter("md5");
		String channel = req.getParameter("channel");
		String pkg = req.getParameter("pkg");
		String vc = req.getParameter("vc");
		String vn = req.getParameter("vn");
		String latStr = req.getParameter("lat");
		String lngStr = req.getParameter("lng");

		String address = req.getParameter("address");
		address = getStr(address);
		String area = req.getParameter("area");
		String device_id = req.getParameter("device_id");// 灏辨槸IMEI
		String imsi = req.getParameter("imsi");
		String mac = req.getParameter("mac");
		String network = req.getParameter("network");
		String operator = req.getParameter("operator");
		operator = getStr(operator);

		String model = req.getParameter("model");
		model = getStr(model);
		String release = req.getParameter("release");
		String phone_number = req.getParameter("phone_number");
		String ip = HttpUtil.getIp(req);
		log.debug("userName : " + userName);
		log.debug("md5Apk : " + md5Apk);
		log.debug("channel : " + channel);
		log.debug("pkg : " + pkg);
		log.debug("vc : " + vc);
		log.debug("vn : " + vn);
		log.debug("latStr : " + latStr);
		log.debug("lngStr : " + lngStr);
		log.debug("address : " + address);
		log.debug("area : " + area);
		log.debug("device_id : " + device_id);
		log.debug("imsi : " + imsi);
		log.debug("mac : " + mac);
		log.debug("network : " + network);
		log.debug("operator : " + operator);
		log.debug("model : " + model);
		log.debug("release : " + release);
		log.debug("phone_number : " + phone_number);
		log.debug("ip : " + ip);

		LoginInfo info = new LoginInfo();
		info.userName = userName;
		info.md5 = md5Apk;
		info.channel = channel;
		info.pkg = pkg;
		info.versionCode = vc;
		info.versionName = vn;
		info.latitude = strToInt(latStr);
		info.longitude = strToInt(lngStr);
		info.address = address;
		info.area = area;
		info.deviceId = device_id;
		info.imsi = imsi;
		info.mac = mac;
		info.model = model;
		info.network = network;
		info.operator = operator;
		info.release = release;
		info.phoneNumber = phone_number;
		info.ip = ip;
		
		info.date=(int)(System.currentTimeMillis()/1000);
		DBHelper.inster(info);
		
		PrintWriter out = resp.getWriter();
		out.close();
		
	}

	private String getStr(String str) {
		return str;// 浜笢闇�杞� 鐧惧害,闃块噷涓嶉渶瑕�
//		if (str != null && !str.equals("")) {
//			try {
//				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
		//return str;
	}

	private int strToInt(String lat) {
		if (lat.indexOf(".") == -1) {
			// 涓嶆槸灏忔暟
			return Integer.parseInt(lat);
		} else {
			float f = Float.parseFloat(lat);
			int z = (int) (f * 1e6);
			return z;
		}
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
