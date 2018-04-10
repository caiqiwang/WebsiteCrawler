package com.study.crawler.lists.abstracts.impl;

import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.lists.abstracts.WebsiteListAbstracts;

public class JDListImpl extends WebsiteListAbstracts {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public JDListImpl(Set<String> set) {// 该构造方法用于单线程
		super(set);
	}

	public JDListImpl() {

	}

	public static void main(String[] args) {
		String url = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&ev=exbrand_%E6%98%93%E6%98%9F%5E&uc=0#J_searchWrap"
				+ "!易星";
		JDListImpl impl = new JDListImpl();
		impl.getWebsiteList(url);
	}

	public void getWebsiteList(String str) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"__jda=122270672.15233201980441143284421.1523320198.1523320198.1523320198.1; __jdb=122270672.22.15233201980441143284421|1.1523320198; __jdc=122270672; __jdv=122270672|baidu|-|organic|not set|1523320198044; PCSYCityID=1213; xtest=1136.cf6b6759; ipLoc-djd=1-72-2799-0; qrsc=3; rkv=V0300; 3AB9D23F7A4B3C9B=KBNBQRDMGP2GAHFIJSMYC7HL3LQKL7AB5DKQXWE443ZW2QL4VCH6WFRMAZJTYQ2J63JSMTTNVDQZZEHUI2RSTN43WI");
		String[] str2 = str.split("!");// 电子烟品牌
		String brand = str2[1];
		String newUrl = str2[0];
		String detailUrl = "";
		String intro = "";
		int maxpage = getAllPage(newUrl);
		for (int i = 0; i < maxpage; i++) {
			if (i == 0) {
				crawlParam.setUrlStr(newUrl);
				Document document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					logger.info("进入分类电子烟url失败 :" + str);
				}
				Elements elements = document.select("div[class=ml-wrap]");
				if (elements.size() == 0) {
					logger.info(" list页面    进入分类电子烟第一个div失败 ");
				}
				elements = elements.select("ul[class=gl-warp clearfix]");
				if (elements.size() == 0) {
					logger.info(" list页面    进入分类电子烟第二个div失败 ");
				}
				elements = elements.select("li[class=gl-item]");
				for (int j = 0; j < elements.size(); j++) {
					Elements info = elements.get(j).select("div[class=gl-i-wrap]");
					info = info.select("div[class=p-name p-name-type-2]");
					info = info.select("a");
					intro = info.select("em").text();// 判断是否卖这个品牌的电子烟
					if (intro.indexOf(brand) > -1) {
						detailUrl = "https:" + info.attr("href") + "!" + brand;
						set.add(detailUrl);
					}
				}
			} else {
				String nextUrl = newUrl.split("uc=0")[0] + "page=" + ((2 * i) + 1) + "&click=0";
				crawlParam.setUrlStr(nextUrl);
				Document document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					logger.info("进入分类电子烟url失败 :" + str);
				}
				Elements elements = document.select("div[class=ml-wrap]");
				if (elements.size() == 0) {
					logger.info(" list页面    进入分类电子烟第一个div失败 ");
				}
				elements = elements.select("ul[class=gl-warp clearfix]");
				if (elements.size() == 0) {
					logger.info(" list页面    进入分类电子烟第二个div失败 ");
				}
				elements = elements.select("li[class=gl-item]");
				for (int j = 0; j < elements.size(); j++) {
					Elements info = elements.get(j).select("div[class=gl-i-wrap]");
					info = info.select("div[class=p-name p-name-type-2]");
					info = info.select("a");
					intro = info.select("em").text();// 判断是否卖这个品牌的电子烟
					if (intro.indexOf(brand) > -1) {
						detailUrl = "https:" + info.attr("href") + "!" + brand;
						set.add(detailUrl);
					}
				}
			}

		}

		crawlParam.setUrlStr(newUrl);

	}

	@Override
	public int getAllPage(String categoryUrl) {
		// TODO Auto-generated method stub
		int page = 1;
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(categoryUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("进入分类电子烟url失败 :" + categoryUrl);
		}
		Elements elements = document.select("div[class=ml-wrap]");
		elements = elements.select("div[class=f-line top]");
		elements = elements.select("div[id=J_topPage][class=f-pager]");
		elements = elements.select("span");
		elements = elements.select("i");
		page = Integer.valueOf(elements.text());
		return page;
	}

}
