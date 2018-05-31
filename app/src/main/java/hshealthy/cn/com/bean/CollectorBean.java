package hshealthy.cn.com.bean;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by 71443 on 2018/5/31.
 */

public class CollectorBean implements Serializable {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
