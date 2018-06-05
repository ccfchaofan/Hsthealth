package hshealthy.cn.com.api;

import android.content.Context;

import hshealthy.cn.com.bean.CollectorBean;
import hshealthy.cn.com.util.AbAppUtil;
import hshealthy.cn.com.util.SPConstantUtils;
import hshealthy.cn.com.util.SPUtils;
import hshealthy.cn.com.util.ScreenUtils;

/**
 * Created by 71443 on 2018/5/25.
 * * Tips: 全局applicationcontext单例 ，防止static方法引用context导致内存泄漏
 */

public class HsHealthyInstance extends CollectorBean {
    public static HsHealthyInstance hstationinstance = getInstance();

    private static final class Holder {
        private static final HsHealthyInstance HI = new HsHealthyInstance();
    }

    public static HsHealthyInstance getInstance() {
        return Holder.HI;
    }

    private HsHealthyInstance() {
    }

    public static Context C() {
        return Holder.HI.getContext();
    }

    public static void init(Context c) {
        getInstance().setContext(c);
//        getInstance().setUrltype(HstationImp.RELEASE);
        getInstance().setSharedPreferencesData();
    }

    public static void init(Context c, String urltype) {
        getInstance().setContext(c);
        getInstance().setUrltype(urltype);
        getInstance().setSharedPreferencesData();
    }


    public void setSharedPreferencesData() {
        SPUtils.init(getContext(), SPConstantUtils.SHARED_PREFERENCE_NAME);//SP初始化
        SPUtils.putString(SPConstantUtils.CLIENTNAME, AbAppUtil.getAppName(getContext()));
        SPUtils.putString(SPConstantUtils.OSVERSION, AbAppUtil.getBuildVersionRelease());
        SPUtils.putString(SPConstantUtils.BRAND, AbAppUtil.getBuildBrand());
        SPUtils.putString(SPConstantUtils.MODEL, AbAppUtil.getSysModel());
        SPUtils.putString(SPConstantUtils.UUID, AbAppUtil.gainUUID());
        SPUtils.putString(SPConstantUtils.RESOLUTION, String.valueOf(
                ScreenUtils.getScreenWidth(getContext())).concat("*").concat(
                String.valueOf(ScreenUtils.getScreenHeight(getContext())
                )));
        SPUtils.putString(SPConstantUtils.CLIENTVERSION, AbAppUtil.getAppVersion(getContext()));
    }
}
