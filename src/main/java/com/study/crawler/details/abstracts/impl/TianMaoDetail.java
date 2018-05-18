package com.study.crawler.details.abstracts.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.small.crawler.util.CrawlerUtil;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.CommodityInfo;

public class TianMaoDetail extends WebsiteDetailAbstract {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/*public TianMaoDetail(List<CommodityInfo> list) {
		super(list);
	}*/

	public TianMaoDetail() {

	}

	public static void main(String[] args) {
		String url = "https://detail.tmall.com/item.htm?id=567057905946&skuId=3608072676853&areaId=330400&user_id=2274104331&cat_id=50100092&is_b=1&rn=2dd9d6a661f0d0729c6ab9979fe3185f!PHIX!猴都电子烟套装PHIX二代烟弹迷你电子烟杆子戒烟器蒸汽烟JULL正品";

		TianMaoDetail impl = new TianMaoDetail();
		impl.getWebsiteDetail(url);
	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		CommodityInfo commodityInfo = new CommodityInfo();
		commodityInfo.setSource("Taobao");
		CrawlParam crawlParam = new CrawlParam();
		String[] str2 = detailUrl.split("!");
		String brand = str2[1];// 品牌名称
		String newUrl = str2[0];// 商品链接
		String name = str2[2];
		commodityInfo.setUrl(newUrl);
		commodityInfo.setName(name);
		commodityInfo.setBrand(brand);
		crawlParam.setUrlStr(newUrl);
		crawlParam.setCharset("gbk");
		crawlParam.setInterval(1000);
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setCookie(
				"hng=CN%7Czh-CN%7CCNY%7C156; cna=b21AERllZBMCAXPHmqNO1XFf; isg=BFxc4YEIarG9ZR74uoLpn5nOLHnOfQCb6DlZ4jZc9Mc-gf0LXubQj4Bz5Wl5EjhX; um=BA335E4DD2FD504FAD5925633FCE4ABD7BC7C1EBCFF7C34FF80A62087FF25C5BB1BBD9B2C5F24BBECD43AD3E795C914C19A3A51CBC43FC962381BD19B87BFC1C; enc=ZAMZqDYB12N%2B1vUVKTwKzjIYAaEiT3IDkcljFRrzx9MKM4KxTuqYa1UkCvnlw9aCDT0h2lOcWR6eAOhZrtks3g%3D%3D; _med=dw:1366&dh:768&pw:1366&ph:768&ist:0; pnm_cku822=098%23E1hv4QvUvbpvUvCkvvvvvjiPPFFZsjYVPsd9AjnEPmP9AjDRRLMpAjibR2FOzj3v2QhvCvvvMM%2FivpvUvvmvWfcIHvkEvpvV2vvC9jxWKphv8vvvvvCvpvvvvvmm86CvCvOvvUUdphvWvvvv9krvpv3FvvmmW6Cvm8WEvpCW2bGIvvw01jc6D40OamDrD7zheTgaW4c6D70OeC6ktnLWsC04whfO%2BneYr2E9ZRAn3w0AhjHCTWex6fItb9TDYExrV8tMLJLwzR9XVvhCvvOvUvvvphvtvpvhvvvvv8wCvvpvvUmm; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; x=__ll%3D-1%26_ato%3D0; lid=%E8%AF%B7%E6%88%91%E5%8F%AB%E9%9B%B7%E6%AD%A3%E5%85%B4; cq=ccp%3D0; uc1=cookie14=UoTeOo0XfW%2FVQg%3D%3D&lng=zh_CN&cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&existShop=false&cookie21=VT5L2FSpccLuJBreK%2BBd&tag=8&cookie15=UIHiLt3xD8xYTw%3D%3D&pas=0; t=d434921d67195e175e56c2941803e622; uc3=nk2=pIq78NogEwo1tsmj&id2=UUppoD4Ev%2BVbtQ%3D%3D&vt3=F8dBz4FRYcmXYmgiIB4%3D&lg2=V32FPkk%2Fw0dUvg%3D%3D; tracknick=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; lgc=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; _tb_token_=f33efd9e5d0d5; cookie2=2ef60abfd1ab3e81d5825058c8e6a351; res=scroll%3A1349*1811-client%3A1349*385-offset%3A1349*1811-screen%3A1366*768; ck1=\"\"; skt=4e10ddd95e104fe8; swfstore=177184; whl=-1%260%260%260; _l_g_=Ug%3D%3D; unb=2288891797; cookie1=BYby8YQDndPhl5ValBXZMeGEc%2B3Oc6mBwpKjw6XZ5ig%3D; login=true; cookie17=UUppoD4Ev%2BVbtQ%3D%3D; _nk_=%5Cu8BF7%5Cu6211%5Cu53EB%5Cu96F7%5Cu6B63%5Cu5174; uss=\"\"");

		// crawlParam.setOutputPath("C:\\Users\\Administrator.PC-201703032132\\Desktop\\tianma.txt");
		// HttpURLConnectionFactory.downloadFile(crawlParam);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			logger.info("detail页面  获取document失败 " + newUrl);
			return;
		}
		Elements elements = document.select("div[id=header]");
		if (elements.size() == 0) {
			logger.debug("detail页面进入div失败 请检查" + newUrl);
			return;
		}
		elements = elements.select("div[id=headerCon]");
		elements = elements.select("div[id=shopExtra]");
		elements = elements.select("div[class=slogo]");
		if (elements.size() == 0) {
			logger.debug("detail页面进入div失败 请检查" + newUrl);
			return;
		}
		elements = elements.select("a[class=slogo-shopname]");

