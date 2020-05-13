package com.wsy.jd2.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wsy.jd2.R;
import com.wsy.jd2.base.BaseActivity;
import com.wsy.jd2.bean.login.VerfiedBean;
import com.wsy.jd2.presenter.login.AffirmPaswPresenter;
import com.wsy.jd2.ui.contract.AffirmPassWordContract;

//确认密码
public class AffirmPassWordActivity extends BaseActivity<AffirmPaswPresenter> implements AffirmPassWordContract.IAffirmPaswView {

    private EditText cell_password;//密码
    private EditText affirm_psw;//确认密码
    private Button accomplish_but;//完成
    private String phoneNum;
    private String verified_str;
    
    @Override
    protected AffirmPaswPresenter initPresenter() {
        return new AffirmPaswPresenter();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        verified_str = intent.getStringExtra("verified_str");

        cell_password = findViewById(R.id.cell_password);
        affirm_psw = findViewById(R.id.affirm_psw);
        accomplish_but = findViewById(R.id.accomplish_but);
        initLinstener();
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_affirm;
    }

    private void initLinstener() {
        accomplish_but.setOnClickListener(v -> {
            String pasw_str = cell_password.getText().toString().trim();
            String apw = affirm_psw.getText().toString().trim();

            if(!TextUtils.isEmpty(pasw_str) && !TextUtils.isEmpty(apw)){
                //忘记密码接口参数 3个
//                当前页面只有密码，手机号和验证码需要上个页面传递过来
                if(pasw_str.equals(apw)){
                    mPresenter.forgetPasw(phoneNum,verified_str,pasw_str);
                }
            }else Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();


        });
    }
//验证手机号，验证码成功，
    @Override
    public void registerResult(VerfiedBean bean) {

        if(bean.getCode()==1){//短信验证码正确，且密码修改成功
            Intent it = new Intent(AffirmPassWordActivity.this,LoginActivity.class);
            startActivity(it);
        }else {
            Toast.makeText(this, bean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
