package cn.httptest.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

public class ExcelUtil {
    public static  Logger logger=Logger.getLogger(ExcelUtil.class);
   public static List<LinkedHashMap<String,String[]>> RigthData =new ArrayList<LinkedHashMap<String, String[]>>();
   public static List<LinkedHashMap<String,String[]>> WrongData=new ArrayList<LinkedHashMap<String, String[]>>();
    public static Object[][] datas(String excelPath,String sheetname,String[] CellNames) {
       // String excelPath = "src/test/resources/接口用例.xlsx";
        //Properties properties=new Properties();
        File file = new File(excelPath);
        Object[][] result=null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            //InputStream in = new BufferedInputStream(new FileInputStream("src/test/resources/application.properties"));
            //properties.load(in);
            Sheet sheet =workbook.getSheet(sheetname);
            //最后一行索引，0开头，有可能不准
            int LastRowNum=sheet.getLastRowNum();
            Row first_row=sheet.getRow(0);
            //得到列数 1开头
            int LastCellNum=first_row.getLastCellNum();
            //存放需要读取的列号
            ArrayList<Integer> Cellnums=new ArrayList<Integer>();
            //存放结果
            result=new Object[LastRowNum][CellNames.length];
            for (int num=0;num<LastCellNum;num++)
            {//得到第一列标题的值
                String value=first_row.getCell(num).getStringCellValue();
                //截取括号前的字符串 和CellNames进行匹配，判断是否是需要读取的列
                value=value.substring(0,value.indexOf("("));
                for (String name:CellNames) {
                    if(name.equals(value))
                    {   //存放需要读取的列号
                        Cellnums.add(num);
                        break;
                    }
                }

            }
            for (int i=1;i<=LastRowNum;i++) {
                 Row row=sheet.getRow(i);
                DataFormatter dataFormatter=new DataFormatter();
                 if(row!=null&&row.getCell(0)!=null&&!"".equals(dataFormatter.formatCellValue(row.getCell(0)).trim()))
                 {
                     for (int z=0;z<Cellnums.size();z++)
                     {   //需要读取的列号
                         int temp=Cellnums.get(z);
                         result[i-1][z]=dataFormatter.formatCellValue(row.getCell(temp));
                         System.out.print(dataFormatter.formatCellValue(row.getCell(temp))+" ");
                     }
                     System.out.println();
                 }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

//    public static void main(String[] args) throws Exception{
//        String [] a={"CaseId","Name","Url","Account","Phone","ActualResponseData"};
//        ExcelUtil.datas(a);
//
//    }

     public static void OutputTesting_Result() {
         XSSFWorkbook right_workbook = new XSSFWorkbook();
         XSSFSheet right_sheet = right_workbook.createSheet("接口测试正确结果");
         CellStyle right_style = right_workbook.createCellStyle();
         //设置黄颜色标记特殊单元格
         right_style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
         right_style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
         right_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

         //生成两份文档 正确和错误
         XSSFWorkbook wrong_workbook = new XSSFWorkbook();
         XSSFSheet wrong_sheet = wrong_workbook.createSheet("接口测试错误结果");
         CellStyle wrong_style = wrong_workbook.createCellStyle();
         //设置特殊颜色标记特殊单元格
         wrong_style.setFillBackgroundColor(IndexedColors.RED.getIndex());
         wrong_style.setFillForegroundColor(IndexedColors.RED.getIndex());
         wrong_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

         //创建第一行表头
         XSSFRow right_row = right_sheet.createRow(0);
         Set<String> title = RigthData.get(0).keySet();
         int temp = 0;
         for (String str : title) {
             logger.info("输出表头："+str);

             //设置表头
             XSSFCell cell = right_row.createCell(temp);
             cell.setCellValue(str);
             //设置列宽
             right_sheet.setColumnWidth(temp, 30 * 256);
             temp++;
         }
         //创建第一行表头
         XSSFRow wrong_row = wrong_sheet.createRow(0);
         temp=0;
         for (String str : title) {

             //设置表头
             XSSFCell cell = wrong_row.createCell(temp);
             cell.setCellValue(str);
             //设置列宽
             wrong_sheet.setColumnWidth(temp, 30 * 256);
             temp++;
         }
         //正确结果
         for (int i = 0; i < RigthData.size(); i++) {
             LinkedHashMap<String, String[]> map = RigthData.get(i);
             right_row = right_sheet.createRow(i + 1);
             Set<String> keys = map.keySet();
             temp= 0;
             for (String key : keys) {

                 String[] result = map.get(key);
                 XSSFCell cell = right_row.createCell(temp);
                 if ("1".equals(result[1])) {
                     cell.setCellValue(result[0]);
                     cell.setCellStyle(right_style);
                 } else {
                     cell.setCellValue(result[0]);
                 }
                 temp++;
             }
         }

         //错误结果
         for (int i = 0; i < WrongData.size(); i++) {
             LinkedHashMap<String, String[]> map = WrongData.get(i);
             wrong_row = wrong_sheet.createRow(i + 1);
             Set<String> keys = map.keySet();
             temp=0;
             for (String key : keys) {
                 String[] result = map.get(key);
                 XSSFCell cell = wrong_row.createCell(temp);
                 if ("2".equals(result[1])) {
                     cell.setCellValue(result[0]);
                     cell.setCellStyle(wrong_style);
                 } else {
                     cell.setCellValue(result[0]);
                 }
                 temp++;
             }
         }

         try {
             File file = new File("src/test/resources/接口测试正确结果.xlsx");
             OutputStream stream = new FileOutputStream(file);
             right_workbook.write(stream);
             stream.close();
             if (wrong_sheet.getLastRowNum() > 0) {
                 File file2 = new File("src/test/resources/接口测试错误结果.xlsx");
                 OutputStream stream2 = new FileOutputStream(file2);
                 wrong_workbook.write(stream2);
                 stream2.close();
             }
           }
            catch(Exception e)
            {e.printStackTrace();}


     }

}
