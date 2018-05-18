package com.study.crawler.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.small.crawler.util.DateTimeUtil;
import com.small.crawler.util.ExcelUtil;
import com.util.CrawlerUtil.CrawlParam;
import com.util.CrawlerUtil.HttpClientFactory;
import com.util.CrawlerUtil.RegexUtil;

/**
 * @author cqw
 * @Introduce 关于保险的一个爬虫，获取所有情况下的 保险的价格 post请求 提交json数据
 * @Param
 * @Return
 * @Time 2018年5月9日
 */
public class InsuranceFactory {
	public static void main(String[] args) {
		int age = 18;
		getInfo(age);
	}

	public static void getInfo(int age) {// age 必须大于18
		List<String> excelList = new ArrayList<String>();
		excelList.add("出生日期;性别;基本保险金额(万);交费方式;交费期间(年);价格;");
		String excelString = "";
		String amount = "";
		String price = "";
		// 获取日期
		String date = DateTimeUtil.getBeforeTime("yyyy-MM-dd", age);
		// 循环集合
		List<String> sexList = new ArrayList<String>();
		sexList.add("男");
		sexList.add("女");
		List<Integer> baseMoneyList = new ArrayList<Integer>();
		baseMoneyList.add(1);
		baseMoneyList.add(4);
		baseMoneyList.add(5);
		baseMoneyList.add(10);
		baseMoneyList.add(20);
		baseMoneyList.add(24);
		baseMoneyList.add(30);
		baseMoneyList.add(40);
		List<String> wayList = new ArrayList<String>();
		wayList.add("一次交清");
		wayList.add("年交");
		wayList.add("月交");
		List<Integer> timeLimitList = new ArrayList<Integer>();
		timeLimitList.add(5);
		timeLimitList.add(10);
		timeLimitList.add(20);
		timeLimitList.add(30);

		// String url = "http://www.e-nci.com/product/quote/calculation.json";
		// 模拟打开页面
		CrawlParam crawlParam = new CrawlParam();
		//
		crawlParam.setRequestHeadInfo("X-Requested-With", "XMLHttpRequest");
		// crawlParam.setUrlStr(url);
		String jsonUrl = "http://www.e-nci.com/product/quote/calculation.json";
		crawlParam.setCookie(
				"AAA-20480=GCKJKIMAFAAA; enci_temp_member_id=9SSvvBlZB7RBcHITL2lD.; uniqueCookieId=1525341529994ihvaogns93nb6; 1525341529994ihvaogns93nb6=userId:,bfd_g:,looyu_id:,tma:; JSESSIONID=dlev6OuaQEl4eA8PUbX7.");
		crawlParam.setUrlStr(jsonUrl);
		crawlParam.setRequestHeadInfo("Content-Type", "application/json");
		crawlParam.setRequestMethod("POST");
		// HttpClientFactory.getDocuemnt(crawlParam);
		// 读取文件保存到map中
		File file = new File("E:\\excel\\baoxian.txt");
		Map<String, String> mapUtil = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				line = line.trim();
				String[] str = line.split(" :\\{");
				mapUtil.put(str[0], str[1]);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer srtBuffer = new StringBuffer();// 用来计算map的key值
		String keybase = ";0:" + age;
		srtBuffer.append(";0:" + age);
		for (int i = 0; i < sexList.size(); i++) {// 0男 1女
			// srtBuffer.append(";1:" + i);
			String key1 = keybase + ";1:" + i;
			for (int j = 0; j < baseMoneyList.size(); j++) {// 基本保险金额
				// 获取保险金额
				amount = baseMoneyList.get(j).toString();
				for (int j2 = 0; j2 < wayList.size(); j2++) {// 交费方式
					if (wayList.get(j2).equals("一次交清")) {
						String key2 = key1 + ";4:0;5:1000";
						// srtBuffer.append(";4:0;5:1000");
						String sku = mapUtil.get(key2);
						sku = RegexUtil.matchNumber(sku);
						String jsonData = "{\"productId\":\"40345DC61D2621D5E0506E0AAE0149D9\",\"sku\":" + sku
								+ ",\"amount\":\"" + amount + "\",\"pshare\":0,\"birthdate\":\"" + date
								+ "\",\"channelCode\":\"D001\"}";
						crawlParam.setJsonData(jsonData);
						String priceInf = HttpClientFactory.postJsonData(crawlParam);
						if (priceInf == null) {
							System.out.println("检查");
							continue;
						}
						JSONObject json = JSONObject.parseObject(priceInf);
						price = json.getString("totalPrice");
						excelString = MessageFormat.format("{0};{1};{2};{3};{4};{5};", date, sexList.get(i), amount,
								wayList.get(j2), "", price);
						excelList.add(excelString);
					} else if (wayList.get(j2).equals("年交")) {
						String key2 = key1 + ";4:12;5:";
						// srtBuffer.append(";4:12;5:");
						for (int k = 0; k < timeLimitList.size(); k++) {// 交费期间
							String key3 = key2 + timeLimitList.get(k);
							// srtBuffer.append(k);
							String sku = mapUtil.get(key3);
							sku = RegexUtil.matchNumber(sku);
							String jsonData = "{\"productId\":\"40345DC61D2621D5E0506E0AAE0149D9\",\"sku\":" + sku
									+ ",\"amount\":\"" + amount + "\",\"pshare\":0,\"birthdate\":\"" + date
									+ "\",\"channelCode\":\"D001\"}";
							crawlParam.setJsonData(jsonData);
							String priceInf = HttpClientFactory.postJsonData(crawlParam);
							if (priceInf == null) {
								System.out.println("检查");
								continue;
							}
							JSONObject json = JSONObject.parseObject(priceInf);
							price = json.getString("totalPrice");
							excelString = MessageFormat.format("{0};{1};{2};{3};{4};{5};", date, sexList.get(i), amount,
									wayList.get(j2), timeLimitList.get(k) + "", price);
							excelList.add(excelString);
						}
					} else {
						String key2 = key1 + ";4:1;5:";
						// srtBuffer.append(";4:1;5");
						for (int k = 0; k < timeLimitList.size(); k++) {// 交费期间
							String key3 = key2 + timeLimitList.get(k);
							// srtBuffer.append(k);
							String sku = mapUtil.get(key3);
							sku = RegexUtil.matchNumber(sku);
							String jsonData = "{\"productId\":\"40345DC61D2621D5E0506E0AAE0149D9\",\"sku\":" + sku
									+ ",\"amount\":\"" + amount + "\",\"pshare\":0,\"birthdate\":\"" + date
									+ "\",\"channelCode\":\"D001\"}";
							crawlParam.setJsonData(jsonData);
							String priceInf = HttpClientFactory.postJsonData(crawlParam);
							if (priceInf == null) {
								System.out.println("检查");
								continue;
							}
							JSONObject json = JSONObject.parseObject(priceInf);
							price = json.getString("totalPrice");
							excelString = MessageFormat.format("{0};{1};{2};{3};{4};{5};", date, sexList.get(i), amount,
									wayList.get(j2), timeLimitList.get(k) + "", price);
							excelList.add(excelString);
						}
					}

				}
			}
		}
		ExcelUtil.exportExcel("E:\\excel\\baoxianInfo.xls", excelList, false);
	}
}
