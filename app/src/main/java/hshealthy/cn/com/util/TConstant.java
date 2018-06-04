package hshealthy.cn.com.util;

import android.content.Context;

/**
 * 常量类
 * @author JPH
 * Date 2016/6/7 0007 9:39
 */
public class TConstant {


    /**
     * request Code 裁剪照片
     **/
    public final static int RC_CROP = 1001;
    /**
     * request Code 从相机获取照片并裁剪
     **/
    public final static int RC_PICK_PICTURE_FROM_CAPTURE_CROP = 1002;
    /**
     * request Code 从相机获取照片不裁剪
     **/
    public final static int RC_PICK_PICTURE_FROM_CAPTURE = 1003;
    /**
     * request Code 从文件中选择照片
     **/
    public final static int RC_PICK_PICTURE_FROM_DOCUMENTS_ORIGINAL = 1004;
    /**
     * request Code 从文件中选择照片并裁剪
     **/
    public final static int RC_PICK_PICTURE_FROM_DOCUMENTS_CROP = 1005;
    /**
     * request Code 从相册选择照片不裁剪
     **/
    public final static int RC_PICK_PICTURE_FROM_GALLERY_ORIGINAL = 1006;
    /**
     * request Code 从相册选择照片并裁剪
     **/
    public final static int RC_PICK_PICTURE_FROM_GALLERY_CROP = 1007;
    /**
     * request Code 选择多张照片
     **/
    public final static int RC_PICK_MULTIPLE = 1008;

    /**
     * request Code 让刚才选择裁剪得到的图片显示在界面上
     **/
    public static final int RC_PICK_CROP_SMALL_PICTURE = 1009;

    /**
     * requestCode 请求权限
     **/
    public final static int PERMISSION_REQUEST_TAKE_PHOTO = 2000;

    /**
     * requestCode 请求存储权限
     **/
    public static final int REQUEST_PERMISSION_STORAGE = 0x01;

    /**
     * requestCode 请求调相机
     **/
    public static final int REQUEST_PERMISSION_CAMERA = 0x02;

    public final static String getFileProviderName(Context context){
        return context.getPackageName()+".fileprovider";
    }
 }