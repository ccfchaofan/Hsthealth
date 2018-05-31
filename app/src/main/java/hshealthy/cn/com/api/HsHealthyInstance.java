package hshealthy.cn.com.api;

import android.content.Context;

import hshealthy.cn.com.bean.CollectorBean;

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

}
