package in.dapai.news.dao;

import in.dapai.news.model.Cover;
import in.dapai.news.model.News;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		DBHelper.init();

		List<Cover> list = DBHelper.CoverDao.getAll(6);
		
		print(list);
		DBHelper.close();
	}

	private static void print(List<Cover> list) {
		for (int i = 0, n = list.size(); i < n; i++) {
			Cover c = list.get(i);
			System.out.println("cover:   "+c);
			for (int j = 0, m = c.getList().size(); j < m; j++) {
				News news = c.getList().get(j);
				System.out.println(news);
			}
		}
	}
}
