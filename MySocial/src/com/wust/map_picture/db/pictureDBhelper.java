package com.wust.map_picture.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class pictureDBhelper extends SQLiteOpenHelper {

	public pictureDBhelper(Context context, String name) {
		super(context, name, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table Mymap_user( id integer primary key autoincrement,  pic_path varchar(50), lat varchar(20), lon varchar(20));";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
