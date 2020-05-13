package com.wsy.jd2.model.login;

import android.util.Log;

import com.wsy.jd2.base.BaseModel;
import com.wsy.jd2.net.NetWorkFactory;
import com.wsy.jd2.net.ParamsUtils;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.net.api.URLConstants;
import com.wsy.jd2.ui.contract.PassWordLoginContract;

import java.util.HashMap;

public class PassWordLoginModel extends BaseModel implements PassWordLoginContract.IPassWordLoginMode {
    @Override
    public <T> void passWordLogin(String userName, String password, ResultCallBack<T> iNetCallBack) {

        HashMap<String, String> commonParams = ParamsUtils.getCommonParams();
        commonParams.put("username",userName);
        commonParams.put("password",password);

        for (String key: commonParams.keySet()) {
            Log.e("TAG","key="+key+",values="+commonParams.get(key));
        }
        NetWorkFactory.getInstance().getNetWork().post(URLConstants.PHONEPAWORD_LOGIN,commonParams,iNetCallBack);
    }
}
