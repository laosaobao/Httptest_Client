package cn.httptest.Case;


import cn.httptest.pojo.Api_Case;
import cn.httptest.util.DataprovideUtil;
import cn.httptest.util.HttpUtil;

import com.alibaba.fastjson.JSONObject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


import java.util.Map;

public class RegisterCase {

    @Test(dataProvider = "Datas")
    public void RegistTest(Api_Case api_case) {
        //Map<String, String> params = (Map<String, String>) JSONObject.parse(parameters);
        String[] output = new String[]{"CaseId","ExpectedResponseData", "ActualResponseData",
                "PreValidateSql", "PreValidateResult",
                "AfterValidateSql", "AfterValidateResult",
                "IsExceptionCase"};
        System.out.println(HttpUtil.doService(api_case,output));
    }

    @DataProvider(name = "Datas")
    public Object[][] datas() {
      //  String[] title = {"Url", "Params", "Type"};
        Object[][] datas = DataprovideUtil.GetApi_CaseByApiName("regist");
        return datas;
    }



}
