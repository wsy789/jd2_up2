package com.wsy.jd2.presenter.login;


import com.wsy.jd2.base.BasePresenter;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.model.login.AffirmPaswMode;
import com.wsy.jd2.model.login.ForgetPWModel;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.ui.contract.ForgetPWContract;

public class ForgetPWPresenter extends BasePresenter<ForgetPWContract.IForgetPWView> implements ForgetPWContract.IForgetPWPresenter {
    private ForgetPWModel forgetPWModel;

    @Override
    protected void initModel() {
        forgetPWModel = new ForgetPWModel();
        addModel(forgetPWModel);
    }

    @Override
    public void getVerified(String string, String type) {

        forgetPWModel.getVerified(string, type, new ResultCallBack<VerfiedBean>() {
            @Override
            public void onSuccess(VerfiedBean verfiedBean) {
                mView.getVerified(verfiedBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }
    //验证手机号与验证码是否一致
    @Override
    public void checkSmsCode(String phoneNum, String smsCode, String type) {

        forgetPWModel.checkSmsCode(phoneNum, smsCode, type, new ResultCallBack<VerfiedBean>() {
            @Override
            public void onSuccess(VerfiedBean verfiedBean) {
                mView.checkSmsCodeResult(verfiedBean);
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }
}
