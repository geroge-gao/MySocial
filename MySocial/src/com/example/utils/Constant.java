package com.example.utils;

public interface Constant {
	
	public String APP_ID="4ae5bedad1b502abc9465c13c639d342";
	String TABLE_AI = "Mood";
	String TABLE_COMMENT = "Comment";

	String NETWORK_TYPE_WIFI = "wifi";
	String NETWORK_TYPE_MOBILE = "mobile";
	String NETWORK_TYPE_ERROR = "error";

	int AI = 0;
	int HEN = 1;
	int CHUN_LIAN = 2;
	int BIAN_BAI = 3;

	int CONTENT_TYPE = 4;

	String PRE_NAME = "my_pre";

	public static final int PUBLISH_COMMENT = 1;
	public static final int NUMBERS_PER_PAGE = 15;// 每次请求返回评论条数
	public static final int SAVE_FAVOURITE = 2;
	public static final int GET_FAVOURITE = 3;
	public static final int GO_SETTINGS = 4;

	public static final String SEX_MALE = "male";
	public static final String SEX_FEMALE = "female";

}
