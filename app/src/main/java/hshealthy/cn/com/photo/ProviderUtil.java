package hshealthy.cn.com.photo;

import android.content.Context;

import hshealthy.cn.com.util.AbAppUtil;


/**
 * 用于解决provider冲突的util
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-03-22  18:55
 */

public class ProviderUtil {

    public static String getFileProviderName(Context context){
//        return  context.getPackageName()+"commonlib.photo.ImagePickerProvider.provider";
        return AbAppUtil.getPhotoProvider();
    }
}
