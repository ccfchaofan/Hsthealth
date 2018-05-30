package hshealthy.cn.com.base;


import hshealthy.cn.com.http.Http;
import hshealthy.cn.com.http.HttpService;

/**
 * MVP --> BaseModel
 * Created by Administrator on 2016/12/15.
 */
public class BaseModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }
}
