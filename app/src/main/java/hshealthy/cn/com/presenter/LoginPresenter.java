package hshealthy.cn.com.presenter;


import hshealthy.cn.com.activity.LoginActivity;
import hshealthy.cn.com.base.BasePresenter;
import hshealthy.cn.com.model.modellmpl.LoginModellmpl;

/**
 * Created by 71443 on 2018/5/29.
 * 登录presenter 控制层
 */

public class LoginPresenter extends BasePresenter<LoginModellmpl, LoginActivity> {

    @Override
    public LoginModellmpl loadModel() {
        return new LoginModellmpl();
    }

    public void getLogin(String phone, String code) {
        getModel().getlogin(phone, code, new LoginModellmpl.InfoHint() {
            @Override
            public void successInfo(String str) {

            }

            @Override
            public void failInfo(String str) {

            }
        });
    }
}
