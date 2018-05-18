package com.study.crawler.entity;

//马蜂窝游记实体类
public class MFWTravelNotesInfo {
	private String title;
	private String travelDetail;// 旅行详情 可能为空 如出发时间等信息
	private String authorInfo;
	private String authorUrl;
	private String releaseTime;// 发布时间
	private String readQuantity;// 阅读量
	private String collect;
	private String shareNumber;
	private String likeCount;// 点赞数
	private String catalog;// 游记目录
	private String travelPlace;// 游记地点
	private String url;

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getTravelPlace() {
		return travelPlace;
	}

	public void setTravelPlace(String travelPlace) {
		this.travelPlace = travelPlace;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTravelDetail() {
		return travelDetail;
	}

	public void setTravelDetail(String travelDetail) {
		this.travelDetail = travelDetail;
	}

	public String getAuthorInfo() {
		return authorInfo;
	}

	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReadQuantity() {
		return readQuantity;
	}

	public void setReadQuantity(String readQuantity) {
		this.readQuantity = readQuantity;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getShareNumber() {
		return shareNumber;
	}

	public void setShareNumber(String shareNumber) {
		this.shareNumber = shareNumber;
	}

	public String getLike() {
		return likeCount;
	}

	public void setLike(String like) {
		this.likeCount = like;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
