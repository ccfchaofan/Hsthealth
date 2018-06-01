package hshealthy.cn.com.rxhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttp {
    private IHttp iHttp;
    private RetrofitHttp retrofitHttp;
    private Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
    private OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .connectTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HttpConstants.CONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
            .addInterceptor(new HeaderInterceptor());

    public RetrofitHttp(){
        iHttp = retrofitBuilder.baseUrl(HttpConstants.URL)
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IHttp.class);
    }

    public IHttp getInstance(){
        if(iHttp == null){
            retrofitHttp = new RetrofitHttp();
        }
        return iHttp;
    }


    class HeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request.newBuilder().build());
            return response;
        }
    }
}