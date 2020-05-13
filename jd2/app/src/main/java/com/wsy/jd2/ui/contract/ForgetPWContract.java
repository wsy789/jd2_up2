package com.wsy.jd2.ui.contract;

import com.wsy.jd2.base.BaseView;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.net.ResultCallBack;

/**
 * 契约类
 * 契约     约定
 */
public class ForgetPWContract {

        public interface IForgetPWView extends BaseView {
            void getVerified(VerfiedBean s);
            void checkSmsCodeResult(VerfiedBean verfiedBean);

        }
        public interface IForgetPWMode{
           <T> void getVerified(String phoneNum, String type, ResultCallBack<T> iNetCallBack);
            //验证手机号与验证码是否一致
            <T> void checkSmsCode(String phoneNum, String smsCode, String type, ResultCallBack<T> iNetCallBack);
        }
        public interface IForgetPWPresenter{
            void getVerified(String string, String type);
            void checkSmsCode(String phoneNum, String smsCode, String type);
        }
}
