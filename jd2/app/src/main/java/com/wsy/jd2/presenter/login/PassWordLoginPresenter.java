package com.wsy.jd2.presenter.login;


import com.wsy.jd2.base.BasePresenter;
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.model.login.LoginModel;
import com.wsy.jd2.model.login.PassWordLoginModel;
import com.wsy.jd2.net.ResultCallBack;
import com.wsy.jd2.ui.contract.PassWordLoginContract;

public class PassWordLoginPresenter extends BasePresenter<PassWordLoginContract.IPassWordLoginView> implements PassWordLoginContract.IPassWordLoginPresenter {

    private PassWordLoginModel passWordLoginModel;

    @Override
    protected void initModel() {
        passWordLoginModel = new PassWordLoginModel();
        addModel(passWordLoginModel);
    }

    @Override
    public void passWordLogin(String username, String password) {

        passWordLoginModel.passWordLogin(username, password, new ResultCallBack<AffirmRegisterBean>() {
            @Override
            public void onSuccess(AffirmRegisterBean affirmRegisterBean) {
                mView.getPWLoginResult(affirmRegisterBean);
            }

            @Override
            public void onFail(String msg) {

            }

        });

    }
}
