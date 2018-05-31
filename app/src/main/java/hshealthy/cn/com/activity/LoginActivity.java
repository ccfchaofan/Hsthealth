package hshealthy.cn.com.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import hshealthy.cn.com.R;
import hshealthy.cn.com.base.BaseActivity;
import hshealthy.cn.com.presenter.LoginPresenter;
import hshealthy.cn.com.view.LoginView;

/**
 * Created by 71443 on 2018/5/29.
 * 登录界面
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    private EditText editUser;
    private EditText editcode;
    private Button btcode;
    private Button btlogin;
    private RadioGroup group;
    private RadioButton rbuser;
    private RadioButton rbdoctor;
    TimeCount timeCount;


    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btcode.setOnClickListener(this);
        btlogin.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        editUser = (EditText) findViewById(R.id.user_phone);
        editcode = (EditText) findViewById(R.id.user_code);
        btcode = (Button) findViewById(R.id.bt_code);
        btlogin = (Button) findViewById(R.id.bt_login);
        group = (RadioGroup) findViewById(R.id.user_type);
        rbuser = (RadioButton) findViewById(R.id.user_rb);
        rbdoctor = (RadioButton) findViewById(R.id.doctor_rb);
        timeCount= new TimeCount(60000, 1000);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.hst_login_view;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_code:
                mPresenter.setCodetime();
                break;
            case R.id.bt_login:
                mPresenter.getLogin(editUser.getText().toString(),editcode.getText().toString());
                break;
        }
    }
    //点击X号清空手机号
    @Override
    public void onClearphoneText() {
        editUser.setText("");
        editcode.setText("");

    }
    //点击验证码60s倒计时
    @Override
    public void onSetCodeTime() {
       timeCount.start();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btcode.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btcode.setClickable(false);
            btcode.setText("("+millisUntilFinished / 1000 +") 秒");
        }

        @Override
        public void onFinish() {
            btcode.setText("重发验证码");
            btcode.setClickable(true);
            btcode.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }
}
