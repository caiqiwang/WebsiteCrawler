package com.study.crawler.details.abstracts.impl;

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
import com.study.crawler.client.ApplicationContextSave;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.MFWTravelNotesInfo;
import com.study.crawler.manager.impl.MFWManagerImpl;
import com.util.CrawlerUtil.RegexUtil;

public class MFWDetailImpl extends WebsiteDetailAbstract {
	private static Logger logger = LoggerFactory.getLogger(MFWDetailImpl.class);

	public MFWDetailImpl() {

	}

	public static void main(String[] args) {
		MFWDetailImpl mFWDetailImpl = new MFWDetailImpl();
		String url = "http://www.mafengwo.cn/i/9125281.html";
		mFWDetailImpl.getWebsiteDetail(url);
	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		MFWManagerImpl MFWImpl = ApplicationContextSave.getBean(MFWManagerImpl.class);
		if (detailUrl.indexOf("cid") > -1) {
			logger.info("该url为自由行攻略信息：" + detailUrl);
			return;
		}
		MFWTravelNotesInfo MFWTravelNotesInfo = new MFWTravelNotesInfo();
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setInterval(5000);
		crawlParam.setUrlStr(detailUrl);
		crawlParam.setCookie(
				"mfw_uuid=5ae13336-a04a-e6e0-94d7-b8c3590e67e5; oad_n=a%3A6%3A%7Bs%3A5%3A%22refer%22%3Bs%3A21%3A%22https%3A%2F%2Fwww.baidu.com%22%3Bs%3A2%3A%22wk%22%3Bs%3A6%3A%22%C2%ED%B7%E4%CE%D1%22%3Bs%3A2%3A%22hp%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A3%3A%22oid%22%3Bi%3A1026%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222018-05-02+18%3A23%3A44%22%3B%7D; __mfwlv=1525256452; __mfwvn=4; __mfwlt=1525257928; uva=s%3A78%3A%22a%3A3%3A%7Bs%3A2%3A%22lt%22%3Bi%3A1524708150%3Bs%3A10%3A%22last_refer%22%3Bs%3A6%3A%22direct%22%3Bs%3A5%3A%22rhost%22%3Bs%3A0%3A%22%22%3B%7D%22%3B; __mfwurd=a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1524708150%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A0%3A%22%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D; __mfwuuid=5ae13336-a04a-e6e0-94d7-b8c3590e67e5; PHPSESSID=mgmcbgcjfh821a836f8h0s2ld2; _r=baidu; _rp=a%3A2%3A%7Bs%3A1%3A%22p%22%3Bs%3A15%3A%22www.baidu.com%2Fs%22%3Bs%3A1%3A%22t%22%3Bi%3A1525256624%3B%7D");
		String bookId = "";
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(detailUrl);
		while (matcher.find()) {
			bookId = matcher.group(1);
		}

		MFWTravelNotesInfo.setUrl(detailUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("获取的document出错 url为：" + detailUrl);
			return;
		}

		if (document.select("div[id=_j_cover_box]").size() > 0) {// 不为旅游攻略
			Elements titleElement = document.select("div[id=_j_cover_box]");
			titleElement = titleElement.select("div[class=_j_titlebg]");
			titleElement = titleElement.select("div[class=view_info]");
			if (titleElement.size() == 0) {
				logger.info("进入获取标题 的div失败 检查url:" + detailUrl);
				return;
			}
			titleElement = titleElement.select("div[class=vi_con]");
			String title = titleElement.select("h1").text();
			MFWTravelNotesInfo.setTitle(title);
			String travelDetail = "";
			Elements travelDetailElements = document.select("div[class=view_con]");
			travelDetailElements = travelDetailElements.select("div[class=travel_directory _j_exscheduleinfo]");
			if (travelDetailElements.size() == 0) {
				travelDetail = "文章未填写该信息";
				MFWTravelNotesInfo.setTravelDetail(travelDetail);
			} else {
				travelDetailElements = travelDetailElements.select("ul");
				travelDetailElements = travelDetailElements.select("li");
				travelDetail = travelDetailElements.text();
				MFWTravelNotesInfo.setTravelDetail(travelDetail);
			}
			// 获取目录
			String catalogUrl = "http://pagelet.mafengwo.cn/note/pagelet/rightBottomCatalogApi?callback=jQuery1&params=%7B%22id%22%3A%22"
					+ bookId + "%22%7D";
			crawlParam.setUrlStr(catalogUrl);
			String catalogInfo = HttpURLConnectionFactory.getDocumentStr(crawlParam);
			if (catalogInfo == null || catalogInfo.equals("")) {
				logger.info(" 获取目录信息出错,当前的id为" + bookId + "目录URL为" + catalogUrl);
				return;
			}
			catalogInfo = RegexUtil.matchBetweenSymbol(catalogInfo, "jQuery1\\(", "\\)", true);
			JSONObject catelogJson = JSONObject.parseObject(catalogInfo);
			catelogJson = catelogJson.getJSONObject("data");
			String catelogHtml = catelogJson.getString("html");
			Document catelogDocument = Jsoup.parse(catelogHtml);
			if (catelogDocument == null) {
				logger.info("获取的目录的document出错 url为：" + catalogUrl);
				return;
			}
			Elements catalogElements = catelogDocument.select("div[id=_j_catalogList]");
			catalogElements = catalogElements.select("ul[class=catalog_content]");
			catalogElements = catalogElements.select("li");
			StringBuffer catalog = new StringBuffer();
			for (int i = 0; i < catalogElements.size(); i++) {
				catalog.append(catalogElements.get(i).text());
			}
			MFWTravelNotesInfo.setCatalog(catalog.toString());

		} else {
			Elements elementsTitle = document.select("div[class=post_title clearfix]");
			elementsTitle = elementsTitle.select("h1");
			String title = elementsTitle.text();
			MFWTravelNotesInfo.setTitle(title);
			String travelDetail = "";
			Elements travelDetailElements = document.select("div[class=summary]");
			travelDetailElements = travelDetailElements.select("div[class=travel_directory _j_exscheduleinfo]");
			travelDetailElements = travelDetailElements.select("div[class=tarvel_dir_list clearfix]");
			if (travelDetailElements.size() == 0) {
				travelDetail = "文章未填写该信息";
				MFWTravelNotesInfo.setTravelDetail(travelDetail);
			} else {
				travelDetailElements = travelDetailElements.select("ul");
				travelDetailElements = travelDetailElements.select("li");
				travelDetail = travelDetailElements.text();
				MFWTravelNotesInfo.setTravelDetail(travelDetail);
			}
			MFWTravelNotesInfo.setCatalog("暂无目录");

		}
		// 获取作者 喜欢数 查看数 时间 收藏等信息
		String infoUrl = "http://pagelet.mafengwo.cn/note/pagelet/headOperateApi?&params=%7B%22iid%22%3A%22" + bookId
				+ "%22%7D&_=1525258428276";
		crawlParam.setUrlStr(infoUrl);
		String Info = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		if (Info == null || Info.equals("")) {
			logger.info(" 获取作者等信息出错,当前的id为" + bookId + "目录URL为" + infoUrl);
			return;
		}
		JSONObject jsonInfo = JSONObject.parseObject(Info);
		jsonInfo = jsonInfo.getJSONObject("data");
		JSONObject readQuantityJson = jsonInfo.getJSONObject("controller_data");
		String travelPlace = readQuantityJson.getString("mdd_name");// 游记mdd
		String readQuantity = readQuantityJson.getString("pv");// 游记浏览量
		MFWTravelNotesInfo.setTravelPlace(travelPlace);
		MFWTravelNotesInfo.setReadQuantity(readQuantity);

		String jsonInfoHtml = jsonInfo.getString("html");
		Document jsonDocument = Jsoup.parse(jsonInfoHtml);
		if (jsonDocument == null) {
			logger.info("获取的作者等信息document出错 url为：" + infoUrl);
			return;
		}
		Elements likeInfo = jsonDocument.select("div[class=vt_center]");
		likeInfo = likeInfo.select("div[class=ding _j_ding_father]");
		likeInfo = likeInfo.select("div[class^=num]");
		String like = likeInfo.text();// 点赞数
		MFWTravelNotesInfo.setLike(like);
		Elements otherInfo = jsonDocument.select("div[class=person]");
		Elements peasonInfo = otherInfo.select("strong");
		String authorUrl = "http://www.mafengwo.cn" + peasonInfo.select("a").attr("href");
		String authorInfo = peasonInfo.select("a").text();// 作者链接和信息
		MFWTravelNotesInfo.setAuthorInfo(authorInfo);
		MFWTravelNotesInfo.setAuthorUrl(authorUrl);
		Elements releaseTimeInfo = otherInfo.select("div[class=vc_time]");
		releaseTimeInfo = releaseTimeInfo.select("span[class=time]");
		String releaseTime = releaseTimeInfo.text();
		MFWTravelNotesInfo.setReleaseTime(releaseTime);

		Elements shareInfo = jsonDocument.select("div[class=bar_share _j_share_father _j_top_share_group]");
		Elements share = shareInfo.select("div[class=bs_share]");
		share = share.select("a");
		share = share.select("span");
		String shareNumber = share.text();
		Elements collect = shareInfo.select("div[class=bs_collect ]");
		collect = collect.select("a");
		collect = collect.select("span");
		String collects = collect.text();
		MFWTravelNotesInfo.setCollect(collects);
		MFWTravelNotesInfo.setShareNumber(shareNumber);
		MFWImpl.insertMFWInfo(MFWTravelNotesInfo);
	}
}
