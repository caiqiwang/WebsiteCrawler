package com.study.crawler.category.abstracts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;
import com.study.crawler.client.ApplicationContextSave;
import com.study.crawler.entity.CategoryInfo;
import com.study.crawler.manager.impl.CategroyManagerImpl;
import com.study.crawler.tool.ConstantUtil;
import com.study.crawler.tool.DocumentUtil;

import redis.clients.jedis.Jedis;

@Service
public class LagouCategoryImpl extends WebsiteCategoryAbstract {

	public LagouCategoryImpl(BlockingQueue<String> categoryQueue) {
		super(categoryQueue);
	}

	public LagouCategoryImpl(Jedis jedis) {
		super(jedis);
	}

	/*public LagouCategoryImpl() {// 测试用 父类的构造方法也要删除
	
	}
	
	public static void main(String[] args) {
		// BlockingQueue<String> categoryQueue = new
		// LinkedBlockingQueue<String>();// 存储分类信息
		LagouCategoryImpl lagouCategoryImpl = new LagouCategoryImpl();
		lagouCategoryImpl.getWebsiteCategory();
	}*/

	public void getWebsiteCategory() {
		// TODO Auto-generated method stub
		String homepageUrl = ConstantUtil.LAGOUURL;
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.setSource("Lagou");
		categoryInfo.setCategoryKind("work");
		// 获取categoryManagerImpl对象
		CategroyManagerImpl categoryManagerImpl = ApplicationContextSave.getBean(CategroyManagerImpl.class);
		Document document = DocumentUtil.getDocument(homepageUrl);
		if (document == null) {
			System.out.println("分类页面获取失败----");
			return;
		}
		Elements elements = document.select("div[class=clearfix]");
		if (elements == null) {
			System.out.println("分类  - 进入第一层级div失败----");
			return;
		}
		elements = elements.select("div[class=mainNavs]");
		if (elements == null) {
			System.out.println("分类  - 进入第2层级div失败----");
			return;
		}
		// 进入正式分类div
		// 用来存储一级分类的name
		List<String> firstCategoryNameList = new ArrayList<String>();
		// 1级分类
		String firstCategoryName = null;
		// 2级分类
		String SecondCategoryName = null;
		// 3级分类
		String ThirdCategoryName = null;
		// 分类的url
		String CategoryUrl = null;
		Elements category = elements.select("div[class=menu_box]");
		for (int i = 0; i < category.size(); i++) { // 获取一级分类
			Elements firstCategory = category.get(i).select("div[class=category-list]");
			firstCategoryName = firstCategory.select("h2").text(); // 获取一级分类名
			// 1级分类 存入分类数据库
			categoryInfo.setCategoryUrl("");
			categoryInfo.setCategoryName(firstCategoryName);
			categoryInfo.setCategoryHierarchyNumber("1级分类");
			categoryInfo.setCategoryHierarchy(firstCategoryName);
			categoryInfo.setCategoryLast("");
			categoryInfo.setIsLeafCategory("0");
			categoryManagerImpl.insertCategoryInfo(categoryInfo);
			firstCategoryNameList.add(firstCategoryName);
		}
		// 取2级分类和3级分类
		for (int i = 0; i < category.size(); i++) {
			// 取出1级分类 1个1级分类对应多个2级分类
			firstCategoryName = firstCategoryNameList.get(i);
			Elements CategoryList = category.get(i).select("div[class=menu_sub dn]");
			CategoryList = CategoryList.select("dl");// 该列表下包含2级和3级分类
			for (int j = 0; j < CategoryList.size(); j++) { // 获取2级分类的列表
				// for (int j = 0; j < 1; j++) { // 获取2级分类的列表
				Elements secendCategory = CategoryList.get(j).select("dt");
				SecondCategoryName = secendCategory.select("span").text();// 获取2级分类名
				// 2级分类 存入分类数据库
				categoryInfo.setCategoryUrl("");
				categoryInfo.setCategoryName(SecondCategoryName);
				categoryInfo.setCategoryHierarchyNumber("2级分类");
				categoryInfo.setCategoryHierarchy(firstCategoryName + "--" + SecondCategoryName);
				categoryInfo.setCategoryLast(firstCategoryName);
				categoryInfo.setIsLeafCategory("0");
				categoryManagerImpl.insertCategoryInfo(categoryInfo);
				// 获取3级分类的列表
				Elements thirdCategory = CategoryList.get(j).select("dd");
				thirdCategory = thirdCategory.select("a"); // 获取 所有的三级分类
				for (int k = 0; k < thirdCategory.size(); k++) {
					// for (int k = 0; k < 1; k++) {
					ThirdCategoryName = thirdCategory.get(k).text(); // 获取三级分类名
					CategoryUrl = thirdCategory.get(k).attr("href");// 获取分类的url;
					// System.out.println(SecondCategoryName + "--" +
					// ThirdCategoryName);
					// 3级分类 存入分类数据库
					categoryInfo.setCategoryUrl(CategoryUrl);
					categoryInfo.setCategoryName(ThirdCategoryName);
					categoryInfo.setCategoryHierarchyNumber("3级分类");
					categoryInfo.setCategoryHierarchy(
							firstCategoryName + "--" + SecondCategoryName + "--" + ThirdCategoryName);
					categoryInfo.setCategoryLast(SecondCategoryName);
					categoryInfo.setIsLeafCategory("1");
					categoryManagerImpl.insertCategoryInfo(categoryInfo);
					// 把3级分类的url存储到队列中 传到list页面
					try {
						categoryQueue.put(CategoryUrl);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
	}
}
