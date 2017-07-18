package in.dapai.news.dao;

import in.dapai.news.model.Cover;
import in.dapai.news.model.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBHelper {
	private static final Log log = LogFactory.getLog(DBHelper.class);
	static String user = "r635r19b4526";
	static String password = "abcthm3b10";
	static String url = "jdbc:mysql://r635r19b4526.mysql.rds.aliyuncs.com:3306/r635r19b4526?useUnicode=true&characterEncoding=utf8&autoReconnect=true";// &autoReconnect=true&failOverReadOnly=false&maxReconnects=10
	// &autoReconnect=true&failOverReadOnly=false&maxReconnects=10 写了报错.不能连接
	static String driver = "com.mysql.jdbc.Driver";
	static Statement stmt;
	static Connection conn;

	public static class CoverDao {
		public static List<Cover> getAll(int size) {
			String sql = startStr + "SELECT id, id_title_news,time FROM tab_cover c  ORDER BY id DESC LIMIT " + size + endStr;
			return get(sql);
		}

		final static String startStr = "SELECT * FROM (", endStr = ")a ORDER BY id ASC;";

		private static List<Cover> get(String sql) {
			List<Cover> list = getUserFileBySql(sql);
			if (list == null || list.size() == 0)
				return null;
			int max = list.get(0).getId(), min = list.get(0).getId();

			for (int i = 0, n = list.size(); i < n; i++) {
				Cover c = list.get(i);
				max = max > c.getId() ? max : c.getId();
				min = min < c.getId() ? min : c.getId();
				c.setList(new ArrayList<News>());
			}

			sql = "SELECT id,id_cover,title,url,url_img FROM tab_news where id_cover BETWEEN " + min + " and " + max ;
			List<News> listNews = getNews(sql);

			for (int i = 0, n = listNews.size(); i < n; i++) {
				News news = listNews.get(i);
				for (int j = 0, m = list.size(); j < m; j++) {
					Cover c = list.get(j);
					if (c.getId() == news.getId_cover()) {
						c.getList().add(news);
						break;
					}
				}
			}
			return list;
		}

		/** 最新的 */
		public static List<Cover> getNew(int cur, int size) {//  
			String sql =  startStr +"SELECT id, id_title_news,time FROM tab_cover c WHERE id > " + cur + "  ORDER BY id DESC LIMIT " + size + endStr;
			return get(sql);
		}

		/** 早期的 */
		public static List<Cover> getOld(long cur, int size) {
			String sql =  startStr +"SELECT id, id_title_news,time FROM tab_cover c WHERE id < " + cur + "  ORDER BY id DESC LIMIT " + size + endStr;
			return get(sql);
		}

		private static List<News> getNews(String sql) {
			ResultSet rs = query(sql);
			if (rs == null)
				return null;
			List<News> result = null;
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				int n = rsmd.getColumnCount();
				System.out.println("count :" + n);
				News tmp;
				result = new ArrayList<News>();
				while (rs.next()) {
					tmp = new News();
					tmp.setId(rs.getInt("id"));
					tmp.setId_cover(rs.getInt("id_cover"));
					tmp.setTitle(rs.getString("title"));
					tmp.setUrl(rs.getString("url"));
					tmp.setUrlImg(rs.getString("url_img"));
					result.add(tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = null;
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			return result;
		}

		/**  */
		private static List<Cover> getUserFileBySql(String sql) {
			ResultSet rs = query(sql);
			if (rs == null)
				return null;
			List<Cover> result = null;
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				int n = rsmd.getColumnCount();
				System.out.println("count :" + n);
				Cover tmp;
				result = new ArrayList<Cover>();
				while (rs.next()) {
					tmp = new Cover();
					tmp.setId(rs.getInt("id"));
					tmp.setId_title_news(rs.getInt("id_title_news"));
					tmp.setTime(rs.getLong("time"));
					result.add(tmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = null;
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			return result;
		}
	}

	private static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			log.debug("查询语句:  " + sql);
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void close() {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			System.out.println("database closed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		if (conn != null) {
			System.out.println("database is init");
			return;
		}
		System.out.println("database initing");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
