package hshealthy.cn.com.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @描述       字符串相关工具类
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 移除空格
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static String  removeEmptyChar(String s) {
        return s.replaceAll(" ", "");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }



    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     *  字符串编码
     */
    public static String  getStrEncode(String str) {

        try {
           return URLEncoder.encode(str,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
           return "";
    }
    /**
     *  字符串解码
     */
    public static String  getStrDecode(String str){
        try {
        return  URLDecoder.decode(str, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * SpannableString 使一个textView展示不同文字大小
     */
    public static SpannableString formatTextSize(String textString, int whichNum) {
        /**
         * SpannableString 使一个textView展示不同文字大小 new RelativeSizeSpan(0.8f)代表正常字体的0.8倍
         */
        SpannableString spannableString = new SpannableString(textString);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), textString.length() - whichNum, textString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static boolean equals(String constant,String newString) {
        return constant != null && constant.equals(newString);
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 过滤除数字和字母之外的其它字符
     */
    public static String replaceChar(String str) {
        return   str.replaceAll(ConstUtils.REGEX_NUMBERLETTER,"");
    }

    /**
     * 是否含有特殊符号
     *
     * @param str 待验证的字符串
     * @return 是否含有特殊符号
     */
    public static boolean hasSpecialCharacter(String str) {
        String regEx = ConstUtils.REGEX_SPECIALCHARACTER;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * null转String
     * @param str
     * @return
     */
    public static String nullOfString(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * String转Int
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        int i = 0;
        if (str != null) {
            try {
                i = Integer.parseInt(str.trim());
            } catch (Exception e) {
                i = 0;
            }

        } else {
            i = 0;
        }
        return i;
    }

    /**
     * Int转String
     * @param i
     * @return
     */
    public static String intToString(int i) {
        String str = "";
        try {
            str = String.valueOf(i);
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

//    public static boolean checkParamsLegal(HstationSource mHstationSource) {
//
//        String tipMsg = "";
//        if(mHstationSource==null){
//            tipMsg = "数据不能为空!";
////            ToastUtils.showToast(tipMsg);
//            return false;
//        }
//
//        if (TextUtils.isEmpty(mHstationSource.getMobile())) {
//            tipMsg = "手机号不能为空!";
////            ToastUtils.showToast(tipMsg);
//            return false;
//        } else if (mHstationSource.getMobile().length() != 11) {
//            tipMsg = "手机号位数不对!";
////            ToastUtils.showToast(tipMsg);
//            return false;
//        }
//
//        if (TextUtils.isEmpty(mHstationSource.getChannel())) {
//            tipMsg = "渠道标识不能为空!";
////            ToastUtils.showToast(tipMsg);
//            return false;
//        }
//
//        if (TextUtils.isEmpty(mHstationSource.getUid())) {
//            tipMsg = "uid不能为空!";
////            ToastUtils.showToast(tipMsg);
//            return false;
//        }
//
//        return true;
//    }
    public static boolean isNullEmpty(String str){
        if(str!=null&&str.length()>0){
            return false;
        }
        return true;
    }
    public static List<Integer[]> filterWithStr(String string ,String str){
        List<Integer[]> temp =new ArrayList<>();
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            temp.add(new Integer[]{matcher.start(),matcher.end()});
        }
        return temp;
    }
}