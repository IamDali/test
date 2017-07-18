package in.dapai.news;

import in.dapai.news.dao.DBHelper;
import in.dapai.news.model.Cover;
import in.dapai.news.model.News;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletGetNews extends HttpServlet {
	private static final Log log = LogFactory.getLog(ServletGetNews.class);
	public ServletGetNews() {
		super();
	}

	public void destroy() {
		DBHelper.close();
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String outStr = "{code:0,msg:'获取失败'}";
		PrintWriter out = response.getWriter();
		String cmd = request.getParameter("cmd");
		String channel=request.getParameter("channel");
		int size = 5;
		List<Cover> list ;
		if(channel!=null)
			log.debug("channel:"+channel);
		if (cmd != null) {
			log.debug("收到的命令:cmd:"+cmd);
			String curStr = request.getParameter("cur");
			if (curStr != null) {
				int cur = Integer.parseInt(curStr);
				log.debug("cur:"+cur);
				
				if (cmd.equals("new")) {
					list = DBHelper.CoverDao.getNew(cur, size);
					outStr = d(list);
				} else if (cmd.equals("old")) {
					list = DBHelper.CoverDao.getOld(cur, size);
					outStr = d(list);
				} else {
					outStr = "{code:0,msg:'获取失败'}";
				}
			}else{
				log.debug("收到命令,但是未收到 cur参数");
				outStr = "{code:0,msg:'获取失败参数不完整'}";
			}

		} else {// 实时
			try {
				list=DBHelper.CoverDao.getAll(size);
				outStr = d(list);
			} catch (Exception e) {
				e.printStackTrace();
				outStr = "{code:0,msg:'获取失败'" + "}";
			}

		}
		out.print(outStr);
		out.flush();
		out.close();
	}

	private String d(List<Cover> list) {
		String result;
		if (list == null || list.size() == 0) {
			result = "{code:2,msg:'没有更多数据'}";
		} else {
			result = "{code:1," + strByCover(list) + "}";
		}
		return result;
	}

	private String strByCover(List<Cover> list) {
		StringBuffer sbCoverIds = new StringBuffer("ids:[");
		StringBuffer sbCoverTieleNewIds = new StringBuffer(",titleid:[");
		StringBuffer times = new StringBuffer(",times:[");
		StringBuffer news = new StringBuffer(",news:[");

		// coverids:[6,5],covertid:[1,2],times:[12312,31321],news[{ids:[1,3],titles["news1","news3"],urls},{}]
		Cover c;
		for (int i = 0, n = list.size(); i < n; i++) {
			c = list.get(i);
			if (i == n - 1) {// 最后一个
				sbCoverIds.append(c.getId() + "]");
				sbCoverTieleNewIds.append(c.getId_title_news() + "]");
				times.append(c.getTime() + "]");
				news.append("{" + strNews(c.getList()) + "}]");

			} else {
				sbCoverIds.append(c.getId() + ",");
				sbCoverTieleNewIds.append(c.getId_title_news() + ",");
				times.append(c.getTime() + ",");
				news.append("{" + strNews(c.getList()) + "},");
			}
		}
		return sbCoverIds.toString() + sbCoverTieleNewIds.toString() + times.toString() + news.toString();
	}

	private String strNews(List<News> list) {
		StringBuffer ids = new StringBuffer("ids:[");
		StringBuffer titles = new StringBuffer(",titles:[");
		StringBuffer urls = new StringBuffer(",urls:[");
		StringBuffer urlImgs = new StringBuffer(",urlimgs:[");
		// coverids:[6,5],covertid:[1,2],times:[12312,31321],news[{ids:[1,3],titles["news1","news3"],urls},{}]
		News news;
		for (int i = 0, n = list.size(); i < n; i++) {
			news = list.get(i);
			if (i == n - 1) {// 最后一个
				ids.append(news.getId() + "]");
				titles.append("'" + news.getTitle() + "']");
				urls.append("'" + news.getUrl() + "']");
				urlImgs.append("'" + news.getUrlImg() + "']");
			} else {
				ids.append(news.getId() + ",");
				titles.append("'" + news.getTitle() + "',");
				urls.append("'" + news.getUrl() + "',");
				urlImgs.append("'" + news.getUrlImg() + "',");
			}
		}
		return ids.toString() + titles.toString() + urls.toString() + urlImgs.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		DBHelper.init();

	}

}
