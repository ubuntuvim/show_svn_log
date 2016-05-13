package com.ubuntuvim.svnlog;


import java.util.Date;

public class SvnLogModel {

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLogEntry() {
		return logEntry;
	}

	public void setLogEntry(String logEntry) {
		this.logEntry = logEntry;
	}

	private long revision;
	private String author;
	private Date date;
	private String message;
	private String logEntry;
	private int entryCount;

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	@Override
	public String toString() {
		return "SvnLogModel [revision=" + revision + ", author=" + author
				+ ", date=" + date + ", message=" + message + ", logEntry="
				+ logEntry + ", entryCount=" + entryCount + "]";
	}

}