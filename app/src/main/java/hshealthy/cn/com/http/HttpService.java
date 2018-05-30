package hshealthy.cn.com.http;


import hshealthy.cn.com.base.BaseHttpResult;
import hshealthy.cn.com.bean.CustomerBean;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求的接口
 * Created by Administrator on 2016/11/9.
 */
public interface HttpService {

//    @FormUrlEncoded
//    @POST("getIpInfo.php")
//    Observable<BaseHttpResult<IpInfoBean>> getIpInfo(@Field("ip") String ip);
    @POST("getlogin.php")
    Observable<BaseHttpResult<CustomerBean>> getIpInfo(@Query("phone") String phone,@Query("code") String code);
}
