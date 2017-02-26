package com.example.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.entity.Comment;
import com.example.entity.Saying;
import com.example.entity.User;
import com.example.mysocial.R;
import com.example.utils.CacheUtils;
import com.example.utils.LogUtils;

public class Editctivity extends Activity implements OnClickListener {

	private static final int REQUEST_CODE_ALBUM = 1;
	private static final int REQUEST_CODE_CAMERA = 2;
	EditText content;
	LinearLayout openlayout;
	LinearLayout takelauout;

	ImageView albunpic;
	ImageView takePic;
	String dateTime;
	Intent intent;
	Button btn_back;
	Button btn_true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editctivity);
		content = (EditText) findViewById(R.id.edit_content);
		openlayout = (LinearLayout) findViewById(R.id.open_layout);
		takelauout = (LinearLayout) findViewById(R.id.take_layout);
		albunpic = (ImageView) findViewById(R.id.open_pic);
		takePic = (ImageView) findViewById(R.id.take_pic);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);

		// 绑定监听
		openlayout.setOnClickListener(this);
		takelauout.setOnClickListener(this);
		albunpic.setOnClickListener(this);
		takePic.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.open_layout:
			Date d = new Date(System.currentTimeMillis());
			dateTime = d.getTime() + "";
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, REQUEST_CODE_ALBUM);
			break;

		case R.id.take_layout:
			Date date = new Date(System.currentTimeMillis());
			dateTime = date.getTime() + "";
			File f = new File(CacheUtils.getCacheDirectory(this, true, "pic")
					+ dateTime);
			if (f.exists()) {
				f.delete();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Uri uri = Uri.fromFile(f);
			Log.e("uri", uri + "");

			Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(camera, REQUEST_CODE_CAMERA);
			break;

		case R.id.btn_back:
			intent = new Intent(Editctivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		// 发表动态
		case R.id.btn_true:
			if(targeturl==null)
				publishNoPic(content.getText().toString(), null);
			else
				publish(content.getText().toString());
			
		}
	}
	
	/*
	 * 发表带图片的动态
	 */
	
	public void publish(final String comment){
		
		final BmobFile file=new BmobFile(new File(targeturl));
		file.upload(this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
			//	Toast.makeText(getApplicationContext(), "文件上传成功", Toast.LENGTH_LONG).show();
				//文件上传成功之后开始上传图文
				publishNoPic(content.getText().toString(), file);
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "文件上传失败"+arg1, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void publishNoPic(final String content,final BmobFile bmobFile){
		User user=User.getCurrentUser(this, User.class);
		final Saying saying=new Saying();
		saying.setAuthor(user);
		saying.setContent(content);
		//设置文件的图片
		if(bmobFile!=null){
			saying.setContentfigureurl(bmobFile);
		}
		
		saying.setLove(0);//点赞为0
		saying.setHate(0);
		saying.setShare(0);//初始化被分享次数
		saying.setComment(0);//初始化评论数
		saying.setPass(true);
		saying.save(getApplicationContext(), new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(Editctivity.this, "发表成功", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
		//		Toast.makeText(getApplicationContext(), "文件上传失败"+arg1, Toast.LENGTH_LONG).show();
				Log.e("Bmob", arg1);
			}
		});
		
	}

	String targeturl = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_ALBUM:
				String fileName = null;
				if (data != null) {
					Uri originalUri = data.getData();
					ContentResolver cr = getContentResolver();
					Cursor cursor = cr.query(originalUri, null, null, null,
							null);
					if (cursor.moveToFirst()) {
						do {
							fileName = cursor.getString(cursor
									.getColumnIndex("_data"));

						} while (cursor.moveToNext());
					}
					Bitmap bitmap = compressImageFromFile(fileName);
					targeturl = saveToSdCard(bitmap);
					albunpic.setBackgroundDrawable(new BitmapDrawable(bitmap));
					takelauout.setVisibility(View.GONE);
				}
				break;
			case REQUEST_CODE_CAMERA:
				String files = CacheUtils.getCacheDirectory(this, true, "pic")
						+ dateTime;
				File file = new File(files);
				if (file.exists()) {
					Bitmap bitmap = compressImageFromFile(files);
					targeturl = saveToSdCard(bitmap);
					takePic.setBackgroundDrawable(new BitmapDrawable(bitmap));
					openlayout.setVisibility(View.GONE);
				} else {

				}
				break;
			default:
				break;
			}
		}
	}

	// 对图片进行缩略的操作
	private Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return bitmap;
	}

	public String saveToSdCard(Bitmap bitmap) {
		String files = CacheUtils.getCacheDirectory(this, true, "pic")
				+ dateTime + "_11.jpg";
		File file = new File(files);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
}
