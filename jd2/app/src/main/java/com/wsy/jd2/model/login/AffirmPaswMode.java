package com.wsy.jd2.model.login;

import android.util.Log;

import com.wsy.jd2.base.BaseModel;
import com.wsy.jd2.net.NetWorkFactory;
import com.wsy.jd2.net.ParamsUtils;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.net.api.URLConstants;
import com.wsy.jd2.ui.contract.AffirmPassWordContract;

import java.util.HashMap;

public class AffirmPaswMode extends BaseModel implements AffirmPassWordContract.IAffirmPaswMode {
    @Override
    public <T> void forgetPasw(String mobile, String sms_code, String password, ResultCallBack<T> iNetCallBack) {

        HashMap<String, String> commonParams = ParamsUtils.getCommonParams();
        commonParams.put("mobile",mobile);
        commonParams.put("password",password);
        commonParams.put("sms_code",sms_code);

        for (String key: commonParams.keySet()) {
            Log.e("TAG","key="+key+",values="+commonParams.get(key));
        }
        NetWorkFactory.getInstance().getNetWork().post(URLConstants.FORGET_PW,commonParams,iNetCallBack);




    }
}
