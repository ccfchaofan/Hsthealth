package hshealthy.cn.com.util;


import java.lang.ref.WeakReference;

/**
 *
 *
 * Tips:弱引用工具类  new WeakReferenceUtil(T).getWeakT();
 */
@SuppressWarnings("unused")
public class WeakReferenceUtil<T> {

    private WeakReference<T> weakReference;

    /**
     *
     */
    @SuppressWarnings("unused")
    private WeakReferenceUtil() {
    }
    @SuppressWarnings("unused")
    public WeakReferenceUtil(T t){
        weakReference =new WeakReference<T>(t);
    }

    @SuppressWarnings("unused")
    public T getWeakT(){
        return weakReference.get();
    }

}