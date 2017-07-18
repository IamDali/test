package in.dapai.ttwqp.server.servlet;

import in.dapai.common.util.UtilsCommon;
import in.dapai.common.constant.Keys;
import in.dapai.ttwqp.server.dao.DBHelper;
import in.dapai.ttwqp.server.model.App;
import in.dapai.ttwqp.server.model.Group;
import in.dapai.ttwqp.server.model.Patch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class Apps extends BaseServlet {
	private static final Log log = LogFactory.getLog(Apps.class);
	boolean debug = true;

	public Apps() {
		super();
	}

	public void destroy() {
		DBHelper.close();
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String channel = request.getParameter(Keys.channel);
		String outStr;
		outStr = "";
		List<Group> groupList = DBHelper.getGroup();
		if (groupList != null && groupList.size() > 0) {
			List<Group> groupListRemo = new ArrayList<Group>();
			// 娓呯悊鎺変笉闇�鐨�
			for (int i = 0, n = groupList.size(); i < n; i++) {
				Group group = groupList.get(i);
				if (group.getId() == 1) {
					// 鍐呮祴鐨処d鏄�
					if (channel == null || !channel.equals("internal"))
						continue;
				}
				if (channel.equals("sss")) {
					// 鏄痵ss鏃�, 鍘绘帀鑻忓窞,鎺屼笂,鍏朵粬绫�
					if (group.getId() == 3 || group.getId() == 4 || group.getId() == 7)
						continue;
				}
				groupListRemo.add(group);
			}
			groupList = groupListRemo;
			StringBuffer sb = new StringBuffer("{code:1,apps:[");
			for (int i = 0, n = groupList.size(); i < n; i++) {
				Group group = groupList.get(i);
				sb.append("{id:" + group.getId() + ",name:'" + group.getName() + "',describe:'" + group.getDescribe() + "',ui:'" + group.getUrlIcon() + "',list:[" + appListToJsonStr(group.getList(), group) + "]}");
				if (i != n - 1) {// 涓嶆槸鏈�悗涓�釜閭ｄ箞娣诲姞閫楀彿
					sb.append(",");
				}
			}
			sb.append("]}");
			outStr = sb.toString();
		} else {
			outStr = "{code:0,msg:'娌℃暟鎹�}";
		}
		out.print(outStr);
		if (debug) {
			log.debug("杩斿洖鐨勭粨鏋滀负:" + outStr);
		}
		out.flush();
		out.close();
	}

	private String appListToJsonStr(List<App> list, Group group) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = list.size(); i < n; i++) {
			App app = list.get(i);
			// TODO 杩欓噷瑙ｆ瀽琛ヤ竵鍖呬俊鎭�浠呬粎鏄负浜嗕笌0.81鐗堟湰鍏煎
			String appOut081Patch = "";
			try {
				// 璁剧疆app鐨勮ˉ涓佸寘淇℃伅
				String patchStr = app.getListPatchJson();

				if (!UtilsCommon.isNull(patchStr)) {
					List<Patch> listPatch = new ArrayList<Patch>();
					JSONArray jaPatch = new JSONArray(patchStr);
					for (int k = 0, m = jaPatch.length(); k < m; k++) {
						JSONObject joPatch = jaPatch.getJSONObject(k);
						Patch patch = new Patch();
						patch.setUrl(joPatch.getString("u"));
						patch.setVc(joPatch.getInt("vc"));
						listPatch.add(patch);
						appOut081Patch = appOut081Patch + patch.getVc() + ",";
					}
					if (!appOut081Patch.equals("")) {
						appOut081Patch.subSequence(0, appOut081Patch.length() - 1);
					}
					app.setListPatch(listPatch);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 琛ヤ竵鏂囦欢鐨勮矾寰�
			String urlPatch = isNull(app.getUrlPatch()) ? group.getUrlPatch() : app.getUrlPatch();
			sb.append("{id:" + app.getId() + ",pkg:'" + app.getPackageName() + "',vc:" + app.getVersionCode() + ",etag:'" + app.getEtag() + "',vn:'" + app.getVersionName() + "',name:'" + app.getName() + "',urlapk:'" + app.getUrlApk() + "',urlicon:'" + app.getUrlIcon() + "',tag:'" + app.getTag() + "',describe:'" + app.getDescribe() + "',ad:" + app.getAutoDownload() + ",urlpatch:'" + urlPatch + "',patch:'" + appOut081Patch + "',patchs:\"" + app.getListPatchJson() + "\"}");
			if (i != n - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
