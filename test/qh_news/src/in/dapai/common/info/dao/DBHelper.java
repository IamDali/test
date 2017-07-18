package in.dapai.common.info.dao;

import in.dapai.common.info.model.LoginInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	public static int inster(LoginInfo info) {
		String sql = "	INSERT INTO common_info_login(username,`date`,md5_apk,channel, package_name," + "version_code,version_name,latitude,longitude, " + "address,area,device_id,imsi,mac,model,network,`operator`," + "`release`,phone_number,ip)  " + "VALUES('" + info.userName + "'," + info.date + ",'" + info.md5 + "','" + info.channel + "','" + info.pkg + "'" + ",'" + info.versionCode + "','" + info.versionName + "'," + info.latitude + "," + info.longitude + "," + "'" + info.address + "','" + info.area + "','" + info.deviceId + "','" + info.imsi + "','" + info.mac + "','" + info.model + "','" + info.network + "','" + info.operator + "','" + info.release + "','" + info.phoneNumber + "','" + info.ip + "');";
		int resultCode = 0;
		Statement stmt1 = null;
		try {
			stmt1 = conn.createStatement();
			resultCode = stmt1.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (stmt1 != null) {
				try {
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return resultCode;
	}

	public static void main(String[] args) {
		init();
		insterOrUpdate("dev", "macc", "userName", "eve", "pkg", "1", 2);
		close();
	}

	public static int insterOrUpdate(String dev, String mac, String userName, String event, String pkg, String vc, int countAdd) {
		String sql = "SELECT `id`,`count` FROM common_info_event_click WHERE device_id = ? AND  `mac`= ? AND package_name= ? ";
		log.debug(sql);
		int resultCode = 0;
		PreparedStatement stmt1 = null;
		PreparedStatement ps = null;
		try {
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, dev);
			stmt1.setString(2, mac);
			stmt1.setString(3, pkg);
			ResultSet rs = stmt1.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			log.debug("行数: " + rowCount);
			if (rowCount == 0) {
				sql = "INSERT INTO common_info_event_click(device_id,mac,username,event,package_name,version_code,count) VALUES(?,?,?,?,?,?,?) ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dev);
				ps.setString(2, mac);
				ps.setString(3, userName);
				ps.setString(4, event);
				ps.setString(5, pkg);
				ps.setString(6, vc);
				ps.setInt(7, countAdd);
			} else {
				long id = rs.getLong("id");
				int oldCount = rs.getInt("count");
				sql = "UPDATE common_info_event_click SET  username= ? , count = ? ,version_code = ?  WHERE id= ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userName);
				ps.setInt(2, oldCount + countAdd);
				ps.setString(3, vc);
				ps.setLong(4, id);
			}
			resultCode = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (stmt1 != null) {
					stmt1.close();
				}
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

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
