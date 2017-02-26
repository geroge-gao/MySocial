package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.entity.Comment;
import com.example.mysocial.R;

public class CommentAdapter extends BaseContentAdapter<Comment> {

	public CommentAdapter(Context context, List<Comment> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	//convertView��ʾװ�ٽ����Ķ���������saying�Ľ���
	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mHolder;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.comment_item, null);
			mHolder.userName = (TextView) convertView
					.findViewById(R.id.userName_comment);
			mHolder.commentContent = (TextView) convertView
					.findViewById(R.id.content_comment);
			mHolder.index = (TextView) convertView
					.findViewById(R.id.index_comment);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();// �����۵�ʵ���������ȡ����
		}

		final Comment comment = dataList.get(position);
		if (comment.getUser() != null) {// ��ȡ���۵��û���
			String nickname=comment.getUser().getNickName();
			if(!"".equals(nickname)){//����ǳƲ�Ϊ�գ�����ʾ�ǳ�
				mHolder.userName.setText(nickname);
			}else{
				mHolder.userName.setText(comment.getUser().getUsername());
			}
		}
		
		mHolder.index.setText((position+1)+"¥");
		mHolder.commentContent.setText(comment.getCommentcontent());
		return convertView;
	}

	public static class ViewHolder {
		public TextView userName;
		public TextView commentContent;
		public TextView index;
	}

}
