package hshealthy.cn.com.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hshealthy.cn.com.R;
import hshealthy.cn.com.base.BaseActivity;
import hshealthy.cn.com.base.BasePresenter;
import hshealthy.cn.com.customview.CircleImageView;
import hshealthy.cn.com.customview.CommonBottomDialog;
import hshealthy.cn.com.util.CameraUtil;
import hshealthy.cn.com.util.TConstant;

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
        personalavatar =  findViewById(R.id.h_personal_avatar);
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
            case R.id.image_user:
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
     * 底部弹框选择图片
     */
    private void showdialog() {

        CommonBottomDialog commonBottomDialog = new CommonBottomDialog(this) {
            @Override
            public void onMenuOne() {
//                takePicture();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
