package com.wsy.jd2.ui.Recommend.rec_news;

import android.content.Context;
import android.net.Uri;
import android.nfc.tech.NfcB;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wsy.jd2.R;
import com.wsy.jd2.base.BaseRlvAdapter;
import com.wsy.jd2.bean.NewsBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class RlvMultiRecVpAdapter extends
        BaseMultiItemQuickAdapter<NewsBean.GoodsListItemBean, BaseViewHolder> {

    private String theme;
    private NewsBean.DataBean.ArticleListBean articleListBean;
    private int view_type;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RlvMultiRecVpAdapter(List<NewsBean.GoodsListItemBean> data) {
        super(data);
        //绑定 layout 对应的 type
        addItemType(NewsBean.GoodsListItemBean.TYPE_Banner, R.layout.list_banner);
        addItemType(NewsBean.GoodsListItemBean.TYPE_Scroll, R.layout.list_scroll);
        addItemType(NewsBean.GoodsListItemBean.TYPE_Lite, R.layout.list_content);
        addItemType(NewsBean.GoodsListItemBean.TYPE_BigPicture, R.layout.list_bigpicture);
        addItemType(NewsBean.GoodsListItemBean.TYPE_BigVideo, R.layout.list_bigvideo);
        addItemType(NewsBean.GoodsListItemBean.TYPE_RightLite, R.layout.list_rightlite);
      /*  List<NewsBean.DataBean.ArticleListBean> article_list = dataBean.getArticle_list();
        for (int i = 0; i < article_list.size(); i++) {
            view_type = article_list.get(i).getView_type();
        }*/
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, NewsBean.GoodsListItemBean item) {
        // 根据返回的 type 分别设置数据
        switch (viewHolder.getItemViewType()) {
            case NewsBean.GoodsListItemBean.TYPE_Banner:
                initBanner(viewHolder, item);
                break;
            case NewsBean.GoodsListItemBean.TYPE_Scroll:
                initScroll(viewHolder, item);
                break;
            case NewsBean.GoodsListItemBean.TYPE_Lite:
                initRecommendList(viewHolder, item);
                break;
            case NewsBean.GoodsListItemBean.TYPE_RightLite:
                initRecommendList(viewHolder, item);
                break;
            case NewsBean.GoodsListItemBean.TYPE_BigPicture:
                initRecommendList(viewHolder, item);
                break;
            case NewsBean.GoodsListItemBean.TYPE_BigVideo:
                initVideoList(viewHolder, item);
                break;
            default:
                break;
        }


    }

    private void initVideoList(BaseViewHolder viewHolder, NewsBean.GoodsListItemBean item) {
        NewsBean.DataBean.ArticleListBean bean = (NewsBean.DataBean.ArticleListBean) item.data;
        viewHolder.setText(R.id.list_video_theme, bean.getTheme());
        viewHolder.setText(R.id.list_video_column_name, bean.getColumn_name());

        JzvdStd jzvdStd =viewHolder.getView(R.id.list_video);
        jzvdStd.setUp(bean.getVideo_url(),
                bean.getTheme(), Jzvd.SCREEN_NORMAL);//参数（播放视频，文章标题，应该是样式）
        Glide.with(mContext)
                .load(bean.getImage_url())
                .into(jzvdStd.posterImageView);
        /*viewHolder.jzvdStd.positionInList = position;
        Glide.with(context)
                .load(bean.getImage_url())//缩略图/预览图
                .into(viewHolder.jzvdStd.posterImageView);*/

   /*     VideoView videoView = viewHolder.getView(R.id.list_video);
        MediaController controller = new MediaController(mContext);
        videoView.setMediaController(controller);
        Uri uri = Uri.parse(bean.getVideo_url());//播放本地视频
        videoView.setVideoURI(uri);
        videoView.start();*/
    }

    private void initScroll(BaseViewHolder viewHolder, NewsBean.GoodsListItemBean item) {
        NewsBean.DataBean.FlashListBean bean = (NewsBean.DataBean.FlashListBean) item.data;
      /*    RxRunTextView scroll_text = viewHolder.getView(R.id.scroll_text);
      //todo 跑马灯的内容都可以点击
        SpannableString spannableString = new SpannableString(bean.getTheme());//内容
        scroll_text.setMovementMethod(LinkMovementMethod.getInstance());
        if (spannableString.toString().isEmpty()){
            scroll_text.setVisibility(View.INVISIBLE);
        }{
            scroll_text.setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.scroll_text,spannableString);
        }*/
        //todo 跑马灯的内容都可以点击
        SpannableString spannableString = new SpannableString(bean.getTheme());//内容
        URLSpan urlSpan = new URLSpan(bean.getShare_link());//点击内容跳转方向
       // spannableString.setSpan(urlSpan, 4, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      /*  *
         * what：对SpannableString进行润色的各种Span；
         * int：需要润色文字段开始的下标；
         * end：需要润色文字段结束的下标；
         * flags：决定开始和结束下标是否包含的标志位，有四个参数可选*/
        TextView mTextView = viewHolder.getView(R.id.recommend_scroll_tv);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(spannableString);


//        scroll_text.setText(spannableString);
/*        List<NewsBean.DataBean.FlashListBean> flash_list = bean.getFlash_list();
        viewHolder.setText(R.id.recommend_scroll_tv,bean.getTheme());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < flash_list.size(); i++) {
            builder.append(flash_list.get(i).getTheme() + "       ");
//            theme = flash_list.get(i).getTheme();
        }
        viewHolder.setText(R.id.recommend_scroll_tv, builder);*/
        /**
         * 跑马灯的内容都可以点击
         * SpannableString：https://www.jianshu.com/p/472fd3e32324
         */
    }

    private void initBanner(BaseViewHolder viewHolder, NewsBean.GoodsListItemBean item) {
        NewsBean.DataBean bean = (NewsBean.DataBean) item.data;
        List<NewsBean.DataBean.BannerListBean> banner_list = bean.getBanner_list();

//        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < banner_list.size(); i++) {
//            builder.append(banner_list.get(i).getTheme()+"       ");
            theme = banner_list.get(i).getTheme();
        }
//        viewHolder.setText(R.id.banner_recomment_tv,builder);
        viewHolder.setText(R.id.banner_recomment_tv, theme);

        Banner banner = viewHolder.getView(R.id.list_recomment_banner);
        banner.setImages(banner_list)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        NewsBean.DataBean.BannerListBean img = (NewsBean.DataBean.BannerListBean) path;
                        Glide.with(context).load(img.getImage_url()).into(imageView);
                    }
                }).start();
    }

    private void initRecommendList(BaseViewHolder viewHolder, NewsBean.GoodsListItemBean item) {
        NewsBean.DataBean.ArticleListBean bean = (NewsBean.DataBean.ArticleListBean) item.data;
        viewHolder.setText(R.id.list_theme, bean.getTheme());
        viewHolder.setText(R.id.list_column_name, bean.getColumn_name());
//        ImageView img = viewHolder.getView(R.id.gools_item_img);
        Glide.with(mContext).load(bean.getImage_url()).into((ImageView) viewHolder.getView(R.id.list_img));

    }
}
