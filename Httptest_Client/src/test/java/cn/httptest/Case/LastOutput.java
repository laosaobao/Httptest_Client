package cn.httptest.Case;

import cn.httptest.util.ExcelUtil;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class LastOutput {
    @AfterSuite
    public void Lastoutput()
    {
        ExcelUtil.OutputTesting_Result();
    }
}
