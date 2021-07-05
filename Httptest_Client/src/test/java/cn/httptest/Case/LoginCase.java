package cn.httptest.Case;

import cn.httptest.pojo.Api_Case;
import cn.httptest.util.DataprovideUtil;
import cn.httptest.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class LoginCase {

    @Test(dataProvider = "Datas")
    public void LoginTest(Api_Case apiCase) {

        //Map<String,String> params= (Map<String, String>)JSONObject.parse(apiCase.getParams());
        String[] output = new String[]{"CaseId","ExpectedResponseData", "ActualResponseData",
                                        "PreValidateSql", "PreValidateResult",
                                         "AfterValidateSql", "AfterValidateResult",
                                          "IsExceptionCase"};
        System.out.println(HttpUtil.doService(apiCase,output));
    }

    @DataProvider(name = "Datas")
    public Object[][] datas() {
        //String[] title = {"Url","NeedToken", "Params", "Type"};
        Object[][] datas = DataprovideUtil.GetApi_CaseByApiName("login");
        return datas;
    }
}
