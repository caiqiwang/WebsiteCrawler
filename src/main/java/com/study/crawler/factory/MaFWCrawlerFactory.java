package com.study.crawler.factory;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.study.crawler.category.abstracts.impl.MaFWCategoryImpl;
import com.study.crawler.details.abstracts.impl.MFWDetailImpl;
import com.study.crawler.lists.abstracts.impl.MaFWListImpl;

import redis.clients.jedis.Jedis;

@Service
public class MaFWCrawlerFactory {
	public void startCrawler() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("foobared");
		System.out.println("服务是否运行：" + jedis.ping());
		new MaFWCategoryImpl(jedis).getWebsiteCategory();

		// 取出redis中 key为cateogry的值
		Set<String> categorySet = jedis.smembers("MfWcategory");
		System.out.println("分类数据共有" + categorySet.size() + "条");
		MaFWListImpl maFWListImpl = new MaFWListImpl(jedis);
		for (String categoryUrl : categorySet) {
			maFWListImpl.getWebsiteList(categoryUrl);
		}
		Set<String> DetailSet = jedis.smembers("MFWdetail");
		System.out.println("列表数据共有" + DetailSet.size() + "条");
		for (String detailUrl : DetailSet) {
			new MFWDetailImpl().getWebsiteDetail(detailUrl);
		}

		jedis.quit();
	}
}
