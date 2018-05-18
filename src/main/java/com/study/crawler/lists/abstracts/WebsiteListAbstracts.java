package com.study.crawler.lists.abstracts;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import com.study.crawler.lists.WebsiteList;

import redis.clients.jedis.Jedis;

public abstract class WebsiteListAbstracts implements WebsiteList, Runnable {
	protected BlockingQueue<String> categoryQueue;// 分类信息
	protected BlockingQueue<String> listQueue;// 存储每个分类下的所有详情url
	protected ExecutorService service;
	protected AtomicInteger atomic;
	protected Jedis jedis;// 用于数据存储到redis中
	// 单线程使用
	protected List<String> list;
	protected Set<String> set;

	public WebsiteListAbstracts(BlockingQueue<String> categoryQueue, BlockingQueue<String> listQueue,
			ExecutorService service, AtomicInteger atomic) {
		this.categoryQueue = categoryQueue;
		this.listQueue = listQueue;
		this.service = service;
		this.atomic = atomic;
	}

	public WebsiteListAbstracts(Set<String> set) {// 该构造方法用于单线程

		this.set = set;
	}

	public WebsiteListAbstracts(Jedis jedis) {
		this.jedis = jedis;
	}

	public WebsiteListAbstracts() {

	}

	public void run() {
		// TODO Auto-generated method stub
		String url = null;
		// System.out.println("run方法这里的5只为调试专用，正常情况下值为0");
		while (categoryQueue.size() > 0) {
			url = getUrl();
			if (url != null) {
				getWebsiteList(url);
			}
		}
		// 条件
		atomic.getAndIncrement();
	}

	public String getUrl() {
		String url = null;
		try {
			synchronized (categoryQueue) { // 保证线程原子性 判断跟取url同步进行
				// System.out.println("getUrl方法这里的5只为调试专用，正常情况下值为0");
				if (categoryQueue.size() > 0) {
					url = categoryQueue.take();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}

	public void getWebsiteList(String str) {
		// TODO Auto-generated method stub
	}

	public abstract int getAllPage(String categoryUrl);

	public abstract int getAllPage(String categoryUrl, List<String> list);
}
