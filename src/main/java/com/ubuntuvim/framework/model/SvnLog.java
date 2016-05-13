package com.ubuntuvim.framework.model;

import java.util.Date;

public class SvnLog {
	
	private int id;
	private String changeFilePath;  // 被改变的文件
	private String revision; //版本号
	private String author;  // 提交修改人
	private String logMsg;  // 备注信息
	private Date changeDate;  //  修改日期
	private String opType;  //操作类型：修改，合并，删除，新增
	private String functionName;  // 业务名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChangeFilePath() {
		return changeFilePath;
	}
	public void setChangeFilePath(String changeFilePath) {
		this.changeFilePath = changeFilePath;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLogMsg() {
		return logMsg;
	}
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	@Override
	public String toString() {
		return "SvnLog [id=" + id + ", changeFilePath=" + changeFilePath
				+ ", revision=" + revision + ", author=" + author + ", logMsg="
				+ logMsg + ", changeDate=" + changeDate + ", opType=" + opType
				+ ", functionName=" + functionName + "]";
	}

}
