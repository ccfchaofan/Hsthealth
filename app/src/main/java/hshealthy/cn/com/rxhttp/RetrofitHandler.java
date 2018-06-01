package hshealthy.cn.com.rxhttp;

import hshealthy.cn.com.bean.ResponseBean;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitHandler {
    //转换线程工具，传入object，返回responseBean
    //转换的过程要在请求的方法后进行转换，transformer的是Obsevable本身，并不对Obsevable数据进行改变，只是转换线程调度
    //可以在定义的过程限定转换的类型，比如Observable.Transformer<Object,String> transformer = new ...;
    public static Observable.Transformer ioTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return ((Observable) o).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }
    };
    //传入定义好的？extends ResponseBean，直接返回T
    //转换成已传入的？类型，然后通过createRespT方法直接做gson转换，获取最终？类型
    //call的过程传入的泛型，在createRespData方法中才能获取data，然后转换成？类型
    //如果传入的o.getData，这获取的是Object,不是Respbean中声明的？类型
    public static <T> Observable.Transformer<ResponseBean<T>,T> handleResponseT(){
        Observable.Transformer<ResponseBean<T>,T> transformer = new Observable.Transformer<ResponseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBean<T>> responseBeanObservable) {
                Observable observable = responseBeanObservable.flatMap(new Func1<ResponseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseBean<T> tResponseBean) {
                        return createRespT(tResponseBean);
                    }
                });
                return observable;
            }
        };
        return transformer;
    }

    //配合handleResp使用的，传入的是RespBean的T，对T进行转换后发射
    //在Action中直接获取已转换完成的T类型
    private static  <T> Observable<T> createRespT(ResponseBean<T> responseBean){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(responseBean.getData());
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    //传入 ? extends Respbean 返回respbean.getData的数据，该数据为不规范的String类型
    //然后通过createRespWithoutT直接将获取到的getData的String通过subScriber发射出去
    //在Action的方法中自定义gson对string进行转换。
    //该转换的过程中Respbean的基础数据比如code，msg无法传递给下一级
    public static Observable.Transformer<ResponseBean,String> handlerResponseStr(){
        Observable.Transformer<ResponseBean,String> transformer = new Observable.Transformer<ResponseBean, String>() {
            @Override
            public Observable<String> call(Observable<ResponseBean> responseBeanObservable) {
                return responseBeanObservable.flatMap(new Func1<ResponseBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBean responseBean) {
                        return createStr(responseBean);
                    }
                });
            }
        };
        return transformer;
    }
    //和handlerResponseStr配合使用，传入的Object T，传出的也是Object T。配合newTransformer的话就是String
    public static <T> Observable createStr(T t){
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static <T> Observable<T> createErr(ResponseBean<T> respBean){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onError(new Throwable(respBean.getCode()+respBean.getMessage()));
            }
        });
    }
}
