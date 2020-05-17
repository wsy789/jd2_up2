package com.wsy.jd2.ui.contract;

import com.wsy.jd2.base.BaseView;
import com.wsy.jd2.bean.CommentBean;
import com.wsy.jd2.bean.RelatedBean;
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.net.ResultCallBack;

/**
 * 详情页面
 */
public class DetailsContract {

        public interface IAffirmView extends BaseView {
            void RelatedListResult(RelatedBean relatedBean);
            void addUserIntegalResult(VerfiedBean bean);
            void isCollectResult(VerfiedBean bean);
            void isSetLikeResult(VerfiedBean bean);
            void commentListResult(CommentBean commentBean);
        }
        public interface IAffirmMode{
            <T> void getRelatedList(String articleID,ResultCallBack<T> iNetCallBack);//获取关联列表
            <T> void addUserIntegral(String articleID,ResultCallBack<T> iNetCallBack);//增加用户积分
            <T> void isSetCollect(String articleID,int type,ResultCallBack<T> iNetCallBack);//设置是否收藏
            <T> void isSetLike(String articleID,int type,ResultCallBack<T> iNetCallBack);//设置是否点赞
            <T> void getCommentList(String articleID,String start,String point_time,ResultCallBack<T> iNetCallBack);
        }
        public interface IAffirmPresenter{
            void getRelatedList(String articleID);//获取关联列表
            void addUserIntegral(String articleID);//增加用户积分
            void isSetCollect(String articleID,int type);//设置是否收藏
            void isSetLike(String articleID,int type);//设置是否点赞
            void getcommentlist(String articleID,String start,String point_time);//文章评论回复列表

        }
}
