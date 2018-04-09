package com.study.crawler.category;

import java.util.concurrent.BlockingQueue;

public interface WebsiteCategory {
	BlockingQueue<String> getWebsiteCategory();
}
