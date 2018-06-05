package hshealthy.cn.com.activity;

import android.view.View;
import android.widget.EditText;

import hshealthy.cn.com.R;
import hshealthy.cn.com.base.BaseActivity;
import hshealthy.cn.com.base.BasePresenter;

/**
 * Created by 71443 on 2018/6/1.
 * 编辑姓名
 */

public class PersonalNameActivity  extends BaseActivity{
    private EditText editText;
    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        editText= findViewById(R.id.edit_name);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.personal_name_edit;
    }

    @Override
    protected void otherViewClick(View view) {

    }
}
