package in.dapai.news.model;

import java.util.List;

public class News {
	private int id;
	private int id_cover;
	private String url;
	private String urlImg;
	private String title;

	public int getId_cover() {
		return id_cover;
	}

	public void setId_cover(int idCover) {
		id_cover = idCover;
	}

	public int getId() {
		return id;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "id:" + id + " coverId: " + id_cover + " title:" + title + " url:" + url + " urlImg:" + urlImg;
	}

}
