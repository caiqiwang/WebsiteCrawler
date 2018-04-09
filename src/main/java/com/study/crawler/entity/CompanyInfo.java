package com.study.crawler.entity;

public class CompanyInfo {// 公司信息表
	public String companyName; // 公司名称
	public String companyDomain; // 领域
	public String developmentalPhase;// 发展阶段
	public String companyScale; // 规模
	public String companyHomepage; // 主页

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDomain() {
		return companyDomain;
	}

	public void setCompanyDomain(String companyDomain) {
		this.companyDomain = companyDomain;
	}

	public String getDevelopmentalPhase() {
		return developmentalPhase;
	}

	public void setDevelopmentalPhase(String developmentalPhase) {
		this.developmentalPhase = developmentalPhase;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getCompanyHomepage() {
		return companyHomepage;
	}

	public void setCompanyHomepage(String companyHomepage) {
		this.companyHomepage = companyHomepage;
	}

}
