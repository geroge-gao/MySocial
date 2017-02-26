package com.example.entity;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {

	public static final String TAG = "user";

	private String signature;// ����ǩ��
	private BmobFile avater;
	private BmobRelation favorite;
	private String sex;
	private BmobFile icon;// �û�ͼ��
	private String NickName;
	
	private List<Picture> pics;

	public List<Picture> getPics() {
		return pics;
	}

	public void setPics(List<Picture> pics) {
		this.pics = pics;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public BmobFile getAvater() {
		return avater;
	}

	public void setAvater(BmobFile avater) {
		this.avater = avater;
	}

	public BmobRelation getFavorite() {
		return favorite;
	}

	public void setFavorite(BmobRelation favorite) {
		this.favorite = favorite;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public static String getTag() {
		return TAG;
	}

	public BmobFile getIcon() {
		return icon;
	}

	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}

}
