package hshealthy.cn.com.model.modellmpl;

import android.support.annotation.NonNull;

import hshealthy.cn.com.MyApp;
import hshealthy.cn.com.base.BaseModel;
import hshealthy.cn.com.bean.CustomerBean;
import hshealthy.cn.com.http.ApiException;
import hshealthy.cn.com.http.CommonSubscriber;
import hshealthy.cn.com.http.CommonTransformer;
import hshealthy.cn.com.model.LoginModel;

/**
 * Created by 71443 on 2018/5/29.
 */

public class LoginModellmpl extends BaseModel implements LoginModel {

    //存储数据，数据库，文件等
    @Override
    public void loginDB(CustomerBean customerBean) {

    }

    public void getlogin(@NonNull String phone , @NonNull String code ,final @NonNull InfoHint infoHint){

        httpService.getIpInfo(phone ,code ).compose(new CommonTransformer<CustomerBean>())
                .subscribe(new CommonSubscriber<CustomerBean>(MyApp.getAppContext()) {
                    @Override
                    public void onNext(CustomerBean bean) {
                        infoHint.successInfo("");
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        infoHint.failInfo(e.message);
                    }

                });
    }

    public void getCode(@NonNull String code){

    }

    //通过接口产生信息回调
    public interface InfoHint {
        void successInfo(String str);
        void failInfo(String str);

    }
}
