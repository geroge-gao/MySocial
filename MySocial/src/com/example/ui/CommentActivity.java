package com.example.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter.AuthorityEntry;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.adapter.CommentAdapter;
import com.example.entity.Comment;
import com.example.entity.Saying;
import com.example.entity.User;
import com.example.mysocial.R;
import com.example.utils.ActivityUtil;
import com.example.utils.Constant;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

@SuppressLint("ServiceCast")
public class CommentActivity extends Activity implements OnClickListener {

	private ImageView user_logo;
	private TextView user_name;
	private TextView content_text;
	private ImageView content_image;
	private TextView item_action_share;
	private TextView item_action_love;
	private TextView item_action_comment;
	private EditText comment_content;
	private Button commentCommit;
	private TextView footer;

	private ListView commentLists;
	ArrayList<Comment> comments = new ArrayList<Comment>();
	private Saying saying;
	CommentAdapter mAdapter;
	LinearLayout commt;

	// Tencent的SDK类
	Tencent mTencent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		//初始化Tencent的SDK类
		mTencent = Tencent.createInstance("1104549376",
				this.getApplicationContext());
		
		user_logo = (ImageView) findViewById(R.id.user_logo);
		user_name = (TextView) findViewById(R.id.user_name);
		content_text = (TextView) findViewById(R.id.content_text);
		content_image = (ImageView) findViewById(R.id.content_image);
		item_action_love = (TextView) findViewById(R.id.item_action_love);
		item_action_comment = (TextView) findViewById(R.id.item_action_comment);
		item_action_share = (TextView) findViewById(R.id.item_action_share);
		commentCommit = (Button) findViewById(R.id.comment_commit);
		commentLists = (ListView) findViewById(R.id.comment_list);
		comment_content = (EditText) findViewById(R.id.comment_content);
		
		commt=(LinearLayout)findViewById(R.id.area_commit);

		footer = (TextView) findViewById(R.id.loadmore);
		footer.setOnClickListener(this);

		mAdapter = new CommentAdapter(CommentActivity.this, comments);
		commentLists.setAdapter(mAdapter);

		// 对控件绑定监听
		item_action_love.setOnClickListener(this);
		item_action_comment.setOnClickListener(this);
		item_action_share.setOnClickListener(this);
		commentCommit.setOnClickListener(this);
		Intent intent = this.getIntent();
		saying = (Saying) intent.getSerializableExtra("data");
		initView();

		showComments();
	}

	public void OnclickItem() {
		commentLists.setCacheColorHint(0);
		commentLists.setScrollingCacheEnabled(false);
		commentLists.setScrollContainer(false);
		commentLists.setFastScrollEnabled(true);
		commentLists.setSmoothScrollbarEnabled(true);
		commentLists.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ActivityUtil.show(CommentActivity.this, "po" + arg2);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		String nickname=saying.getAuthor().getNickName();
		if(!"".equals(nickname)){
			user_name.setText(nickname);
		}else{
			user_name.setText(saying.getAuthor().getUsername());
		}
		content_text.setText(saying.getContent());
		BmobFile bf = saying.getContentfigureurl();
		bf.loadImage(CommentActivity.this, content_image);
		if (saying.isMyLove()) {
			item_action_love.setText(saying.getLove() + "");
			item_action_love.setTextColor(Color.parseColor("#D95555"));
		} else {
			item_action_love.setText(saying.getLove() + "");
			item_action_love.setTextColor(Color.parseColor("#000000"));
		}
		User user=saying.getAuthor();
		BmobFile icon=user.getIcon();
		if(icon!=null){
			icon.loadImage(CommentActivity.this,user_logo);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

		case R.id.item_action_comment:
			OnclickComment();
			break;
		case R.id.item_action_love:
			saying.setMyLove(true);
			int love = saying.getLove();
			love += 1;
			saying.setLove(love);
			saying.setMyLove(true);
			saying.update(CommentActivity.this,new UpdateListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(CommentActivity.this, "saying数据更新成功", Toast.LENGTH_LONG).show();
					mAdapter.notifyDataSetChanged();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			if (saying.isMyLove()) {
				item_action_love.setText(love + "");
				item_action_love.setTextColor(Color.parseColor("#D95555"));
			} else {
				item_action_love.setText(love + "");
				item_action_love.setTextColor(Color.parseColor("#D95555"));
			}
			
			Toast.makeText(CommentActivity.this, "点赞成功", Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.item_action_share:
			ShareToQQ();
			break;
		case R.id.comment_commit:
			onClickComment();
			break;
		case R.id.loadmore:
			showComments();
		}
	}

	//
	private void onClickComment() {
		User user = User.getCurrentUser(CommentActivity.this, User.class);
		String content = comment_content.getText().toString();
		publishComment(user, content);
	}

	// 发表评论
	private void publishComment(User user, String content) {
		int number = saying.getComment();
		saying.setComment(number + 1);
		final Comment comment = new Comment();
		// 设置评论的作者
		comment.setUser(user);
		// 设置评论的内容
		comment.setCommentcontent(content);
		comment.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_LONG)
						.show();
				if (mAdapter.getDataList().size() < Constant.NUMBERS_PER_PAGE) {
					mAdapter.getDataList().add(comment);
					mAdapter.notifyDataSetChanged();
				}
				comment_content.setText("");
				hideInput();

				// 将动态与评论绑定在一起
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				saying.setRelation(relation);
				saying.update(CommentActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Log.e("comment", "更新评论成功");
						showComments();
						commt.setVisibility(View.GONE);
						
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("comment", "绑定失败" + arg1);
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CommentActivity.this, "评论失败" + arg1,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	// 隐藏输入面板
	private void hideInput() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromInputMethod(comment_content.getWindowToken(), 0);

	}

	// 显示输入面板
	private void OnclickComment() {
		commt.setVisibility(View.VISIBLE);
		comment_content.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(comment_content, 0);
	}

	public void showComments() {
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		// 通过联系找到对应的评论
		// 导入对应的联系人
		query.include("user");
		query.order("createdAt");
		query.addWhereRelatedTo("relation", new BmobPointer(saying));
		query.findObjects(CommentActivity.this, new FindListener<Comment>() {

			@Override
			public void onSuccess(List<Comment> data) {
				// TODO Auto-generated method stub
				mAdapter.setDataList(data);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CommentActivity.this, "加载失败" + arg1,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	public void ShareToQQ(){
		Bundle params=new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "  ");
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content_text.getText().toString());
		String url="http://file.bmob.cn/"+saying.getContentfigureurl().getUrl();
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, url);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://gao123.bmob.cn/");
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "图点");
		mTencent.shareToQQ(this, params, new BaseIUlistener());
	}

	class BaseIUlistener implements IUiListener {

		@Override
		public void onError(UiError e) {

			Log.d("error---->", e.errorMessage);
		}

		@Override
		public void onComplete(Object arg0) {

			Log.d("onComplete----->", arg0.toString());

		}

		@Override
		public void onCancel() {

			Log.d("oncancle----->", "cancle");
		}

	}

}
