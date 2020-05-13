package com.wsy.jd2.ui.contract;

import com.wsy.jd2.base.BaseView;
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.net.ResultCallBack;

/**
 * 契约类
 * 契约     约定
 */
public class PassWordLoginContract {

        public interface IPassWordLoginView extends BaseView {
            void  getPWLoginResult(AffirmRegisterBean string);
        }
        public interface IPassWordLoginMode{
            <T> void passWordLogin(String username, String password, ResultCallBack<T> iNetCallBack);
        }
        public interface IPassWordLoginPresenter{
            void passWordLogin(String username, String password);
        }
}
