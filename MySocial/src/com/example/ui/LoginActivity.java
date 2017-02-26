package com.example.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.example.entity.User;
import com.example.mysocial.R;
import com.wust.map_picture.Main;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText et_user, et_psd;
	private Button btn_login;
	private TextView forget, tv_register;
	private String username, password;
	private TextView tv_email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		et_user = (EditText) findViewById(R.id.username);
		et_psd = (EditText) findViewById(R.id.password);
		btn_login = (Button) findViewById(R.id.Login);
		tv_register = (TextView) findViewById(R.id.register);
 
		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.Login:
			Login();
			break;
		
		case R.id.register:
			Register();
		}

	}

	public void Login() {

		username = et_user.getText().toString();
		password = et_user.getText().toString();

		// 判断用户名和密码是否为空
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (TextUtils.isEmpty(password)) {
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG)
					.show();
			return;
		}

		// 显示登陆进度条
		final ProgressDialog progress=new ProgressDialog(LoginActivity.this);
		progress.setMessage("正在登陆");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		user.login(LoginActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, Main.class);
				startActivity(intent);
				progress.dismiss();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "登陆失败:"+arg1, Toast.LENGTH_LONG).show();
				progress.dismiss();
			}
		});
	}
	
	public void Register(){
		Intent intent=new Intent();
		intent.setClass(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}
	


}
