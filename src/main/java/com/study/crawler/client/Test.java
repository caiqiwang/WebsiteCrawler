package com.study.crawler.client;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.study.crawler.details.abstracts.impl.MFWDetailImpl;

import redis.clients.jedis.Jedis;

@Service
public class Test {
	public void startCrawler() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("foobared");
		System.out.println("服务是否运行：" + jedis.ping());

		// 取出redis中 key为cateogry的值
		Set<String> DetailSet = jedis.smembers("MFWdetail");
		System.out.println("列表数据共有" + DetailSet.size() + "条");
		for (String detailUrl : DetailSet) {
			new MFWDetailImpl().getWebsiteDetail(detailUrl);
		}

		jedis.quit();
	}
}
