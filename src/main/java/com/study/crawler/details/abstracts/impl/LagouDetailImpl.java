package com.study.crawler.details.abstracts.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.small.crawler.util.DateTimeUtil;
import com.small.crawler.util.document.CrawlParam;
import com.small.crawler.util.document.HttpURLConnectionFactory;
import com.study.crawler.client.ApplicationContextSave;
import com.study.crawler.details.abstracts.WebsiteDetailAbstract;
import com.study.crawler.entity.CompanyInfo;
import com.study.crawler.entity.RecruitmentInfo;
import com.study.crawler.manager.impl.CompanyManagerImpl;
import com.study.crawler.manager.impl.RecruitmentInfoManagerImpl;

public class LagouDetailImpl extends WebsiteDetailAbstract {
	private CompanyManagerImpl campanyImpl = ApplicationContextSave.getBean(CompanyManagerImpl.class);
	private RecruitmentInfoManagerImpl recruitmentInfoImpl = ApplicationContextSave
			.getBean(RecruitmentInfoManagerImpl.class);

	public LagouDetailImpl(BlockingQueue<String> listQueue, ExecutorService service, AtomicInteger atomic, int number) {
		super(listQueue, service, atomic, number);
		service.execute(this);
	}

	public LagouDetailImpl() {

	}

	public static void main(String[] args) {
		LagouDetailImpl impl = new LagouDetailImpl();
		String url = "https://www.lagou.com/jobs/4347782.html";// 没有 hr 相关信息
		String url2 = "https://www.lagou.com/jobs/4223542.html";// 没有公司主页
		String url3 = "https://www.lagou.com/jobs/4377221.html";
		impl.getRecruitmentInfo(url3);
		// impl.getCompanyInfo(url3);
		// impl.getMSInfo(url);
	}

	@Override
	public void getWebsiteDetail(String detailUrl) {
		CompanyInfo companyInfo = getCompanyInfo(detailUrl);
		campanyImpl.insertCompanyInfo(companyInfo);
		RecruitmentInfo recruitmentInfo = getRecruitmentInfo(detailUrl);
		recruitmentInfoImpl.insertRecruitmetInfo(recruitmentInfo);
	}