		String storeName = elements.select("strong").text();
		commodityInfo.setStoreName(storeName);
		// 截取newUrl中的id
		Pattern pattern = Pattern.compile("\\?id=(\\d+)");
		Matcher matcher = pattern.matcher(newUrl);
		String id = "";
		while (matcher.find()) {
			id = matcher.group(1);
		}
		// 获取全部评价
		String allCountUrl = "https://dsr-rate.tmall.com/list_dsr_info.htm?itemId=" + id
				+ "&spuId=0&sellerId=2280906531&_ksTS=1524554545106_201&callback=jsonp202";
		crawlParam.setUrlStr(allCountUrl);
		String AllCountStr = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		if (AllCountStr != null) {
			AllCountStr = CrawlerUtil.matchBetweenSymbol(AllCountStr, "jsonp202\\(", "\\)", true);
			System.out.println(AllCountStr.toString());
			JSONObject AllCountJson = JSONObject.parseObject(AllCountStr);
			AllCountJson = AllCountJson.getJSONObject("dsr");
			String allCount = AllCountJson.getString("rateTotal");
			commodityInfo.setAllcount(allCount);
			// System.out.println(allCount);
		}
		/*// 获取总销量
		String allSaleUrl = "https://mdskip.taobao.com/core/initItemDetail.htm?isUseInventoryCenter=false&cartEnable=true&service3C=false&isApparel=false&isSecKill=false&tmallBuySupport=true&isAreaSell=false&tryBeforeBuy=false&offlineShop=false&itemId="
				+ id
				+ "&showShopProm=false&cachedTimestamp=1524620200054&isPurchaseMallPage=false&isRegionLevel=false&household=false&sellerPreview=false&queryMemberRight=true&addressLevel=2&isForbidBuyItem=false&callback=setMdskip&timestamp=1524622161302&isg=null&isg2=BCgogU0v5hT99MqEzobVuwWS-BX6-Yw_BPUt_uJZu6OUPcinimFc6740MVXNFkQz&areaId=330400&cat_id=50100092";
		crawlParam.setUrlStr(allSaleUrl);
		String AllSale = HttpURLConnectionFactory.getDocumentStr(crawlParam);
		if (AllSale == null) {
			System.out.println(AllSale);
		}*/
		// list.add(commodityInfo);
	}

}
