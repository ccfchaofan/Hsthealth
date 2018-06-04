package hshealthy.cn.com.bean;

/**
 * Created by Administrator on 2018/3/26.
 */
public class AirPortBean  {
    private Long id;

    private Long pId;
    private String pCode;
    private String name;
    private String code;
    private Integer codeType;
    public AirPortBean(Long id, Long pId, String pCode, String name, String code,
                       Integer codeType) {
        this.id = id;
        this.pId = pId;
        this.pCode = pCode;
        this.name = name;
        this.code = code;
        this.codeType = codeType;
    }
    public AirPortBean() {
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
    public Integer getCodeType() {
        return this.codeType;
    }
    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }
    public Long getPId() {
        return this.pId;
    }
    public void setPId(Long pId) {
        this.pId = pId;
    }
    public String getPCode() {
        return this.pCode;
    }
    public void setPCode(String pCode) {
        this.pCode = pCode;
    }


}
