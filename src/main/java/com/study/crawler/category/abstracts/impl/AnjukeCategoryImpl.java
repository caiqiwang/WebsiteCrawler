package com.study.crawler.category.abstracts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;
import com.study.crawler.entity.CategoryInfo;
import com.study.crawler.tool.ConstantUtil;

import redis.clients.jedis.Jedis;

public class AnjukeCategoryImpl extends WebsiteCategoryAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public AnjukeCategoryImpl(BlockingQueue<String> categoryQueue) {
		super(categoryQueue);
	}

	public AnjukeCategoryImpl(Jedis jedis) {
		super(jedis);
	}

	public AnjukeCategoryImpl() {// 测试用 父类的构造方法也要删除

	}

	public static void main(String[] args) {
		AnjukeCategoryImpl impl = new AnjukeCategoryImpl();
		impl.getWebsiteCategory();
	}

	@Override
	public void getWebsiteCategory() {
		String url = ConstantUtil.ANJUKEURL;
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.setSource("anjuke");
		categoryInfo.setCategoryKind("house");
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(url);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("categroy 页面  首页url进入失败");
			return;
		}
		Elements elements = document.select("div[class=cont-wrap m-t-box]");
		if (elements.size() == 0) {
			logger.info("category 页面 进入div失败 --第一次进入");
			return;
		}
		elements = elements.select("div[class=box search-cont p-none clearfix]");
		if (elements.size() == 0) {
			logger.info("category 页面 进入div失败 --第二次进入");
			return;
		}
		// 左边1级分类列表
		Elements leftTab = elements.select("div[class=left-tabs]");// 左边1级标题
		leftTab = leftTab.select("a");
		// 用来存储一级分类的name
		List<String> firstCategoryNameList = new ArrayList<String>();
		String firstCategoryName = null;// 1级分类
		String SecondCategoryName = null;// 2级分类
		String ThirdCategoryName = null; // 3级分类
		String CategoryUrl = null;// 分类的url
		for (int i = 0; i < leftTab.size(); i++) {
			Elements firstCategory = leftTab.get(i).select("span");
			firstCategoryName = firstCategory.text();
			System.out.println(firstCategoryName.toString() + "  " + firstCategoryName.length());
			firstCategoryName = firstCategoryName.replaceAll(" ", "");// 获取1级分类并去空格
			if (firstCategoryName.indexOf("卖") < 0) {
				categoryInfo.setCategoryName(firstCategoryName);
				categoryInfo.setCategoryUrl("");
				categoryInfo.setCategoryHierarchyNumber("1级分类");
				categoryInfo.setCategoryHierarchy(firstCategoryName);// 分类层级
				categoryInfo.setCategoryLast("");// 上级分类
				categoryInfo.setIsLeafCategory("0");
				firstCategoryNameList.add(firstCategoryName);
			}
		}
		Elements rightTab = elements.select("div[class=right-conts]");
		Elements lease = rightTab.select("div[class=tab-contents clearfix]");// 进入租房和写字楼2级或者3级分类标签
		for (int i = 0; i < lease.size() - 1; i++) {// 获最后一个为卖房 不考虑
			if (i == 0) {// 租房
				firstCategoryName = firstCategoryNameList.get(1);
				Elements leaseinfo = lease.get(i).select("div[class=hot-areas]");
				leaseinfo = leaseinfo.select("div[class=areas list-height]");
				leaseinfo = leaseinfo.select("a");
				for (int j = 0; j < leaseinfo.size(); j++) {// 获取url 等信息
					SecondCategoryName = leaseinfo.get(j).text();
					CategoryUrl = leaseinfo.get(j).attr("href");
					categoryInfo.setCategoryName(SecondCategoryName); // 存入实体类
					categoryInfo.setCategoryUrl(CategoryUrl);
					categoryInfo.setCategoryHierarchyNumber("2级分类");
					categoryInfo.setCategoryHierarchy(firstCategoryName + "_" + SecondCategoryName);// 分类层级
					categoryInfo.setCategoryLast(firstCategoryName);// 上级分类
					categoryInfo.setIsLeafCategory("1");
				}
			} else {// 写字楼
				firstCategoryName = firstCategoryNameList.get(2);
				Elements leaseinfo = lease.get(i).select("div[class=details float_l]");
				leaseinfo = leaseinfo.select("div[class=areas]");
				leaseinfo = leaseinfo.select("a");
				for (int j = 0; j < leaseinfo.size(); j++) {
					SecondCategoryName = leaseinfo.get(j).text();
					CategoryUrl = leaseinfo.get(j).attr("href");
					categoryInfo.setCategoryName(SecondCategoryName); // 存入实体类
					categoryInfo.setCategoryUrl(CategoryUrl);
					categoryInfo.setCategoryHierarchyNumber("2级分类");
					categoryInfo.setCategoryHierarchy(firstCategoryName + "_" + SecondCategoryName);// 分类层级
					categoryInfo.setCategoryLast(firstCategoryName);// 上级分类
					categoryInfo.setIsLeafCategory("1");
				}
			}
		}
		Elements buyHouse = rightTab.select("div[class=buy-house tab-contents]");// 进入买房的分类标签
		buyHouse = buyHouse.select("div[class=clearfix]");
		buyHouse = buyHouse.select("div[class=details float_l]");// 获取2级标签 --住房
		firstCategoryName = firstCategoryNameList.get(0);// 一级标题
		for (int i = 0; i < buyHouse.size(); i++) {
			Elements secondtitle = buyHouse.get(i).select("p");
			SecondCategoryName = secondtitle.text();// 2级分类
			categoryInfo.setCategoryName(SecondCategoryName); // 存入实体类
			categoryInfo.setCategoryUrl("");
			categoryInfo.setCategoryHierarchyNumber("2级分类");
			categoryInfo.setCategoryHierarchy(firstCategoryName + "__" + SecondCategoryName);// 分类层级
			categoryInfo.setCategoryLast(firstCategoryName);// 上级分类
			categoryInfo.setIsLeafCategory("0");
			Elements threetitle = buyHouse.get(i).select("div[class=areas]");
			threetitle = threetitle.select("a");
			for (int j = 0; j < threetitle.size(); j++) {// 3级分类
				CategoryUrl = threetitle.get(j).attr("href");
				ThirdCategoryName = threetitle.get(j).text();
				categoryInfo.setCategoryName(ThirdCategoryName); // 存入实体类
				categoryInfo.setCategoryUrl(CategoryUrl);
				categoryInfo.setCategoryHierarchyNumber("3级分类");
				categoryInfo
						.setCategoryHierarchy(firstCategoryName + "_" + SecondCategoryName + "_" + ThirdCategoryName);// 分类层级
				categoryInfo.setCategoryLast(SecondCategoryName);// 上级分类
				categoryInfo.setIsLeafCategory("1");
			}
		}
	}
}
