package hshealthy.cn.com.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hshealthy.cn.com.digest.DigestUtils;


/**
 * MD5加密
 */

public class MD5Utils {

    private static final String SignType = "MD5";
    private static final String inputCharset = "UTF-8";
    private MD5Utils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * MD5加密
     */
    public static String getSign(String md5_key, Map<String, String> dataMap) throws Exception {
        List<String> keyList = new ArrayList<>(dataMap.keySet());
        Collections.sort(keyList);
        StringBuilder builder = new StringBuilder();
        for (String mapKey : keyList) {
            if (!(dataMap.get(mapKey).length()< dataMap.get(mapKey).getBytes().length)) {
                builder.append(dataMap.get(mapKey));
            }
        }
        builder.append(md5_key);
        MessageDigest md5 = MessageDigest.getInstance(SignType);
        md5.update(builder.toString().getBytes(inputCharset));
        byte[] md5Bytes = md5.digest();
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
        /**
     * 生成签名
     *
     * @return 签名
     */
    public static String genSign(Map<String, String> map, String md5key) {
        List<String> keys = sortKeyList(map);//排序key
        String content = "key=" + md5key;//密钥排第一位
        for (String key : keys) {
            if ("sign".equals(key) || "data".equals(key))
                continue;
            content += "&" + key + "="
                    + (null == map.get(key) ? "" : String.valueOf(map.get(key)));
        }
        return DigestUtils.md5Hex(content);
    }



    public static List<String> sortKeyList(Map<String, String> map) {
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list,String.CASE_INSENSITIVE_ORDER);//按字符排序，不区分大小写
        return list;
    }
    /**
     *
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}