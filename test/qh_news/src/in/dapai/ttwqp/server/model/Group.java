package in.dapai.ttwqp.server.model;

import java.util.List;

public class Group {
	private int id;
	private String name;
	private String describe;
	private String urlIcon;
	private List<App> list;
	private String urlPatch;// 如果app本身补丁前缀为空的话,那就有group的 .

	public int getId() {
		return id;
	}

	public String getUrlIcon() {
		return urlIcon;
	}

	public String getDescribe() {
		return describe;
	}

	public String getUrlPatch() {
		return urlPatch;
	}

	public void setUrlPatch(String urlPatch) {
		this.urlPatch = urlPatch;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setUrlIcon(String urlIcon) {
		this.urlIcon = urlIcon;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<App> getList() {
		return list;
	}

	public void setList(List<App> list) {
		this.list = list;
	}
}
