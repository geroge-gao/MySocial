package com.example.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Picture extends BmobObject {

	private double longitude;//经度
	private double latitude;//纬度
	private BmobFile pic;//保存图片

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
