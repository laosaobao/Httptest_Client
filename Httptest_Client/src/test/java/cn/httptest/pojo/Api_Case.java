package cn.httptest.pojo;

public class Api_Case {
    private Integer ApiId;
    private String ApiName;
    private String Type;
    private String Url;
    private Integer NeedToken;


    private Integer CaseId;
    private String Desc;
    private String Params;
    private String ExpectedResponseData;
    private String Collect_params;
    private String ActualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;
    private Integer IsExceptionCase;

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Integer getIsExceptionCase() {
        return IsExceptionCase;
    }

    public void setIsExceptionCase(Integer isExceptionCase) {
        IsExceptionCase = isExceptionCase;
    }

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











    public String getCollect_params() {
        return Collect_params;
    }

    public void setCollect_params(String collect_params) {
        Collect_params = collect_params;
    }



    public Integer getCaseId() {
        return CaseId;
    }

    public void setCaseId(Integer caseId) {
        CaseId = caseId;
    }





    public String getParams() {
        return Params;
    }

    public void setParams(String params) {
        Params = params;
    }

    public String getExpectedResponseData() {
        return ExpectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        ExpectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return ActualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        ActualResponseData = actualResponseData;
    }

    public String getPreValidateSql() {
        return PreValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        PreValidateSql = preValidateSql;
    }

    public String getPreValidateResult() {
        return PreValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        PreValidateResult = preValidateResult;
    }

    public String getAfterValidateSql() {
        return AfterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        AfterValidateSql = afterValidateSql;
    }

    public String getAfterValidateResult() {
        return AfterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        AfterValidateResult = afterValidateResult;
    }
}
