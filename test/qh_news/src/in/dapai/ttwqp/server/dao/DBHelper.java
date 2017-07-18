package in.dapai.ttwqp.server.dao;

import in.dapai.ttwqp.server.model.App;
import in.dapai.ttwqp.server.model.Group;
import in.dapai.ttwqp.server.model.Patch;

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
	public static CoverDao coverDao;

	public static void main(String[] args) {
		init();
		List<Group> list = getGroup();
		System.out.println();
		;
	}

	public static List<Group> getGroup() {
		String sql = "SELECT * FROM ttwqp_group ORDER BY `order`";
		ResultSet rs = query(sql);
		if (rs == null)
			return null;
		List<Group> result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			System.out.println("count :" + n);
			Group tmp;
			result = new ArrayList<Group>();
			while (rs.next()) {
				tmp = new Group();
				tmp.setId(rs.getInt("id"));
				tmp.setName(rs.getString("name"));
				tmp.setUrlIcon(rs.getString("url_icon"));
				tmp.setDescribe(rs.getString("describe"));
				tmp.setUrlPatch(rs.getString("urlpatch"));
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
		for (int i = 0; i < result.size(); i++) {
			Group tmp = result.get(i);
			tmp.setList(getUserFileIsPublicAll(tmp.getId()));
		}
		return result;
	}

	/** 获取所有的补丁 */
	public static List<Patch> getPatchs() {
		ResultSet rs = null;
		List<Patch> list = null;
		String sql = "SELECT * from ttwqp_app_patch";
		try {
			Statement stmt = conn.createStatement();
			log.debug("查询语句:  " + sql);
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Patch>();
			while (rs.next()) {
				list.add(fullPatch(rs));
			}
		} catch (Exception e) {// CommunicationsException
			e.printStackTrace();
			list = null;
		}
		return list;
	}

	private static Patch fullPatch(ResultSet rs) throws SQLException {
		Patch tmp;
		tmp = new Patch();
		tmp.setId(rs.getInt("id"));
		tmp.setMd5Old(rs.getString("md5_old"));
		tmp.setIdApp(rs.getInt("id_app"));
		tmp.setUrl(rs.getString("url"));
		tmp.setChannel(rs.getString("channel"));
		tmp.setSize(rs.getLong("size"));
		tmp.setVc(rs.getInt("vc"));
		tmp.setAppName(rs.getString("app_name"));
		return tmp;
	}

	/** 填充一个app ,rs已经下移 */
	private static App fullApp(ResultSet rs) throws SQLException {
		App tmp;
		tmp = new App();
		tmp.setId(rs.getInt("id"));
		tmp.setPackageName(rs.getString("package"));
		tmp.setVersionCode(rs.getInt("versioncode"));
		tmp.setVersionName(rs.getString("versionname"));
		tmp.setName(rs.getString("name"));
		tmp.setUrlApk(rs.getString("urlapk"));
		tmp.setUrlIcon(rs.getString("urlicon"));
		tmp.setEtag(rs.getString("etag"));
		tmp.setAutoDownload(rs.getInt("autodownload"));
		tmp.setDescribe(rs.getString("describe"));
		tmp.setTag(rs.getString("tag"));
		tmp.setUrlPatch(rs.getString("urlpatch"));
		tmp.setPatch(rs.getString("patch"));
		tmp.setListPatchJson(rs.getString("patch"));

		tmp.setTime(rs.getLong("time"));
		tmp.setSize(rs.getLong("size"));
		tmp.setUpdateLog(rs.getString("updatelog"));
		return tmp;

	}

	private static List<App> getUserFileIsPublicAll(int groupId) {
		String sql = "SELECT  id,package,`time`,`size`,`updatelog`,versioncode,patch,versionname,urlpatch, `name` ,urlapk,urlicon,etag,`describe` ,`tag`,autodownload FROM ttwqp_app WHERE active=1 AND `group`=" + groupId;
		ResultSet rs = query(sql);
		if (rs == null)
			return null;
		List<App> result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			System.out.println("count :" + n);
			App tmp;
			result = new ArrayList<App>();
			while (rs.next()) {
				tmp = fullApp(rs);
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

	private static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			log.debug("查询语句:  " + sql);
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {// CommunicationsException
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
			if (coverDao == null) {
				coverDao = new CoverDao(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
