package cn.httptest.Case;

import cn.httptest.pojo.Api_Case;
import cn.httptest.util.DataprovideUtil;
import cn.httptest.util.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CrudCase {

    @Test(dataProvider = "CrudData")
    public void Crudtest(Api_Case api_case){

        String[] output = new String[]{"CaseId","ExpectedResponseData", "ActualResponseData",
                "PreValidateSql", "PreValidateResult",
                "AfterValidateSql", "AfterValidateResult",
                "IsExceptionCase"};
        System.out.println(HttpUtil.doService(api_case,output));
    }



    @DataProvider(name = "CrudData")
    public Object[][] dataprovider()
    {
        Object[][] datas = DataprovideUtil.GetApi_CaseByApiName("getProduct");
        return datas;
    }

}
