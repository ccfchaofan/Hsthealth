package hshealthy.cn.com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.ButterKnife;
import hshealthy.cn.com.util.LogUtils;
import hshealthy.cn.com.util.ToastUtil;
import hshealthy.cn.com.util.WeakReferenceUtil;

/**
 * BaseActivity
 */
public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity implements BaseView, View.OnClickListener {
    protected View view;
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        ButterKnife.bind(this);
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initListener();
        initData();
    }

    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    /**
     * context弱引用 用于外部引用控件
     *
     * @return
     */
    protected Context getWeakContext() {
        return new WeakReferenceUtil<BaseActivity>(this).getWeakT();
    }

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }

    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        ToastUtil.setToast(str);
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        ToastUtil.setToast(contentId);
    }

    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass(), str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
