package hshealthy.cn.com.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.ArrayList;
import java.util.List;

import hshealthy.cn.com.R;

/**
 * Created by Wokee
 * Tips: fragment操作类
 */

public class FragmentStack {


    public interface OnBackPressedHandlingFragment {
        boolean onBackPressed();
    }

    private Activity activity;
    private FragmentManager manager;
    private int containerId;

    public FragmentStack(Activity activity, FragmentManager manager, int containerId) {
        this.activity = activity;
        this.manager = manager;
        this.containerId = containerId;
    }


    public int size() {
        return getFragments().size();
    }


    public void push(Fragment fragment) {

        Fragment top = peek();
        if (top != null) {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .remove(top)
                    .add(containerId, fragment, indexToTag(manager.getBackStackEntryCount() + 1))
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
        else {
            manager.beginTransaction()
                    .add(containerId, fragment, indexToTag(0))
                    .commitAllowingStateLoss();
        }

        manager.executePendingTransactions();
    }


    public boolean back() {
        Fragment top = peek();
        if (top instanceof OnBackPressedHandlingFragment) {
            if (((OnBackPressedHandlingFragment)top).onBackPressed())
                return true;
        }
        return pop();
    }


    public boolean pop() {
        if (manager.getBackStackEntryCount() == 0)
            return false;
        manager.popBackStackImmediate();
        return true;
    }


    public void replace(Fragment fragment) {
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(containerId, fragment, indexToTag(0))
                .commitAllowingStateLoss();
        manager.executePendingTransactions();
    }


    public Fragment peek() {
        return manager.findFragmentById(containerId);
    }


    @SuppressWarnings("unchecked")
    public <T> T findCallback(Fragment fragment, Class<T> callbackType) {

        Fragment back = getBackFragment(fragment);

        if (back != null && callbackType.isAssignableFrom(back.getClass()))
            return (T)back;

        if (callbackType.isAssignableFrom(activity.getClass()))
            return (T)activity;

        return null;
    }

    private Fragment getBackFragment(Fragment fragment) {
        List<Fragment> fragments = getFragments();
        for (int f = fragments.size() - 1; f >= 0; f--) {
            if (fragments.get(f) == fragment && f > 0)
                return fragments.get(f - 1);
        }
        return null;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>(manager.getBackStackEntryCount() + 1);
        for (int i = 0; i < manager.getBackStackEntryCount() + 1; i++) {
            Fragment fragment = manager.findFragmentByTag(indexToTag(i));
            if (fragment != null)
                fragments.add(fragment);
        }
        return fragments;
    }

    private String indexToTag(int index) {
        return Integer.toString(index);
    }

}
