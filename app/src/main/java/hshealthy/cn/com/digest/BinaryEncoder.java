package hshealthy.cn.com.digest;


/**
 * Created by chen.sun on 2017/9/27.
 */

public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] var1) throws EncoderException;
}
