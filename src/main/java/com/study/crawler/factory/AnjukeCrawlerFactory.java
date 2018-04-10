package com.study.crawler.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnjukeCrawlerFactory {
	@Value("${com.study.ThreadNumber}")
	private int number;

	public void startCrawler() {
		BlockingQueue categoryQueue = new LinkedBlockingQueue<>();
		BlockingQueue listQueue = new LinkedBlockingQueue<>();
		ExecutorService service = Executors.newCachedThreadPool();

	}
}
