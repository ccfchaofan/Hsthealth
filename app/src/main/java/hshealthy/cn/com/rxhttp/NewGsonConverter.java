package hshealthy.cn.com.rxhttp;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class NewGsonConverter<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final TypeAdapter adapter;

    NewGsonConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        try {
            JSONObject jsonObject = new JSONObject(body);
            int status = jsonObject.optInt("status");
            if (HttpConstants.RESP_SUCCESS != status){
                //如果返回的数据结构不标准，那么只通过status来判定是否成功，
                //不成功的情况下，给data字段赋值为空。保证ResponseBean能够进行解析。
                jsonObject.put("data","");
                body = jsonObject.toString();
            }
            return (T) adapter.fromJson(body);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
