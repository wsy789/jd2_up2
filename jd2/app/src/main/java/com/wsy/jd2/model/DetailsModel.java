package com.wsy.jd2.model;

import android.util.Log;

import com.wsy.jd2.base.BaseModel;
import com.wsy.jd2.net.NetWorkFactory;
import com.wsy.jd2.net.ParamsUtils;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.net.api.URLConstants;
import com.wsy.jd2.ui.contract.DetailsContract;

import java.util.HashMap;

public class DetailsModel extends BaseModel implements DetailsContract.IAffirmMode {
    //获取关联列表
    @Override
    public <T> void getRelatedList(String articleID, ResultCallBack<T> iNetCallBack) {
        HashMap<String, String> commonParams = ParamsUtils.getCommonParams();
        commonParams.put("id",articleID);
        for (String key: commonParams.keySet()) {
            Log.e("TAG","key="+key+",values="+commonParams.get(key));
        }
        NetWorkFactory.getInstance().getNetWork().get(URLConstants.ARTICLE_Related_LIST,commonParams,iNetCallBack);

    }

    @Override
    public <T> void addUserIntegral(String articleID, ResultCallBack<T> iNetCallBack) {

    }

    @Override
    public <T> void isSetCollect(String articleID, int type, ResultCallBack<T> iNetCallBack) {
        HashMap<String, String> commonParams = ParamsUtils.getCommonParams();
        commonParams.put("id",articleID);
        commonParams.put("type",type+"");
        for (String key: commonParams.keySet()) {
            Log.e("TAG","key="+key+",values="+commonParams.get(key));
        }
        NetWorkFactory.getInstance().getNetWork().post(URLConstants.SETCOLLECT,commonParams,iNetCallBack);

    }

    @Override
    public <T> void isSetLike(String articleID, int type, ResultCallBack<T> iNetCallBack) {

    }

    @Override
    public <T> void getCommentList(String articleID, String start, String point_time, ResultCallBack<T> iNetCallBack) {

        HashMap<String, String> commonParams = ParamsUtils.getCommonParams();
        commonParams.put("article_id",articleID);
        commonParams.put("start",start);
        commonParams.put("point_time",point_time);
        for (String key: commonParams.keySet()) {
            Log.e("TAG","key="+key+",values="+commonParams.get(key));
        }
        NetWorkFactory.getInstance().getNetWork().get(URLConstants.COMMENTLIST,commonParams,iNetCallBack);

    }
}
