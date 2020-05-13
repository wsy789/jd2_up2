package com.wsy.jd2.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsy.jd2.R;
import com.wsy.jd2.base.BaseActivity;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.presenter.login.RegisterMSMPresenter;
import com.wsy.jd2.ui.contract.RegisterMSMContract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//注册页面
public class RegisterMSMCodeActivity extends BaseActivity<RegisterMSMPresenter> implements RegisterMSMContract.IRegisterMSMView {

    private EditText reg_phone_num;
    private EditText reg_verfied;
    private Button reg_next_step;//下一步
    private Button reg_send_verfied_bug;
    private TextView reg_bug;//验证码登录
    private TextView mPassLogin;//密码登录

    private String reg_edit_phone_num;
    private String reg_edit_sms_code;



    @Override
    protected RegisterMSMPresenter initPresenter() {
        return new RegisterMSMPresenter();
    }

    @Override
    public void initView() {
        reg_phone_num = (EditText) findViewById(R.id.reg_im_phone);
        reg_verfied = (EditText) findViewById(R.id.reg_im_code);
        reg_send_verfied_bug = (Button) findViewById(R.id.send_verfied_bug);//获取验证码
        reg_next_step = (Button) findViewById(R.id.reg_next_step);//下一步
        reg_bug = (TextView) findViewById(R.id.login_code);//验证码登录
        mPassLogin = (TextView) findViewById(R.id.login_pass);//密码登录
        initLinstener();
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_im;
    }

    private void initLinstener() {
//        获取验证码
        reg_send_verfied_bug.setOnClickListener(v -> {
            String phonenum = reg_phone_num.getText().toString();
            if (!TextUtils.isEmpty(phonenum) && isMobileNO(phonenum)) {
//                  表示可以发送验证码  type 1表示注册发送验证码
                mPresenter.getVerified(phonenum, "1");
            } else {
                Toast.makeText(RegisterMSMCodeActivity.this, "请输入正确得手机号", Toast.LENGTH_SHORT).show();
            }
        });


//        注册
        //验证手机号与验证码是否是匹配的
        reg_bug.setOnClickListener(v -> {

            reg_edit_phone_num = reg_phone_num.getText().toString();
            reg_edit_sms_code = reg_verfied.getText().toString();
//手机号是否为空 && 输入的手机号是否是  手机号
            if (!TextUtils.isEmpty(reg_edit_phone_num) && isMobileNO(reg_edit_phone_num)) {
                //验证码是否为空，检测是否是合格验证码
                if (!TextUtils.isEmpty(reg_edit_sms_code)) {
                    Pattern pattern = Pattern.compile("\\d{6}");
                    boolean matches = pattern.matcher(reg_edit_sms_code).matches();
                    if (matches) {
                        //type 1表示注册发送验证码
                        mPresenter.checkSmsCode(reg_edit_phone_num, reg_edit_sms_code, "1");
                    } else {
                        Toast.makeText(RegisterMSMCodeActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterMSMCodeActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterMSMCodeActivity.this, "请输入正确得手机号", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //    验证码返回获取
    @Override
    public void getVerified(VerfiedBean s) {

        if (s.getCode() == 1) {
            Toast.makeText(RegisterMSMCodeActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterMSMCodeActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void getLoginResult(String string) {

    }

    //檢查验证码 返回结果
    @Override
    public void checkSmsCodeResult(VerfiedBean verfiedBean) {
        if (verfiedBean.getCode() == 1) {//确认注册 ， 去填写密码，与手机号绑定
//            跳转另一个页面了
            Intent intent = new Intent(this, AffirmRegisterActivity.class);
            //传手机号
            intent.putExtra("phoneNum", reg_edit_phone_num);
            startActivity(intent);
        }
    }


    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
//      验证手机号码错误
            Log.e("TAG", "手机号错误" + e.getMessage());
            flag = false;
        }
        return flag;
    }

}
