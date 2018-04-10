package com.study.crawler.category.abstracts;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.study.crawler.category.WebsiteCategory;

import redis.clients.jedis.Jedis;

public class WebsiteCategoryAbstract implements WebsiteCategory {
	protected BlockingQueue<String> categoryQueue;
	protected Jedis jedis;
	protected List<String> list;

	// 可以吧数据存在radis 或者 队列中
	public WebsiteCategoryAbstract(BlockingQueue<String> categoryQueu) {
		this.categoryQueue = categoryQueu;
	}

	public WebsiteCategoryAbstract(Jedis jedis) {
		this.jedis = jedis;
	}

	public WebsiteCategoryAbstract(List<String> list) {
		this.list = list;
	}

	public WebsiteCategoryAbstract() {

	}

	public void getWebsiteCategory() {
		// TODO Auto-generated method stub
	}

}
