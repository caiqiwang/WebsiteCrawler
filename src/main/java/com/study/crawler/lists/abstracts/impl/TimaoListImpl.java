package com.study.crawler.lists.abstracts.impl;

import java.util.ArrayList;
import java.util.List;
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

public class TimaoListImpl extends WebsiteListAbstracts {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public TimaoListImpl(Set<String> set) {// 该构造方法用于单线程
		super(set);
	}

	public TimaoListImpl() {

	}

	public static void main(String[] args) {
		String url = "https://list.tmall.com/search_product.htm?q=%C9%BD%E1%B0&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!Phix";

		String pageUrl = " https://list.tmall.com/search_product.htm?q=%B3%AC%B8%D0%BE%F5&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!Phix";
		List<String> urlList = new ArrayList<>();
		TimaoListImpl impl = new TimaoListImpl();
		// int page = impl.getAllPage(pageUrl, urlList);
		/*for (int i = 0; i < urlList.size(); i++) {
			System.out.println(urlList.get(i));
		}*/
		// System.out.println(page);
		impl.getWebsiteList(pageUrl);
	}

	@Override
	public void getWebsiteList(String str) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setCharset("gbk");
		crawlParam.setInterval(3000);
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"hng=CN%7Czh-CN%7CCNY%7C156; cna=b21AERllZBMCAXPHmqNO1XFf; isg=BNXVB9YuI8szsAejm-XwVEjh5dJPeomAOcYgGVd6g8ybrvWgHyKZtONsfDIYtaGc; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C19A3A51CBC43FC962381BD19B87BFC1C; enc=ZAMZqDYB12N%2B1vUVKTwKzjIYAaEiT3IDkcljFRrzx9MKM4KxTuqYa1UkCvnlw9aCDT0h2lOcWR6eAOhZrtks3g%3D%3D; _med=dw:1366&dh:768&pw:1366&ph:768&ist:0; pnm_cku822=098%23E1hvz9vUvbpvUvCkvvvvvjiPPFFOAjtbR2s9ljrCPmP91j3PPLM9AjrPRFcOsj1hiQhvCvvv9UUPvpvhvv2MMQyCvhQp4vWvClsOafFCKdyIvExr1WoKDVQEfJBlYb8rwm0GAnLOHdUf8%2BClYW9XjV%2BRfaCl%2Bb8rwZClYExr58tev0zhQjZ7%2B3%2BrafFClfy6uphvmvvv92VTJBdfkphvC9QvvOClLbyCvm9vvvvvphvvvvvv9krvpvFavvmm86Cv2vvvvUUdphvUOQvv9FnvpvkFvphvC9vhvvCvp2yCvvpvvvvv; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; x=__ll%3D-1%26_ato%3D0; lid=%E8%AF%B7%E6%88%91%E5%8F%AB%E9%9B%B7%E6%AD%A3%E5%85%B4; cq=ccp%3D1; t=d434921d67195e175e56c2941803e622; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4FRYcmXYmgiIB4%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _tb_token_=78beeb638b603; cookie2=1d5697134bca2b2cad4fee924a5bde99; res=scroll%3A1349*1795-client%3A1349*631-offset%3A1349*1795-screen%3A1366*768; swfstore=243084");
		String[] str2 = str.split("!");// 电子烟品牌
		String brand = str2[1];
		String newUrl = str2[0];
		List<String> urlList = new ArrayList<>();
		int maxPage = getAllPage(newUrl, urlList);
		String url = "";
		String name = "";
		for (int i = 0; i < maxPage; i++) {
			if (i == 0) {
				crawlParam.setUrlStr(newUrl);
				Document document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					logger.info("list页面进入失败，请检查原因" + newUrl);
					continue;
				}
				Elements elements = document.select("div[id=J_ItemList]");
				if (elements.size() == 0) {
					logger.info("list页面进入div失败，请检查原因" + newUrl);
					continue;
				}
				elements = elements.select("div[class=product ]");
				if (elements.size() == 0) {
					logger.info("list页面进入div[class=product ]失败，请检查原因" + newUrl);
					continue;
				}
				for (int j = 0; j < elements.size(); j++) {
					Elements listInfo = elements.get(j).select("div[class=product-iWrap]");
					listInfo = listInfo.select("p[class=productTitle]");
					listInfo = listInfo.select("a");
					name = listInfo.attr("title");
					url = "https:" + listInfo.attr("href") + "!" + brand + "!" + name;
					set.add(url);
				}
			} else {
				String nextUrl = "https://list.tmall.com/search_product.htm" + urlList.get(i - 1);
				crawlParam.setUrlStr(nextUrl);
				Document document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					logger.info("list页面进入失败，请检查原因" + newUrl);
					continue;
				}
				Elements elements = document.select("div[class=view grid-nosku ][id=J_ItemList]");
				if (elements.size() == 0) {
					logger.info("list页面进入div失败，请检查原因" + newUrl);
					continue;
				}
				elements = elements.select("div[class=product]");
				if (elements.size() == 0) {
					logger.info("list页面进入div[class=product]失败，请检查原因" + newUrl);
					continue;
				}
				for (int j = 0; j < elements.size(); j++) {
					Elements listInfo = elements.get(j).select("div[class=product-iWrap]");
					listInfo = listInfo.select("p[class=productTitle]");
					listInfo = listInfo.select("a");
					name = listInfo.attr("title");
					url = "https:" + listInfo.attr("href") + "!" + brand + "!" + name;
					set.add(url);
				}
			}
		}
	}

	@Override
	public int getAllPage(String categoryUrl, List<String> list) {
		int page = 1;
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setCharset("gbk");
		crawlParam.setInterval(5000);
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"hng=CN%7Czh-CN%7CCNY%7C156; cna=b21AERllZBMCAXPHmqNO1XFf; isg=BNXVB9YuI8szsAejm-XwVEjh5dJPeomAOcYgGVd6g8ybrvWgHyKZtONsfDIYtaGc; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C19A3A51CBC43FC962381BD19B87BFC1C; enc=ZAMZqDYB12N%2B1vUVKTwKzjIYAaEiT3IDkcljFRrzx9MKM4KxTuqYa1UkCvnlw9aCDT0h2lOcWR6eAOhZrtks3g%3D%3D; _med=dw:1366&dh:768&pw:1366&ph:768&ist:0; pnm_cku822=098%23E1hvz9vUvbpvUvCkvvvvvjiPPFFOAjtbR2s9ljrCPmP91j3PPLM9AjrPRFcOsj1hiQhvCvvv9UUPvpvhvv2MMQyCvhQp4vWvClsOafFCKdyIvExr1WoKDVQEfJBlYb8rwm0GAnLOHdUf8%2BClYW9XjV%2BRfaCl%2Bb8rwZClYExr58tev0zhQjZ7%2B3%2BrafFClfy6uphvmvvv92VTJBdfkphvC9QvvOClLbyCvm9vvvvvphvvvvvv9krvpvFavvmm86Cv2vvvvUUdphvUOQvv9FnvpvkFvphvC9vhvvCvp2yCvvpvvvvv; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; x=__ll%3D-1%26_ato%3D0; lid=%E8%AF%B7%E6%88%91%E5%8F%AB%E9%9B%B7%E6%AD%A3%E5%85%B4; cq=ccp%3D1; t=d434921d67195e175e56c2941803e622; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4FRYcmXYmgiIB4%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _tb_token_=78beeb638b603; cookie2=1d5697134bca2b2cad4fee924a5bde99; res=scroll%3A1349*1795-client%3A1349*631-offset%3A1349*1795-screen%3A1366*768; swfstore=243084");
		crawlParam.setUrlStr(categoryUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		Elements elements = document.select("div[class=ui-page]");
		if (elements.size() == 0) {
			logger.info("getAllPage   进入第一个Elements失败： " + categoryUrl);
			return 0;
		}
		elements = elements.select("div[class=ui-page-wrap]");
		// 取最大页数
		Elements PageElements = elements.select("b[class=ui-page-skip]");
		PageElements = PageElements.select("form[name=filterPageForm");
		String allPage = elements.text();
		Pattern pattern = Pattern.compile("([\\d]+)");
		Matcher match = pattern.matcher(allPage);
		String id = "";
		while (match.find()) {
			id = match.group(1);
		}
		page = Integer.valueOf(id);
		if (page != 1) {
			// 取页数url
			Elements PageUrlElements = elements.select("b[class=ui-page-num]");
			PageUrlElements = PageUrlElements.select("a");
			String url = "";
			for (int i = 0; i < PageUrlElements.size(); i++) {
				url = PageUrlElements.get(i).attr("href");
				list.add(url);
			}
			list.remove(list.size() - 1);
		}
		return page;
	}

	@Override
	public int getAllPage(String categoryUrl) {
		return 1;
	}

}
