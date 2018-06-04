package hshealthy.cn.com.rxhttp;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import hshealthy.cn.com.util.HashUtil;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttp {
    private static IHttp iHttp;
    private static RetrofitHttp retrofitHttp;
    private Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
    private OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .connectTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HttpConstants.CONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
            .addInterceptor(new HeaderInterceptor())
            .addInterceptor(new LogInterceptor());

    public RetrofitHttp(){
        iHttp = retrofitBuilder.baseUrl(HttpConstants.URL)
                .client(okBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(NewGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IHttp.class);
    }

    public static IHttp getInstance(){
        if(iHttp == null){
            retrofitHttp = new RetrofitHttp();
        }
        return iHttp;
    }


    class HeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            StringBuffer stringBuffer = new StringBuffer();
            String timeStamp = Calendar.getInstance().getTimeInMillis()+"";
            stringBuffer.append(HttpConstants.CLIENT_KEY).append(timeStamp).append(HttpConstants.CLIENT_SALT);

            Request newRequest = request.newBuilder()
                    .addHeader("clientkey", HttpConstants.CLIENT_KEY)
                    .addHeader("timestamp", timeStamp)
                    .addHeader("sign", HashUtil.getMD5(stringBuffer.toString()).toUpperCase())
                    .build();

            Request.Builder requestBuilder = newRequest.newBuilder();
            //添加post的公共参数  https://www.aliyun.com/jiaocheng/20007.html
//             String token = "$2y$13$1EwwKyEnGXBIrHeHcBi7b.YpQMUQmQ2.ONswFGem/to5LO3mtmKS.";

//            String token = "$2y$13$x4a60THz..zqWD0.oavC2.Glv6WMwWYThTKtqy2UX8.Yx4Gtz9G3K";
            String token= "";
            if (newRequest.body() instanceof FormBody) {//表单提交
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oldFormBody = (FormBody) newRequest.body();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }
                newFormBody.add("access_token", token);
                requestBuilder.method(newRequest.method(), newFormBody.build());
            } else if (newRequest.body() instanceof MultipartBody) {//multipart提交
                MultipartBody.Builder newFormBody = new MultipartBody.Builder();
                // 默认是multipart/mixed,大坑
                newFormBody.setType(MediaType.parse("multipart/form-data"));
                MultipartBody oldFormBody = (MultipartBody) newRequest.body();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBody.addPart(oldFormBody.part(i));
                }
                newFormBody.addFormDataPart("access_token", token);
                requestBuilder.method(newRequest.method(), newFormBody.build());
            } else if (TextUtils.equals(newRequest.method(), "POST")) {//其他 例如空body
                FormBody.Builder newFormBody = new FormBody.Builder();
                newFormBody.add("access_token", token);
                requestBuilder.method(newRequest.method(), newFormBody.build());
            }
            newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    }

    class LogInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            System.out.println(content);
            System.out.println("--------------------request--------------------");
            System.out.println(response.request().url());
            System.out.println("--------------------headers--------------------");
            if (chain.request().headers() != null && chain.request().headers().size() > 0) {
                System.out.println(chain.request().headers().toString());
            }
            System.out.println("--------------------body--------------------");
            RequestBody requestBody = chain.request().body();
            if (requestBody != null) {
                MediaType mMediaType = requestBody.contentType();
                if (mMediaType != null) {
                    if (isText(mMediaType)) {
                        System.out.println("params : " + bodyToString(response.request()));
                    } else {
                        System.out.println("params : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            System.out.println("--------------------response--------------------");
            System.out.println(content.toString());
            System.out.println("--------------------response end--------------------");

            if (response.body() != null) {
                // 深坑！
                // 打印body后原ResponseBody会被清空，需要重新设置body
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }
    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}