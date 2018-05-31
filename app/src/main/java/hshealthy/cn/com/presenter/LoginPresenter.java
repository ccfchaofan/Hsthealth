package hshealthy.cn.com.presenter;


import hshealthy.cn.com.activity.LoginActivity;
import hshealthy.cn.com.base.BasePresenter;
import hshealthy.cn.com.model.modellmpl.LoginModellmpl;
import hshealthy.cn.com.util.ToastUtil;
import hshealthy.cn.com.view.LoginView;

/**
 * Created by 71443 on 2018/5/29.
 * 登录presenter 控制层
 */

public class LoginPresenter extends BasePresenter<LoginModellmpl, LoginActivity> {
    LoginView iLoginView;

    @Override
    public LoginModellmpl loadModel() {
        return new LoginModellmpl();
    }

    public LoginPresenter(LoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    //登录调用
    public void getLogin(String phone, String code) {
        getModel().getlogin(phone, code, new LoginModellmpl.InfoHint() {
            @Override
            public void successInfo(String str) {
                ToastUtil.setToast(str);
            }

            @Override
            public void failInfo(String str) {
                ToastUtil.setToast(str);
            }
        });
    }

    //点击验证码调用
    public void getcode(String phone) {

    }

    //点击验证码操作
    public void setCodetime() {
        iLoginView.onSetCodeTime();

    }
}
