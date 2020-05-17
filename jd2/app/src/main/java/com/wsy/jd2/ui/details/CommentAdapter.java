package com.wsy.jd2.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wsy.jd2.R;
import com.wsy.jd2.bean.CommentBean;
import com.wsy.jd2.bean.RelatedBean;
import com.wsy.jd2.widget.CommentsView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CommentBean.DataBean.CommentListBean> commentList;


    public CommentAdapter(Context context, ArrayList<CommentBean.DataBean.CommentListBean> commentList) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_like, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        CommentBean.DataBean.CommentListBean bean = commentList.get(position);
        Glide.with(context).load(bean.getHead_url()).into(recyclerHolder.mImgComment);
        recyclerHolder.mUsernameComment.setText(bean.getUsername());
        recyclerHolder.mTimeComment.setText(bean.getTime_describe());
        recyclerHolder.mTextComment.setText(bean.getContent());
        recyclerHolder.mLikeNumComment.setText(bean.getPraise_count_describe());//被点赞数
        recyclerHolder.mReturnComment.setList(bean.getReply_list());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView mImgComment;
        private TextView mUsernameComment;
        private TextView mTimeComment;
        private TextView mLikeNumComment;
        private ImageView mLikeImgComment;
        private TextView mTextComment;
        private CommentsView mReturnComment;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            mImgComment = (ImageView) itemView.findViewById(R.id.comment_img);
            mUsernameComment = (TextView) itemView.findViewById(R.id.comment_username);
            mTimeComment = (TextView) itemView.findViewById(R.id.comment_time);
            mLikeNumComment = (TextView) itemView.findViewById(R.id.comment_like_num);
            mLikeImgComment = (ImageView) itemView.findViewById(R.id.comment_like_img);
            mTextComment = (TextView) itemView.findViewById(R.id.comment_text);
             mReturnComment = itemView.findViewById(R.id.comment_return_view);
        }
    }
}
