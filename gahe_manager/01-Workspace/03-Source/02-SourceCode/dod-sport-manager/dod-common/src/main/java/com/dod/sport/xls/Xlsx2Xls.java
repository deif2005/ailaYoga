package com.dod.sport.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;




/**
 * Xlsx2Xls
 *
 * @author zhaoqian
 * @date 2016/9/5 0005
 */
public class Xlsx2Xls {


    private String outFn;
    private File inpFn;

    public Xlsx2Xls(String inpFn){
        this.outFn = inpFn.replace("xlsx", "xls");
        this.inpFn = new File(inpFn);
    }
    public void xlsx2xls() throws InvalidFormatException,IOException {

        InputStream in = new FileInputStream(inpFn);
        try {
            XSSFWorkbook wbIn = new XSSFWorkbook(in);
            File outF = new File(outFn);
            if (outF.exists()) {
                outF.delete();
            }
            Workbook wbOut = new HSSFWorkbook();
            int sheetCnt = wbIn.getNumberOfSheets();
            for (int i = 0; i < sheetCnt; i++) {
                Sheet sIn = wbIn.getSheetAt(0);
                Sheet sOut = wbOut.createSheet(sIn.getSheetName());
                Iterator<Row> rowIt = sIn.rowIterator();
                while (rowIt.hasNext()) {
                    Row rowIn = rowIt.next();
                    Row rowOut = sOut.createRow(rowIn.getRowNum());
                    Iterator<Cell> cellIt = rowIn.cellIterator();
                    while (cellIt.hasNext()) {
                        Cell cellIn = cellIt.next();
                        Cell cellOut = rowOut.createCell(cellIn.getColumnIndex(), cellIn.getCellType());
                        switch (cellIn.getCellType()) {
                            case Cell.CELL_TYPE_BLANK: break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                cellOut.setCellValue(cellIn.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                cellOut.setCellValue(cellIn.getErrorCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                cellOut.setCellFormula(cellIn.getCellFormula());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                cellOut.setCellValue(cellIn.getNumericCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING:
                                cellOut.setCellValue(cellIn.getStringCellValue());
                                break;
                        }
                        {
                            CellStyle styleIn = cellIn.getCellStyle();
                            CellStyle styleOut = cellOut.getCellStyle();
                            styleOut.setDataFormat(styleIn.getDataFormat());
                        }cellOut.setCellComment(cellIn.getCellComment());

                    }
                }
            }
            OutputStream out = new BufferedOutputStream(new FileOutputStream(outF));
            try {
                wbOut.write(out);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        String inpFn = "G:\\测试\\测试.xls";
        Xlsx2Xls test = new Xlsx2Xls(inpFn);
        test.xlsx2xls();
    }

}
