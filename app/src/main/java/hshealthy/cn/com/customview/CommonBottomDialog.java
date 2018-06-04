package hshealthy.cn.com.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import hshealthy.cn.com.R;


/**
 * 底部dialog
 */
public abstract class CommonBottomDialog extends Dialog{

    private Context mContext;
    private LinearLayout lay_lb_hslib_menuone;
    private LinearLayout lay_lb_hslib_menutwo;
    private LinearLayout lay_lb_hslib_canel;
    public TextView tv_lb_hslib_menuone;
    public TextView tv_lb_hslib_menutwo;
    public TextView  tv_lb_hslib_canel;
    public TextView tv_lb_hslib_titles;
    public CommonBottomDialog(Context context) {
        super(context, R.style.hslib_DialogStyle_4);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.commonbottom_dialog, null);
        setContentView(view);
        tv_lb_hslib_titles = (TextView) view.findViewById(R.id.text_titles);
        tv_lb_hslib_menuone = (TextView) view.findViewById(R.id.tv_lb_hslib_menuone);
        tv_lb_hslib_menutwo = (TextView) view.findViewById(R.id.tv_lb_hslib_menutwo);
        tv_lb_hslib_canel = (TextView) view.findViewById(R.id.tv_lb_hslib_canel);
        tv_lb_hslib_menuone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuOne();
            }
        });
        tv_lb_hslib_menutwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuTwo();
            }
        });
        tv_lb_hslib_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

    }

    public  void  setTitleText(String text){
        tv_lb_hslib_titles.setVisibility(View.VISIBLE);
        tv_lb_hslib_titles.setText(text);
    }

    public void  setTitleTextVisibility(int visibility){
        tv_lb_hslib_titles.setVisibility(visibility);
    }

    public  void  setCanelText(String text){
        tv_lb_hslib_canel.setVisibility(View.VISIBLE);
        tv_lb_hslib_canel.setText(text);
    }

    public  void  setMenuOneText(String text){
        tv_lb_hslib_menuone.setText(text);
    }

    public  void  setMenuTwoText(String text){
        tv_lb_hslib_menutwo.setText(text);
    }

    public  void  setMenuTwoTextVisibility(int  visibility){
        tv_lb_hslib_menutwo.setVisibility(visibility);
    }
    public  void  setMenuOneTextColor(int color){
        tv_lb_hslib_menuone.setTextColor(color);
    }
    public  void  setMenuTwoTextColor(int color){
        tv_lb_hslib_menutwo.setTextColor(color);
    }

    @Override
    public void show() {
        super.show();
        showOnBottom();
    }

    @Override
    public void dismiss() {
        dismiss(true);
    }
    public void dismiss(boolean callDmListener) {
        super.dismiss();
        if (callDmListener)
            onDisMissListener();
    }
    private void  showOnBottom(){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public abstract void onMenuOne();

    public abstract void onMenuTwo();

    public abstract void onCancel();

    public void onDisMissListener(){};


}
