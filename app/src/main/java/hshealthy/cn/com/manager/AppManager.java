package hshealthy.cn.com.manager;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class AppManager {
    private static ArrayList<SoftReference<Activity>> activityStack;
    private static AppManager instance;
    // Activity栈的MaxSize，为0表示不限制
    private int mActivityStackMaxSize = 0;
    private AppManager(){
        if (null == activityStack){
            activityStack = new ArrayList<SoftReference<Activity>>(20);
        }
    }

    public static AppManager getInstance() {
        if (null == instance){
            instance = new AppManager();
        }
        return instance;
    }

    public void pushActivity(Activity activity){
        if (null != activity){
            activityStack.add(new SoftReference<>(activity));
            checkAndMaintainActivityStack(mActivityStackMaxSize);
        }
    }

    /**
     * 从Activity栈中移除最上面的Activity
     *
     * @return 返回移除的Activity
     */
    public Activity popActivity() {
        int size = activityStack.size();
        if (size == 0) {
            return null;
        }
        SoftReference<Activity> sr = activityStack.remove(size - 1);
        if (sr == null) {
            return null;
        }
        Activity activity = sr.get();
        return activity;
    }

    /**
     * 从Activity栈中移除某个index对应的Activity
     *
     * @param index 索引
     * @return 返回移除的Activity
     */
    public Activity popActivity(int index) {
        int size = activityStack.size();
        if (size == 0) {
            return null;
        }
        if (index < 0 || index >= size) {
            return null;
        }
        SoftReference<Activity> sr = activityStack.remove(index);
        if (sr == null) {
            return null;
        }
        Activity activity = sr.get();
        return activity;
    }

    /**
     * 得到当前处于栈顶的Activity
     *
     * @return
     */
    public Activity currentActivity() {
        int size = activityStack.size();
        if (size == 0) {
            return null;
        }
        SoftReference<Activity> sr = activityStack.get(size - 1);
        if (sr == null) {
            return null;
        }
        Activity activity = sr.get();
        return activity;
    }


    //------------------------------
    //扩展功能，管理stack的容量大小
    //------------------------------

    OnAllActivityClosed mActivityClosed;
    /**
     * 保留3个Activity，其他的Activity都释放移除并且finish
     */
    public void releaseAllPossibleAcitivities() {
        this.checkAndMaintainActivityStack(3);
    }

    /**
     * 清空Activity栈，所有的Activity都释放移除并且finish
     */
    public void releaseAllAcitivities() {
        if (activityStack != null) {
            while (!activityStack.isEmpty()) {
                SoftReference<Activity> act = activityStack.remove(0);

                if (act != null && act.get() != null) {
                    Activity activity = act.get();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        }

        if (mActivityClosed != null) {
            mActivityClosed.onActivityClosed();
        }
    }
    /**
     * 得到当前Activity栈里面的管理的Activity的数量
     *
     * @return
     */
    public int getSize() {
        return activityStack.size();
    }
    /**
     * 获得Activity栈的最大数量
     *
     * @return
     * @see AppManager
     */
    public int getActivityStackMaxSize() {
        return mActivityStackMaxSize;
    }

    private void checkAndMaintainActivityStack(int activityStackMaxSize) {
        if (activityStackMaxSize == 0) {
            return;
        }

        int currentSize = AppManager.getInstance().getSize();
        while (currentSize > activityStackMaxSize) {
            currentSize--;
            // remove the second bottom.
            Activity act = AppManager.getInstance().popActivity(1);
            if (act != null) {
                act.finish();
            }
        }
    }
    /**
     * 设置所有的Activity都关闭了的监听回调
     *
     * @param activityClosed 回调
     */
    public void setOnActivityAllClosed(OnAllActivityClosed activityClosed) {
        mActivityClosed = activityClosed;
    }

    /**
     * Activity全部关闭时调用的接口
     *
     * @author libin18
     */
    public interface OnAllActivityClosed {
        public void onActivityClosed();
    }
}
