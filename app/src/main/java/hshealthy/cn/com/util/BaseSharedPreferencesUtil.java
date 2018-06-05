/**
 * 
 */
package hshealthy.cn.com.util;

import android.content.Context;

/**
 * 
 * 
 * Created by Wokee
 * 
 * Tips: sharedPre.. 主项目继承实现更多拓展 缓存类
 */
public class BaseSharedPreferencesUtil {

	/**
	 * 示例key
	 */
	public final static String testkey = "testkey";

	/**
	 * 简单写入sp
	 * 
	 * @param value
	 */
	public static void putTestString(String value) {
		putString(testkey, value);
	}

	/**
	 * 提取对应key的sp
	 * 
	 * @return
	 */
	public static String getTestString() {
		return getString(testkey, "");
	}

	/**
	 * 主键名称
	 */
	public static String USE_KEY = "default_key";

	/**
	 * 对全局context保持持有
	 */
	public static Context context;

	public static void init(Context appcontext, String userKey) {
		context = appcontext;
		USE_KEY = userKey;
	}

	public static void remove(String key) {
		StoreShareValue.remove(key, context, USE_KEY);
	}

	public static void clear() {
		StoreShareValue.clear(context, USE_KEY);
	}

	/**
	 * 封装putString
	 * 
	 * @param key
	 * @param value
	 */
	public static void putString(String key, String value) {
		StoreShareValue.putString(key, value + "", context, USE_KEY);
	}

	/**
	 * 封装getString
	 * 
	 * @param key
	 * @param defaultStr
	 */
	public static String getString(String key, String defaultStr) {
		return StoreShareValue.getString(key, defaultStr, context, USE_KEY);
	}

	/**
	 * 封装putboolean
	 * 
	 * @param key
	 * @param value
	 */
	public static void putBoolean(String key, boolean value) {
		StoreShareValue.putBoolean(key, value, context, USE_KEY);
	}

	/**
	 * 封装getboolean
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return StoreShareValue.getBoolean(key, defaultValue, context, USE_KEY);
	}

	/**
	 * 封装putobject
	 * 
	 * @param key
	 * @param value
	 */
	public static void putObject(String key, Object value) {
		StoreShareValue.putObject(key, value, context, USE_KEY);
	}

	/**
	 * 封装getobject
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		return StoreShareValue.getObject(key, USE_KEY, context);
	}

	/**
	 * 封装
	 * 
	 * @param key
	 * @param value
	 */
	public static void putInt(String key, int value) {
		StoreShareValue.putInt(key, value, context, USE_KEY);
	}

	/**
	 * 封装
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return StoreShareValue.getInt(key, 0, context, USE_KEY);
	}
}