	/* 传入详情页url
	 * 用来获取公司信息
	 */
	@Override
	public CompanyInfo getCompanyInfo(String detailUrl) {
		CompanyInfo companyInfo = new CompanyInfo();
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setUrlStr(detailUrl);
		crawlParam.setCookie(
				"1acd4d9d61d14863b31a8709b013cd72; user_trace_token=20180402210646-ec446b7f-c8a4-4294-ba8c-0eb8a31e66b2; _ga=GA1.2.647896013.1522674282; LGUID=20180402210652-b5ebcd3d-3676-11e8-ae0d-525400f775ce; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1522674565; index_location_city=%E6%9D%AD%E5%B7%9E; LG_LOGIN_USER_ID=3ae69721ab8b80dd9b97aaae5b3c420c363f159a6ec567c8; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=108; gate_login_token=5432f9a0c9bd4f465e80692dd52142ebb8b7a89efb03c372; JSESSIONID=ABAAABAAAGGABCBCB228B04DCFC398E87E059FC3B4A40F1; _gat=1; LGSID=20180404185233-47b0f6bd-37f6-11e8-b3ac-525400f775ce; PRE_UTM=; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DnXX1ATJ6kIbng6umSEG_gW3tZj065bKnP96TI76ZM6S%26ck%3D6194.2.72.257.168.294.170.334%26shh%3Dwww.baidu.com%26sht%3Dbaiduhome_pg%26wd%3D%26eqid%3De674a716000465df000000045ac4ae6d; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; LGRID=20180404185247-4fa50d81-37f6-11e8-b738-5254005c3644; _putrc=6DDA6DD78B0B5EA4; login=true; unick=%E8%94%A1%E5%85%B6%E6%9C%9B; TG-TRACK-CODE=index_navigation; X_HTTP_TOKEN=f2489871eada2509d747dd0c9d014904");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			System.out.println(" getCompanyInfo 的detail 页面进入失败，url为：" + detailUrl);
			return null;
		}
		Elements elements = document.select("div[class=content_r]");
		if (elements.size() == 0) {
			System.out.println("detail 获取公司页面div失败 url为： " + detailUrl);
			return null;
		}
		elements = elements.select("dl[class=job_company]");
		if (elements.size() == 0) {
			System.out.println("detail 获取公司页面div失败 url为： " + detailUrl);
			return null;
		}
		// 获取公司名称
		Elements companyNames = elements.select("dt");
		companyNames = companyNames.select("a");
		String companyName = companyNames.select("img").attr("alt");// 公司名
		// 获取其他信息
		Elements detailInfo = elements.select("dd");
		detailInfo = detailInfo.select("ul[class=c_feature]");
		detailInfo = detailInfo.select("li");
		String companyDomain = detailInfo.get(0).text(); // 领域
		if (companyDomain.length() <= 3) {
			companyDomain = "";
		} else {
			companyDomain = companyDomain.split("领域")[0];
		}
		String developmentalPhase = detailInfo.get(1).text();// 发展阶段
		if (developmentalPhase.length() <= 5) {
			developmentalPhase = "";
		} else {
			developmentalPhase = developmentalPhase.split("发展阶段")[0];
		}
		String companyScale = detailInfo.get(2).text(); // 规模
		if (companyScale.length() <= 3) {
			companyScale = "";
		} else {
			companyScale = companyScale.split("规模")[0];
		}
		String companyHomepage = detailInfo.get(3).text();// 公司主页
		if (companyHomepage.length() <= 6) {// 判断是否存在主页
			companyHomepage = "";
		} else {

			companyHomepage = companyHomepage.split("公司主页")[0];
		}
		companyInfo.setCompanyName(companyName);
		companyInfo.setCompanyDomain(companyDomain);
		companyInfo.setCompanyHomepage(companyHomepage);
		companyInfo.setCompanyScale(companyScale);
		companyInfo.setDevelopmentalPhase(developmentalPhase);
		return companyInfo;
	}

	/* 传入详情页url
	 * 用来获取招聘信息
	 */
	@Override
	public RecruitmentInfo getRecruitmentInfo(String detailUrl) {
		RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
		CrawlParam crawlParam = new CrawlParam();
		crawlParam.setCookie(
				"1acd4d9d61d14863b31a8709b013cd72; user_trace_token=20180402210646-ec446b7f-c8a4-4294-ba8c-0eb8a31e66b2; _ga=GA1.2.647896013.1522674282; LGUID=20180402210652-b5ebcd3d-3676-11e8-ae0d-525400f775ce; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1522674565; index_location_city=%E6%9D%AD%E5%B7%9E; LG_LOGIN_USER_ID=3ae69721ab8b80dd9b97aaae5b3c420c363f159a6ec567c8; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=108; gate_login_token=5432f9a0c9bd4f465e80692dd52142ebb8b7a89efb03c372; JSESSIONID=ABAAABAAAGGABCBCB228B04DCFC398E87E059FC3B4A40F1; _gat=1; LGSID=20180404185233-47b0f6bd-37f6-11e8-b3ac-525400f775ce; PRE_UTM=; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DnXX1ATJ6kIbng6umSEG_gW3tZj065bKnP96TI76ZM6S%26ck%3D6194.2.72.257.168.294.170.334%26shh%3Dwww.baidu.com%26sht%3Dbaiduhome_pg%26wd%3D%26eqid%3De674a716000465df000000045ac4ae6d; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; LGRID=20180404185247-4fa50d81-37f6-11e8-b738-5254005c3644; _putrc=6DDA6DD78B0B5EA4; login=true; unick=%E8%94%A1%E5%85%B6%E6%9C%9B; TG-TRACK-CODE=index_navigation; X_HTTP_TOKEN=f2489871eada2509d747dd0c9d014904");
		crawlParam.setRequestHeadInfo("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0");
		crawlParam.setUrlStr(detailUrl);
		Document document = HttpURLConnectionFactory.getDocument(crawlParam);
		if (document == null) {
			System.out.println(" getRecruitmentInfo 的detail 页面进入失败  url为：" + detailUrl);
			return null;
		}
		// 进入 招聘信息div
		Elements elements = document.select("div[class=position-content-l]");
		if (elements == null) {
			System.out.println("detail 进入招聘页面顶部div失败 url为：" + detailUrl);
			return null;
		}

		Elements baseInfo = elements.select("div[class=job-name]");
		String departmentInfo = baseInfo.select("div[class=company]").text();// 招聘部门名称
		String positionInfo = baseInfo.select("span").text(); // 招聘职位信息

		Elements baseInfo2 = elements.select("dd[class=job_request]");
		Elements label = baseInfo2.select("ul[class=position-label clearfix]");
		label = label.select("li");
		baseInfo2 = baseInfo2.select("p"); // 有2个p标签
		String jobLableInfo = label.text(); // 标签信息
		String payInfo = null;
		String placeInfo = null;
		String workExperienceInfo = null;
		String educationInfo = null;
		String jobCategoryInfo = null;
		String releaseTime = null;
		if (baseInfo2.size() > 0) {
			Elements base = baseInfo2.get(0).select("span");
			payInfo = base.get(0).text(); // 薪资信息
			placeInfo = base.get(1).text(); // 工作地点
			placeInfo = placeInfo.replace("/", "");
			workExperienceInfo = base.get(2).text(); // 工作经验
			workExperienceInfo = workExperienceInfo.replace("/", "");
			educationInfo = base.get(3).text(); // 学历要求
			educationInfo = educationInfo.replace("/", "");
			jobCategoryInfo = base.get(4).text(); // 工作性质
		}
		if (baseInfo2.size() > 1) {
			releaseTime = baseInfo2.get(1).text(); // 发布时间,及拼接
			String dayNum = null;
			if (releaseTime.indexOf("天") > 0) {
				dayNum = releaseTime.substring(0, 1);
				int offset = Integer.parseInt(dayNum);
				offset = offset - offset * 2;
				dayNum = DateTimeUtil.getTime("yyyy-MM-dd", offset);
				releaseTime = dayNum + releaseTime.split("前")[1];

			} else if (releaseTime.indexOf(":") > 0) {
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String time = dateFormat.format(now);
				releaseTime = time + " 发布于拉勾网";
				// System.out.println(releaseTime);
			}
		} else {
			System.err.println("基本招聘信息不存在  url为： " + detailUrl);
		}
		Elements otherInfo = document.select("div[class=content_l fl]");
		// middleinfo 包含任职要求和职位诱惑 ，详细工作地点 发布者信息
		Elements middleinfo = otherInfo.select("dl[class=job_detail][id=job_detail]");
		middleinfo = middleinfo.select("dd");// 判断是否存在职位信息
		String positionTheTemptation = null;
		String jobDescription = null;
		String workPlace = null;
		String jobPromulgator = null;
		String chatWith = null;
		String resumeProcessing = null; // 简历处理
		String activePeriodOfTime = null;
		if (middleinfo.size() > 0) {
			positionTheTemptation = middleinfo.get(0).select("p").text(); // 职位诱惑

		}
		if (middleinfo.size() > 1) {
			Elements workInfo = middleinfo.get(1).select("div");
			workInfo = workInfo.select("p");
			String info = workInfo.text();// 包含岗位职责和任职要求
			// 这里2个字段共用 页面 不能区分
			jobDescription = info; // 岗位职责
			// String jobRequirements = info;// 任职要求
		}
		if (middleinfo.size() > 2) {
			Elements DetailPlace = middleinfo.get(2).select("div[class=work_addr]");
			workPlace = DetailPlace.text().split("查看地图")[0]; // 详细工作地点
		}
		if (middleinfo.size() > 3) {
			// 发布者信息
			Elements promulgator = middleinfo.get(3).select("div[class=border clearfix]");

			Elements publisherName = promulgator.select("div[class=publisher_name]");
			publisherName = publisherName.select("a");
			jobPromulgator = publisherName.select("span").text(); // 职位发布者姓名
			promulgator = promulgator.select("div[class=publisher_data]");

			if (promulgator.size() != 0) {
				promulgator = promulgator.select("div");
				// 聊天意愿
				chatWith = "聊天意愿：";
				Elements chat = promulgator.get(0).select("span");
				chatWith = chatWith + chat.get(2).text() + chat.get(3).text();
				resumeProcessing = "简历处理："; // 简历处理
				Elements Processing = promulgator.get(1).select("span");
				resumeProcessing = resumeProcessing + Processing.get(2).text() + Processing.get(3).text();
				// 活跃时段
				activePeriodOfTime = "活跃时段：";
				Elements active = promulgator.get(2).select("span");
				activePeriodOfTime = activePeriodOfTime + active.get(2).text() + active.get(3).text();
				// 面试评价----
			}
		} else {
			System.err.println("职位信息不存在  url为 ：" + detailUrl);
		}

		// 存放到实体类
		recruitmentInfo.setWorkUrl(detailUrl);
		recruitmentInfo.setDepartmentInfo(departmentInfo);
		recruitmentInfo.setPositionInfo(positionInfo);
		recruitmentInfo.setPayInfo(payInfo);
		recruitmentInfo.setPlaceInfo(placeInfo);
		recruitmentInfo.setWorkExperienceInfo(workExperienceInfo);
		recruitmentInfo.setEducationInfo(educationInfo);
		recruitmentInfo.setJobCategoryInfo(jobCategoryInfo);
		recruitmentInfo.setJobLableInfo(jobLableInfo);
		recruitmentInfo.setReleaseTime(releaseTime);
		recruitmentInfo.setPositionTheTemptation(positionTheTemptation);
		recruitmentInfo.setJobDescription(jobDescription);
		recruitmentInfo.setWorkPlace(workPlace);
		recruitmentInfo.setJobPromulgator(jobPromulgator);
		recruitmentInfo.setChatWith(chatWith);
		recruitmentInfo.setResumeProcessing(resumeProcessing);
		recruitmentInfo.setActivePeriodOfTime(activePeriodOfTime);
		recruitmentInfo.setInterviewAppraisal("");// 未写
		return recruitmentInfo;
	}

}
