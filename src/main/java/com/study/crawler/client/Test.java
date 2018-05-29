package com.study.crawler.client;

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
import org.springframework.stereotype.Service;

import com.small.crawler.util.ExcelUtil;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.category.ProxySpirits;
import com.study.crawler.factory.waibaoFactory;
import com.util.CrawlerUtil.RegexUtil;

import redis.clients.jedis.Jedis;

@Service
public class Test {
	public static void main(String[] args) {
		// MarketPhoneSell m=new MarketPhoneSell();
		MarketPhoneSells.marketPhoneSellStart();
	}

}

class MarketPhoneSells {// 赶集网电话销售
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
		Set<String> detailSet = new HashSet<String>();
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("foobared");
		System.out.println("开始");
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
		ExcelUtil.exportExcel("E:\\excel\\ganji.xls", excelList, false);
	}

	public static Set<String> getDetailUrl(String categporyUrl, Jedis jedis) {
		int number = 3;
		String ip = "";
		String host = "";
		Set<String> detailSet = new HashSet<String>();
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				CrawlParam crawlParam = new CrawlParam(categporyUrl);
				crawlParam.setCookie(
						"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; WantedListPageScreenType=1366; __utma=32156897.142494278.1526293571.1526564340.1527074291.5; __utmc=32156897; __utmz=32156897.1527074291.5.4.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; bizs=%5B%5D; gj_footprint=%5B%5B%22%5Cu5feb%5Cu9012%5Cu5458%22%2C%22%5C%2Fzpkuaidi%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337,1527074479; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341,1527074480; pos_detail_zcm_popup=2018-5-23; username_login_n=cqw153212; GanjiLoginType=0; last_name=cqw153212; sscode=CecJpOerLk%2BY3qpVCexCiYrz; GANJISESSID=vvipuso6hb475mjaees8lp8qc2; GanjiEmail=1532129385%40qq.com; GanjiUserName=cqw153212; GanjiUserInfo=%7B%22user_id%22%3A773098813%2C%22email%22%3A%221532129385%40qq.com%22%2C%22username%22%3A%22cqw153212%22%2C%22user_name%22%3A%22cqw153212%22%2C%22nickname%22%3A%22%22%7D; _gl_tracker=%7B%22ca_source%22%3A%22mail.qq.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22other_mail.qq.com%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A69486787630%7D; 58tj_uuid=fccf0e57-2348-490f-a4a2-847fef0b5f12; new_session=1; new_uv=1; utm_source=; spm=; init_refer=http%253A%252F%252Fwww.ganji.com%252Fvip%252Faccount%252Fauth.php%253F_rid%253D0.8423298655211711; als=0; wmda_uuid=bb0452df1aa983a7855894cd32507104; wmda_new_uuid=1; wmda_session_id_3603688536834=1527074730952-8e9040fb-b890-1695; wmda_visited_projects=%3B3603688536834; findjob_zcm_banner=2018-5-23; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpkuaidi%7C-%2Chz%7Czpmugong%7C-; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1527074755; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWbvPWNQnW0kPZ98pZwVTHEdPjDzP1N1syubPHTVPjuWuBY3PWnQsHTLuWw-myDYPyNQPTDLPH9zPHD1PWNKnEDzrHmvPHDzP1TYTHDknTDQnjTKnBn1ninVczYKnWmWsinVTHDdnW0kP1ELP1bvP1mKnk7MphQG0Lw_uyuYTHNKnEDVTiYQTiYKnTDkTiYKnT7-mWbOmWmzuAEvPhuWrHI6uHTdrHNzrjIbuH7hrHKBPkD1PWEdnWDkrEDzn1bLnHbznH91n19krjTKsEDzc1nQczYWsEDzPBnVczYKnEDVTiYKsE71uyQhTy6YIZTlszqCXBOMmyOJpiOWUvYfTHDQn1bkrjNzPH9vnWTvrH9vPHbOPHNKnH91sWDzra3QPjc8rHbKP101njb3rjD1THDKPAmLm1mvnWDvnHw6rAc1PkD%26v%3D2%22%2C1527074763793%5D; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1527074764; webimFangTips=773098813; ganji_login_act=1527074774846; __utmb=32156897.26.10.1527074291; xxzl_smartid=84fef28524dfba45d3b652d62c753333");
				crawlParam.setUseProxy(true);
				Document document = null;
				while (document == null || document.toString().contains("访问过于频繁")) {
					if (proxy == null) { // 为空
						logger.info("使用代理IP");
						String infos = ProxySpirits.getUsefulProxy();
						if (infos == null) {
							return null;
						}
						proxy = infos;// 值替换
						String[] ipArrays = infos.split(":");
						ip = ipArrays[0];
						host = ipArrays[1];
					} else {
						String[] ipArrays = proxy.split(":");
						ip = ipArrays[0];
						host = ipArrays[1];
					}
					document = HttpURLConnectionFactory.getDocument(crawlParam, ip, host);
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
						"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; WantedListPageScreenType=1366; __utma=32156897.142494278.1526293571.1526564340.1527074291.5; __utmc=32156897; __utmz=32156897.1527074291.5.4.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; bizs=%5B%5D; gj_footprint=%5B%5B%22%5Cu5feb%5Cu9012%5Cu5458%22%2C%22%5C%2Fzpkuaidi%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337,1527074479; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341,1527074480; pos_detail_zcm_popup=2018-5-23; username_login_n=cqw153212; GanjiLoginType=0; last_name=cqw153212; sscode=CecJpOerLk%2BY3qpVCexCiYrz; GANJISESSID=vvipuso6hb475mjaees8lp8qc2; GanjiEmail=1532129385%40qq.com; GanjiUserName=cqw153212; GanjiUserInfo=%7B%22user_id%22%3A773098813%2C%22email%22%3A%221532129385%40qq.com%22%2C%22username%22%3A%22cqw153212%22%2C%22user_name%22%3A%22cqw153212%22%2C%22nickname%22%3A%22%22%7D; _gl_tracker=%7B%22ca_source%22%3A%22mail.qq.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22other_mail.qq.com%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A69486787630%7D; 58tj_uuid=fccf0e57-2348-490f-a4a2-847fef0b5f12; new_session=1; new_uv=1; utm_source=; spm=; init_refer=http%253A%252F%252Fwww.ganji.com%252Fvip%252Faccount%252Fauth.php%253F_rid%253D0.8423298655211711; als=0; wmda_uuid=bb0452df1aa983a7855894cd32507104; wmda_new_uuid=1; wmda_session_id_3603688536834=1527074730952-8e9040fb-b890-1695; wmda_visited_projects=%3B3603688536834; findjob_zcm_banner=2018-5-23; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpkuaidi%7C-%2Chz%7Czpmugong%7C-; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1527074755; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWbvPWNQnW0kPZ98pZwVTHEdPjDzP1N1syubPHTVPjuWuBY3PWnQsHTLuWw-myDYPyNQPTDLPH9zPHD1PWNKnEDzrHmvPHDzP1TYTHDknTDQnjTKnBn1ninVczYKnWmWsinVTHDdnW0kP1ELP1bvP1mKnk7MphQG0Lw_uyuYTHNKnEDVTiYQTiYKnTDkTiYKnT7-mWbOmWmzuAEvPhuWrHI6uHTdrHNzrjIbuH7hrHKBPkD1PWEdnWDkrEDzn1bLnHbznH91n19krjTKsEDzc1nQczYWsEDzPBnVczYKnEDVTiYKsE71uyQhTy6YIZTlszqCXBOMmyOJpiOWUvYfTHDQn1bkrjNzPH9vnWTvrH9vPHbOPHNKnH91sWDzra3QPjc8rHbKP101njb3rjD1THDKPAmLm1mvnWDvnHw6rAc1PkD%26v%3D2%22%2C1527074763793%5D; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1527074764; webimFangTips=773098813; ganji_login_act=1527074774846; __utmb=32156897.26.10.1527074291; xxzl_smartid=84fef28524dfba45d3b652d62c753333");

				crawlParam.setUseProxy(true);
				Document document = null;
				while (document == null || document.toString().contains("访问过于频繁")) {
					if (proxy == null) { // 为空
						logger.info("使用代理IP");
						String infos = ProxySpirits.getUsefulProxy();
						if (infos == null) {
							return null;
						}
						proxy = infos;// 值替换
						String[] ipArrays = infos.split(":");
						ip = ipArrays[0];
						host = ipArrays[1];
					} else {
						String[] ipArrays = proxy.split(":");
						ip = ipArrays[0];
						host = ipArrays[1];
					}
					document = HttpURLConnectionFactory.getDocument(crawlParam, ip, host);
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
		if (id.length() <= 8) {
			logger.info("该url有问题 检查 :" + detailUrl);
			return excelList;
		}
		String ip = "";
		String host = "";
		// 获取公司名
		CrawlParam crawlParam = new CrawlParam(detailUrl);
		crawlParam.setCookie(
				"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; WantedListPageScreenType=1366; __utma=32156897.142494278.1526293571.1526564340.1527074291.5; __utmc=32156897; __utmz=32156897.1527074291.5.4.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; bizs=%5B%5D; gj_footprint=%5B%5B%22%5Cu5feb%5Cu9012%5Cu5458%22%2C%22%5C%2Fzpkuaidi%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337,1527074479; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341,1527074480; pos_detail_zcm_popup=2018-5-23; username_login_n=cqw153212; GanjiLoginType=0; last_name=cqw153212; sscode=CecJpOerLk%2BY3qpVCexCiYrz; GANJISESSID=vvipuso6hb475mjaees8lp8qc2; GanjiEmail=1532129385%40qq.com; GanjiUserName=cqw153212; GanjiUserInfo=%7B%22user_id%22%3A773098813%2C%22email%22%3A%221532129385%40qq.com%22%2C%22username%22%3A%22cqw153212%22%2C%22user_name%22%3A%22cqw153212%22%2C%22nickname%22%3A%22%22%7D; _gl_tracker=%7B%22ca_source%22%3A%22mail.qq.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22other_mail.qq.com%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A69486787630%7D; 58tj_uuid=fccf0e57-2348-490f-a4a2-847fef0b5f12; new_session=1; new_uv=1; utm_source=; spm=; init_refer=http%253A%252F%252Fwww.ganji.com%252Fvip%252Faccount%252Fauth.php%253F_rid%253D0.8423298655211711; als=0; wmda_uuid=bb0452df1aa983a7855894cd32507104; wmda_new_uuid=1; wmda_session_id_3603688536834=1527074730952-8e9040fb-b890-1695; wmda_visited_projects=%3B3603688536834; findjob_zcm_banner=2018-5-23; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpkuaidi%7C-%2Chz%7Czpmugong%7C-; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1527074755; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWbvPWNQnW0kPZ98pZwVTHEdPjDzP1N1syubPHTVPjuWuBY3PWnQsHTLuWw-myDYPyNQPTDLPH9zPHD1PWNKnEDzrHmvPHDzP1TYTHDknTDQnjTKnBn1ninVczYKnWmWsinVTHDdnW0kP1ELP1bvP1mKnk7MphQG0Lw_uyuYTHNKnEDVTiYQTiYKnTDkTiYKnT7-mWbOmWmzuAEvPhuWrHI6uHTdrHNzrjIbuH7hrHKBPkD1PWEdnWDkrEDzn1bLnHbznH91n19krjTKsEDzc1nQczYWsEDzPBnVczYKnEDVTiYKsE71uyQhTy6YIZTlszqCXBOMmyOJpiOWUvYfTHDQn1bkrjNzPH9vnWTvrH9vPHbOPHNKnH91sWDzra3QPjc8rHbKP101njb3rjD1THDKPAmLm1mvnWDvnHw6rAc1PkD%26v%3D2%22%2C1527074763793%5D; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1527074764; webimFangTips=773098813; ganji_login_act=1527074774846; __utmb=32156897.26.10.1527074291; xxzl_smartid=84fef28524dfba45d3b652d62c753333");

		Document companyDocument = null;
		crawlParam.setUseProxy(true);
		while (companyDocument == null || companyDocument.toString().contains("访问过于频繁")) {
			if (proxy == null) { // 为空
				logger.info("使用代理IP");
				String infos = ProxySpirits.getUsefulProxy();
				if (infos == null) {
					return null;
				}
				proxy = infos;// 值替换
				String[] ipArrays = infos.split(":");
				ip = ipArrays[0];
				host = ipArrays[1];
			} else {
				String[] ipArrays = proxy.split(":");
				ip = ipArrays[0];
				host = ipArrays[1];
			}
			companyDocument = HttpURLConnectionFactory.getDocument(crawlParam, ip, host);
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
				"statistics_clientid=me; lg=1; ganji_uuid=1139085258620698659955; xxzl_deviceid=wRoCeGDnEPrfMtTYqIhZ9zgXI8fBIl0YaeDnWddFs6aD5MeNXeApYPbK3my8NJGi; cityDomain=hz; ganji_xuuid=9d6c404f-4982-4763-eeb6-fc0d8d390a30.1526449113921; WantedListPageScreenType=1366; __utma=32156897.142494278.1526293571.1526564340.1527074291.5; __utmc=32156897; __utmz=32156897.1527074291.5.4.utmcsr=hz.ganji.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; bizs=%5B%5D; gj_footprint=%5B%5B%22%5Cu5feb%5Cu9012%5Cu5458%22%2C%22%5C%2Fzpkuaidi%5C%2F%22%5D%5D; _wap__utmganji_wap_newCaInfo_V2=%7B%22ca_n%22%3A%22-%22%2C%22ca_s%22%3A%22self%22%2C%22ca_i%22%3A%22-%22%7D; Hm_lvt_655ab0c3b3fdcfa236c3971a300f3f29=1526449218,1526564337,1527074479; Hm_lvt_8da53a2eb543c124384f1841999dcbb8=1526449223,1526564341,1527074480; pos_detail_zcm_popup=2018-5-23; username_login_n=cqw153212; GanjiLoginType=0; last_name=cqw153212; sscode=CecJpOerLk%2BY3qpVCexCiYrz; GANJISESSID=vvipuso6hb475mjaees8lp8qc2; GanjiEmail=1532129385%40qq.com; GanjiUserName=cqw153212; GanjiUserInfo=%7B%22user_id%22%3A773098813%2C%22email%22%3A%221532129385%40qq.com%22%2C%22username%22%3A%22cqw153212%22%2C%22user_name%22%3A%22cqw153212%22%2C%22nickname%22%3A%22%22%7D; _gl_tracker=%7B%22ca_source%22%3A%22mail.qq.com%22%2C%22ca_name%22%3A%22-%22%2C%22ca_kw%22%3A%22-%22%2C%22ca_id%22%3A%22-%22%2C%22ca_s%22%3A%22other_mail.qq.com%22%2C%22ca_n%22%3A%22-%22%2C%22ca_i%22%3A%22-%22%2C%22sid%22%3A69486787630%7D; 58tj_uuid=fccf0e57-2348-490f-a4a2-847fef0b5f12; new_session=1; new_uv=1; utm_source=; spm=; init_refer=http%253A%252F%252Fwww.ganji.com%252Fvip%252Faccount%252Fauth.php%253F_rid%253D0.8423298655211711; als=0; wmda_uuid=bb0452df1aa983a7855894cd32507104; wmda_new_uuid=1; wmda_session_id_3603688536834=1527074730952-8e9040fb-b890-1695; wmda_visited_projects=%3B3603688536834; findjob_zcm_banner=2018-5-23; zhaopin_lasthistory=zpfangjingjiren%7Czpfangjingjiren; zhaopin_historyrecords=hz%7Czpfangjingjiren%7C-%2Chz%7Czpkuaidi%7C-%2Chz%7Czpmugong%7C-; Hm_lpvt_655ab0c3b3fdcfa236c3971a300f3f29=1527074755; _gl_speed=%5B%22http%3A%2F%2Fcjump.ganji.com%2Fgjclick%3Ftarget%3DpZwY0jCfsv6lshI6UhGGshPfUiql0Au6UhIJpyOMph-zuy3fnWbvPWNQnW0kPZ98pZwVTHEdPjDzP1N1syubPHTVPjuWuBY3PWnQsHTLuWw-myDYPyNQPTDLPH9zPHD1PWNKnEDzrHmvPHDzP1TYTHDknTDQnjTKnBn1ninVczYKnWmWsinVTHDdnW0kP1ELP1bvP1mKnk7MphQG0Lw_uyuYTHNKnEDVTiYQTiYKnTDkTiYKnT7-mWbOmWmzuAEvPhuWrHI6uHTdrHNzrjIbuH7hrHKBPkD1PWEdnWDkrEDzn1bLnHbznH91n19krjTKsEDzc1nQczYWsEDzPBnVczYKnEDVTiYKsE71uyQhTy6YIZTlszqCXBOMmyOJpiOWUvYfTHDQn1bkrjNzPH9vnWTvrH9vPHbOPHNKnH91sWDzra3QPjc8rHbKP101njb3rjD1THDKPAmLm1mvnWDvnHw6rAc1PkD%26v%3D2%22%2C1527074763793%5D; Hm_lpvt_8da53a2eb543c124384f1841999dcbb8=1527074764; webimFangTips=773098813; ganji_login_act=1527074774846; __utmb=32156897.26.10.1527074291; xxzl_smartid=84fef28524dfba45d3b652d62c753333");

		crawlParam.setUrlStr(phoneUrl);
		crawlParam.setUseProxy(true);
		Document document = null;
		while (document == null || document.toString().contains("访问过于频繁")) {
			if (proxy == null) { // 为空
				logger.info("使用代理IP");
				String infos = ProxySpirits.getUsefulProxy();
				if (infos == null) {
					return null;
				}
				proxy = infos;// 值替换
				String[] ipArrays = infos.split(":");
				ip = ipArrays[0];
				host = ipArrays[1];
			} else {
				String[] ipArrays = proxy.split(":");
				ip = ipArrays[0];
				host = ipArrays[1];
			}
			document = HttpURLConnectionFactory.getDocument(crawlParam, ip, host);
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
