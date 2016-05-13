package com.ubuntuvim.framework.model;

public class SVNSettings {
	
	private int id;
	private String svnBasePath;
	private String username;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSvnBasePath() {
		return svnBasePath;
	}

	public void setSvnBasePath(String svnBasePath) {
		this.svnBasePath = svnBasePath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SVNSettings [id=" + id + ", svnBasePath=" + svnBasePath
				+ ", username=" + username + ", password=" + password + "]";
	}
}
