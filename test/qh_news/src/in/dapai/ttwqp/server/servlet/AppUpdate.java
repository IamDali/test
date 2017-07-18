package in.dapai.ttwqp.server.servlet;

import in.dapai.common.constant.Keys;
import in.dapai.ttwqp.server.dao.DBHelper;
import in.dapai.ttwqp.server.model.App;
import in.dapai.ttwqp.server.model.Patch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static in.dapai.ttwqp.server.servlet.DBServlet.DBcache;
import static in.dapai.ttwqp.server.dao.IDBCache.startKeyApk;
import static in.dapai.ttwqp.server.dao.IDBCache.startKeyPatch;

public class AppUpdate extends BaseServlet {
	private static final Log log = LogFactory.getLog(AppUpdate.class);

	public AppUpdate() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	// {code:1,update:'all',md5:'32wei',url:'http://sdfsdf',size:'5000',
	// time:132132,vn:'v1.92',content:'sdjfklsdjf\n*lksjf\nlsdjflk\n'}
	// {code:1,update:'patch',md5:'32wei',patch',patchmd5:'32wei',
	// patchsize:'122331',size:'5000',updatelog:'sdjfklsdjf\n*lksjf\nlsdjflk\n'}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String versionCode = request.getParameter(Keys.versionCode);// 客户端的versionCode
		String channel = request.getParameter(Keys.channel);// 客户端的channel

		String md5 = request.getParameter(Keys.md5); // 客户端本地的apk的md5值
		String pkg = request.getParameter(Keys.packageName);
		String idApp = request.getParameter(Keys.idApp);
		log.debug("请求参数versionCode: " + versionCode);
		log.debug("请求参数channel: " + channel);
		log.debug("请求参数md5: " + md5);
		log.debug("请求参数pkg: " + pkg);
		log.debug("请求参数idApp: " + idApp);
		PrintWriter out = response.getWriter();
		Patch patch = (Patch) DBcache.get(startKeyPatch + md5);
		App app;
		StringBuffer sb = new StringBuffer();
		if (patch == null) {// 全包更新
			app = (App) DBcache.get(startKeyApk + idApp);
		} else {// 补丁更新
			app = (App) DBcache.get(startKeyApk + patch.getIdApp());
		}
		if (app == null) {
			sb.append("{code:0,msg:'未查询到app信息'}");
		} else {
			sb.append("{code:1,md5:'" + app.getEtag().toUpperCase() + "',size:" + app.getSize() + ",updatelog:'" + 
					app.getUpdateLog() + "',vn:'v" + app.getVersionName() + "',time:" + app.getTime()+"000");
			if (patch == null) {
				sb.append(",type:'all',url:'" + app.getUrlApk() + "'");
			} else {
				sb.append(",type:'patch',url:'" + patch.getUrl() + "',patchsize:" + patch.getSize());
			}
			sb.append("}");
		}

		// out.print("{code:1,type:'patch',md5:'32wei',patchmd5:'32wei',patchsize:"
		// + "'1651459',size:'11814325',updatelog:'v1.92版本更新\\n\\n" +
		// space + "·新增节操场\\n\\n" + space + "·百人场支持玩家做庄\\n\\n\\n" +
		// space + "·新增背景音乐选择\\n\\n" + space + "·优化发牌速度'" + "" +
		// ",vn:'v1.92',time:1397458756000,url:'http://126.am/MYypz2'} ");
		log.debug("返回: "+sb);
		out.print(sb);
		out.flush();
		out.close();
	}

	public static void main(String[] args) {
		File file = new File("D:\\workspaceqihang\\原生天天玩棋牌\\otherApk/internal/1_in.dapai.hpysz.test_35.apk");
		System.out.println(file.length());

		System.out.println(bitToMb(11814325, "%.1f"));
	}

	public static String bitToMb(long bit, String format) {
		if (format == null)
			format = "%.2f";
		if (bit > 0) {
			double mb = bit / 1024.0 / 1024.0;
			return String.format(format, mb);
		}
		return "";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
