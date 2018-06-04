package hshealthy.cn.com.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import hshealthy.cn.com.api.HsHealthyInstance;
import hshealthy.cn.com.util.WeakReferenceUtil;

public abstract class BaseFragment extends Fragment implements FragmentStack.OnBackPressedHandlingFragment{

    private int layoutId;

    private View fView;

    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        fView = inflater.inflate(layoutId, container, false);
        initView();
        return fView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= HsHealthyInstance.C();
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    /*
         * 初始化操作 主要用于setLayout
         */
    protected abstract void init();

    /*
     * 初始化用于初始化界面
     */
    protected abstract void initView();

    /*
     * 初始化界面完成 初始化事件
     */
    protected abstract void initEvent();
    /*
     * 处理事件冲突 (non-Javadoc)
     * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
     * android.os.Bundle)
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.onViewCreated(view, savedInstanceState, false);
    }
    /**
     *
     * @param view
     * @param savedInstanceState
     * @param isTouched 底层view是否支持事件
     */
    public void onViewCreated(View view, Bundle savedInstanceState, boolean isTouched) {
        if (!isTouched) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        super.onViewCreated(view, savedInstanceState);
        initEvent();
    }
    /**
     * Context弱引用 用于外部引用
     *
     * @return
     */
    protected Context getWeakActivity() {
        return new WeakReferenceUtil<FragmentActivity>(getActivity()).getWeakT();
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView() == null ? fView :super.getView();
    }

    /**
     * 简化findViewById
     * @parad
     * @return
     */
    @SuppressWarnings("unchecked")
    protected  <T extends View> T  findView(int id) {
        if (getView() != null) {
            return (T)getView().findViewById(id);
        }
        return null;
    }


    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.Fragment#onDestroyView()
     */
    @Override
    public void onDestroyView() {
        System.gc();
        super.onDestroyView();
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.gc();
        super.onSaveInstanceState(outState);
    }


    /**
     * 返回键处理
     *  true 交给fragment
     *  false  交给父类
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }


}
