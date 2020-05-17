package com.wsy.jd2.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.wsy.jd2.R;
import com.wsy.jd2.bean.NewsBean;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWeb;
    private NewsBean.DataBean.ArticleListBean articleListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        articleListBean = intent.getParcelableExtra("article");

        initView();

    }

    private void initView() {
        mWeb = (WebView) findViewById(R.id.web);
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.loadUrl(articleListBean.getLink());

    }
}
