package com.study.crawler.factory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.ExcelUtil;
import com.util.CrawlerUtil.CrawlParam;
import com.util.CrawlerUtil.HttpClientFactory;
import com.util.CrawlerUtil.RegexUtil;

public class other {

	public static void main(String[] args) {
		/*	Set<String> detailSet = new HashSet<String>();
			// detailSet.addAll(MarketPhoneSell.getDetailUrl("http://jinhua.ganji.com/zpdianhuaxiaoshou/"));
			List<String> excelList = new ArrayList<String>();
		
			excelList.addAll(MarketPhoneSell.getDetailInfo("http://jinhua.ganji.com/zpshichangyingxiao/2863488352x.htm"));
		
			System.out.println(detailSet.size());*/
		/*String id = RegexUtil.matchNumber("http://jinhua.ganji.com/zpshichangyingxiao/2947857096x.htm");
		System.out.println(id);*/
		/*	String info = "没有找到合适的工作";
			Jedis jedis = new Jedis("127.0.0.1", 6379);
			jedis.auth("foobared");
			CrawlParam crawlParams = new CrawlParam("http://shaoxing.ganji.com/zpdianhuaxiaoshou/");
			ProxyUtil.getProxyPool(crawlParams, jedis, "ganjiProxy", info, "xici");*/
		MarketPhoneSell2.marketPhoneSellStart();
	}
}

class MarketPhoneSell2 {// 赶集网电话销售
	private static Logger logger = LoggerFactory.getLogger(waibaoFactory.class);

	public static void marketPhoneSellStart() {

		List<String> categoryList = new ArrayList<String>();
		// categoryList.add("http://shaoxing.ganji.com/zpdianhuaxiaoshou/");//
		// 绍兴电话销售
		// categoryList.add("http://huzhou.ganji.com/zpdianhuaxiaoshou/");//
		// 湖州电话销售
		categoryList.add("http://zjtaizhou.ganji.com/zpdianhuaxiaoshou/");// 台州电话销售
		// categoryList.add("http://wenzhou.ganji.com/zpdianhuaxiaoshou/");//
		// 温州电话销售
		// categoryList.add("http://nb.ganji.com/zpdianhuaxiaoshou/");// 宁波电话销售
		// categoryList.add("http://jinhua.ganji.com/zpdianhuaxiaoshou/");//
		// 金华电话销售
		// categoryList.add("http://jiaxing.ganji.com/zpdianhuaxiaoshou/");//
		// 嘉兴电话销售
		// categoryList.add("http://lishui.ganji.com/zpdianhuaxiaoshou/");//
		// 丽水电话销售
		// categoryList.add("http://yiwu.ganji.com/zpdianhuaxiaoshou/");//
		// 义乌电话销售
		// categoryList.add("http://quzhou.ganji.com/zpdianhuaxiaoshou/");//
		// 衢州电话销售
		Set<String> detailSet = new HashSet<String>();
		// Jedis jedis = new Jedis("127.0.0.1", 6379);
		// jedis.auth("foobared");
		for (String categoryUrl : categoryList) {
			detailSet.addAll(getDetailUrl(categoryUrl));
		}
		System.out.println(detailSet.size() + "===============");
		List<String> excelList = new ArrayList<String>();
		excelList.add("公司名;类型;联系人;联系电话");
		for (String detailUrl : detailSet) {
			excelList.addAll(getDetailInfo(detailUrl));
		}
		System.out.println(excelList.size() + "========");
		ExcelUtil.exportExcel("E:\\excel\\ganji.xls", excelList);
	}

