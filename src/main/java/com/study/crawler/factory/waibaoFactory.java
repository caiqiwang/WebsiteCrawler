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
import com.util.CrawlerUtil.HttpClient;
import com.util.CrawlerUtil.HttpClientFactory;
import com.util.CrawlerUtil.RegexUtil;

import redis.clients.jedis.Jedis;

public class waibaoFactory {

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
		MarketPhoneSell.marketPhoneSellStart();
	}
}

class MarketPhoneSell {// 赶集网电话销售
	private static Logger logger = LoggerFactory.getLogger(waibaoFactory.class);
	private static String proxy = null;

	public static void marketPhoneSellStart() {

		List<String> categoryList = new ArrayList<String>();
		categoryList.add("http://shaoxing.ganji.com/zpdianhuaxiaoshou/");// 绍兴电话销售
		categoryList.add("http://huzhou.ganji.com/zpdianhuaxiaoshou/");// 湖州电话销售
		categoryList.add("http://zjtaizhou.ganji.com/zpdianhuaxiaoshou/");// 台州电话销售
		categoryList.add("http://wenzhou.ganji.com/zpdianhuaxiaoshou/");// 温州电话销售
		categoryList.add("http://nb.ganji.com/zpdianhuaxiaoshou/");// 宁波电话销售
		categoryList.add("http://jinhua.ganji.com/zpdianhuaxiaoshou/");// 金华电话销售
		categoryList.add("http://jiaxing.ganji.com/zpdianhuaxiaoshou/");// 嘉兴电话销售
		categoryList.add("http://lishui.ganji.com/zpdianhuaxiaoshou/");// 丽水电话销售
		categoryList.add("http://yiwu.ganji.com/zpdianhuaxiaoshou/");// 义乌电话销售
		categoryList.add("http://quzhou.ganji.com/zpdianhuaxiaoshou/");// 衢州电话销售
		Set<String> detailSet = new HashSet<String>();
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("foobared");
		for (String categoryUrl : categoryList) {
			detailSet.addAll(getDetailUrl(categoryUrl, jedis));
		}
		System.out.println(detailSet.size() + "==========1=======");
		List<String> excelList = new ArrayList<String>();
		excelList.add("公司名;类型;联系人;联系电话");
		for (String detailUrl : detailSet) {
			excelList.addAll(getDetailInfo(detailUrl, jedis));
		}
		System.out.println(excelList.size() + "=========2========");
		ExcelUtil.exportExcel("E:\\excel\\ganji", excelList, false);
	}

