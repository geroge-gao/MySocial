package com.wust.map_picture.db;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class pictureDAO {
		private pictureDBhelper  opener;

		public pictureDAO(Context context) {
			String name ;
			User user=User.getCurrentUser(context, User.class);
			name=user.getUsername()+".db";
		    opener=new pictureDBhelper(context, name);
		}
		
		public void insert(picUser picuser) {
			SQLiteDatabase db=opener.getWritableDatabase();
			if(db.isOpen()){	
				String sql="insert into Mymap_user(pic_path,lat,lon) values(?,?,?);";
				db.execSQL(sql,new Object[]{picuser.getPic_path(),picuser.getLat(),picuser.getLon()});
				db.close();
			}
		}
		
		public void delete(int id) {
			SQLiteDatabase db=opener.getWritableDatabase();
			if(db.isOpen()){	
				String sql="delete from Mymap_user where id=?;";
				db.execSQL(sql,new Object[]{id} );
				db.close();
			}
		}
		public void delete(String pic) {
			SQLiteDatabase db=opener.getWritableDatabase();
			if(db.isOpen()){	
				String sql="delete from Mymap_user where pic_path=?;";
				db.execSQL(sql,new Object[]{pic} );
				db.close();
			}
		}
		public List<picUser> queryAll()
		{
			SQLiteDatabase db=opener.getReadableDatabase();
			if(db.isOpen()) {

				String sql="select id,pic_path,lat,lon from Mymap_user;";
				Cursor cursor=db.rawQuery(sql, null);
				if(cursor!=null && cursor.getCount()>0) {
					List<picUser> list=new ArrayList<picUser>();
					int id;
					String pic_path;
					double lat,lon;
					while(cursor.moveToNext()) {
						 id=cursor.getInt(0);
					    pic_path=cursor.getString(1);
						lat=cursor.getDouble(2);
						lon=cursor.getDouble(3);
						list.add(new picUser(id, pic_path, lat, lon));
					}
					db.close();
					return list;
				}
				db.close();
			}
			return null;
		}
}
