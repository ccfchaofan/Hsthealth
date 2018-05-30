package hshealthy.cn.com.util;

/*
 * @创建者     master
 * @描述       常量相关工具类
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class ConstUtils {

    private ConstUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /******************** 正则相关常量 ********************/

    //手机号表达式
    public  static final String MOBILE_SIMPLE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";

    /**
     * 正则：IP地址
     */
    public static final String REGEX_IP  = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    /**
     * 正则：URL
     */
    public static final String REGEX_URL= "[a-zA-z]+://[^\\s]*";
    /**
     * 正则：汉字
     */
    public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";

    /**
     * 正则：字母和数字
     */
    public static final String REGEX_NUMBERLETTER= "[^0-9a-zA-Z]";

    /**
     * 正则：特殊符号
     */
    public static final String REGEX_SPECIALCHARACTER = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

}