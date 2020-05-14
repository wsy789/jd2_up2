package com.wsy.jd2.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.wsy.jd2.R;
import com.wsy.jd2.bean.NewsBean;

public class DetailsActivity extends AppCompatActivity {

    private WebView mDetailsWebNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        NewsBean.DataBean.ArticleListBean articleListBean = intent.getParcelableExtra("article");

        mDetailsWebNews = (WebView) findViewById(R.id.news_details_web);
        //一般写webview 需要以下四步
        WebSettings settings = mDetailsWebNews.getSettings();
        settings.setJavaScriptEnabled(true);
        mDetailsWebNews.setWebViewClient(new WebViewClient());
        mDetailsWebNews.setWebChromeClient(new WebChromeClient());

        mDetailsWebNews.loadUrl(articleListBean.getLink());
    }

}
