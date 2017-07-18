package in.dapai.ttwqp.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.read.apk.AnalysisApk;
import com.read.apk.ApkInfo;

public class Update extends HttpServlet {

	public Update() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
	 
		PrintWriter out = response.getWriter();
		String cmd = request.getParameter("cmd");
		if (cmd != null) {
			if (cmd.equals("apkinfo")) {
					String urlApk=request.getParameter("urlApk");
					ApkInfo apkinfo=getApkInfoByApkFileUrl(urlApk);
					out.print(apkinfo);
			}
		}
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
	}

	private ApkInfo getApkInfoByApkFileUrl(String url) {
		String path = url;// "http://192.168.1.233:8082/qh_wlh_tu-0.5.1.apk";
		HttpURLConnection con = null;
		InputStream is = null;
		URL u = null;
		ApkInfo apkInfo = null;
		try {
			u = new URL(path);
			con = (HttpURLConnection) u.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			is = con.getInputStream();
			apkInfo = AnalysisApk.unZip(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.disconnect();
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return apkInfo;
	}
}
