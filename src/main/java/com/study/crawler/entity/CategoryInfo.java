package com.study.crawler.entity;

public class CategoryInfo {// 分类信息表
	public String categoryName; // 分类名
	public String categoryHierarchyNumber;// 分类层级编号
	public String categoryHierarchy;// 分类层级
	public String categoryLast; // 上级分类
	public String categoryUrl;// 分类url
	public String categoryKind;// 分类类型
	public String isLeafCategory;// 是否是最后的分类
	public String source;// 来源

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryHierarchyNumber() {
		return categoryHierarchyNumber;
	}

	public void setCategoryHierarchyNumber(String categoryHierarchyNumber) {
		this.categoryHierarchyNumber = categoryHierarchyNumber;
	}

	public String getCategoryHierarchy() {
		return categoryHierarchy;
	}

	public void setCategoryHierarchy(String categoryHierarchy) {
		this.categoryHierarchy = categoryHierarchy;
	}

	public String getCategoryLast() {
		return categoryLast;
	}

	public void setCategoryLast(String categoryLast) {
		this.categoryLast = categoryLast;
	}

	public String getCategoryUrl() {
		return categoryUrl;
	}

	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}

	public String getCategoryKind() {
		return categoryKind;
	}

	public void setCategoryKind(String categoryKind) {
		this.categoryKind = categoryKind;
	}

	public String getIsLeafCategory() {
		return isLeafCategory;
	}

	public void setIsLeafCategory(String isLeafCategory) {
		this.isLeafCategory = isLeafCategory;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
