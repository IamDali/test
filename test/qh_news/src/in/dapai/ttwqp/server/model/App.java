package in.dapai.ttwqp.server.model;

import java.util.List;

public class App {
 

	private int id;
	private String packageName;
	private int versionCode;
	private String versionName;
	private String name;
	private String urlIcon;
	private String tag;
	private String urlApk;
	private String etag;
	private String describe;
	private int order;
	private Group group;
	private String urlPatch;// 补丁文件的前缀,如果数据库中没有的话,那么就是用group的,目前是使用的group..不想每个都去填写,又不知道以后会不会给每个app分配一个补丁地址.
	private String patch;// 存在补丁的版本 :eg 24,25,26
	private List<Patch> listPatch;
	private String listPatchJson;// 服务端不需要解析此json,由客户端解析
	private long time;// 更新时间
	private long size;// apk包的大小
	private String updateLog;// 更新日志
	/** 是否自动下载0为false 大于0为true */
	private int autoDownload;

	public int getAutoDownload() {
		return autoDownload;
	}

	public void setAutoDownload(int autoDownload) {
		this.autoDownload = autoDownload;
	}

	public List<Patch> getListPatch() {
		return listPatch;
	}

	public void setListPatch(List<Patch> listPatch) {
		this.listPatch = listPatch;
	}

	public int getId() {
		return id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getListPatchJson() {
		return listPatchJson;
	}

	public void setListPatchJson(String listPatchJson) {
		this.listPatchJson = listPatchJson;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public String getUrlPatch() {
		return urlPatch;
	}

	public void setUrlPatch(String urlPatch) {
		this.urlPatch = urlPatch;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlIcon() {
		return urlIcon;
	}

	public void setUrlIcon(String icon) {
		this.urlIcon = icon;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUrlApk() {
		return urlApk;
	}

	public void setUrlApk(String urlApk) {
		this.urlApk = urlApk;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
