package hshealthy.cn.com.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */
public class AirPortCityBean {
    private Long id;
    private String name;
    private String code;
    /**
     * 331- 国内  332-国际
     */
    private String productId;
    /**
     * 1- 城市三字码 0- 国内三字码
     */
    private Integer codeType;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 拼音首字母
     */
    private String py;
    /**
     * 是否是标题（A、B、C....Z）
     */
    private Boolean isTitle;
    /**
     * 滑动的位置
     */
    private Integer scrollPos;
    private List<AirPortBean> airport;
    /**
     * Used to resolve relations
     */
    /**
     * Used for active entity operations.
     */

    public AirPortCityBean(Long id, String name, String code, String productId, Integer codeType,
                           String pinyin, String py) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.productId = productId;
        this.codeType = codeType;
        this.pinyin = pinyin;
        this.py = py;
    }

    public AirPortCityBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getCodeType() {
        return this.codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Boolean getTitle() {
        return isTitle;
    }

    public void setTitle(Boolean title) {
        isTitle = title;
    }

    public Integer getScrollPos() {
        return scrollPos;
    }

    public void setScrollPos(Integer scrollPos) {
        this.scrollPos = scrollPos;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPy() {
        return this.py;
    }

    public void setPy(String py) {
        this.py = py;
    }

}
