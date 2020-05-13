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
import com.wsy.jd2.bean.login.AffirmRegisterBean;
import com.wsy.jd2.presenter.login.PassWordLoginPresenter;
import com.wsy.jd2.ui.contract.PassWordLoginContract;
import com.wsy.jd2.ui.main.MainActivity;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//密码登录
public class PassWordLoginActivity extends BaseActivity<PassWordLoginPresenter> implements PassWordLoginContract.IPassWordLoginView {

    private EditText cell_phone_num;
    private EditText import_pass;
    private Button forget_pass;//忘记密码按钮
    private Button pass_login;//登录按钮
    private TextView verification_login;//验证码登录按钮
    private TextView go_register;


    @Override
    protected PassWordLoginPresenter initPresenter() {
        return new PassWordLoginPresenter();
    }

    @Override
    public void initView() {
        cell_phone_num = findViewById(R.id.cell_phone_num);
        import_pass = findViewById(R.id.import_pass);
        forget_pass = findViewById(R.id.but_forget_password);
        pass_login = findViewById(R.id.pass_login);//登录按钮
        verification_login = findViewById(R.id.verification_login);//验证码登录
        go_register = findViewById(R.id.go_register);//跳转注册按钮
        initLinstener();
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_pw;
    }


    private void initLinstener() {
        //点击忘记密码按钮
        forget_pass.setOnClickListener(v -> {
            String phone_str = cell_phone_num.getText().toString().trim();
            if(!TextUtils.isEmpty(phone_str) && isMobileNO(phone_str)){
                Intent forgetPass = new Intent(this,ForgetPassWordActivity.class);
                forgetPass.putExtra("phoneNum",phone_str);
                startActivity(forgetPass);
            } else {
                Toast.makeText(PassWordLoginActivity.this, "请输入正确得手机号", Toast.LENGTH_SHORT).show();
            }

        });
        //点击登录按钮
        pass_login.setOnClickListener(v -> {
            String phone_str = cell_phone_num.getText().toString().trim();
            String pass_str= import_pass.getText().toString().trim();
            if (!TextUtils.isEmpty(phone_str) && !TextUtils.isEmpty(pass_str)){
//                 手机号是不是正确得
                if(isMobileNO(phone_str)){
                    mPresenter.passWordLogin(phone_str,pass_str);
                }else {
                    Toast.makeText(this, "请输入正确得手机号", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
            }
        });
        //验证码登录按钮
        verification_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        go_register.setOnClickListener(v -> {
            //立即注册
            Intent intent = new Intent(this, RegisterMSMCodeActivity.class);
            startActivity(intent);
        });
    }
    //点击登录返回结果
    @Override
    public void getPWLoginResult(AffirmRegisterBean bean) {
        if(bean.getCode().equals("1")){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            //登录成功 , 回到首页
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
        
    }

    /**
     * 验证手机号码----TODO 最好写成工具类
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
//            LOG.error("验证手机号码错误", e);
            Log.e("TAG","手机号错误"+e.getMessage());
            flag = false;
        }
        return flag;
    }

}
