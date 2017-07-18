package in.dapai.common.bulletin.model;

import java.util.Map;
import java.util.Set;

public class Bulletin {
	private int id;
	private String title;
	private int time;
	private String content;
	private String tag;
	private String filter;//允许被访问的
	private String unFilter;//不允许访问的
	private Set<String > keySet;
	

	public Set<String> getKeySet() {
		return keySet;
	}

	public void setKeySet(Set<String> keySet) {
		this.keySet = keySet;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
