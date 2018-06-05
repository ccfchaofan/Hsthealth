package hshealthy.cn.com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import hshealthy.cn.com.R;
import hshealthy.cn.com.base.BaseActivity;
import hshealthy.cn.com.base.BasePresenter;
import hshealthy.cn.com.customview.CircleImageView;
import hshealthy.cn.com.customview.CommonBottomDialog;
import hshealthy.cn.com.photo.ImagePicker;
import hshealthy.cn.com.util.AbAppUtil;
import hshealthy.cn.com.util.CameraUtil;
import hshealthy.cn.com.util.Log;
import hshealthy.cn.com.util.OnBooleanListener;
import hshealthy.cn.com.util.TConstant;
import hshealthy.cn.com.util.ToastUtils;


/**
 * Created by 71443 on 2018/6/1.
 * 个人信息
 */

public class PersonalInformationActivtiy extends BaseActivity {
    private RelativeLayout rlimageuser;
    private CircleImageView personalavatar;
    private RelativeLayout rlusername;
    private RelativeLayout rlusersex;
    private RelativeLayout rluserphone;
    private RelativeLayout rluserqrcode;
    private RelativeLayout rluseradress;
    private TextView textsex, textname, textphone;
    public Uri imageUriFromCamera;
    public OnBooleanListener onPermissionListener;
    public final String USER_CROP_IMAGE_NAME = "temporary.png";
    public final String USER_IMAGE_NAME = "image.png";
    private static final String UPHEADTYPE = "1";
    public static final int EDITDITEX_INFOMATION_CORD = 3101;
    public Uri cropImageUri;
    Bitmap photo;
    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        rlimageuser.setOnClickListener(this);
        rlusername.setOnClickListener(this);
        rlusersex.setOnClickListener(this);
        rluserphone.setOnClickListener(this);
        rluserqrcode.setOnClickListener(this);
        rluseradress.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        rlimageuser =  findViewById(R.id.image_user);
        personalavatar =  findViewById(R.id.personal_image);
        rlusername =  findViewById(R.id.rl_user_name);
        rlusersex =  findViewById(R.id.rl_user_sex);
        rluserphone =  findViewById(R.id.rl_user_phone);
        rluserqrcode =  findViewById(R.id.rl_user_qr_code);
        rluseradress =  findViewById(R.id.rl_user_adress);
        textname =  findViewById(R.id.text_name);
        textsex =  findViewById(R.id.text_sex);
        textphone =  findViewById(R.id.text_phone);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.personal_information;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.image_user://调用相机
                showdialog();
                break;
            case R.id.rl_user_name:
                break;
            case R.id.rl_user_sex:
                break;
            case R.id.rl_user_phone:
                break;
            case R.id.rl_user_qr_code:
                break;
            case R.id.rl_user_adress:
                break;

        }

    }

    /**
     * 调用相机
     */
    private void takePicture() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.CAMERA)) {
                imageUriFromCamera = ImagePicker.getInstance().takePicture(this, TConstant.RC_PICK_PICTURE_FROM_CAPTURE_CROP, createImagePathFile(this));
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, TConstant.REQUEST_PERMISSION_CAMERA);
            }
        } else {
            imageUriFromCamera = ImagePicker.getInstance().takePicture(this, TConstant.RC_PICK_PICTURE_FROM_CAPTURE_CROP, createImagePathFile(this));
        }
    }
    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
    public File createImagePathFile(Activity activity) {
        //文件目录可以根据自己的需要自行定义
        File file = new File(activity.getExternalCacheDir(), USER_IMAGE_NAME);
        return file;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TConstant.REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageUriFromCamera = ImagePicker.getInstance().takePicture(this, TConstant.RC_PICK_PICTURE_FROM_CAPTURE_CROP, createImagePathFile(this));
            } else {
                ToastUtils.showToast("权限被禁止，无法打开相机");
            }
        } else if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                if (onPermissionListener != null) {
                    onPermissionListener.onClick(true);
                }
            } else {
                //权限拒绝
                if (onPermissionListener != null) {
                    onPermissionListener.onClick(false);
                }
            }
            return;
        }
    }

    protected void setImageToView() {
            try {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                personalavatar.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }



    public void startPhotoZoom(Uri uri) {
        File CropPhoto=new File(getExternalCacheDir(),"crop.jpg");
        try{
            if(CropPhoto.exists()){
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }

        cropImageUri=Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, TConstant.RC_PICK_CROP_SMALL_PICTURE);
    }

    /**
     * 底部弹框选择图片
     */
    private void showdialog() {

        CommonBottomDialog commonBottomDialog = new CommonBottomDialog(this) {
            @Override
            public void onMenuOne() {
                takePicture();
                dismiss();
            }

            @Override
            public void onMenuTwo() {
                startActivityForResult(CameraUtil.openAlbum(), TConstant.RC_PICK_PICTURE_FROM_GALLERY_CROP);
                dismiss();
            }

            @Override
            public void onCancel() {
                dismiss();
            }


        };
        commonBottomDialog.setMenuOneText("拍照");
        commonBottomDialog.setMenuTwoText(getString(R.string.album));
        commonBottomDialog.setCanelText(getString(R.string.cancel));
        commonBottomDialog.setCanceledOnTouchOutside(true);
        commonBottomDialog.show();
    }

    //相机或照相回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TConstant.RC_PICK_PICTURE_FROM_CAPTURE_CROP:

                    if (imageUriFromCamera != null) {
                        startPhotoZoom(imageUriFromCamera);
                        break;
                    }
                    break;
                case TConstant.RC_PICK_PICTURE_FROM_GALLERY_CROP:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case TConstant.RC_PICK_CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
                case EDITDITEX_INFOMATION_CORD:
                    break;
            }
        }
    }
}
