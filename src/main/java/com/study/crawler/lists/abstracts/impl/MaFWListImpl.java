package com.study.crawler.lists.abstracts.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.lists.abstracts.WebsiteListAbstracts;

import redis.clients.jedis.Jedis;

public class MaFWListImpl extends WebsiteListAbstracts {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public MaFWListImpl() {

	}

	public MaFWListImpl(Jedis jedis) {
		super(jedis);
	}

	public static void main(String[] args) {
		String url = "http://www.mafengwo.cn/travel-scenic-spot/mafengwo/11141.html";
		MaFWListImpl maFWListImpl = new MaFWListImpl();
		maFWListImpl.getWebsiteList(url);
	}

	@Override
	public void getWebsiteList(String str) {
		// Set<String> set = new HashSet<String>();
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(str);
		String id = "";
		while (matcher.find()) {
			id = matcher.group(1);
		}
		if ("".equals(id)) {
			logger.info("无法获取到列表链接的id 检查url" + str);
			return;
		}
		String JsonUrl = "http://pagelet.mafengwo.cn/mdd/pagelet/communityApi?callback=jQuery123&params=%7B%22iMddid%22%3A%22"
				+ id + "%22%7D&_=1524724386274";

		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(JsonUrl);
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		String DetailUrl = "";
		String info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		if (info == null) {
			logger.info("获取游记列表失败 url为" + JsonUrl + "分类url为：" + str);
			return;
		}
		// info = CrawlerUtil.matchBetweenSymbol(info, "jQuery123\\(", "\\)",
		// true);

		JSONObject json = JSONObject.parseObject(info);
		json = json.getJSONObject("data");
		String htmlInfo = json.getString("html");
		Document document = Jsoup.parse(htmlInfo);
		if (document == null) {
			logger.info("马蜂窝列表页面获取 失败 请检查jsonUrl" + JsonUrl);
			return;
		}
		// 获取第一页url
		Elements elements = document.select("div[class=_notelist]");
		elements = elements.select("div[class=tn-list]");
		if (elements.size() == 0) {
			logger.info("马蜂窝列表页面进入div失败,检查document是否存在" + JsonUrl + "分类url为：" + str);
			return;
		}
		elements = elements.select("div[class=tn-item clearfix]");
		for (int i = 0; i < elements.size(); i++) {
			Elements detailInfo = elements.get(i).select("div[class=tn-image]").select("a");
			DetailUrl = "http://www.mafengwo.cn" + detailInfo.attr("href");
			// System.out.println(DetailUrl);
			// set.add(DetailUrl);
			jedis.sadd("MFWdetail", DetailUrl);
		}
		// 获取页数
		Elements pageElment = document.select("div[class=_pagebar]");
		pageElment = pageElment.select("div[class=m-pagination]");
		if (elements.size() == 0) {
			logger.info("马蜂窝列表页面进入div失败,检查document是否存在" + JsonUrl + "分类url为：" + str);
			return;
		}
		pageElment = pageElment.select("span[class=count]");
		pageElment = pageElment.select("span");
		// 获取总页数
		int maxPage = Integer.parseInt(pageElment.get(1).text());
		/*if(){
			
		}*/
		crawlParam = new CrawlParam();
		for (int i = 2; i < maxPage; i++) {// 获取翻页的游记信息
			crawlParam.setPostParam("mddid=" + id + "&pageid=mdd_index&sort=1&cost=0&days=0&month=0&tagid=0&page=" + i);
			crawlParam.setRequestMethod("POST");
			crawlParam.setUrlStr("http://www.mafengwo.cn/gonglve/ajax.php?act=get_travellist");
			crawlParam.setCookie(
					"PHPSESSID=uq65e6rsudl4rb621begccbpo3; mfw_uuid=5ae1315d-3abe-49ce-ba03-2bbc42fada24; _r=baidu; _rp=a%3A2%3A%7Bs%3A1%3A%22p%22%3Bs%3A18%3A%22www.baidu.com%2Flink%22%3Bs%3A1%3A%22t%22%3Bi%3A1524707677%3B%7D; oad_n=a%3A5%3A%7Bs%3A5%3A%22refer%22%3Bs%3A21%3A%22https%3A%2F%2Fwww.baidu.com%22%3Bs%3A2%3A%22hp%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A3%3A%22oid%22%3Bi%3A1026%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222018-04-26+09%3A54%3A37%22%3B%7D; UM_distinctid=162ffa665dd67-07a48499ef5b31-61131b7e-100200-162ffa665de328; uva=s%3A264%3A%22a%3A4%3A%7Bs%3A13%3A%22host_pre_time%22%3Bs%3A10%3A%222018-04-26%22%3Bs%3A2%3A%22lt%22%3Bi%3A1524707679%3Bs%3A10%3A%22last_refer%22%3Bs%3A137%3A%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DxiO4hfvSXtB1cwKoCBHC3H9Hc5GV0L-6w-fbwp79jFf483qtmMYMw5Rj6Ti_nxdQ%26wd%3D%26eqid%3De701a76c00008ac7000000045ae13158%22%3Bs%3A5%3A%22rhost%22%3Bs%3A13%3A%22www.baidu.com%22%3B%7D%22%3B; __mfwurd=a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1524707679%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D; __mfwuuid=5ae1315d-3abe-49ce-ba03-2bbc42fada24; __mfwlv=1524731918; __mfwvn=3; __mfwlt=1524731918; CNZZDATA30065558=cnzz_eid%3D1179554685-1524705166-null%26ntime%3D1524726766");
			// 这个页面直接是json格式 不需要正则匹配
			String pageInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
			if (pageInfo == null) {
				logger.info("马蜂窝解析翻页url第" + i + "页失败" + "分类url为：" + str);
				continue;
			}
			JSONObject pageJson = JSONObject.parseObject(pageInfo);
			String allInfo = pageJson.getString("list");
			Document pageDocument = Jsoup.parse(allInfo);
			if (pageDocument == null) {
				logger.info("马蜂窝列表翻页docuemnt获取 失败 请检查  当前页数为" + i + "分类url为：" + str);
				continue;
			}

			Elements pageElements = pageDocument.select("div[class=tn-list]");
			if (pageElements.size() == 0) {
				logger.info("马蜂窝列表页面进入div失败,检查document是否存在" + JsonUrl);
				return;
			}
			pageElements = pageElements.select("div[class=tn-item clearfix]");
			for (int j = 0; j < pageElements.size(); j++) {
				Elements detailInfo = pageElements.get(j).select("a");
				DetailUrl = "http://www.mafengwo.cn" + detailInfo.attr("href");
				// System.out.println(DetailUrl);
				// set.add(DetailUrl);
				jedis.sadd("MFWdetail", DetailUrl);
			}
		}
		// System.out.println(set.size());
	}

	@Override
	public int getAllPage(String categoryUrl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAllPage(String categoryUrl, List<String> list) {
		// TODO Auto-generated method stub
		return 0;
	}

}
