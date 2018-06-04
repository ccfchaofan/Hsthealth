package hshealthy.cn.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chen.sun on 2017/10/17.相册相机工具类
 */
public class CameraUtil {
    public static Uri cropImageUri;
    public static String USER_CROP_IMAGE_NAME = "temporary.png";
    public static String USER_IMAGE_NAME = "image.png";
    private static final String UPHEADTYPE = "1";
    public static Uri imageUriFromCamera;
    /**
     * 检测SD卡是否可用
     *
     * @return true 可用，false不可用。
     */
    public static boolean isSDExit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 新建文件夹到手机本地
     *
     * @param fileFolder ,文件夹的路径名称
     * @return
     */
    public static boolean createDir(String fileFolder) {
        File dir = new File(fileFolder);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 新建文件到手机本地
     *
     * @param fileNameWithPath ,文件名包含路径
     * @return , true新建成功, false新建失败
     */
    public static boolean createFile(String fileNameWithPath) {
        File file = new File(fileNameWithPath);
        try {
            if (isSDExit() && file.exists()) {
                if (file.delete()) {
                    return file.createNewFile();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 新建文件到手机指定路径
     *
     * @param dirPath  ,文件的文件夹目录路径
     * @param fileName ,文件名
     * @return , true新建成功, false新建失败
     */
    public static boolean createFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        try {
            if (isSDExit() && file.exists()) {
                if (file.delete()) {
                    return file.createNewFile();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 创建相机拍照图片名称
     *
     * @param fileType ,文件的类型，即扩展名，例如.jpg 、.mp4 、.mp3等
     * @return , 图片文件名,格式形式20161011_111523.jpg
     */
    public static String createFileName(String fileType) {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        fileName = sdf.format(date) + fileType;
        return fileName;
    }

    /**
     * 保存图片的Bitmap数据到sd卡指定路径
     *
     * @param fileNameWithPath ,图片的路径
     * @param bitmap           ,图片的bitmap数据
     */
    public static void savePhotoToPath(String fileNameWithPath, Bitmap bitmap) {
        if (isSDExit()) {
            File file = new File(fileNameWithPath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                if (bitmap != null) {
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                        fos.flush();
                        fos.close();
                    }
                }
            } catch (FileNotFoundException e) {
                file.delete();
                e.printStackTrace();
            } catch (IOException e) {
                file.delete();
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param dirPath  ,文件的文件夹目录路径
     * @param fileName ,文件名
     * @return , true删除成功, false删除失败
     */
    public static boolean deleteFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

    /**
     * 更新系统的Media库
     *
     * @param context
     */
    public static void updateSystemMedia(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{
                Environment.getExternalStorageDirectory().getAbsolutePath()
        }, null, null);
    }

    /**
     * 打开手机系统相册, method one
     * //android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
     * @return intent, Activity调用的intent
     */
    public static Intent openAlbum() {
        Intent intent=new Intent(Intent.ACTION_PICK);//ACTION_OPEN_DOCUMENT
        intent.setType("image/*");
        return intent;
    }

    /**
     * 打开手机系统相册,指定类型
     *
     * @return intent, Activity调用的intent
     */
    public static Intent openAlbum(String type) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(type);
        return intent;
    }

    /**
     * 打开手机系统相册, method two
     *
     * @return intent, Activity调用的intent
     */
    public static Intent openGallery() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /**
     * 打开手机系统相机拍照
     *
     * @param uri , 用于保存手机拍照后所获图片的uri
     * @return intent, Activity调用的intent
     */
    public static Intent openCamera(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("autofocus", true);//进行自动对焦操作
        return intent;
    }

    /**
     * 打开手机系统的图片裁剪Activity
     *
     * @param inUri  , 待裁剪图片的uri
     * @param outUri , 裁剪后图片保存的uri
     * @return intent , Activity调用的intent
     */
    public static Intent cropPicture(Uri inUri, Uri outUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inUri, "image/*");     //设置图片资源路径
        intent.putExtra("scale", true);
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent;
    }

    /**
     * 获取裁切intent
     * @param uri
     * @param outputX
     * @param outputY
     * @param aspectX
     * @param aspectY
     * @param outputFormat
     * @return
     */
    public static Intent getCropIntent(Intent intent, Uri uri, int outputX, int outputY, float aspectX, float aspectY, String outputFormat) {
        if(intent == null)intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType( uri, "image/*" );
        intent.putExtra( "crop", "true" );
        intent.putExtra( "aspectX", (int)(aspectX + .5) );
        intent.putExtra( "aspectY", (int)(aspectY + .5) );
        intent.putExtra( "outputX", outputX );
        intent.putExtra( "outputY", outputY );
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        if (uri != null) {
            intent.putExtra( MediaStore.EXTRA_OUTPUT, uri );
            intent.putExtra( "return-data", false );
        } else
            intent.putExtra( "return-data", true );
        if(TextUtils.isEmpty( outputFormat ))
            intent.putExtra( "outputFormat", Bitmap.CompressFormat.JPEG.toString() );
        else intent.putExtra( "outputFormat", outputFormat );
        intent.putExtra( "noFaceDetection", true );
        return intent;
    }

    public static Intent getCamaraPickIntent(Uri uri){
        if(uri == null) return null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static  Intent startPhotoZoom(Uri uri ,Activity activity) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        File file = new File(activity.getExternalCacheDir(), USER_CROP_IMAGE_NAME);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //高版本一定要加上这两句话，做一下临时的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            FileProvider.getUriForFile(activity, "commonlib.photo.ImagePickerProvider.provider", file);
           FileProvider.getUriForFile(activity, AbAppUtil.getPhotoProvider(), file);

        }
        cropImageUri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        intent.putExtra("aspectX", 1000);
        intent.putExtra("aspectY", 999);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        return intent;
    }

    public static File createImageFile(Activity activity) {
      //文件目录可以根据自己的需要自行定义
      File file;
//      if(FileUtil.checkSaveLocationExists()){
//          FileUtil.createPath(String.format("%1$s%2$s",Environment.getExternalStorageDirectory(),"/temp"));
//          file = new File(Environment.getExternalStorageDirectory(),
//                    String.format("%1$s%2$s%3$s","temp/", AbDateUtil.getNowTimeMills(),".jpg"));
//      }else
          {
          file = new File(activity.getExternalCacheDir(), AbDateUtil.getNowTimeMills()+".jpg");
      }
      return file;

    }

    public static File createImagePathFile(Activity activity) {
        //文件目录可以根据自己的需要自行定义
        File file = new File(activity.getExternalCacheDir(), USER_IMAGE_NAME);
        return file;
    }

    /**
     * 将刚拍照的相片在相册中显示
     * @param activity
     * @param file
     */
    public static void  sendAlbum(Activity activity,File file){
        Uri localUri = Uri.fromFile(file);
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
        activity.sendBroadcast(localIntent);
    }

    /**
     * 将刚拍照的相片在相册中显示
     * @param activity
     * @param uri
     */
    public static void  sendUriAlbum(Activity activity,Uri uri){
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        activity.sendBroadcast(localIntent);
    }
    /**
     * 调用相机
     */
    public  static  Intent takePicture(Uri imageUriFromCameras) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCameras);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    imageUriFromCameras);
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        return  intent;
    }
}