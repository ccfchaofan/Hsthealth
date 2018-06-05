package hshealthy.cn.com;

import android.app.Application;
import android.content.Context;

import hshealthy.cn.com.api.HsHealthyInstance;
import io.rong.imkit.RongIM;

/**
 * Created by 71443 on 2018/5/28.
 */

public class MyApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        HsHealthyInstance.init(getApplicationContext(),"");
        RongIM.init(this);
    }

    /**
     * @return 全局的上下文
     */
    public static Context getAppContext() {
        return appContext;
    }
}
