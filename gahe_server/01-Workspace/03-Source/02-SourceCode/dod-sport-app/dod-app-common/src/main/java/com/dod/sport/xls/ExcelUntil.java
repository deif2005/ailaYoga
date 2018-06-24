package com.dod.sport.xls;
/**
 * @Title: ExcelUntil.java
 * @Package: com.test
 * @Description: TODO
 * @author lyf
 * @date 2015年11月16日 上午10:20:18 
 * @version 1.3.1
 */

import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author ZQ
 * @date 2015年11月16日 上午10:20:18 
 * @version V1.3.1
 */

public class ExcelUntil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUntil.class);

    /**
     *
     * @param inputStream
     *          文件流
     * @param sheetArray
     *          sheet（可以为空）
     * @param columnsArray
     *           excel列名
     * @return
     * @throws IOException
     * @throws JXLException
     * @throws InvalidFormatException
     */
    public static List<String> ReadExcelUntil(InputStream inputStream,String[] sheetArray, String[] columnsArray){
        List<String> resultList = new ArrayList<String>();
        try {
            Workbook workbook = Workbook.getWorkbook(inputStream);
            if(sheetArray!=null && sheetArray.length>0){
                for (int i = 0; i < sheetArray.length; i++) {
                    Sheet sheet = workbook.getSheet(sheetArray[i]);
                    int maxColumns = sheet.getColumns(); //总列数
                    int maxRows = sheet.getRows();       //总行数
                    if(maxColumns>0 && maxRows>0){
                        String[][] resultArray = new String[maxRows][columnsArray.length];
                        for (int j = 0; j < columnsArray.length; j++) {
                            if(columnsArray[j] != null && !"".equals(columnsArray[j])){
                                resultArray[0][j] = columnsArray[j];
                                List<String> valueList = readCellValue(sheet, columnsArray[j], maxRows, maxColumns);
                                for (int k = 0; k < valueList.size(); k++) {
                                    resultArray[k+1][j] = valueList.get(k);
                                }
                            }
                        }
                        for (int j = 0; j < resultArray.length; j++) {
                            StringBuffer sb = new StringBuffer();
                            for (int h = 0; h < columnsArray.length; h++) {
                                if(sb.length()>0){
                                    sb.append(","+resultArray[j][h]);
                                }
                                else{
                                    sb.append(resultArray[j][h]);
                                }
                            }
                            resultList.add(sb.toString());
                        }
                    }
                }
            }else{
                Sheet[] sheets = workbook.getSheets();
                int maxColumns = sheets[0].getColumns(); //总列数
                int maxRows = sheets[0].getRows();       //总行数
                if(maxColumns>0 && maxRows>0){
                    String[][] resultArray = new String[maxRows][columnsArray.length];
                    for (int j = 0; j < columnsArray.length; j++) {
                        if(columnsArray[j] != null && !"".equals(columnsArray[j])){
                            resultArray[0][j] = columnsArray[j];
                            List<String> valueList = readCellValue(sheets[0], columnsArray[j], maxRows, maxColumns);
                            for (int k = 0; k < valueList.size(); k++) {
                                resultArray[k+1][j] = valueList.get(k);
                            }
                        }
                    }
                    for (int j = 0; j < resultArray.length; j++) {
                        StringBuffer sb = new StringBuffer();
                        for (int h = 0; h < columnsArray.length; h++) {
                            if(sb.length()>0){
                                sb.append(","+resultArray[j][h]);
                            }
                            else{
                                sb.append(resultArray[j][h]);
                            }
                        }
                        resultList.add(sb.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("读取excel时发生异常：",e);
        }
        return resultList;
    }

    public static List<String> ReadExcelUntilEX(InputStream inputStream,String[] sheetArray, String[] columnsArray){
        List<String> resultList = new ArrayList<String>();
        try {
            Workbook workbook = Workbook.getWorkbook(inputStream);
            if (sheetArray != null && sheetArray.length > 0) {
                for (int i = 0; i < sheetArray.length; i++) {
                    Sheet sheet = workbook.getSheet(sheetArray[i]);
                    int maxColumns = sheet.getColumns(); //总列数
                    int maxRows = sheet.getRows();       //总行数
                    if (maxColumns > 0 && maxRows > 0) {
                        String[][] resultArray = new String[maxRows][columnsArray.length];
                        for (int j = 0; j < columnsArray.length; j++) {
                            if (columnsArray[j] != null && !"".equals(columnsArray[j])) {
                                resultArray[0][j] = columnsArray[j];
                                List<String> valueList = readCellValue(sheet, columnsArray[j], maxRows, maxColumns);
                                for (int k = 0; k < valueList.size(); k++) {
                                    resultArray[k + 1][j] = valueList.get(k);
                                }
                            }
                        }
                        for (int j = 0; j < resultArray.length; j++) {
                            StringBuffer sb = new StringBuffer();
                            for (int h = 0; h < columnsArray.length; h++) {
                                if (sb.length() > 0) {
                                    sb.append("," + resultArray[j][h]);
                                } else {
                                    sb.append(resultArray[j][h]);
                                }
                            }
                            resultList.add(sb.toString());
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("读取excel时发生异常：",e);
        }
        return resultList;
    }
    public static List<String> readCellValue(Sheet sheet, String columnsName, int maxRows, int maxColumns){
        List<String> valueList = new ArrayList<String>();
        for (int j = 0; j < maxRows; j++) {
            for (int k = 0; k < maxColumns; k++) {
                String cellStr = sheet.getCell(k, j).getContents();
                if(cellStr!=null&&cellStr.trim().equals(columnsName)){
                    for (int m = j; m < maxRows; m++) {
                        String value = sheet.getCell(k, m).getContents();
                        if(!value.equals(cellStr)&&value!=null&&!"".equals(value)){
                            valueList.add(value);
                        }
                    }
                    break;
                }
            }
        }
        return valueList;
    }
/*根据表头获取列号 aisq
    public static int getColumnIndex(Sheet sheet, String columnsName,int maxColumns){
        int columnIndex = 0;
        for (int k = 0; k < maxColumns; k++) {
            String cellStr = sheet.getCell(k, 0).getContents();
            if(cellStr!=null&&cellStr.trim().equals(columnsName)){
                columnIndex = k;
                break;
            }
        }
        return columnIndex;
    }
*/
}
