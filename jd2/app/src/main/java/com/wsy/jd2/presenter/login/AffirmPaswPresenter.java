package com.wsy.jd2.presenter.login;


import com.wsy.jd2.base.BasePresenter;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.model.login.AffirmPaswMode;
import com.wsy.jd2.model.login.AffirmRegisterModel;
import com.wsy.jd2.model.login.LoginModel;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.ui.contract.AffirmPassWordContract;

public class AffirmPaswPresenter extends BasePresenter<AffirmPassWordContract.IAffirmPaswView> implements AffirmPassWordContract.IAffirmPaswPresenter {

    private AffirmPaswMode affirmPaswMode;

    @Override
    protected void initModel() {
        affirmPaswMode = new AffirmPaswMode();
        addModel(affirmPaswMode);
    }

    @Override
    public void forgetPasw(String phoneNum, String sms, String password) {

        affirmPaswMode.forgetPasw(phoneNum, sms, password, new ResultCallBack<VerfiedBean>() {
            @Override
            public void onSuccess(VerfiedBean bean) {

                mView.registerResult(bean);
            }

            @Override
            public void onFail(String msg) {

            }

        });

    }
}
