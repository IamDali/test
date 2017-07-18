package in.dapai.common.servlet;

//import static in.dapai.common.constant.Keys.accessId;
//import static in.dapai.common.constant.Keys.accessKey;


import in.dapai.oss.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.android.util.Helper;
import com.aliyun.openservices.HttpMethod;

public class GetPUTAvatarUrl extends HttpServlet {
	private static final Log log = LogFactory.getLog(GetPUTAvatarUrl.class);

	public GetPUTAvatarUrl() {
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
		UUID uuid = UUID.randomUUID();
		String shortName=  uuid.toString().replace("-", "")+".jpg";
		String objName = "avatar/" +shortName;
		String result = Util.genPar(objName, HttpMethod.PUT, "image/jpeg", "6fVgt8d5fGWgESXz", "80KOHp7pXGP3diOcRix5ITMeyeJo6F", "qhcdn");
		result = "{objname:'"+ shortName + "',puturl:'http://qhcdn.oss-cn-hangzhou.aliyuncs.com/" + objName + "?" + result + "'}";
		//result = "{objname:'http://qhcdn.vlahao.com/" + objName + "',puturl:'http://qhcdn.oss-cn-hangzhou.aliyuncs.com/" + objName + "?" + result + "'}";
		log.debug("鑾峰彇鐨勪笂浼爑rl涓� " + result);
		out.print(result);
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
	}
}