	public static Set<String> getDetailUrl(String categporyUrl, Jedis jedis) {
		int number = 3;
		Set<String> detailSet = new HashSet<String>();
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				CrawlParam crawlParam = new CrawlParam(categporyUrl);
				crawlParam.setCookie(
						"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; gj_footprint=%5B%5B%22%5Cu6728%5Cu5de5%22%2C%22%5C%2Fzpmugong%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; WantedListPageScreenType=1366; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A77935129787%7D; GANJISESSID=910lp87qkdepsufkqq2pk5u0or; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpmugong%7C-; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1526564337; __utma=32156897.142494278.1526293571.1526464052.1526564340.4; __utmc=32156897; __utmz=32156897.1526564340.4.3.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341; webimFangTips=3128938903; pos_detail_zcm_popup=2018-5-17; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWb1nHnOnW0zng98pZwVTHTOnhn3ujE1syuhrHcVPj0YPiY3uHELsHNkm16WuHwWn1T3uEDLnjmdP19vnW0KnEDzrHnQn1bzP1cQTHDknTDdrTDzc1nQczYWsEDzPBnVczYKnHNzPWNvPjndnWnQrEDQTyIJUA-1IAQ-uMEKPEDQTiYKsHDKsEDkTHTKsEDkTHu-rymdnhNOrywBnvPbmhc1mHNLmvDOrHI-PHcQP1wWTHndrHEYP1mOTHc1P1DLrHE3rH93njnzrTDVTHcWn1DWsinVTHcvczYWsEDQTiYKsEDVTgP-UAmKpZwY0jCfsv6lshI6UhGGshPfUitKnHD1rHT3PHcdrjmznjmOrjmdrHbdPEDQnHN8nHbzsWD3PB3QrHNKsEDQTynYmH66ujEknhwWPHwbPH0K%26v%3D2%22%2C1526565203675%5D; ganji_login_act=1526565204183; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1526565205; __utmt=1; __utmb=32156897.6.10.1526564340");

				crawlParam.setUseProxy(true);
				Document document = null;
				System.out.println();
				while (document == null || document.toString().contains("访问过于频繁")) {
					if (proxy == null) { // 为空
						logger.info("使用代理IP");
						Long size = jedis.scard("ganjiProxy");
						if (size == 0) {
							logger.info("代理IP池中代理意见全部使用，请重新设置。代码运行将结束");
							return null;
						}
						String infos = jedis.srandmember("ganjiProxy");
						proxy = infos;// 值替换
						String[] ipArrays = infos.split(":");
						jedis.srem("ganjiProxy", infos);
						crawlParam.setProxyHost(ipArrays[0]);
						crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
					} else {
						String[] ipArrays = proxy.split(":");
						crawlParam.setProxyHost(ipArrays[0]);
						crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
					}
					document = HttpClient.getDoGetDocument(crawlParam, number);
				}
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
					detailSet.add(detail);
				}
			} else {
				String newUrl = categporyUrl + "o" + (i + 1) + "/";
				CrawlParam crawlParam = new CrawlParam(newUrl);
				crawlParam.setCookie(
						"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; gj_footprint=%5B%5B%22%5Cu6728%5Cu5de5%22%2C%22%5C%2Fzpmugong%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; WantedListPageScreenType=1366; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A77935129787%7D; GANJISESSID=910lp87qkdepsufkqq2pk5u0or; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpmugong%7C-; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1526564337; __utma=32156897.142494278.1526293571.1526464052.1526564340.4; __utmc=32156897; __utmz=32156897.1526564340.4.3.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341; webimFangTips=3128938903; pos_detail_zcm_popup=2018-5-17; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWb1nHnOnW0zng98pZwVTHTOnhn3ujE1syuhrHcVPj0YPiY3uHELsHNkm16WuHwWn1T3uEDLnjmdP19vnW0KnEDzrHnQn1bzP1cQTHDknTDdrTDzc1nQczYWsEDzPBnVczYKnHNzPWNvPjndnWnQrEDQTyIJUA-1IAQ-uMEKPEDQTiYKsHDKsEDkTHTKsEDkTHu-rymdnhNOrywBnvPbmhc1mHNLmvDOrHI-PHcQP1wWTHndrHEYP1mOTHc1P1DLrHE3rH93njnzrTDVTHcWn1DWsinVTHcvczYWsEDQTiYKsEDVTgP-UAmKpZwY0jCfsv6lshI6UhGGshPfUitKnHD1rHT3PHcdrjmznjmOrjmdrHbdPEDQnHN8nHbzsWD3PB3QrHNKsEDQTynYmH66ujEknhwWPHwbPH0K%26v%3D2%22%2C1526565203675%5D; ganji_login_act=1526565204183; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1526565205; __utmt=1; __utmb=32156897.6.10.1526564340");

				crawlParam.setUseProxy(true);
				Document document = null;
				while (document == null || document.toString().contains("访问过于频繁")) {
					if (proxy == null) { // 为空
						logger.info("使用代理IP");
						Long size = jedis.scard("ganjiProxy");
						if (size == 0) {
							logger.info("代理IP池中代理意见全部使用，请重新设置。代码运行将结束");
							return null;
						}
						String infos = jedis.srandmember("ganjiProxy");
						proxy = infos;// 值替换
						String[] ipArrays = infos.split(":");
						jedis.srem("ganjiProxy", infos);
						crawlParam.setProxyHost(ipArrays[0]);
						crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
					} else {
						String[] ipArrays = proxy.split(":");
						crawlParam.setProxyHost(ipArrays[0]);
						crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
					}
					document = HttpClient.getDoGetDocument(crawlParam, number);
				}
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
					detailSet.add(detail);
				}
			}
		}
		return detailSet;
	}

	public static List<String> getDetailInfo(String detailUrl, Jedis jedis) {
		int number = 3;
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
				"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; gj_footprint=%5B%5B%22%5Cu6728%5Cu5de5%22%2C%22%5C%2Fzpmugong%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; WantedListPageScreenType=1366; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A77935129787%7D; GANJISESSID=910lp87qkdepsufkqq2pk5u0or; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpmugong%7C-; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1526564337; __utma=32156897.142494278.1526293571.1526464052.1526564340.4; __utmc=32156897; __utmz=32156897.1526564340.4.3.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341; webimFangTips=3128938903; pos_detail_zcm_popup=2018-5-17; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWb1nHnOnW0zng98pZwVTHTOnhn3ujE1syuhrHcVPj0YPiY3uHELsHNkm16WuHwWn1T3uEDLnjmdP19vnW0KnEDzrHnQn1bzP1cQTHDknTDdrTDzc1nQczYWsEDzPBnVczYKnHNzPWNvPjndnWnQrEDQTyIJUA-1IAQ-uMEKPEDQTiYKsHDKsEDkTHTKsEDkTHu-rymdnhNOrywBnvPbmhc1mHNLmvDOrHI-PHcQP1wWTHndrHEYP1mOTHc1P1DLrHE3rH93njnzrTDVTHcWn1DWsinVTHcvczYWsEDQTiYKsEDVTgP-UAmKpZwY0jCfsv6lshI6UhGGshPfUitKnHD1rHT3PHcdrjmznjmOrjmdrHbdPEDQnHN8nHbzsWD3PB3QrHNKsEDQTynYmH66ujEknhwWPHwbPH0K%26v%3D2%22%2C1526565203675%5D; ganji_login_act=1526565204183; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1526565205; __utmt=1; __utmb=32156897.6.10.1526564340");

		Document companyDocument = HttpClientFactory.getDocuemnt(crawlParam);
		crawlParam.setUseProxy(true);
		while (companyDocument == null || companyDocument.toString().contains("访问过于频繁")) {
			if (proxy == null) { // 为空
				logger.info("使用代理IP");
				Long size = jedis.scard("ganjiProxy");
				if (size == 0) {
					logger.info("代理IP池中代理意见全部使用，请重新设置。代码运行将结束");
					return null;
				}
				String infos = jedis.srandmember("ganjiProxy");
				proxy = infos;// 值替换
				String[] ipArrays = infos.split(":");
				jedis.srem("ganjiProxy", infos);
				crawlParam.setProxyHost(ipArrays[0]);
				crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
			} else {
				String[] ipArrays = proxy.split(":");
				crawlParam.setProxyHost(ipArrays[0]);
				crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
			}
			companyDocument = HttpClient.getDoGetDocument(crawlParam, number);
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
				"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; gj_footprint=%5B%5B%22%5Cu6728%5Cu5de5%22%2C%22%5C%2Fzpmugong%5C%2F%22%5D%2C%5B%22%5Cu7535%5Cu8bdd%5Cu9500%5Cu552e%22%2C%22%5C%2Fzpdianhuaxiaoshou%5C%2F%22%5D%5D; WantedListPageScreenType=1366; _gl_tracker=%7B%22ca_source%22%3A%22www.baidu.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22seo_baidu%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A77935129787%7D; GANJISESSID=910lp87qkdepsufkqq2pk5u0or; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpmugong%7C-; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1526564337; __utma=32156897.142494278.1526293571.1526464052.1526564340.4; __utmc=32156897; __utmz=32156897.1526564340.4.3.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341; webimFangTips=3128938903; pos_detail_zcm_popup=2018-5-17; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWb1nHnOnW0zng98pZwVTHTOnhn3ujE1syuhrHcVPj0YPiY3uHELsHNkm16WuHwWn1T3uEDLnjmdP19vnW0KnEDzrHnQn1bzP1cQTHDknTDdrTDzc1nQczYWsEDzPBnVczYKnHNzPWNvPjndnWnQrEDQTyIJUA-1IAQ-uMEKPEDQTiYKsHDKsEDkTHTKsEDkTHu-rymdnhNOrywBnvPbmhc1mHNLmvDOrHI-PHcQP1wWTHndrHEYP1mOTHc1P1DLrHE3rH93njnzrTDVTHcWn1DWsinVTHcvczYWsEDQTiYKsEDVTgP-UAmKpZwY0jCfsv6lshI6UhGGshPfUitKnHD1rHT3PHcdrjmznjmOrjmdrHbdPEDQnHN8nHbzsWD3PB3QrHNKsEDQTynYmH66ujEknhwWPHwbPH0K%26v%3D2%22%2C1526565203675%5D; ganji_login_act=1526565204183; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1526565205; __utmt=1; __utmb=32156897.6.10.1526564340");

		crawlParam.setUrlStr(phoneUrl);
		crawlParam.setUseProxy(true);
		Document document = null;
		while (document == null || document.toString().contains("访问过于频繁")) {
			if (proxy == null) { // 为空
				logger.info("使用代理IP");
				Long size = jedis.scard("ganjiProxy");
				if (size == 0) {
					logger.info("代理IP池中代理意见全部使用，请重新设置。代码运行将结束");
					return null;
				}
				String infos = jedis.srandmember("ganjiProxy");
				proxy = infos;// 值替换
				String[] ipArrays = infos.split(":");
				jedis.srem("ganjiProxy", infos);
				crawlParam.setProxyHost(ipArrays[0]);
				crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
			} else {
				String[] ipArrays = proxy.split(":");
				crawlParam.setProxyHost(ipArrays[0]);
				crawlParam.setProxyPort(Integer.parseInt(ipArrays[1]));
			}
			document = HttpClient.getDoGetDocument(crawlParam, number);
		}
		Elements elements = document.select("div[class=apply-pos-v2-tit]");
		String phone = elements.select("b").text();
		elements = elements.select("span[class=font-grey]");
		String name = elements.text();
		String[] array = name.split("（");
		name = array[0];
		String infos = MessageFormat.format("{0};{1};{2};{3}", company, type, name, phone);
		excelList.add(infos);
		return excelList;
	}
}

