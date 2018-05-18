package com.study.crawler.lists.abstracts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		// 淘宝不进入detail 直接在list获取
		// 存储店铺url
		List<String> shopUrl = new ArrayList<>();
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setInterval(3000);
		crawlParam.setCharset("GBk");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"l=AtDQjVLQBi8zKSEhMpSUff1ooBQgqLTj; cna=b21AERllZBMCAXPHmqNO1XFf; thw=cn; isg=BKmphNe21xUACuun4JuZEAqMu1bD3p3MbSqMHUueJRDPEskkk8ateJcA0DYkkTXg; t=d434921d67195e175e56c2941803e622; mt=ci=-1_0; hng=CN%7Czh-CN%7CCNY%7C156; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C20AB33EAD48989B3AEF4EDEC93E272F7; enc=6SkaKGMawzPopY%2B2PUpFn8GzfymiAryWrUwAMQ%2B2KLv096n40JjjUe4m1cxbNc0s4CnRqg1azFClU4thR%2BGM2g%3D%3D; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4PAiWXZsYcgopc%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _cc_=UIHiLt3xSw%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; cookie2=262e2b15bc99c2d8d151c1e7b8001181; v=0; _tb_token_=f3efee5e55e31; uc1=cookie14=UoTePTFAO7xchQ%3D%3D; JSESSIONID=218B0947D01A46001A11AD1D90E92FA6; swfstore=282971");
		String[] str2 = str.split("!");// 电子烟品牌
		String brand = str2[1];
		String newUrl = str2[0];// 分类url

		// Set<String> UrlSet=new HashSet<String>();

		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				crawlParam.setUrlStr(newUrl);
				String info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
				Pattern pattern = Pattern.compile("shop(\\d+)\\.taobao");
				Matcher matcher = pattern.matcher(info);
				while (matcher.find()) {
					String id = matcher.group(1);
					String storeUrl = "https://shop" + id + ".taobao.com";
					shopUrl.add(storeUrl);
				}
			} else {
				String url = newUrl + "&s=" + (i * 20);
				crawlParam.setUrlStr(url);
				String info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
				if (info.equals("筛选条件加的太多")) {
					break;
				} else {
					Pattern pattern = Pattern.compile("shop(\\d+)\\.taobao");
					Matcher matcher = pattern.matcher(info);
					while (matcher.find()) {
						String id = matcher.group(1);
						String storeUrl = "https://shop" + id + ".taobao.com";
						shopUrl.add(storeUrl);
					}
				}
			}
		}

		for (String url : shopUrl) {
			// 判断是否是天猫店铺
			crawlParam.setUrlStr(url);
			// crawlParam.setOutputPath("E:\\excel\\tbTest.txt");

			// HttpURLConnectionFactory.downloadFile(crawlParam);

			String shopInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
			if (shopInfo.indexOf("天猫Tmall") > 0 || shopInfo.indexOf("飞猪") > 0) {
				logger.info("该商店是天猫店铺，不在淘宝页面处理");
				continue;
			}
			// 本店搜品牌url
			String searchUrl = url + "/search.htm?q=" + brand
					+ "&searcy_type=item&s_from=newHeader&source=&ssid=s5-e&search=y&spm=a1z10.1.1996643285.d4916905&initiative_id=shopz_20180419";
			crawlParam.setUrlStr(searchUrl);
			// crawlParam.setOutputPath("E:\\excel\\searchTest.txt");
			// HttpURLConnectionFactory.downloadFile(crawlParam);
			String searchInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
			if (searchInfo.indexOf("很抱歉，搜索到") > 0) {
				logger.info("不存在" + brand + "名字的商品");
				continue;
			}
			String JsonUrl = url
					+ "/i/asynSearch.htm?_ksTS=1524184345366_212&callback=jsonp213&mid=w-15496061164-0&wid=15496061164&path=/search.htm&search=y&q="
					+ brand
					+ "&searcy_type=item&s_from=newHeader&source=null&ssid=s5-e&spm=a1z10.1.1996643285.d4916905&initiative_id=shopz_20180420";
			crawlParam.setUrlStr(JsonUrl);
			// crawlParam.setOutputPath("E:\\excel\\searchTest.txt");
			// HttpURLConnectionFactory.downloadFile(crawlParam);
			String detailUrl = "";
			String document = HttpURLConnectionFactory.getDocumentStr(crawlParam);
			Pattern pattern = Pattern.compile("htm\\?id=(\\d+)");
			Matcher matcher = pattern.matcher(document);
			while (matcher.find()) {
				String id = matcher.group(1);
				detailUrl = "https//:item.taobao.com/item.htm?id=" + id + "!" + brand;
				// System.out.println(detailUrl);
				set.add(detailUrl);
			}
		}
	}

	@Override
	public int getAllPage(String categoryUrl) {
		int page = 1;

		return page;
	}

	@Override
	public int getAllPage(String categoryUrl, List<String> list) {
		// TODO Auto-generated method stub
		return 0;
	}

}
