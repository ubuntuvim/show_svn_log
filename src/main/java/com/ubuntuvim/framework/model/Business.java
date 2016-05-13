package com.ubuntuvim.framework.model;

import java.util.Date;

public class Business {
	private int id;
	private String busiNo;
	private String busiName;
	private int categoryType;
	private String interInfoNo;   // 管理外键的数组 
	private String remark;
	private String wordDocPath;
	private Date createDate;
	private String busiDetailInfo;
	// 临时成员，返回数据到前端时用于保存管理的接口信息
	//  临时成员，返回数据到前端时用于保存业务类型
	private String categoryTypeText;

	public String getCategoryTypeText() {
		return categoryTypeText;
	}

	public void setCategoryTypeText(String categoryTypeText) {
		this.categoryTypeText = categoryTypeText;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public int getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	public String getInterInfoNo() {
		return interInfoNo;
	}

	public void setInterInfoNo(String interInfoNo) {
		this.interInfoNo = interInfoNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWordDocPath() {
		return wordDocPath;
	}

	public void setWordDocPath(String wordDocPath) {
		this.wordDocPath = wordDocPath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBusiDetailInfo() {
		return busiDetailInfo;
	}

	public void setBusiDetailInfo(String busiDetailInfo) {
		this.busiDetailInfo = busiDetailInfo;
	}


	@Override
	public String toString() {
		return "Business [id=" + id + ", busiNo=" + busiNo + ", busiName="
				+ busiName + ", categoryType=" + categoryType
				+ ", interInfoNo=" + interInfoNo + ", remark=" + remark
				+ ", wordDocPath=" + wordDocPath + ", createDate=" + createDate
				+ ", busiDetailInfo=" + busiDetailInfo  + ", categoryTypeText=" + categoryTypeText
				+ "]";
	}
}