class fivePhoneSell {
	private static Logger logger = LoggerFactory.getLogger(waibaoFactory.class);

	public static void phoneSellStart() {
		List<String> categoryList = new ArrayList<String>();
		categoryList.add("http://sx.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-018e-3584-7fbdf14967c9&ClickID=1");// 绍兴电话销售
		categoryList.add("http://huzhou.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-0038-02af-7cc33d1c0fb2&ClickID=1");// 湖州
																														// 电话销售
		categoryList.add("http://tz.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-084a-5729-c5a535a88bd1&ClickID=1");// 台州电话销售
		categoryList.add("http://wz.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-08f8-bcee-ee093853e211&ClickID=1");// 温州电话销售
		categoryList.add("http://nb.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-0875-4c63-12094230a1f4&ClickID=1");// 宁波电话销售
		categoryList.add("http://jh.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-0bf5-e0e4-f59d257a028e&ClickID=1");// 金华电话销售
		categoryList.add("http://jx.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-0637-4d47-0b5ba912a230&ClickID=1");// 嘉兴电话销售
		categoryList.add("http://lishui.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-0301-4979-ff2a224bdf6d&ClickID=1");// 丽水电话销售
		categoryList
				.add("http://yueqingcity.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-08dd-5a09-1ab5a88ce12d&ClickID=1");// 乐清电话销售
		categoryList.add("http://quzhou.58.com/dianhuaxiaoshou/?PGTID=0d00255a-0000-015e-e645-1622862bc9b2&ClickID=1");// 衢州电话销售
		Set<String> detailSet = new HashSet<String>();
		for (String categoryUrl : categoryList) {
			detailSet.addAll(getDetailUrl(categoryUrl));
		}

	}

