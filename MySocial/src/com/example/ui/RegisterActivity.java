package com.example.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;

import com.example.entity.User;
import com.example.mysocial.R;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText et_user;
	private EditText et_psd;
	private EditText et_confirm;
	private EditText et_email;
	private Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		et_user=(EditText)findViewById(R.id.et_user);
		et_psd=(EditText)findViewById(R.id.et_psd);
		et_confirm=(EditText)findViewById(R.id.et_confirm);
		register=(Button)findViewById(R.id.btn_resigter);
		register.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Register();
	}
	
	public void Register(){
		String username=et_user.getText().toString();
		String password=et_psd.getText().toString();
		String repassword=et_confirm.getText().toString();
		
		
		if(TextUtils.isEmpty(username)){
			Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		
		if(TextUtils.isEmpty(password)){
			Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		
		if(TextUtils.isEmpty(repassword)){
			Toast.makeText(getApplicationContext(), "请再次输入密码",Toast.LENGTH_LONG).show();
			return ;
		}
		
		if(!password.equals(repassword)){
			Toast.makeText(getApplicationContext(), "两次输入密码不一致",Toast.LENGTH_LONG).show();
			return ;
		}
		
		
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.signUp(RegisterActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
				finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "注册失败"+arg1, Toast.LENGTH_LONG).show();
			}
		});
		
		
	}

}
