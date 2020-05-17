package com.wsy.jd2.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mmkv.MMKV;
import com.wsy.jd2.R;
import com.wsy.jd2.base.BaseActivity;
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.presenter.login.LoginPresenter;
import com.wsy.jd2.ui.contract.LoginContract;
import com.wsy.jd2.ui.main.MainActivity;
import com.wsy.jd2.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {


    private EditText phone_num;
    private EditText verfied;
    private Button send_verfied_bug;
    private Button login;
    private TextView mRegisterLogPassMine;
    private TextView mRegisterImMine;


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {
        phone_num = findViewById(R.id.mine_register_phone);
        verfied = findViewById(R.id.mine_register_code);
        send_verfied_bug = findViewById(R.id.send_verfied_bug);
        login = findViewById(R.id.mine_register_bt);
        mRegisterLogPassMine = (TextView) findViewById(R.id.mine_register_log_pass);//密码登录
        mRegisterImMine = (TextView) findViewById(R.id.mine_register_im);//立即注册
        initLinstener();
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login1;
    }


    private String edit_phone_num;
    private String edit_sms_code;

    private void initLinstener() {
        mRegisterLogPassMine.setOnClickListener(v -> {
//密码登录
            Log.e("TAG", "initLinstener: ——密码登录" );
            Intent intentPass = new Intent(this, PassWordLoginActivity.class);
            startActivity(intentPass);
            Log.e("TAG", "initLinstener: ——密码登录，跳转后" );
        });
        mRegisterImMine.setOnClickListener(v -> {
            //立即注册
            Intent intent = new Intent(this, RegisterMSMCodeActivity.class);
            startActivity(intent);
        });
// 发送验证码
        send_verfied_bug.setOnClickListener(v -> {
//判断咱们手机号是否为空，判断 验证 手机号是否正确，发送咱们验证码
            String phonenum = phone_num.getText().toString();
            if (!TextUtils.isEmpty(phonenum) && isMobileNO(phonenum)) {
// 表示可以发送验证码  type 4表示登录发送验证码
                mPresenter.getVerified(phonenum, "4");
            } else {
                ToastUtil.showToastShort("请输入正确得手机号");

            }
        });

//登录
        login.setOnClickListener(v -> {
            Log.i("TAG", "initLinstener: ——点击登录");
            edit_phone_num = phone_num.getText().toString();
            edit_sms_code = verfied.getText().toString();
            if (!TextUtils.isEmpty(edit_phone_num) && isMobileNO(edit_phone_num)) {
                if (!TextUtils.isEmpty(edit_sms_code)) {
// 需要用正则表达式判断验证码是否是6位，且都是数字
                    Pattern pattern = Pattern.compile("\\d{6}");
                    boolean matches = pattern.matcher(edit_sms_code).matches();
                    if (matches) {
                        Log.e("TAG", edit_phone_num + "验证码值：" + edit_sms_code);
                        // 判断你得手机号，和你发送得验证码是否正确，如果正确，调用登录接口
                        // 提交服务器进行判断
                        mPresenter.checkSmsCode(edit_phone_num, edit_sms_code, "4");
// 服务器给我们下发得验证码短信，接收手机号给他，验证码也给他，传回去，判断对不对
// 如果不正确，提示用户
                    } else {
                        Toast.makeText(LoginActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "请输入正确得手机号", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //  是否发送了短信验证码
    @Override
    public void getVerified(VerfiedBean s) {
        if (s.getCode() == 1) {
            Log.i("TAG", "initLinstener: ——成功");
            Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
//            ToastUtil.showToastShort("成功");
            //走到这就崩了
            Log.i("TAG", "initLinstener: ——成功2");

        } else {
            Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 先不去登录 ,获取验证码以后，
     * 判断验证码是否正确
     * 如果正确，在去登录
     *
     * @param affirmRegisterBean
     */
    @Override
    public void getLoginResult(AffirmRegisterBean affirmRegisterBean) {
        if (affirmRegisterBean.getCode().equals("1")) {
            Toast.makeText(this, "登录成功返回数据，且code等于1", Toast.LENGTH_SHORT).show();

            if (null != affirmRegisterBean.getData().getToken().getValue() && affirmRegisterBean.getData().getToken().getValue() != "") {
                MMKV mmkv = MMKV.defaultMMKV();
//                用户信息 直接展示， 在本地存储了
                mmkv.encode("token", affirmRegisterBean.getData().getToken().getValue());
                mmkv.encode("expire_time", affirmRegisterBean.getData().getToken().getExpire_time());
                mmkv.encode("head_url", affirmRegisterBean.getData().getUser_info().getHead_url());
                mmkv.encode("nickname", affirmRegisterBean.getData().getUser_info().getNickname());
                mmkv.encode("mobile", affirmRegisterBean.getData().getUser_info().getMobile());

                finish();//因为有好几个页面需要跳到此页面来
            }
        }
    }

    // 判断去接口验证的 验证码是否正确
    @Override
    public void checkSmsCodeResult(VerfiedBean verfiedBean) {

        if (verfiedBean.getCode() == 1) {
//            表明验证码正确   电话与验证码匹配
//            自动去登录操作了。
//            ToastUtil.showToastShort("验证码输入正确");
            Toast.makeText(LoginActivity.this, "验证码输入正确", Toast.LENGTH_SHORT).show();
            mPresenter.login(edit_phone_num, edit_sms_code);
        } else {
            Toast.makeText(LoginActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();

        }


    }
//上方
  /*  public void startRegister(View view) {
        Intent intent = new Intent(this, RegisterMSMCodeActivity.class);
        startActivity(intent);
    }*/

    //验证手机号码
    private boolean isMobileNO(String edit_phone_num) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-9]))\\d{8}$");
            Matcher m = p.matcher(edit_phone_num);
            flag = m.matches();
        } catch (Exception e) {
//            LOG.error("验证手机号码错误", e);
            Log.e("TAG", "手机号错误" + e.getMessage());
            flag = false;
        }
        return flag;
    }
}