	public static Set<String> getDetailUrl(String categporyUrl) {
		Set<String> detailSet = new HashSet<String>();
		int page = getListPage(categporyUrl);
		if (page == 0) {
			logger.info("最大页数获取失败 检查url" + categporyUrl);
			return null;
		}
		String newUrl = categporyUrl.split("\\?PG")[0];
		for (int i = 0; i < page; i++) {
			if (i == 0) {
				CrawlParam crawlParam = new CrawlParam(categporyUrl);
				Document document = HttpClientFactory.getDocuemnt(crawlParam);
				;
				if (document == null) {
					logger.info("获取列表信息失败 检查url:" + categporyUrl);
					return null;
				}

			} else {
				newUrl = newUrl + "pn" + i + "/";
				CrawlParam crawlParam = new CrawlParam(newUrl);
				Document document = HttpClientFactory.getDocuemnt(crawlParam);
				if (document == null) {
					logger.info("获取列表信息失败 检查url:" + categporyUrl);
					return null;
				}
			}
		}
		return detailSet;
	}

	public static int getListPage(String categporyUrl) {
		int page = 0;
		CrawlParam crawlParam = new CrawlParam(categporyUrl);
		Document document = HttpClientFactory.getDocuemnt(crawlParam);
		Elements pageElement = document.select("div[class=pagesout]");
		if (pageElement.size() == 0) {
			logger.info("获取翻页方法中，进入div失败：" + categporyUrl);
			return 0;
		}
		pageElement = pageElement.select("span[class=num_operate]");
		pageElement = pageElement.select("i[class=total_page]");
		page = Integer.parseInt(pageElement.text());
		return page;
	}
}
