package cn.httptest.Dao;

import cn.httptest.pojo.Api_Case;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ApiMapper {
    public List<Api_Case> GetApi_CaseByApiName(@Param("ApiName") String ApiName);
}
