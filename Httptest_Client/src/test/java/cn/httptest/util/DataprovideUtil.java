package cn.httptest.util;

import cn.httptest.Dao.ApiMapper;
import cn.httptest.pojo.Api_Case;
import java.util.List;

public class DataprovideUtil {
    private static ApiMapper apiMapper=SqlsessionLoad.sqlSession.getMapper(ApiMapper.class);

    public static Object[][] GetApi_CaseByApiName(String ApiName)
    {
        List<Api_Case> ApiList=apiMapper.GetApi_CaseByApiName(ApiName);
        Object[][] result=new Object[ApiList.size()][1];
        for (int i=0;i<ApiList.size();i++) {
             Api_Case api_case =ApiList.get(i);
             result[i][0]=api_case;
        }
        return result;
    }
}
