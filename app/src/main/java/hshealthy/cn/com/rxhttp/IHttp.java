package hshealthy.cn.com.rxhttp;

import java.util.List;

import hshealthy.cn.com.bean.AirPortCityBean;
import hshealthy.cn.com.bean.ResponseBean;
import retrofit2.http.POST;
import rx.Observable;

public interface IHttp {

    @POST("v1/airticket/airportcode/")
    Observable<ResponseBean<List<AirPortCityBean>>> getAirPortCode();
}
