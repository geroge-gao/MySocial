package com.example.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Saying extends BmobObject implements Serializable {

	private User author;
	private String content;// 发布的评论
	private BmobFile Contentfigureurl;//保存的图片
	private int love;// 点赞次数
	private int hate;// 讨厌次数
	private int share;//
	private int comment;// 评论数
	private boolean isPass;
	private boolean myFav;// 收藏
	private boolean myLove;// 赞
	private BmobRelation relation;//将动态和评论进行关联



	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BmobFile getContentfigureurl() {
		return Contentfigureurl;
	}

	public void setContentfigureurl(BmobFile contentfigureurl) {
		
		Contentfigureurl = contentfigureurl;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getHate() {
		return hate;
	}

	public void setHate(int hate) {
		this.hate = hate;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean isMyFav() {
		return myFav;
	}

	public void setMyFav(boolean myFav) {
		this.myFav = myFav;
	}

	public boolean isMyLove() {
		return myLove;
	}

	public void setMyLove(boolean myLove) {
		this.myLove = myLove;
	}

	public BmobRelation getRelation() {
		return relation;
	}

	public void setRelation(BmobRelation relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "QiangYu [author=" + author + ", content=" + content
				+ ", Contentfigureurl=" + Contentfigureurl + ", love=" + love
				+ ", hate=" + hate + ", share=" + share + ", comment="
				+ comment + ", isPass=" + isPass + ", myFav=" + myFav
				+ ", myLove=" + myLove + ", relation=" + relation + "]";
	}

}
