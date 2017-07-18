package in.dapai.common.bulletin.dao;

import in.dapai.common.bulletin.model.Bulletin;
import in.dapai.ttwqp.server.model.App;

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
import org.omg.CORBA.Request;

public class DBHelper {
	private static final Log log = LogFactory.getLog(DBHelper.class);
	static String user = "r635r19b4526";
	static String password = "abcthm3b10";
	static String url = "jdbc:mysql://r635r19b4526.mysql.rds.aliyuncs.com:3306/r635r19b4526?useUnicode=true&characterEncoding=utf8&autoReconnect=true";// &autoReconnect=true&failOverReadOnly=false&maxReconnects=10
	// &autoReconnect=true&failOverReadOnly=false&maxReconnects=10 写了报错.不能连接
	static String driver = "com.mysql.jdbc.Driver";
	static Statement stmt;
	static Connection conn;

	/** 获取最新公告 */
	public static List<Integer> getBulletinIdsByFilter(String appId, String version, String channel, int size) {
		String sql = "SELECT id_bulletin FROM common_bulletin_fen WHERE id_app= " + appId + " AND version='" + version + "' AND channel='" + channel + "' ORDER BY id_bulletin DESC LIMIT " + size;
		return getBulletinIdsBySql(sql);
	}

	/** 获取bulletin 公告老于 cur */
	public static List<Integer> getBulletinIdsByFilter(String appId, String version, String channel, int size, int cur) {
		String sql = "SELECT id_bulletin FROM common_bulletin_fen WHERE id_bulletin < " + cur + " AND id_app= " + appId + " AND version='" + version + "' AND channel='" + channel + "' ORDER BY id_bulletin DESC LIMIT " + size;
		return getBulletinIdsBySql(sql);
	}

	public static List<Integer> getBulletinIdsBySql(String sql) {
		log.debug("查询语句:  " + sql);
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
		List<Integer> result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			System.out.println("count :" + n);
			result = new ArrayList<Integer>();
			while (rs.next()) {
				result.add(rs.getInt("id_bulletin"));
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

	public static List<Bulletin> getAllBulletin() {
		String sql = "SELECT id,title,content,time,tag,filter FROM common_bulletin";
		log.debug("查询语句:  " + sql);
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
		List<Bulletin> result = null;
		try {
			Bulletin tmp;
			result = new ArrayList<Bulletin>();
			while (rs.next()) {
				tmp = fullBulletin(rs);
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

	private static Bulletin fullBulletin(ResultSet rs) {
		Bulletin bulletin = new Bulletin();
		try {
			bulletin.setId(rs.getInt("id"));
			bulletin.setTitle(rs.getString("title"));
			bulletin.setContent(rs.getString("content"));
			bulletin.setTag(rs.getString("tag"));
			bulletin.setTime(rs.getInt("time"));
			bulletin.setFilter(rs.getString("filter"));
		} catch (Exception e) {
			e.printStackTrace();
			bulletin = null;
		}
		return bulletin;
	}

	private static ResultSet query(String sql, Statement stmt) {
		ResultSet rs = null;
		try {
			log.debug("查询语句:  " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {// CommunicationsException
			e.printStackTrace();
		}
		return rs;
	}

	public static int insert(String title, String content, int time, String filter) {
		int resultCode = 0;
		try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO common_bulletin(title,content,`time`,filter) VALUES('" + title + "','" + content + "'," + time + ",'" + filter + "')";
			resultCode = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCode;
	}

	public static void close() {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
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
			// / stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
