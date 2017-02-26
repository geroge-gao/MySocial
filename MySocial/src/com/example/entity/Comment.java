package com.example.entity;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

	private User user;
	private String Commentcontent;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCommentcontent() {
		return Commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		Commentcontent = commentcontent;
	}

}
