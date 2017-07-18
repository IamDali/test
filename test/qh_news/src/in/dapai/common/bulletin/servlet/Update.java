package in.dapai.common.bulletin.servlet;

import in.dapai.common.bulletin.dao.DBHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Update extends HttpServlet {

	public Update() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String timeStr = request.getParameter("time");
		String filter = request.getParameter("filter");

		PrintWriter out = response.getWriter();
		if (title == null) {
			ServletBulletin.uptateMap();
			out.append("updateMap  ok ");
		} else {
			title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
			if (content != null)
				content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
			if (filter != null)
				filter = new String(filter.getBytes("ISO-8859-1"), "UTF-8");
			try {
				Date date = format.parse(timeStr);
				long t = date.getTime();
				int time = (int) (t / 1000);
				int re = DBHelper.insert(title, content, time, filter);
				if (re > 0) {
					ServletBulletin.uptateMap();
					out.append("updateMap  ok  :title:  " + title + "   content: " + content + "  time:" + timeStr + "  t:" + t + "  filter:" + filter);
				} else {
					out.append("数据库插入失败:title:  " + title + "   content: " + content + "  time:" + timeStr + "  t:" + t + "  filter:" + filter);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.append("error : " + e.getMessage());
			}
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		DBHelper.init();
	}

}
