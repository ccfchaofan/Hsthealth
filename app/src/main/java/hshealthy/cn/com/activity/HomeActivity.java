package hshealthy.cn.com.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import java.util.List;

import hshealthy.cn.com.R;
import hshealthy.cn.com.adapter.ViewPagerAdapter;
import hshealthy.cn.com.bean.AirPortCityBean;
import hshealthy.cn.com.fragment.BaseFragment;
import hshealthy.cn.com.rxhttp.RetrofitHandler;
import hshealthy.cn.com.rxhttp.RetrofitHttp;
import hshealthy.cn.com.util.ToastUtils;
import hshealthy.cn.com.view.BottomNavigationViewHelper;

/**
 * HomeActivity 主界面
 */

public class HomeActivity extends AppCompatActivity {
    private long firstTime = 0;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RetrofitHttp.getInstance().getAirPortCode()
                .compose(RetrofitHandler.handleResponseT())
                .compose(RetrofitHandler.ioTransformer)
                .subscribe(list ->{
                    System.out.println(list);
                    List<AirPortCityBean> mCityList = (List<AirPortCityBean>) list;
                    System.out.println(mCityList);
                },throwable -> {
                    System.out.println(throwable);
                });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_expert:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_healthy:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_personal:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(BaseFragment.newInstance("首页"));
        adapter.addFragment(BaseFragment.newInstance("专家"));
        adapter.addFragment(BaseFragment.newInstance("健康"));
        adapter.addFragment(BaseFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
    }



    //连按两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                ToastUtils.showLongToast(HomeActivity.this,"再按一次退出程序");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}