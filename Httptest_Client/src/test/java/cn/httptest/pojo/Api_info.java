package cn.httptest.pojo;

public class Api_info {
    private Integer ApiId;
    private String ApiName;
    private String Type;
    private String Url;
    private Integer NeedToken;

    public Integer getNeedToken() {
        return NeedToken;
    }

    public void setNeedToken(Integer needToken) {
        NeedToken = needToken;
    }

    public Integer getApiId() {
        return ApiId;
    }

    public void setApiId(Integer apiId) {
        ApiId = apiId;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
