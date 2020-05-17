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
import com.wsy.jd2.bean.RelatedBean;

import java.util.ArrayList;

public class NewsRelatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RelatedBean.DataBean.AccessArticleListBean> arrayList;

    public NewsRelatedAdapter(Context context, ArrayList<RelatedBean.DataBean.AccessArticleListBean> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);
        return new RecyclerHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder= (RecyclerHolder) holder;
        RelatedBean.DataBean.AccessArticleListBean bean = arrayList.get(position);
        Glide.with(context).load(bean.getImage_url()).into(recyclerHolder.mImgList);
        recyclerHolder.mThemeList.setText(bean.getTheme());
        recyclerHolder.mColumnNameList.setText(bean.getColumn_name());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView mImgList;
        private TextView mThemeList;
        private TextView mColumnNameList;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            mImgList = (ImageView) itemView.findViewById(R.id.list_img);
            mThemeList = (TextView) itemView.findViewById(R.id.list_theme);
            mColumnNameList = (TextView) itemView.findViewById(R.id.list_column_name);
        }
    }
}
