package hshealthy.cn.com.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Tips:通用Util整理
 */

public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast mToast;

    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;

    /**
     * toast show
     * @param msg
     */
    public static void showToast(String msg){
        showToast(msg,Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int dur){
        if (mToast != null)
            mToast.cancel();
//            mToast=Toast.makeText(HstationInstance.C(),msg,dur);
            mToast.show();
    }


    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showShortToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showShortToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToastSafe(final Context context, final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT, args);
            }
        });
    }


    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showLongToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToastSafe(final Context context, final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }


    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showShortToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_SHORT, args);
    }


    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showLongToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     *
     * @param context 上下文
     * @param layoutId 布局文件id
     * @param text  文字
     * @param imgUrl 图片url
     * @param duration 持续时间
     */
//    public static void showCustomToastIntegral(Context context,int layoutId,CharSequence text,String imgUrl,int duration ){
//        LayoutInflater inflate = LayoutInflater.from(context);
//        View v = inflate.inflate(layoutId, null);
//        TextView tv= (TextView) v.findViewById(R.id.toast_text);
//        ImageView iv= (ImageView) v.findViewById(R.id.toast_img);
//        Glide.with(context).load(imgUrl).into(iv);
//        tv.setText(text);
//        Toast result = new Toast(context);
//        result.setView(v);
//        result.setGravity(Gravity.CENTER, 0, 0);
//        result.setDuration(duration);
//        result.show();
//    }
//    public static void showCustomToastIntegral(Context context,int layoutId,CharSequence text,int imgResId,int duration ){
//        LayoutInflater inflate = LayoutInflater.from(context);
//        View v = inflate.inflate(layoutId, null);
//        TextView tv= (TextView) v.findViewById(R.id.toast_text);
//        ImageView iv= (ImageView) v.findViewById(R.id.toast_img);
//        iv.setImageResource(imgResId);
//        tv.setText(text);
//        Toast result = new Toast(context);
//        result.setView(v);
//        result.setGravity(Gravity.CENTER, 0, 0);
//        result.setDuration(duration);
//        result.show();
//    }
//    public static void showCustomToastIntegral(Context context,CharSequence text,int imgResId,int duration ){
//        showCustomToastIntegral(context,R.layout.lb_hslib_toast_integral_layout,text,imgResId,duration);
//    }
//    public static void showCustomToastIntegral(Context context,CharSequence text,String imgUrl,int duration ){
//        showCustomToastIntegral(context,R.layout.lb_hslib_toast_integral_layout,text,imgUrl,duration);
//    }



    /**
     * 显示定制的吐司
     *
     * @param context        上下文
     * @param customLayoutId 布局文件id
     * @param resId          文字资源id
     * @param imgResId       图片资源id
     * @param duration       持续时间
     */
//    public static void showCustomToast(Context context, int customLayoutId, int resId, int imgResId, int duration) {
//        showCustomToast(context, customLayoutId, context.getResources().getString(resId), imgResId, duration);
//    }

    /**
     * 显示定制的吐司
     *
     * @param context        上下文
     * @param customLayoutId 布局文件id
     * @param text           文字
     * @param imgResId       图片资源id
     * @param duration       持续时间
     */
//    public static void showCustomToast(Context context, int customLayoutId, CharSequence text, int imgResId, int duration) {
//        LayoutInflater inflate = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflate.inflate(customLayoutId, null);
//        TextView tv = (TextView) v.findViewById(R.id.toast_msg);
//        tv.setText(text);
//        ImageView iv = (ImageView) v.findViewById(R.id.toast_img);
//        iv.setImageResource(imgResId);
//        Toast result = new Toast(context);
//        result.setView(v);
//        result.setGravity(Gravity.CENTER, 0, 0);
//        result.setDuration(duration);
//        result.show();
//    }

    /**
     * 显示定制的吐司
     *
     * @param context        上下文
     * @param text           文字
     * @param imgResId       图片资源id
     * @param duration       持续时间
     */
//    public static void showCustomToast(Context context, CharSequence text, int imgResId, int duration) {
//        showCustomToast(context, R.layout.lb_hslib_toast_custom_layout, text, imgResId, duration);
//    }

    /**
     * 显示定制的吐司
     *
     * @param context        上下文
     * @param resId          文字资源id
     * @param imgResId       图片资源id
     * @param duration       持续时间
     */
//    public static void showCustomToast(Context context, int resId, int imgResId, int duration) {
//        showCustomToast(context, R.layout.lb_hslib_toast_custom_layout, resId, imgResId, duration);
//    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context, CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, int resId, int duration, Object... args) {
        showToast(context, String.format(context.getResources().getString(resId), args), duration);
    }


    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
