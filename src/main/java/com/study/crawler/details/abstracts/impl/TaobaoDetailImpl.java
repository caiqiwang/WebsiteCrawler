package com.study.crawler.details.abstracts.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.small.crawler.util.CrawlerUtil;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.CommodityInfo;
import com.util.CrawlerUtil.RegexUtil;

public class TaobaoDetailImpl extends WebsiteDetailAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public TaobaoDetailImpl(List<CommodityInfo> list) {
		super(list);
	}

	public TaobaoDetailImpl() {

	}

	public static void main(String[] args) {
		String url = "https://s.taobao.com/search?q=RELX%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306"
				+ "!RELX";
		TaobaoDetailImpl impl = new TaobaoDetailImpl();
		impl.getWebsiteDetail(url);
	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		System.out.println(detailUrl);
		// 淘宝不进入detail 直接在list获取

		CrawlParam crawlParam = new CrawlParam();
		String[] str2 = detailUrl.split("!");
		String brand = str2[1];// 品牌名称
		String newUrl = str2[0];// 商品链接
		int page = Integer.parseInt(str2[2]);
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"l=AtDQjVLQBi8zKSEhMpSUff1ooBQgqLTj; cna=b21AERllZBMCAXPHmqNO1XFf; thw=cn; isg=BAYG7CE8oPLwCHS-e0ouSQEtVP9Iz2HzduszpPAv_ikR86YNWPeaMeyBz68_wEI5; t=d434921d67195e175e56c2941803e622; hng=CN%7Czh-CN%7CCNY%7C156; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914CE25E48183086678EBDEC74B57C9244A2; enc=QE2Q8e7Bn0Tqt27ntXGHTTd9Sz2%2FKtguTHzZjJP1dn8UQmMYTu3KzbskmsmxegB%2BQ%2BrkyE5qOPyQ8vP6i7fdYA%3D%3D; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4FRYcmXYmgiIB4%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _cc_=U%2BGCWk%2F7og%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; mt=np=&ci=-1_0; uc1=cookie14=UoTeOoBZgTg6Ag%3D%3D; cookie2=232edad36a28c02c90a879e9406646b1; v=0; _tb_token_=eb8eee76e38e7; JSESSIONID=3FE488EC710B3008686E6B8F1369B4EC; alitrackid=www.taobao.com; lastalitrackid=www.taobao.com; swfstore=294096");
		String sales = "";
		String allcount = "";
		String storeName = "";
		String name = "";
		String detailUrls = "";
		String price = "";
		for (int i = 0; i < page; i++) {
			if (i == 0) {
				String nextUrl = "https://s.taobao.com/search?data-key=s&data-value=0&ajax=true&q=" + brand
						+ "%E7%94%B5%E5%AD%90%E7%83%9F&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20180428&ie=utf8&bcoffset=3&ntoffset=0&p4ppushleft=1%2C48";
				crawlParam.setUrlStr(nextUrl);
				String info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
				JSONObject AllInfo = JSONObject.parseObject(info);
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				AllInfo = AllInfo.getJSONObject("mods");
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				AllInfo = AllInfo.getJSONObject("itemlist");
				AllInfo = AllInfo.getJSONObject("data");
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				JSONArray allArrayInfo = AllInfo.getJSONArray("auctions");
				for (int j = 0; j < allArrayInfo.size(); j++) {
					CommodityInfo commodityInfo = new CommodityInfo();
					commodityInfo.setBrand(brand);

					JSONObject comment = allArrayInfo.getJSONObject(j);
					name = comment.getString("raw_title");
					if (!((name.indexOf(brand.toLowerCase()) > -1 || name.indexOf(brand.toUpperCase()) > -1
							|| name.indexOf(brand) > -1) && name.indexOf("电子烟") > -1 && name.indexOf("保护膜") < 0
							&& name.indexOf("保护套") < 0)) {
						continue;
					}
					name = name.replaceAll("&nbsp;", "");
					name = name.replaceAll("&amp;", "");
					name = name.replaceAll("amp;", "");
					commodityInfo.setName(name);
					sales = RegexUtil.matchNumber(comment.getString("view_sales"));
					commodityInfo.setSales(sales);
					detailUrls = comment.getString("detail_url");
					if (detailUrls.indexOf("tmall") > -1 || detailUrls.indexOf("simba") > -1) {
						logger.info("url格式不符合" + detailUrls);
						continue;
					}
					if (detailUrls.indexOf("http") < 0) {
						detailUrls = "https:" + detailUrls;
					}
					String[] detail = detailUrls.split("&ns");
					commodityInfo.setUrl(detail[0]);
					// 获取店铺名
					storeName = comment.getString("nick");
					commodityInfo.setStoreName(storeName);
					// 价格
					price = comment.getString("view_price");
					commodityInfo.setPrice(price);
					// 获取总评价数
					Pattern pattern = Pattern.compile("id=(\\d+)");
					Matcher matcher = pattern.matcher(detailUrls);
					String id = "";
					while (matcher.find()) {
						id = matcher.group(1);
					}
					String allCountUrl = "https://rate.taobao.com/detailCount.do?_ksTS=1524192825242_157&callback=jsonp158&itemId="
							+ id;
					crawlParam.setUrlStr(allCountUrl);
					String allCountInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
					if (allCountInfo == null) {
						commodityInfo.setAllcount("");
					} else {
						allCountInfo = CrawlerUtil.matchBetweenSymbol(allCountInfo, "158\\(", "\\)");
						JSONObject allCountJson = JSONObject.parseObject(allCountInfo);
						allcount = allCountJson.getString("count");
						commodityInfo.setAllcount(allcount);
					}
					list.add(commodityInfo);
				}
			} else {// 翻页url
				String nextUrl = "https://s.taobao.com/search?data-key=s&data-value=" + (i - 1) * 44 + "&ajax=true&q="
						+ brand
						+ "%E7%94%B5%E5%AD%90%E7%83%9F&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20180428&ie=utf8&bcoffset=3&ntoffset=0&p4ppushleft=1%2C48";
				crawlParam.setUrlStr(nextUrl);
				String info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
				JSONObject AllInfo = JSONObject.parseObject(info);
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				AllInfo = AllInfo.getJSONObject("mods");
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				AllInfo = AllInfo.getJSONObject("itemlist");
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				AllInfo = AllInfo.getJSONObject("data");
				if (AllInfo == null) {
					logger.info("错误url:" + nextUrl);
					continue;
				}
				JSONArray allArrayInfo = AllInfo.getJSONArray("auctions");
				for (int j = 0; j < allArrayInfo.size(); j++) {
					CommodityInfo commodityInfo = new CommodityInfo();
					commodityInfo.setBrand(brand);

					JSONObject comment = allArrayInfo.getJSONObject(j);
					name = comment.getString("raw_title");
					if (!((name.indexOf(brand.toLowerCase()) > -1 || name.indexOf(brand.toUpperCase()) > -1
							|| name.indexOf(brand) > -1) && name.indexOf("电子烟") > -1 && name.indexOf("保护膜") < 0
							&& name.indexOf("保护套") < 0)) {
						continue;
					}
					name = name.replaceAll("&nbsp;", "");
					name = name.replaceAll("&amp;", "");
					name = name.replaceAll("amp;", "");
					commodityInfo.setName(name);
					sales = RegexUtil.matchNumber(comment.getString("view_sales"));
					commodityInfo.setSales(sales);
					detailUrls = comment.getString("detail_url");
					if (detailUrls.indexOf("tmall") > -1 || detailUrls.indexOf("simba") > -1) {
						logger.info("url格式不符合" + detailUrls);
						continue;
					}
					if (detailUrls.indexOf("http") < 0) {
						detailUrls = "https:" + detailUrls;
					}
					String[] detail = detailUrls.split("&ns");
					commodityInfo.setUrl(detail[0]);

					// 获取店铺名
					storeName = comment.getString("nick");
					commodityInfo.setStoreName(storeName);
					// 价格
					price = comment.getString("view_price");
					commodityInfo.setPrice(price);
					// 获取总评价数
					Pattern pattern = Pattern.compile("id=(\\d+)");
					Matcher matcher = pattern.matcher(detailUrls);
					String id = "";
					while (matcher.find()) {
						id = matcher.group(1);
					}
					String allCountUrl = "https://rate.taobao.com/detailCount.do?_ksTS=1524192825242_157&callback=jsonp158&itemId="
							+ id;
					crawlParam.setUrlStr(allCountUrl);
					String allCountInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
					if (allCountInfo == null) {
						commodityInfo.setAllcount("");
					} else {
						allCountInfo = CrawlerUtil.matchBetweenSymbol(allCountInfo, "158\\(", "\\)");
						JSONObject allCountJson = JSONObject.parseObject(allCountInfo);
						allcount = allCountJson.getString("count");
						commodityInfo.setAllcount(allcount);
					}

					list.add(commodityInfo);
				}
			}
		}
		System.out.println("当前页面的爬取完毕后共有" + list.size());
		// 组合总评价数的url
		/*Pattern pattern = Pattern.compile("id=(\\d+)");
		Matcher matcher = pattern.matcher(newUrl);
		String id = "";
		while (matcher.find()) {
			id = matcher.group(1);
		}*/

		// 获取差评内容和数量
		/*String poorCountUrl = "https://rate.taobao.com/feedRateList.htm?auctionNumId=" + id
				+ "&userNumId=76694896&currentPageNum=1&pageSize=20&rateType=-1&orderType=sort_weight&attribute=&sku=&hasSku=false&folded=0&ua=098%23E1hvwQvWvPvvUvCkvvvvvjiPPFFhtjiPRLFZgjrCPmPwsj3URs5hzjlEP2LhsjYU3QhvCvvhvvvEvpCWmPKevvwY1nsZ6COqb64B9W2%2B%2BfvsxI2hTWeARFxjKOmxfXKKNB3r0j7Q%2Bul68NoxfaAKHkx%2Fzjc6kbhBfvDrzjc6%2Bulz8uAUSfe1%2BbyCvm9vvvvvphvvvvvv9krvpv2Dvvmm86Cv2vvvvUUdphvUOQvv9FnvpvkFuphvmvvv92V9BfH9kphvC9QvvOClLvhCvvOvUvvvphvPvpvhvv2MMsyCvvpvvvvviQhvCvvv9U8rvpvEvvA2v5EAvmkF9phvV0DwfNMjzHi47e1qzAuU6WRM6cnC4IjpKG4jOLyCvvpvvvvv&_ksTS=1524191559468_2298&callback=jsonp_tbcrate_reviews_list";
		crawlParam.setUrlStr(poorCountUrl);
		String poolCountInof = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		poolCountInof = CrawlerUtil.matchBetweenSymbol(poolCountInof, "list\\(", "\\)", true);
		JSONObject poorCountJson = JSONObject.parseObject(poolCountInof);
		
		JSONArray jsonArray = poorCountJson.getJSONArray("comments");
		String poorCount = jsonArray.size() + "";
		String poorInfo = "";
		if (jsonArray.size() != 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				poorInfo = poorInfo + (i + 1) + ". " + json.getString("content") + " ";
			}
		}
		commodityInfo.setPoorCount(poorCount);
		commodityInfo.setPoorInfo(poorInfo);*/
		// list.add(commodityInfo);
	}

}
