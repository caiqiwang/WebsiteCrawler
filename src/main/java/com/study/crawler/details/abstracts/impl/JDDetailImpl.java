package com.study.crawler.details.abstracts.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.small.crawler.util.CrawlerUtil;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.CompanyInfo;
import com.study.crawler.entity.RecruitmentInfo;

public class JDDetailImpl extends WebsiteDetailAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public JDDetailImpl(List<String> list) {
		super(list);
	}

	public JDDetailImpl() {

	}

	public static void main(String[] args) {
		String url = "https://item.jd.com/22947032974.html!超感觉";
		JDDetailImpl impl = new JDDetailImpl();
		impl.getWebsiteDetail(url);
	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setCharset("gbk");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"__jda=122270672.15233201980441143284421.1523320198.1523320198.1523320198.1; __jdb=122270672.22.15233201980441143284421|1.1523320198; __jdc=122270672; __jdv=122270672|baidu|-|organic|not set|1523320198044; PCSYCityID=1213; xtest=1136.cf6b6759; ipLoc-djd=1-72-2799-0; qrsc=3; rkv=V0300; 3AB9D23F7A4B3C9B=KBNBQRDMGP2GAHFIJSMYC7HL3LQKL7AB5DKQXWE443ZW2QL4VCH6WFRMAZJTYQ2J63JSMTTNVDQZZEHUI2RSTN43WI");
		String[] str2 = detailUrl.split("!");// 电子烟品牌
		String brand = str2[1];// 品牌名称
		String newUrl = str2[0];// 商品链接
		crawlParam.setUrlStr(newUrl);
		System.out.println("当前的url为：" + newUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("进入分类电子烟url失败 :" + newUrl);
		}
		Elements shopInfo = document.select("div[id=crumb-wrap][class=crumb-wrap]");
		if (shopInfo.size() == 0) {
			logger.info("进入详情电子烟div失败 :" + newUrl);
		}
		shopInfo = shopInfo.select("div[class=w]");
		Elements tradeName = shopInfo.select("div[class=crumb fl clearfix]");
		String tradeNames = tradeName.select("div[class=item ellipsis]").text();// 商品名称；
		Elements shopName = shopInfo.select("div[class=contact fr clearfix]");
		shopName = shopName.select("div[class=J-hove-wrap EDropdown fr]");
		shopName = shopName.select("div[class=item]");
		String shopNames = shopName.get(0).select("div[class=name]").select("a").text();// 店铺名

		String regex = "([\\d]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(newUrl);
		String id = "";
		if (match.find()) {
			id = match.group(1);
		}
		// 评价内容url
		String discussUrl = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv20&productId="
				+ id + "&score=1&sortType=5&page=0&pageSize=10&isShadowSku=0&fold=1";
		crawlParam = new CrawlParam();
		crawlParam.setCharset("gbk");
		crawlParam.setUrlStr(discussUrl);
		String discussInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		discussInfo = CrawlerUtil.matchBetweenSymbol(discussInfo, "vv20\\(", ")");
		// discussInfo = discussInfo.replaceAll("\\", "");
		System.out.println(discussInfo);
		JSONObject json = JSONObject.parseObject(discussInfo);
		JSONObject JSONCount = json.getJSONObject("productCommentSummary");

		String all = JSONCount.getString("commentCountStr");// 总评
		String bad = JSONCount.getString("poorCountStr");// 差评
		String badcomments = "";// 差评详情
		if (!bad.equals("0")) {
			JSONArray jsonArray = json.getJSONArray("comments");

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject comment = jsonArray.getJSONObject(i);
				badcomments = badcomments + (i + 1) + ". " + comment.getString("content") + " ";
			}
		}
		String info = tradeNames + ";" + newUrl + ";" + "" + ";" + all + ";" + bad + ";" + badcomments + ";" + brand
				+ ";" + shopNames + ";" + "京东" + ";";
		list.add(info);
		// String info = "商品名称;商品链接;月销量;评价数;差评数;差评内容;电子烟品牌;店铺名称;来源网站;";
	}

	@Override
	public CompanyInfo getCompanyInfo(String detailUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecruitmentInfo getRecruitmentInfo(String detailUrl) {
		// TODO Auto-generated method stub
		return null;
	};

}
