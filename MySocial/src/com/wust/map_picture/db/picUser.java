package com.wust.map_picture.db;

public class picUser {
	private int id;
	private String pic_path;
	private double lat;
	private double lon;
	
	public picUser(int id, String pic_path, double lat, double lon) {
		super();
		this.id = id;
		this.pic_path = pic_path;
		this.lat = lat;
		this.lon = lon;
	}
	public picUser(String pic_path,String lat, String lon) {
		super();
		this.pic_path = pic_path;
		this.lat = Double.parseDouble(lat);
		this.lon =Double.parseDouble(lon);
	}
	public picUser(String pic_path, double lat, double lon) {
		super();
		this.pic_path = pic_path;
		this.lat = lat;
		this.lon = lon;
	}
	
	public picUser() {
		super();
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "picUser [id=" + id + ", pic_path=" + pic_path + ", lat=" + lat
				+ ", lon=" + lon + "]";
	}
	

}
