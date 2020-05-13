package com.wsy.jd2.ui.contract;


import com.wsy.jd2.base.BaseView;
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.net.ResultCallBack;

public class LoginContract {

        public interface ILoginView extends BaseView {
//登录
            void getVerified(VerfiedBean s);//获取验证码
            void  getLoginResult(AffirmRegisterBean affirmRegisterBean);//获取login结果

        void checkSmsCodeResult(VerfiedBean verfiedBean);//判断验证码是否正确

        }
        public interface ILoginMode{
           <T> void getVerified(String phoneNum, String type, ResultCallBack<T> iNetCallBack);
            <T> void login(String mobile, String smsCode, ResultCallBack<T> iNetCallBack);

        <T> void checkSmsCode(String phoneNum, String smsCode, String type, ResultCallBack<T> iNetCallBack);
        }
        public interface ILoginPresenter{
            void getVerified(String string, String type);
            void login(String mobile, String smsCode);

    void checkSmsCode(String phoneNum, String smsCode, String type);
        }
}
