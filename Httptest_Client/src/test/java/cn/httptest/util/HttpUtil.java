package cn.httptest.util;


import cn.httptest.Dao.VariableinfoMapper;
import cn.httptest.pojo.Api_Case;
import cn.httptest.pojo.Variable_info;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;

public class HttpUtil {

    public static HashMap<String, String> ResponseVar = new HashMap<String, String>();
    public static HashMap<String, String> CaseVar;
    public static Logger logger=Logger.getLogger(HttpUtil.class);
    static {
        HttpUtil.CaseVar=new HashMap<String, String>();
       List<Variable_info> varlist= SqlsessionLoad.sqlSession.getMapper(VariableinfoMapper.class).selectAllVariable();
        for (Variable_info var:varlist) {
            CaseVar.put(var.getName_var(),var.getValue());
        }
    }

    public static String doGet(String url, Map<String, String> params, Integer needToken) {
        Set<String> keys = params.keySet();
        //标记是否第一个参数
        int mark = 1;
        for (String key : keys) {
            if (mark == 1)
                url += ("?" + key + "=" + params.get(key).toString());
            else
                url += ("&" + key + "=" + params.get(key).toString());
            mark++;
        }
        //声明get方式
        HttpGet get = new HttpGet(url);
        if (needToken != null && needToken.equals(1)) {
            get.addHeader("Token", ResponseVar.get("Token"));
        }
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response;
        String result = "";
        try {
            response = httpClient.execute(get);
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code);
            result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(result);
            jsonObject.put("StatusLine", code);
            result = jsonObject.toJSONString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String doPost(String url, Map<String, String> params, Integer needToken) {
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();


        String result = "";
        try {
            if (params!=null) {
                Set<String> keys = params.keySet();
                //设置参数
                for (String key : keys) {
                    parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
                post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
            }
            if (needToken != null && needToken.equals(1)) {
                post.addHeader("Token", ResponseVar.get("${Token}"));
            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code);
            //相应结果是Entity类型
            result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(result);
            jsonObject.put("StatusLine", code);
            result = jsonObject.toJSONString();
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doService(Api_Case apiCase, String[] outputTitle) {
        String url = apiCase.getUrl();
        String type = apiCase.getType();
        Integer needToken = apiCase.getNeedToken();
        String Collect_params = apiCase.getCollect_params();
        String paramsJson = apiCase.getParams();
        Integer IsExceptionCase = apiCase.getIsExceptionCase();
        String ExpectedResponseData = apiCase.getExpectedResponseData();//期望的响应结果

        String result = "";
        //按照约定，请求的json参数，若包含${},则该部分变量从之前接口的返回值提取出来，需要进行替换，可能有多个，用while判断
        if (paramsJson!=null&&!"".equals(paramsJson.trim())) {
            while (paramsJson.contains("${")) {
                Set<String> keys = ResponseVar.keySet();
                //循环全局变量，看看是否为json串里需要替换的变量 是则替换，这样写简单且替换字符串准确率高，变量多时可能废时间
                for (String key : keys) {
                    if (paramsJson.contains(key))
                        paramsJson=paramsJson.replace(key, ResponseVar.get(key));
                }
            }
            while (paramsJson.contains("#{")) {
                Set<String> keys = CaseVar.keySet();
                //循环全局变量，看看是否为json串里需要替换的变量 是则替换，这样写简单且替换字符串准确率高，变量多时可能废时间
                for (String key : keys) {
                    if (paramsJson.contains(key)) {
                        paramsJson = paramsJson.replace(key, CaseVar.get(key));
                        break;
                    }
                }
            }
        }
        logger.info("接口--调用参数"+paramsJson);
        Map<String, String> params = (Map<String, String>) JSONObject.parse(paramsJson);
        if ("get".equals(type)) {
            result = HttpUtil.doGet(url, params, needToken);
            apiCase.setActualResponseData(result);
        } else if ("post".equals(type)) {
            logger.info("接口url："+url);
            result = HttpUtil.doPost(url, params, needToken);
            apiCase.setActualResponseData(result);
        }

        JSONObject resultJson = JSON.parseObject(result);
        //判断是否正常用例，若为正常用例再判断需不需要提取参数
        if (IsExceptionCase == 0) {
            //判断是否需要提取返回参数到全局变量供后面的接口使用
            if (Collect_params != null && !"".equals(Collect_params.trim())) {  //以逗号分割需要提取的参数，
                String[] collects = Collect_params.split(",");
                //提取变量时，key为${}格式
                for (String key : collects) {
                    if (resultJson.getString(key) != null && !"".equals(resultJson.getString(key).trim()))
                        ResponseVar.put("${" + key + "}", resultJson.getString(key));
                    else
                        System.out.println("用例" + apiCase.getCaseId() + apiCase.getDesc() + "响应结果没有找到要提取的参数" + key);
                }
            }

        }

        //保存输出结果的map，传入到ExcelUtil的static 变量里，由ExcelUtil进行测试结果输出
        LinkedHashMap<String, String[]> output_map = new LinkedHashMap<String, String[]>();
        String[] output_array;//存放两个值，回写的数据和单元格颜色，0正常1黄色，2红色
        //根据result响应结果进行判断，判断用例是否执行成功，正常情况用例和异常情况用例需要分开判断
        boolean temp = true;//若最终结果为false，则该map应该写入ExcelUtil的错误wronglist
        for (String title : outputTitle) {
            output_array = new String[2];
            //判断响应结果
            if (title.equals("ActualResponseData")) {
                //正常用例
                if (IsExceptionCase == 0) {  //正常用例，需要先判断状态码，再比对实际返回参数和期望返回参数
                    if ((Integer) resultJson.get("StatusLine") == 200) {  //状态码200，且期望返回值不为空，根据期望返回值比对实际返回值
                        if (ExpectedResponseData != null && !"".equals(ExpectedResponseData.trim())) {
                            JSONObject expectedResponseJson = JSON.parseObject(ExpectedResponseData);
                            Set<String> keys = expectedResponseJson.keySet();
                            int right = 0;//记录期望响应和实际响应结果之间比对正确的个数
                            for (String key : keys) {
                                if (expectedResponseJson.get(key).equals(resultJson.get(key)))
                                    right++;
                            }
                            //期望响应结果里有多个key，只要命中其中一个为黄，全不命中为红，全中为正常
                            if (keys.size() > 1) {
                                if (right == 0) {
                                    output_array[0] = result;
                                    output_array[1] = "2";
                                    output_map.put("ActualResponseData", output_array);
                                    temp = false;
                                } else if (right == keys.size()) {
                                    output_array[0] = result;
                                    output_array[1] = "0";
                                    output_map.put("ActualResponseData", output_array);
                                } else {
                                    output_array[0] = result;
                                    output_array[1] = "1";
                                    output_map.put("ActualResponseData", output_array);
                                }
                            }
                            //状态码200，期望响应结果只有一个key，命中为正常，不命中为黄
                            else {
                                if (right > 0) {
                                    output_array[0] = result;
                                    output_array[1] = "0";
                                    output_map.put("ActualResponseData", output_array);
                                } else {
                                    output_array[0] = result;
                                    output_array[1] = "1";
                                    output_map.put("ActualResponseData", output_array);
                                }
                            }
                        }
                        //状态码200，且期望返回值为空的情况，直接正确
                        else {
                            output_array[0] = result;
                            output_array[1] = "0";
                            output_map.put("ActualResponseData", output_array);
                        }
                    }
                    //正常用例，状态码非200，则直接判定为错误，标红
                    else {
                        output_array[0] = result;
                        output_array[1] = "2";
                        output_map.put("ActualResponseData", output_array);
                        temp = false;
                    }
                }
                //非正常用例(即测试异常情况的用例，按照另外规则进行实际响应和期望响应的比对)
                else {//非正常用例，不判断状态码，当期望返回值为空时，直接正确
                    if (ExpectedResponseData == null && "".equals(ExpectedResponseData.trim())) {
                        output_array[0] = result;
                        output_array[1] = "0";
                        output_map.put("ActualResponseData", output_array);
                    }
                    //非正常用例，期望返回值不为空时的情况
                    else {
                        JSONObject expectedResponseJson = JSON.parseObject(ExpectedResponseData);
                        Set<String> keys = expectedResponseJson.keySet();
                        int right = 0;//记录期望响应和实际响应结果之间比对正确的个数
                        for (String key : keys) {
                            if (expectedResponseJson.get(key).equals(resultJson.get(key)))
                                right++;
                        }
                        //期望响应结果里有多个key，只要命中其中一个为黄，全不命中为红，全中为正常
                        if (keys.size() > 1) {
                            if (right == 0) {
                                output_array[0] = result;
                                output_array[1] = "2";
                                output_map.put("ActualResponseData", output_array);
                                temp = false;
                            } else if (right == keys.size()) {
                                output_array[0] = result;
                                output_array[1] = "0";
                                output_map.put("ActualResponseData", output_array);
                            } else {
                                output_array[0] = result;
                                output_array[1] = "1";
                                output_map.put("ActualResponseData", output_array);
                            }
                        }
                        //期望响应结果只有一个key，命中为正常，不命中为黄
                        else {
                            if (right > 0) {
                                output_array[0] = result;
                                output_array[1] = "0";
                                output_map.put("ActualResponseData", output_array);
                            } else {
                                output_array[0] = result;
                                output_array[1] = "1";
                                output_map.put("ActualResponseData", output_array);
                            }
                        }

                    }
                }

            }
            //标题不是ActualResponseData，直接写入
            else {
                try {//根据名称，通过反射获取值
                    Class clazz = Class.forName("cn.httptest.pojo.Api_Case");
                    Method method = clazz.getMethod("get" + title);
                    Object str = method.invoke(apiCase);
                    if (str==null)
                        output_array[0]="";
                    else output_array[0] = str.toString();

                    output_array[1] = "0";
                    output_map.put(title, output_array);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        }
        //标题循环结束，放入一行结果到ExcelUtil
        if (temp == true)
            ExcelUtil.RigthData.add(output_map);
        else
            ExcelUtil.WrongData.add(output_map);

        return result;
    }
}

