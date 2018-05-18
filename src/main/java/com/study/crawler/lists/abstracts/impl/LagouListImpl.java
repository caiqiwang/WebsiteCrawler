package com.study.crawler.lists.abstracts.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.lists.abstracts.WebsiteListAbstracts;

public class LagouListImpl extends WebsiteListAbstracts {
	/*public static void main(String[] args) {
		ListImpl listImpl = new ListImpl();
		String url = "https://www.lagou.com/zhaopin/shichangguwen/";
		String urls = "https://www.lagou.com/zhaopin/PHP/2/?filterOption=2";
		// int a =
		// listImpl.getAllPage("https://www.lagou.com/zhaopin/shichangyingxiao1/?labelWords=label");
		listImpl.getWebsiteList(url);
		// System.out.println(a);
	
	}*/
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public LagouListImpl(BlockingQueue<String> categoryQueue, BlockingQueue<String> listQueue, ExecutorService service,
			AtomicInteger atomic) {
		super(categoryQueue, listQueue, service, atomic);
		// TODO Auto-generated constructor stub
		service.execute(this);
	}

	public void getWebsiteList(String url) {
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setCookie(
				"SEARCH_ID=414aa3be231f478ba56b028e238a985d; user_trace_token=20180402210646-ec446b7f-c8a4-4294-ba8c-0eb8a31e66b2; _ga=GA1.2.647896013.1522674282; LGUID=20180402210652-b5ebcd3d-3676-11e8-ae0d-525400f775ce; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1522674565; _gid=GA1.2.1797991517.1522674567; JSESSIONID=ABAAABAAADEAAFI2CC8514F2A9083680EB80B44BE908485; LGRID=20180403204136-5920491d-373c-11e8-b2c4-525400f775ce; index_location_city=%E6%9D%AD%E5%B7%9E; TG-TRACK-CODE=index_navigation; X_HTTP_TOKEN=f2489871eada2509d747dd0c9d014904; LG_LOGIN_USER_ID=3ae69721ab8b80dd9b97aaae5b3c420c363f159a6ec567c8; _putrc=6DDA6DD78B0B5EA4; login=true; unick=%E8%94%A1%E5%85%B6%E6%9C%9B; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=108; gate_login_token=f47a312db30183f23401cf36c4087f8c2c03f235c2ca11cc; gate_login_token=\"\"; _gat=1; LGSID=20180403204116-4d42524b-373c-11e8-b2c4-525400f775ce; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		String detailUrl = null;
		int maxPage = getAllPage(url);
		// System.out.println("maxPage= " + maxPage);
		if (maxPage == 0) {
			return;
		}
		String newurl = null;// 表示第一页之后的url
		Document document = null;
		maxPage = 1;
		for (int i = 0; i < maxPage; i++) {
			if (i == 0) {
				crawlParam.setUrlStr(url);
				document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					System.out.println("获取list页面 失败");
				}
				Elements elements = document.select("div[class=s_position_list ]");
				if (elements == null) {
					System.out.println("list  进入职位列表 div失败");
				}
				elements = elements.select("ul[class=item_con_list]");
				elements = elements.select("li");
				for (int j = 0; j < elements.size(); j++) {
					Elements wordUrl = elements.get(j).select("div[class=position]");
					wordUrl = wordUrl.select("div[class=p_top]");
					wordUrl = wordUrl.select("a");
					detailUrl = wordUrl.attr("href");
					try {
						listQueue.put(detailUrl);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("添加到list queue 队列失败");
						e.printStackTrace();
					}
				}
				System.out.println("成功添加list页面 第一页内容到 队列" + url);
			} else {
				newurl = url + (i + 1) + "/";
				crawlParam.setUrlStr(newurl);
				document = HttpURLConnectionFactory.getDocument(crawlParam);
				if (document == null) {
					System.out.println("获取第二页之后的list页面 失败  可能是没有第二页的url");
					return;
				}
				Elements elements = document.select("div[class=s_position_list ]");
				if (elements == null) {
					System.out.println("list 第二页之后的list页面   进入职位列表 div失败");
					return;
				}
				elements = elements.select("ul[class=item_con_list]");
				elements = elements.select("li");
				for (int j = 0; j < elements.size(); j++) {
					Elements wordUrl = elements.get(j).select("div[class=position]");
					wordUrl = wordUrl.select("div[class=p_top]");
					wordUrl = wordUrl.select("a");
					detailUrl = wordUrl.attr("href");
					try {
						listQueue.put(detailUrl);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("成功添加list页面  第" + (i + 1) + "页内容到 队列对应的url为" + url);
			}
		}
	}

	@Override
	public int getAllPage(String categoryUrl) { // 获取最大的翻页数
		// TODO Auto-generated method stub
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(categoryUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		int page = 1;
		if (document == null) {
			System.out.println("获取list页面  总页数失败");
			return 0;
		}
		Elements elements = document.select("div[class=pager_container]");
		if (elements == null) {
			System.out.println("获取list页面 失败");
		}

		Elements allPage = elements.select("a");
		if (allPage.size() > 2) {
			String maxPage = allPage.get(allPage.size() - 2).text();// 倒数第2个数为最大页数
			page = Integer.parseInt(maxPage);
		}
		if (page >= 2) {
			page = 2;
		}
		return page;
	}

	@Override
	public int getAllPage(String categoryUrl, List<String> list) {
		// TODO Auto-generated method stub
		return 0;
	}
}
