package hshealthy.cn.com.digest;



/**
 * Created by chen.sun on 2017/9/27.
 */

public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}
