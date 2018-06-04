package hshealthy.cn.com.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hshealthy.cn.com.R;
import hshealthy.cn.com.activity.PersonalInformationActivtiy;
import hshealthy.cn.com.customview.CircleImageView;

/**
 * Created by 71443 on 2018/6/4.
 */

public class PresonalCenterFragment extends BaseFragment {
    private RelativeLayout rlbtinfo;//跳转到个人中心
    private CircleImageView circleImageView;//头像
    private TextView personalname;//用户名
    private TextView personalphone;//账号
    private RelativeLayout rlhealthplan;//健康计划
    private RelativeLayout rlhealtharchives;//健康档案
    private RelativeLayout rlmyassets;//我的资产
    private RelativeLayout rltaskcenter;//任务中心
    private RelativeLayout rlmyorder;//我的订单
    private RelativeLayout rlcollection;//收藏
    private RelativeLayout rlsetup;//设置

    @Override
    protected void init() {
        setLayoutId(R.layout.hst_personal_canter);
    }

    @Override
    protected void initView() {
        circleImageView =  findView(R.id.h_personal_avatar);
        personalname =  findView(R.id.personal_name);
        personalphone =  findView(R.id.personal_phone);
        rlhealtharchives =  findView(R.id.rl_health_archives);
        rlhealthplan =  findView(R.id.rl_health_plan);
        rlmyassets =  findView(R.id.rl_my_assets);
        rltaskcenter =  findView(R.id.rl_task_center);
        rlmyorder =  findView(R.id.rl_my_order);
        rlcollection = findView(R.id.rl_collection);
        rlsetup =  findView(R.id.rl_setup);
        rlbtinfo =findView(R.id.rl_bt_info);
    }

    @Override
    protected void initEvent() {
        rlbtinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getWeakActivity(),PersonalInformationActivtiy.class);
                startActivity(intent);

            }
        });
        rlhealtharchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlhealthplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlmyassets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rltaskcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlmyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlsetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
