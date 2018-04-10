package com.study.crawler.details.abstracts.impl;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.CompanyInfo;
import com.study.crawler.entity.RecruitmentInfo;

public class JDDetailImpl extends WebsiteDetailAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {

	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"__jda=122270672.15233201980441143284421.1523320198.1523320198.1523320198.1; __jdb=122270672.22.15233201980441143284421|1.1523320198; __jdc=122270672; __jdv=122270672|baidu|-|organic|not set|1523320198044; PCSYCityID=1213; xtest=1136.cf6b6759; ipLoc-djd=1-72-2799-0; qrsc=3; rkv=V0300; 3AB9D23F7A4B3C9B=KBNBQRDMGP2GAHFIJSMYC7HL3LQKL7AB5DKQXWE443ZW2QL4VCH6WFRMAZJTYQ2J63JSMTTNVDQZZEHUI2RSTN43WI");
		String[] str2 = detailUrl.split("!");// 电子烟品牌
		String brand = str2[1];
		String newUrl = str2[0];
		crawlParam.setUrlStr(newUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("进入分类电子烟url失败 :" + newUrl);
		}

	};

	public CompanyInfo getCompanyInfo(String detailUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecruitmentInfo getRecruitmentInfo(String detailUrl) {
		// TODO Auto-generated method stub
		return null;
	}
}
