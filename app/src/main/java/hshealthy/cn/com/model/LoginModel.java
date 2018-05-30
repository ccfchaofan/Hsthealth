package hshealthy.cn.com.model;


import hshealthy.cn.com.bean.CustomerBean;

/**
 * Created by 71443 on 2018/5/29.
 * （个人喜好，一般我会在model接口中定义数据库操作接口，当然最好放上网络操作和逻辑操作）
 */

public interface LoginModel  {
    void loginDB(CustomerBean customerBean);

}
