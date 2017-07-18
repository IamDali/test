package in.dapai.news.model;

import java.util.List;

public class Cover {
	private int id;
	private int id_title_news;
	private long time;
	private List<News> list;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_title_news() {
		return id_title_news;
	}

	public void setId_title_news(int idTitleNews) {
		id_title_news = idTitleNews;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public List<News> getList() {
		return list;
	}

	public void setList(List<News> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "id:" + id + "id_title_news:" + id_title_news + " time:" + time+"   newSZie:"+list.size();
	}

}
