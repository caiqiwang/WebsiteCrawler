package com.study.crawler.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.study.crawler.category.abstracts.impl.LagouCategoryImpl;
import com.study.crawler.details.abstracts.impl.LagouDetailImpl;
import com.study.crawler.lists.abstracts.impl.LagouListImpl;

@Service
public class LagouCrawlerFactory {
	@Value("${com.study.ThreadNumber}")
	public int number;

	public void startCrawler() {
		System.out.println(number);
		BlockingQueue<String> categoryQueue = new LinkedBlockingQueue<String>();// 存储分类信息
		BlockingQueue<String> listQueue = new LinkedBlockingQueue<String>();// 存储每个分类下的所有详情url
		ExecutorService service = Executors.newCachedThreadPool();
		AtomicInteger atomic = new AtomicInteger(0);
		//
		new LagouCategoryImpl(categoryQueue);
		System.out.println("分类信息获取完毕 url总数为 ：" + categoryQueue.size());
		for (int i = 0; i < number; i++) {
			new LagouListImpl(categoryQueue, listQueue, service, atomic);
		}
		for (int i = 0; i < number; i++) {
			new LagouDetailImpl(listQueue, service, atomic, number);
		}

		service.shutdown();
		while (!service.isTerminated()) {
			try {
				TimeUnit.SECONDS.sleep(10);
				System.out.println("等待线程结束");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
