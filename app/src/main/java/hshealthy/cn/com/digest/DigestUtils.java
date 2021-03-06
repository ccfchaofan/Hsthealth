package hshealthy.cn.com.digest;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chen.sun on 2017/9/27.
 */

public class DigestUtils {
    public DigestUtils() {
    }

    static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2.getMessage());
        }
    }

    private static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }

    private static MessageDigest getShaDigest() {
        return getDigest("SHA");
    }

    public static byte[] md5(byte[] data) {
        return getMd5Digest().digest(data);
    }

    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    public static String md5Hex(byte[] data) {
        return new String(Hex.encodeHex(md5(data)));
    }

    public static String md5Hex(String data) {
        return new String(Hex.encodeHex(md5(data)));
    }

    public static byte[] sha(byte[] data) {
        return getShaDigest().digest(data);
    }

    public static byte[] sha(String data) {
        return sha(data.getBytes());
    }

    public static String shaHex(byte[] data) {
        return new String(Hex.encodeHex(sha(data)));
    }

    public static String shaHex(String data) {
        return new String(Hex.encodeHex(sha(data)));
    }
}