	public static Set<String> getDetailUrl(String categporyUrl) {
		// int number = 3;
		Set<String> detailSet = new HashSet<String>();
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				CrawlParam crawlParam = new CrawlParam(categporyUrl);
				/*String info = jedis.srandmember("ganjiProxy");
				String[] ipArray = info.split(":");
				jedis.srem("ganjiProxy", info);
				crawlParam.setProxyHost(ipArray[0]);
				crawlParam.setProxyPort(Integer.parseInt(ipArray[1]));
				crawlParam.setUseProxy(true);*/
				Document document = HttpClientFactory.getDocuemnt(crawlParam);
				Elements pageElements = document.select("div[class=new-dl-wrapper]");
				pageElements = pageElements.select("div[data-widget=app/ms_v2/wanted/list.js#companyAjaxBid]");
				if (pageElements.size() == 0) {
					logger.info("获取列表信息失败 检查url:" + categporyUrl);
					continue;
				}
				pageElements = pageElements.select("dl");
				for (int j = 0; j < pageElements.size(); j++) {
					Elements detailElements = pageElements.get(j).select("dt");
					String detail = detailElements.select("a").attr("href");
					/*detailElements = detailElements.select("div[class=new-dl-company]");
					String company = detailElements.select("a").text();
					detail = detail + "!" + company;*/
					detailSet.add(detail);
				}
			} else {
				String newUrl = categporyUrl + "o" + (i + 1) + "/";
				CrawlParam crawlParam = new CrawlParam(newUrl);
				// crawlParam.setUseProxy(true);
				Document document = HttpClientFactory.getDocuemnt(crawlParam);
				Elements istrue = document.select("div[class=lab-zbd]");
				if (istrue.size() > 0) {// 判断页面是否存在
					logger.info("该页面信息不存在:" + newUrl);
					return detailSet;
				}
				Elements pageElements = document.select("div[class=new-dl-wrapper]");
				pageElements = pageElements.select("div[data-widget=app/ms_v2/wanted/list.js#companyAjaxBid]");
				if (pageElements.size() == 0) {
					logger.info("获取列表信息失败 检查url:" + newUrl);
					return detailSet;
				}
				pageElements = pageElements.select("dl");
				if (pageElements.size() == 0) {
					logger.info("列表信息可能为空 或者获取失败  检查url:" + newUrl);
					return detailSet;
				}
				for (int j = 0; j < pageElements.size(); j++) {
					Elements detailElements = pageElements.get(j).select("dt");
					String detail = detailElements.select("a").attr("href");
					/*detailElements = detailElements.select("div[class=new-dl-company]");
					String company = detailElements.select("a").text();
					detail = detail + "!" + company;*/
					detailSet.add(detail);
				}
			}
		}
		return detailSet;
	}

	public static List<String> getDetailInfo(String detailUrl) {
		List<String> excelList = new ArrayList<String>();
		if (!detailUrl.contains("ganji")) {
			logger.info("该招聘不为赶集招聘 :" + detailUrl);
			return excelList;
		}
		// 获取id
		String id = RegexUtil.matchNumber(detailUrl);
		if (id.length() <= 9) {
			logger.info("该url有问题 检查 :" + detailUrl);
			return excelList;
		}
		// 获取公司名
		CrawlParam crawlParam = new CrawlParam(detailUrl);
		crawlParam.setCookie(
				"ganji_uuid=3024473084233674116085; ganji_xuuid=3c8cd2b5-ebac-4d50-ee05-d0a3662a8b6b.1526276564440; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A49364560764%7D; GANJISESSID=0q8nfbp5ue098csjl89n20938u; xxzl_deviceid=UeAydWPnxjR2pfrSJnW5CMQWA8HrGPVI4cr9jZRuEfN1WgeaDTAX0jK1AFyx1pYE; cityDomain=hz; gj_footprint=%5B%5B%22%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpshichangyingxiao%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; lg=1; STA_DS=1; WantedListPageScreenType=1366; pos_detail_zcm_popup=2018-5-14; sscode=3G3ac%2BnbJAJDcepk3Gl7FOfy; GanjiUserName=%23t_771438645; GanjiUserInfo=%7B%22user_id%22%3A771438645%2C%22email%22%3A%22%22%2C%22username%22%3A%22%23t_771438645%22%2C%22user_name%22%3A%22%23t_771438645%22%2C%22nickname%22%3A%22%22%7D; bizs=%5B%5D; supercookie=AmpkAQZ4AwD1WQN5MQExMwD2MzD0LGL4BTL2AQx2LzLkLGD5MQLlAwIuLwZkMGp4ZwZ%3D; GanjiLoginType=1; xxzl_smartid=c247698df7f3780d22ff63a5939de343; citydomain=jinhua; last_name=%23t_771438645; ganji_login_act=1526293088312");
		Document companyDocument = HttpClientFactory.getDocuemnt(crawlParam);
		if (companyDocument == null) {
			logger.info("获取公司信息document失败 检查url:" + detailUrl);
			return excelList;
		}
		Elements companyElements = companyDocument.select("div[class=right-box]");
		companyElements = companyElements.select("div[class=module-company]");
		companyElements = companyElements.select("div[class=company-info]");
		if (companyElements.size() == 0) {
			logger.info("公司信息div不存在:" + detailUrl);
			return excelList;
		}
		String company = companyElements.select("h3").text();
		Pattern pattern = Pattern.compile("//(\\w+)\\.");
		Matcher mahcher = pattern.matcher(detailUrl);
		String place = "";// 获取地区名
		while (mahcher.find()) {
			place = mahcher.group(1);
		}

		// 拼凑电话页面的url
		String phoneUrl = "http://www.ganji.com/pub/pub.php?act=pub&cid=11&reply=32%3B" + id + "%3B2&fbranch=i&domain="
				+ place + "&is_iframe=1&from=viewFullPhone&source_position=wanted_detail_tel_pub";

		String type = "电话销售";
		crawlParam = new CrawlParam();
		crawlParam.setCookie(
				"ganji_uuid=3024473084233674116085; ganji_xuuid=3c8cd2b5-ebac-4d50-ee05-d0a3662a8b6b.1526276564440; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A49364560764%7D; GANJISESSID=0q8nfbp5ue098csjl89n20938u; xxzl_deviceid=UeAydWPnxjR2pfrSJnW5CMQWA8HrGPVI4cr9jZRuEfN1WgeaDTAX0jK1AFyx1pYE; cityDomain=hz; gj_footprint=%5B%5B%22%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpshichangyingxiao%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; lg=1; STA_DS=1; WantedListPageScreenType=1366; pos_detail_zcm_popup=2018-5-14; sscode=3G3ac%2BnbJAJDcepk3Gl7FOfy; GanjiUserName=%23t_771438645; GanjiUserInfo=%7B%22user_id%22%3A771438645%2C%22email%22%3A%22%22%2C%22username%22%3A%22%23t_771438645%22%2C%22user_name%22%3A%22%23t_771438645%22%2C%22nickname%22%3A%22%22%7D; bizs=%5B%5D; supercookie=AmpkAQZ4AwD1WQN5MQExMwD2MzD0LGL4BTL2AQx2LzLkLGD5MQLlAwIuLwZkMGp4ZwZ%3D; GanjiLoginType=1; xxzl_smartid=c247698df7f3780d22ff63a5939de343; citydomain=jinhua; last_name=%23t_771438645; ganji_login_act=1526293088312");

		crawlParam.setUrlStr(phoneUrl);
		Document document = HttpClientFactory.getDocuemnt(crawlParam);
		if (document == null) {
			logger.info("获取列表信息失败 检查url:" + phoneUrl);
			return excelList;
		}
		Elements elements = document.select("div[class=apply-pos-v2-tit]");
		String phone = elements.select("b").text();
		elements = elements.select("span[class=font-grey]");
		String name = elements.text();
		String[] array = name.split("（");
		name = array[0];
		String infos = MessageFormat.format("{0};{1};{2};{3}", company, type, name, phone);
		excelList.add(infos);
		System.out.println("=====" + infos + "=====");
		return excelList;
	}
}
