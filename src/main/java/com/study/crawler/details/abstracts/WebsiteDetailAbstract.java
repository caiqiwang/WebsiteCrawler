package com.study.crawler.details.abstracts;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import com.study.crawler.details.WebsiteDetail;
import com.study.crawler.entity.CompanyInfo;
import com.study.crawler.entity.RecruitmentInfo;

public abstract class WebsiteDetailAbstract implements WebsiteDetail, Runnable {
	protected BlockingQueue<String> listQueue;// 存储每个分类下的所有详情url
	protected ExecutorService service;
	protected AtomicInteger atomic;
	protected int number;

	public WebsiteDetailAbstract(BlockingQueue<String> listQueue, ExecutorService service, AtomicInteger atomic,
			int number) {
		this.listQueue = listQueue;
		this.service = service;
		this.atomic = atomic;
		this.number = number;
	}

	public WebsiteDetailAbstract() {

	}

	public void run() {
		// TODO Auto-generated method stub
		String url = null;
		while (!(atomic.get() == number && listQueue.size() == 0)) {
			url = getUrl();
			if (url != null) {
				getWebsiteDetail(url);
			}
		}
	}

	public String getUrl() {
		String url = null;
		synchronized (listQueue) {
			if (listQueue.size() > 0) {
				try {
					url = listQueue.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return url;
	}

	public void getWebsiteDetail(String detailUrl) {
		// TODO Auto-generated method stub

	}

	public abstract CompanyInfo getCompanyInfo(String detailUrl);

	public abstract RecruitmentInfo getRecruitmentInfo(String detailUrl);
}
