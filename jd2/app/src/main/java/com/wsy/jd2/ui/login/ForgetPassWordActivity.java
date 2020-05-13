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
import com.wsy.jd2.presenter.login.ForgetPWPresenter;
import com.wsy.jd2.ui.contract.ForgetPWContract;
import com.wsy.jd2.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//忘记密码页面
public class ForgetPassWordActivity extends BaseActivity<ForgetPWPresenter> implements ForgetPWContract.IForgetPWView {

    private String phoneNum;
    private EditText cell_phone_num;//手机号
    private EditText import_verified;//输入验证码
    private TextView verified_get;//获取验证码

    private Button next_but;//下一步but
    private String verified_str;


    @Override
    protected ForgetPWPresenter initPresenter() {
        return new ForgetPWPresenter();
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");

        cell_phone_num = findViewById(R.id.reg_im_phone);
          import_verified = findViewById(R.id.import_verified);//输入验证码
          verified_get= findViewById(R.id.verified_get);//获取验证码
          next_but= findViewById(R.id.reg_next_step);//下一步but

//把手机号传过来，并赋值
        cell_phone_num.setText(phoneNum);
        //TODO 5.12 走到这闪回loginActivity页面
        Log.i("TAG", "initView: 把手机号传过来了");
        initLinstener();

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forgetpassword;
    }

    private void initLinstener() {
//      TODO  在当前页面，设置手机号不可被编辑，上个页面传递过来得
        next_but.setOnClickListener(v -> {
            verified_str = import_verified.getText().toString().trim();
            if(!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(verified_str)){
                //下一步 去修改密码。type2 修改密码
                mPresenter.checkSmsCode(phoneNum, verified_str,"2");
            }else{
                Toast.makeText(this, "手机号和验证码不能为空", Toast.LENGTH_SHORT).show();
            }
        });

//发送验证码
        verified_get.setOnClickListener(v -> {

//判断咱们手机号是否为空，判断 验证 手机号是否正确，发送咱们验证码
            String phonenum = cell_phone_num.getText().toString();
            if (!TextUtils.isEmpty(phonenum) && isMobileNO(phonenum)) {
// 表示可以发送验证码  type 2 表示修改密码
                mPresenter.getVerified(phoneNum,"2");
            } else {
                ToastUtil.showToastShort("请输入正确得手机号");

            }
        });
    }

    @Override
    public void getVerified(VerfiedBean bean) {

        if(bean.getCode() ==1) {
            Toast.makeText(this, "验证码发送成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "验证码发送失败", Toast.LENGTH_SHORT).show();
        }
    }
//验证手机号与验证码是否一致
    @Override
    public void checkSmsCodeResult(VerfiedBean verfiedBean) {

        if(verfiedBean.getCode()==1){
//            验证成功以后，需要跳转页面
            Intent it = new Intent(ForgetPassWordActivity.this,AffirmPassWordActivity.class);
//            验证码和手机号发送下个页面
            it.putExtra("phoneNum",phoneNum);
            it.putExtra("verified_str",verified_str);
            startActivity(it);
        }
    }


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
