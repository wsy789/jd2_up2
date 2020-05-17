package com.wsy.jd2.presenter;

import com.wsy.jd2.base.BasePresenter;
import com.wsy.jd2.bean.CommentBean;
import com.wsy.jd2.bean.RelatedBean;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.model.DetailsModel;
import com.wsy.jd2.model.login.AffirmPaswMode;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.ui.contract.AffirmPassWordContract;
import com.wsy.jd2.ui.contract.DetailsContract;

public class DetailsPresenter extends BasePresenter<DetailsContract.IAffirmView> implements DetailsContract.IAffirmPresenter{
    private DetailsModel detailsModel;

    @Override
    protected void initModel() {
        detailsModel = new DetailsModel();
        addModel(detailsModel);
    }
    @Override
    public void getRelatedList(String articleID) {//获取关联列表
        detailsModel.getRelatedList(articleID, new ResultCallBack<RelatedBean>() {
            @Override
            public void onSuccess(RelatedBean relatedBean) {
                mView.RelatedListResult(relatedBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public void addUserIntegral(String articleID) {

    }

    @Override
    public void isSetCollect(String articleID, int type) {
        detailsModel.isSetCollect(articleID,type, new ResultCallBack<VerfiedBean>() {
            @Override
            public void onSuccess(VerfiedBean verfiedBean) {
                mView.isCollectResult(verfiedBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public void isSetLike(String articleID, int type) {

    }

    @Override
    public void getcommentlist(String articleID, String start, String point_time) {
        detailsModel.getCommentList(articleID,start,point_time, new ResultCallBack<CommentBean>() {
            @Override
            public void onSuccess(CommentBean commentBean) {

                mView.commentListResult(commentBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
