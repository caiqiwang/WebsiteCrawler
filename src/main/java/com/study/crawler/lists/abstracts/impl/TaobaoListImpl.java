package com.study.crawler.lists.abstracts.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.lists.abstracts.WebsiteListAbstracts;

public class TaobaoListImpl extends WebsiteListAbstracts {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public TaobaoListImpl(Set<String> set) {// 该构造方法用于单线程
		super(set);
	}

	public TaobaoListImpl() {

	}

	public static void main(String[] args) {
		String url = "https://shopsearch.taobao.com/search?app=shopsearch&q=Phix&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306"
				+ "!Phix";
		TaobaoListImpl impl = new TaobaoListImpl();
		impl.getWebsiteList(url);
	}

	@Override
	public void getWebsiteList(String str) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"l=AtDQjVLQBi8zKSEhMpSUff1ooBQgqLTj; cna=b21AERllZBMCAXPHmqNO1XFf; thw=cn; isg=BKmphNe21xUACuun4JuZEAqMu1bD3p3MbSqMHUueJRDPEskkk8ateJcA0DYkkTXg; t=d434921d67195e175e56c2941803e622; mt=ci=-1_0; hng=CN%7Czh-CN%7CCNY%7C156; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C20AB33EAD48989B3AEF4EDEC93E272F7; enc=6SkaKGMawzPopY%2B2PUpFn8GzfymiAryWrUwAMQ%2B2KLv096n40JjjUe4m1cxbNc0s4CnRqg1azFClU4thR%2BGM2g%3D%3D; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4PAiWXZsYcgopc%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _cc_=UIHiLt3xSw%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; cookie2=262e2b15bc99c2d8d151c1e7b8001181; v=0; _tb_token_=f3efee5e55e31; uc1=cookie14=UoTePTFAO7xchQ%3D%3D; JSESSIONID=218B0947D01A46001A11AD1D90E92FA6; swfstore=282971");
		String[] str2 = str.split("!");// 电子烟品牌
		String brand = str2[1];
		String newUrl = str2[0];

		int maxpage = getAllPage(newUrl);
		if (maxpage == 0) {
			return;
		}
		for (int i = 0; i < maxpage; i++) {

		}
	}

	@Override
	public int getAllPage(String categoryUrl) {
		int page = 1;
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(categoryUrl);
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		infoMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		infoMap.put("Connection", "keep-alive");
		infoMap.put("Upgrade-Insecure-Requests", "1");
		crawlParam.setCookie(
				"l=AtDQjVLQBi8zKSEhMpSUff1ooBQgqLTj; cna=b21AERllZBMCAXPHmqNO1XFf; thw=cn; isg=BKmphNe21xUACuun4JuZEAqMu1bD3p3MbSqMHUueJRDPEskkk8ateJcA0DYkkTXg; t=d434921d67195e175e56c2941803e622; mt=ci=-1_0; hng=CN%7Czh-CN%7CCNY%7C156; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C20AB33EAD48989B3AEF4EDEC93E272F7; enc=6SkaKGMawzPopY%2B2PUpFn8GzfymiAryWrUwAMQ%2B2KLv096n40JjjUe4m1cxbNc0s4CnRqg1azFClU4thR%2BGM2g%3D%3D; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4PAiWXZsYcgopc%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _cc_=UIHiLt3xSw%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; cookie2=262e2b15bc99c2d8d151c1e7b8001181; v=0; _tb_token_=f3efee5e55e31; uc1=cookie14=UoTePTFAO7xchQ%3D%3D; JSESSIONID=218B0947D01A46001A11AD1D90E92FA6; swfstore=282971");
		crawlParam.setRequestHeadInfo(infoMap);
		// crawlParam.setOutputPath("E:\\excel\\test.html");
		// HttpURLConnectionFactory.downloadFile(crawlParam);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("getAllPage   进入搜索的document失败： " + categoryUrl);
			return 0;
		}
		Elements elements = document.select("div[class=m-page g-clearfix]");
		if (elements == null) {
			logger.info("getAllPage   进入第一个Elements失败： " + categoryUrl);
			return 0;
		}
		elements = elements.select("div[class=inner clearfix]");
		elements = elements.select("div[class=total]");
		String pageInfo = elements.text();
		Pattern pattern = Pattern.compile("([\\d]+)");
		Matcher match = pattern.matcher(pageInfo);
		String s = "";
		if (match.find()) {
			s = match.group(1);
		}
		page = Integer.valueOf(s);
		return page;
	}

}
