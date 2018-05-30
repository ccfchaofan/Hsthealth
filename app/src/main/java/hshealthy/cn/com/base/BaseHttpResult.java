package hshealthy.cn.com.base;

/**
 * 抽取的一个基类的bean,直接在泛型中传data就行
 * <p>
 * Created by Administrator on 2016/12/14.
 */
public class BaseHttpResult<T> {
    private int code;
    private String message;// 接口暂时不提供该字段
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
