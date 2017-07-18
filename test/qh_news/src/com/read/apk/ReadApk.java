package com.read.apk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class ReadApk {
	/**
	 * @param args
	 * @return void
	 * @author hsx
	 * @time 2013-4-24下午03:20:24
	 */
	public static void main(String[] args) {
		String path = "http://192.168.1.233:8081/qh_wlh_tu-0.5.1.apk";
		HttpURLConnection con = null;
		InputStream is = null;
		URL u = null;
		try {
			u = new URL(path);
			con = (HttpURLConnection) u.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			is = con.getInputStream();
			ApkInfo apkInfo = AnalysisApk.unZip(is);
			System.out.println(apkInfo);
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

	}
}
