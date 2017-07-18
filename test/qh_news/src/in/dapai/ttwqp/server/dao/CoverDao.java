package in.dapai.ttwqp.server.dao;

import in.dapai.news.model.Cover;
import in.dapai.news.model.News;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoverDao {
	Connection conn;

	public CoverDao(Connection conn) {
		this.conn = conn;
	}

	public List<Cover> getAll(int size) {
		String sql = startStr + "SELECT id, id_title_news,time FROM ttwqp_news_cover c  ORDER BY id DESC LIMIT " + size + endStr;
		return get(sql);
	}

	final static String startStr = "SELECT * FROM (", endStr = ")a ORDER BY id DESC;";

	private List<Cover> get(String sql) {
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

		sql = "SELECT id,id_cover,title,url_content,url_icon FROM ttwqp_news_item where id_cover BETWEEN " + min + " and " + max;
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
	public List<Cover> getNew(int cur, int size) {//  
		String sql = startStr + "SELECT id, id_title_news,time FROM ttwqp_news_cover c WHERE id > " + cur + "  ORDER BY id DESC LIMIT " + size + endStr;
		return get(sql);
	}

	/** 早期的 */
	public List<Cover> getOld(long cur, int size) {
		String sql = startStr + "SELECT id, id_title_news,time FROM ttwqp_news_cover c WHERE id < " + cur + "  ORDER BY id DESC LIMIT " + size + endStr;
		return get(sql);
	}

	private List<News> getNews(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			rs = null;
		}
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
				tmp.setUrl(rs.getString("url_content"));
				tmp.setUrlImg(rs.getString("url_icon"));
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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**  */
	private List<Cover> getUserFileBySql(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			rs = null;
		}
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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
