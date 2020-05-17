package com.wsy.jd2.ui.details;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mmkv.MMKV;
import com.wsy.jd2.R;
import com.wsy.jd2.base.BaseActivity;
import com.wsy.jd2.bean.CommentBean;
import com.wsy.jd2.bean.NewsBean;
import com.wsy.jd2.bean.RelatedBean;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.presenter.DetailsPresenter;
import com.wsy.jd2.ui.contract.DetailsContract;
import com.wsy.jd2.ui.login.LoginActivity;

import java.util.ArrayList;

public class DetailsActivity extends BaseActivity<DetailsPresenter> implements DetailsContract.IAffirmView {

    private WebView mDetailsWebNews;
    private RecyclerView mRecyRelated;
    private ArrayList<RelatedBean.DataBean.AccessArticleListBean> arrayList = new ArrayList<>();
    private ArrayList<CommentBean.DataBean.CommentListBean> commentList = new ArrayList<>();
    private NewsRelatedAdapter adapter;
    private RelatedBean relatedBean = new RelatedBean();
    private NewsBean.DataBean.ArticleListBean articleListBean;
    private ImageView mDetailsBackTitle;
    private ImageView mDetailsSearchTitle;
    private ImageView mDetailsLikeTitle;
    private ImageView mDetailsCollectTitle;
    private ImageView mDetailsShareTitle;
    private RecyclerView mRecyCommentRelated;
    private CommentAdapter commentAdapter;

    @Override
    protected DetailsPresenter initPresenter() {
        return new DetailsPresenter();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        articleListBean = intent.getParcelableExtra("article");

        mDetailsWebNews = (WebView) findViewById(R.id.news_details_web);
        mRecyRelated = (RecyclerView) findViewById(R.id.related_recy);//文章列表
        mRecyCommentRelated = (RecyclerView) findViewById(R.id.related_recy_comment);//评论列表
        mDetailsBackTitle = (ImageView) findViewById(R.id.title_details_back);
        mDetailsSearchTitle = (ImageView) findViewById(R.id.title_details_search);
        mDetailsLikeTitle = (ImageView) findViewById(R.id.title_details_like);//点赞
        mDetailsCollectTitle = (ImageView) findViewById(R.id.title_details_collect);//收藏
        mDetailsShareTitle = (ImageView) findViewById(R.id.title_details_share);
        if (articleListBean != null) {
            //文章列表
            adapter = new NewsRelatedAdapter(this, arrayList);
            mRecyRelated.setLayoutManager(new LinearLayoutManager(this));
            mRecyRelated.setAdapter(adapter);
//评论列表
            commentAdapter = new CommentAdapter(this, commentList);
            mRecyRelated.setLayoutManager(new LinearLayoutManager(this));
            mRecyRelated.setAdapter(commentAdapter);
            initWebView();
            initTitle();//返回分享，点赞，收藏
        } else {
            Toast.makeText(this, "文章内容有误", Toast.LENGTH_SHORT).show();
        }

        initListener();

    }


    private void initTitle() {
        int is_good = articleListBean.getIs_good();//是否点赞
        int is_collect = articleListBean.getIs_collect();//是否收藏

        if (is_good == 1) {//已点赞
            mDetailsLikeTitle.setClickable(true);
            Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();
        } else {
            mDetailsLikeTitle.setClickable(false);
            Toast.makeText(this, "取消点赞", Toast.LENGTH_SHORT).show();
        }
        if (is_collect == 1) {//已收藏
            mDetailsCollectTitle.setSelected(true);
//            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            mDetailsCollectTitle.setSelected(false);
//            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
    }

    private void initWebView() {//有关webView
        //一般写webview 需要以下四步
        WebSettings settings = mDetailsWebNews.getSettings();
        settings.setJavaScriptEnabled(true);
        mDetailsWebNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //當webView加载完成以后，再完成文章列表
                mPresenter.getRelatedList(articleListBean.getId());
                //获取文章评论
                mPresenter.getcommentlist(articleListBean.getId(), "0", "0");
                super.onPageFinished(view, url);
            }

            //                              应该重写URL加载
            @RequiresApi
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //在本页面加载，
//                view.loadUrl(request.getUrl().toString(), request.getRequestHeaders());
                return true;
            }
        });
        mDetailsWebNews.setWebChromeClient(new WebChromeClient());
        mDetailsWebNews.loadUrl(articleListBean.getLink());
    }

    private void initListener() {
        mDetailsLikeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMKV mmkv = MMKV.defaultMMKV();
                String token = mmkv.decodeString("token", "");
                //判断是否登录过，登录以后才能点赞
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(DetailsActivity.this, "当前未登录，请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    //已登录
                    mPresenter.isSetLike(articleListBean.getId(), articleListBean.getIs_good());
                }
            }
        });
        mDetailsCollectTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMKV mmkv1 = MMKV.defaultMMKV();
                String token1 = mmkv1.decodeString("token", "");
                if (TextUtils.isEmpty(token1)) {
                    startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                    Toast.makeText(DetailsActivity.this, "当前未登录，请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    //已登录
                    Toast.makeText(DetailsActivity.this, "点击收藏", Toast.LENGTH_SHORT).show();

//                    mPresenter.isSetCollect(articleListBean.getId(), articleListBean.getIs_collect() == 1 ? 2 : 1);
                    //articleListBean.getIs_collect()==1 ? 2 : 1
                    //如果等于1，就是已收藏，那就给他2，改成未收藏
                    //如果等于0，就是未收藏，那就给他1，改成已收藏
                }
            }
        });
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_details;
    }

    @Override
    public void RelatedListResult(RelatedBean relatedBean) {//获取关联列表
//        this.relatedBean =relatedBean;
        this.arrayList.addAll(relatedBean.getData().getAccess_article_list());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addUserIntegalResult(VerfiedBean bean) {

    }

    @Override
    public void isCollectResult(VerfiedBean bean) {
        if (bean.getCode() == 1) {//网络请求成功
            if (articleListBean.getIs_collect() == 1) {//已收藏状态，改成未收藏状态
                articleListBean.setIs_collect(0);
                mDetailsCollectTitle.setSelected(false);
                Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                articleListBean.setIs_collect(1);
                mDetailsCollectTitle.setSelected(true);
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void isSetLikeResult(VerfiedBean bean) {

    }

    @Override
    public void commentListResult(CommentBean commentBean) {

    }


}
