package hshealthy.cn.com.util;

import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/*
 *
 * Log工具类
 */
public class Log {

    private static Toast toast = null;
    /**
     * isDebug :是用来控制，是否打印日志
     */
    private static final boolean isDeBug = true;
    /**
     * VERBOSE日志形式的标识符
     */
    public static final int VERBOSE = 5;
    /**
     * DEBUG日志形式的标识符
     */
    public static final int DEBUG = 4;
    /**
     * INFO日志形式的标识符
     */
    public static final int INFO = 3;
    /**
     * WARN日志形式的标识符
     */
    public static final int WARN = 2;
    /**
     * ERROR日志形式的标识符
     */
    public static final int ERROR = 1;

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;


    /**
     * 把异常用来输出日志的综合方法
     *
     * @param tag       日志标识
     * @param throwable 抛出的异常
     * @param type      日志类型
     */
    public static void log(String tag, Throwable throwable, int type) {
        log(tag, exToString(throwable), type);
    }

    /**
     * 用来输出日志的综合方法（文本内容）
     *
     * @param tag  日志标识
     * @param msg  要输出的内容
     * @param type 日志类型
     */
    public static void log(String tag, String msg, int type) {
        switch (type) {
            case VERBOSE:
                v(tag, msg);// verbose等级
                break;
            case DEBUG:
                d(tag, msg);// debug等级
                break;
            case INFO:
                i(tag, msg);// info等级
                break;
            case WARN:
                w(tag, msg);// warn等级
                break;
            case ERROR:
                e(tag, msg);// error等级
                break;
            default:
                break;
        }
    }

    /**
     * verbose等级的日志输出
     *
     * @param tag 日志标识
     * @param msg 要输出的内容
     */
    public static void v(String tag, String msg) {
        // 是否开启日志输出
        if (isDeBug) {
            android.util.Log.v(tag, msg);
        }

    }

    /**
     * debug等级的日志输出
     *
     * @param tag 标识
     * @param msg 内容
     */
    public static void d(String tag, String msg) {
        if (isDeBug) {
            android.util.Log.d(tag, msg);
        }

    }

    /**
     * info等级的日志输出
     *
     * @param tag 标识
     * @param msg 内容
     */
    public static void i(String tag, String msg) {
        if (isDeBug) {
            android.util.Log.i(tag, msg);
        }

    }

    /**
     * warn等级的日志输出
     *
     * @param tag 标识
     * @param msg 内容
     */
    public static void w(String tag, String msg) {
        if (isDeBug) {
            android.util.Log.w(tag, msg);
        }

    }

    /**
     * error等级的日志输出
     *
     * @param tag 标识
     * @param msg 内容
     */
    public static void e(String tag, String msg) {
        if (isDeBug) {
            android.util.Log.e(tag, msg);
        }

    }

    /**
     * 把异常信息转化为字符串
     *
     * @param ex 异常信息
     * @return 异常信息字符串
     */
    private static String exToString(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        String result = writer.toString();
        return result;
    }

    public static void showLogCompletion(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0 || !isDeBug) {
            return;
        }
        int segmentSize = 1 * 1000;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            android.util.Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                android.util.Log.e(tag, logContent);
            }
            android.util.Log.e(tag, msg);// 打印剩余日志
        }
    }
}
