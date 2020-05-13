package com.wsy.jd2.ui.contract;

import com.wsy.jd2.base.BaseView;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.net.ResultCallBack;

public class AffirmPassWordContract {


    public interface IAffirmPaswView extends BaseView {
        void registerResult(VerfiedBean bean);
    }
    public interface IAffirmPaswMode{
        <T> void forgetPasw(String mobile, String sms_code, String password, ResultCallBack<T> iNetCallBack);
    }
    public interface IAffirmPaswPresenter{
        void forgetPasw(String mobile, String sms_code, String password);

    }
}
