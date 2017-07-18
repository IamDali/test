package in.dapai.oss.util;

import in.dapai.oss.model.ObjectMetaData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.aliyun.android.util.Helper;
import com.aliyun.openservices.HttpMethod;

public class Util {
	private static final Log log = LogFactory.getLog(Util.class);
	private static final double EARTH_RADIUS = 6378137.0;

	// 返回单位是米
	public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static String genMore(String objectName, HttpMethod method, Map<String, String> attrs, String accessId, String accessKey, String bucketName) {
		long date = (System.currentTimeMillis() + 10 * 60 * 1000);

		String ossHeaders = generateCanonicalizedHeader(attrs);
		String dateStr = date + "";// 1392807006149L
		String authorization = generateAuthorization(accessId, accessKey, method.name(), "", "", dateStr, ossHeaders, "/" + bucketName);
		String url = "OSSAccessKeyId=" + accessId + "&Expires=" + dateStr + "&Signature=" + authorization;
		return url;
	}

	/** 只返回问号以后的,不包含问号 */
	public static String genPar(String objectName, HttpMethod method, String contentType, String accessId, String accessKey, String bucketName) {
		// userName=java.net.URLEncoder.encode(userName);
		// objectName=java.net.URLEncoder.encode(objectName);
		if (contentType == null)
			contentType = "application/x-director";// 根据用户上传的东西决定
		long date = (System.currentTimeMillis() + 10 * 60 * 1000);

		ObjectMetaData objMetaData = new ObjectMetaData();
		objMetaData.setContentType(contentType);
		String ossHeaders = generateCanonicalizedHeader(objMetaData.getAttrs());
		String resource = "/" + bucketName + "/" + objectName;

		String dateStr = date + "";// 1392807006149L

		String authorization = generateAuthorization(accessId, accessKey, method.name(), "", contentType, dateStr, ossHeaders, resource);
		// userName=java.net.URLEncoder.encode(userName);
		// objectName=java.net.URLEncoder.encode(objectName);
		String url = "OSSAccessKeyId=" + accessId + "&Expires=" + dateStr + "&Signature=" + authorization;
		return url;
	}

	/**
	 * 生成CanonicalizedHeader, 以\n结束
	 */
	public static String generateCanonicalizedHeader(Map<String, String> headers) {
		String ossHeader = "";
		List<String> list = new ArrayList<String>();
		list.addAll(headers.keySet());
		Collections.sort(list);

		String post = "";
		for (String s : list) {
			if (s.equals(post)) {
				ossHeader += "," + headers.get(s);
			} else {
				ossHeader += "\n" + s + ":" + headers.get(s);
			}
			post = s;
		}

		if (!Helper.isEmptyString(ossHeader)) {
			ossHeader = ossHeader.trim();
			ossHeader += "\n";
		}
		return ossHeader;
	}

	/**
	 * 生成签名字符串
	 */
	public static String generateAuthorization(String accessId, String accessKey, String content) {
		String signature = null;
		try {
			signature = Helper.getHmacSha1Signature(content, accessKey);
		} catch (Exception e) {
			log.info("authorization" + e.toString());
		}
		return signature;
	}

	/**
	 * 生成签名字符串
	 */
	public static String generateAuthorization(String accessId, String accessKey, String httpMethod, String md5, String type, String date, String ossHeaders, String resource) {
		String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders + resource;
		log.info("content:" + content);
		return generateAuthorization(accessId, accessKey, content);
	}
}
