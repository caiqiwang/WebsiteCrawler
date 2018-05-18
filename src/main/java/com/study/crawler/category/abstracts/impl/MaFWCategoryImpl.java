package com.study.crawler.category.abstracts.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;
import com.study.crawler.client.ApplicationContextSave;
import com.study.crawler.entity.CategoryInfo;
import com.study.crawler.manager.impl.CategroyManagerImpl;
import com.study.crawler.tool.ConstantUtil;

import redis.clients.jedis.Jedis;

//马蜂窝分类页面  分类信息做数据库存储处理
public class MaFWCategoryImpl extends WebsiteCategoryAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public MaFWCategoryImpl(Jedis jedis) {
		super(jedis);
	}

	public MaFWCategoryImpl() {

	}

	public static void main(String[] args) {
		MaFWCategoryImpl maFWCategoryImpl = new MaFWCategoryImpl();
		maFWCategoryImpl.getWebsiteCategory();
	}

	@Override
	public void getWebsiteCategory() {
		Set<String> set = new HashSet<String>();
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.setSource("马蜂窝");
		categoryInfo.setCategoryKind("travel");
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(ConstantUtil.MAFENGWO);
		crawlParam.setCookie(
				"PHPSESSID=cksq050qucf7d0i2f4pch2que2; mfw_uuid=5ae13336-a04a-e6e0-94d7-b8c3590e67e5; oad_n=a%3A3%3A%7Bs%3A3%3A%22oid%22%3Bi%3A1029%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222018-04-26+10%3A02%3A30%22%3B%7D; __mfwlv=1524707989; __mfwvn=1; __mfwlt=1524708083; uva=s%3A78%3A%22a%3A3%3A%7Bs%3A2%3A%22lt%22%3Bi%3A1524708150%3Bs%3A10%3A%22last_refer%22%3Bs%3A6%3A%22direct%22%3Bs%3A5%3A%22rhost%22%3Bs%3A0%3A%22%22%3B%7D%22%3B; __mfwurd=a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1524708150%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A0%3A%22%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D; __mfwuuid=5ae13336-a04a-e6e0-94d7-b8c3590e67e5");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		CategroyManagerImpl categoryManagerImpl = ApplicationContextSave.getBean(CategroyManagerImpl.class);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("马蜂窝分类页面进入 失败 请检查原因" + ConstantUtil.MAFENGWO);
			return;
		}
		Elements elements = document.select("div[class=row row-hot]");
		if (elements.size() == 0) {
			logger.info("马蜂窝 分类页面进入div失败" + ConstantUtil.MAFENGWO);
			return;
		}
		elements = elements.select("div[class=wrapper]");
		// 获取一级分类信息
		Elements firstCategory = elements.select("div[class=r-navbar]");
		firstCategory = firstCategory.select("a");
		String firstCategoryName = "";
		String SecondCategoryName = "";
		String SecondCategoryUrl = "";
		String ThirdCategoryName = "";
		String ThirdCategoryUrl = "";
		List<String> firstCategoryNameList = new ArrayList<>();
		for (int i = 0; i < firstCategory.size(); i++) {
			firstCategoryName = firstCategory.get(i).text();
			// 1级分类入库
			categoryInfo.setCategoryUrl("");
			categoryInfo.setCategoryName(firstCategoryName);
			categoryInfo.setCategoryHierarchyNumber("1级分类");
			categoryInfo.setCategoryHierarchy(firstCategoryName);
			categoryInfo.setCategoryLast("");
			categoryInfo.setIsLeafCategory("0");
			firstCategoryNameList.add(firstCategoryName);
			categoryManagerImpl.insertCategoryInfo(categoryInfo);
		}

		// 获取2-3 级分类信息
		Elements category = elements.select("div[class^=hot-list]");
		if (category.size() == 0) {
			logger.info("马蜂窝 分类页面进入div失败" + ConstantUtil.MAFENGWO);
			return;
		}
		for (int i = 0; i < category.size(); i++) {
			String firstName = firstCategoryNameList.get(i);// 获取1级分类
			Elements info = category.get(i).select("div[class=col]");
			for (int j = 0; j < info.size(); j++) {// 分左右2边
				Elements DetailInfo = info.get(j).select("dl");// 一边的全部信息
				for (int k = 0; k < DetailInfo.size(); k++) {// 遍历一边的所有信息
					Elements SecondCategory = DetailInfo.get(k).select("dt");
					SecondCategoryName = SecondCategory.text();
					SecondCategoryUrl = "http://www.mafengwo.cn" + SecondCategory.select("a").attr("href");
					// 2级分类入库
					categoryInfo.setCategoryUrl(SecondCategoryUrl);
					categoryInfo.setCategoryName(SecondCategoryName);
					categoryInfo.setCategoryHierarchyNumber("2级分类");
					categoryInfo.setCategoryHierarchy(firstName + "--" + SecondCategoryName);
					categoryInfo.setCategoryLast(firstName);
					categoryInfo.setIsLeafCategory("0");
					categoryManagerImpl.insertCategoryInfo(categoryInfo);
					// 3级分类
					Elements thirdCategory = DetailInfo.get(k).select("dd");
					for (int l = 0; l < thirdCategory.size(); l++) {
						Elements thirdCategoryInfo = thirdCategory.get(l).select("a");
						for (int m = 0; m < thirdCategoryInfo.size(); m++) {
							Element thirdinfo = thirdCategoryInfo.get(m);
							ThirdCategoryName = thirdinfo.text();
							ThirdCategoryUrl = "http://www.mafengwo.cn" + thirdinfo.attr("href");
							// 3级分类入库
							categoryInfo.setCategoryUrl(ThirdCategoryUrl);
							categoryInfo.setCategoryName(ThirdCategoryName);
							categoryInfo.setCategoryHierarchyNumber("3级分类");
							categoryInfo.setCategoryHierarchy(
									firstName + "--" + SecondCategoryName + "--" + ThirdCategoryName);
							categoryInfo.setCategoryLast(SecondCategoryName);
							categoryInfo.setIsLeafCategory("1");
							categoryManagerImpl.insertCategoryInfo(categoryInfo);
							// 存放到redis 中
							jedis.sadd("MfWcategory", ThirdCategoryUrl);
							// set.add(firstName + "--" + SecondCategoryName +
							// "--" + ThirdCategoryName);
						}
					}
				}
			}
		}
		/*System.out.println(set.size());
		for (String str : set) {
			System.out.println(str);
		}*/
	}

}
