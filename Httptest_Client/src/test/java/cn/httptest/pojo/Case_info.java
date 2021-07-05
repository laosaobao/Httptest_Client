package cn.httptest.pojo;

public class Case_info {
    private Integer CaseId;
    private String Dese;
    private Integer ApiId;
    private String Params;
    private String ExpectedResponseData;
    private String Collect_params;
    private String ActualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;
    private Integer isExceptionCase;

    public String getCollect_params() {
        return Collect_params;
    }

    public void setCollect_params(String collect_params) {
        Collect_params = collect_params;
    }

    public Integer getIsExceptionCase() {
        return isExceptionCase;
    }

    public void setIsExceptionCase(Integer isExceptionCase) {
        this.isExceptionCase = isExceptionCase;
    }

    public Integer getCaseId() {
        return CaseId;
    }

    public void setCaseId(Integer caseId) {
        CaseId = caseId;
    }

    public String getDese() {
        return Dese;
    }

    public void setDese(String dese) {
        Dese = dese;
    }

    public Integer getApiId() {
        return ApiId;
    }

    public void setApiId(Integer apiId) {
        ApiId = apiId;
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
