package hshealthy.cn.com.util;

import android.widget.Toast;
import hshealthy.cn.com.MyApp;

/**
 * Toast 相关工具类
 */
public class ToastUtil {
    public static Toast toast;

    public static void setToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void setToast(int contentId) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), contentId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(contentId);
        }
        toast.show();
    }
}
