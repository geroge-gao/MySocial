package com.example.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Picture extends BmobObject {

	private double longitude;//����
	private double latitude;//γ��
	private BmobFile pic;//����ͼƬ

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public BmobFile getPic() {
		return pic;
	}

	public void setPic(BmobFile pic) {
		this.pic = pic;
	}

}
