package in.dapai.ttwqp.server.model;

public class Patch {
	// 从versionCode7以后,下面两个仅用于方便看
	private int vc;// 老apk的versionCode
	private String appName;// apk的名字

	private String url;// 补丁文件的下载地址
	private int id;
	private String md5Old;// 老的apk文件的md5
	private int idApp;
	private String channel;
	private long size;// 补丁文件的尺寸
 

	public int getVc() {
		return vc;
	}

	public void setVc(int vc) {
		this.vc = vc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMd5Old() {
		return md5Old;
	}

	public void setMd5Old(String md5Old) {
		this.md5Old = md5Old;
	}

	public int getIdApp() {
		return idApp;
	}

	public void setIdApp(int idApp) {
		this.idApp = idApp;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
