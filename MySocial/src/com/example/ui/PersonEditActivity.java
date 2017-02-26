package com.example.ui;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.entity.User;
import com.example.mysocial.R;
import com.example.mysocial.R.id;
import com.example.mysocial.R.layout;
import com.example.mysocial.R.menu;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PersonEditActivity extends Activity implements OnClickListener {

	private ImageView icon;
	private Button save;
	private EditText nickname;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_edit);
		icon = (ImageView) findViewById(R.id.icon);
		save = (Button) findViewById(R.id.btn_save);
		nickname = (EditText) findViewById(R.id.NickName);
		icon.setOnClickListener(this);
		save.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_edit, menu);
		return true;
	}

	// 将图片转换成圆形的
	public Bitmap ToRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int r;// 去最小的半径
		r = width > height ? height : width;
		Bitmap backgroundBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(backgroundBitmap);// 画圆
		Paint paint = new Paint();
		paint.setAntiAlias(true);// 设置边缘光滑，去掉锯齿
		RectF rect = new RectF(0, 0, r, r);
		canvas.drawRoundRect(rect, r / 2, r / 2, paint);// 画圆
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rect, paint);// 将圆形的图片画出来
		return backgroundBitmap;// 返回圆形图片
	}

	public void showIcon() {
		PrintImage();
	}

	// 选择图片并且将其存储路径调用出来
	private void PrintImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 0);
	}
	
	public void saveDate(){
		final User user=User.getCurrentUser(PersonEditActivity.this,User.class);
		File file=new File(path);
		final BmobFile bf=new BmobFile(file);
		user.setNickName(nickname.getText().toString());
		bf.upload(PersonEditActivity.this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.e("upload", "文件上传成功");
			//	Toast.makeText(getApplicationContext(), "文件上传成功", Toast.LENGTH_LONG).show();
				user.setIcon(bf);
				user.update(PersonEditActivity.this,new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Log.e("update", "文件保存成功");
						Toast.makeText(PersonEditActivity.this, "用户信息保存成功", Toast.LENGTH_LONG).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("update", "error:"+arg1);
						Toast.makeText(getApplicationContext(), arg1, Toast.LENGTH_LONG).show();
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), arg1, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {// 当选择图片成功的时候会返回result_ok
			Log.e("error--->", "onActivityResult error");
			return;
		}

		Bitmap bitmap = null;

		// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
		ContentResolver resolver = getContentResolver();
		// 判断接受的activity
		if (requestCode == 0) {
			try {
				Uri uri = data.getData();// 获取图片的uri
				// 显示得到bitmap
				bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
				icon.setImageBitmap(ToRoundBitmap(bitmap));

				// 获取图片的存储路径
				String[] filepathColum = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(uri, filepathColum,
						null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filepathColum[0]);
				path = cursor.getString(columnIndex);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.icon:
			showIcon();
			break;
		case R.id.btn_save:
			saveDate();
		}
	}

}
