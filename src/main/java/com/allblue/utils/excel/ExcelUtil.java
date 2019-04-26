package com.allblue.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/4/13 14:07
 **/
public class ExcelUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {

        File file = new File("C:\\Users\\Xone\\Downloads\\lucky_admin.xlsx");
        Map<String, List<List>> excelMap = readExcel(file);
        if (excelMap != null) {
            Iterator<Map.Entry<String, List<List>>> iterator = excelMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<List>> entry = iterator.next();
                List<List> sheetList = entry.getValue();
                System.out.println("Sheet名:" + entry.getKey());
                for (List cellList : sheetList) {
                    System.out.println(cellList);
                }
                System.out.println();
            }
//            for (String key : excelMap.keySet()) {
//                List<List> sheetList = excelMap.get(key);
//                System.out.println("Sheet名:" + key);
//                for (List cellList : sheetList) {
//                    System.out.println(cellList);
//                }
//                System.out.println();
//            }
        }

        writeExcel(excelMap, "D:/writeExcel.xlsx");

    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public static Map<String, List<List>> readExcel(File file) {
        try {
            //校验文件是否存在
            checkExcelValid(file);
            // 读取Excel
            Workbook wb = getWorkbook(file);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            Map<String, List<List>> outerList = new HashMap<>();
            for (int index = 0; index < sheet_size; index++) {
                List<List> sheetList = new ArrayList<>();
                // 遍历每个Sheet对象
                Sheet sheet = wb.getSheetAt(index);
                String name = wb.getSheetName(index);
                // 该页的总行数
                int totalRowNum = sheet.getLastRowNum() + 1;
                for (int i = 0; i < totalRowNum; i++) {
                    List<String> innerList = new ArrayList<>();
                    // 遍历每行row数据
                    Row row = sheet.getRow(i);
                    // 该页的总列数
                    int totalCellNum = row.getLastCellNum();
                    for (int j = 0; j < totalCellNum; j++) {
                        //遍历每个cell数据
                        Cell cell = row.getCell(j);
                        if (!cell.toString().isEmpty()) {
                            Object value = getRightTypeCell(cell);
                            innerList.add(value.toString());
                        }
                    }
                    if (!innerList.isEmpty())
                        sheetList.add(i, innerList);
                }
                outerList.put(name, sheetList);
                wb.close();
            }
            return outerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeExcel(Map<String, List<List>> data, String filePath) {
        OutputStream out = null;
        try {
            // 判断是否存在文件
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(filePath);

            Workbook workBook = createWorkbook(file);

            Iterator<Map.Entry<String, List<List>>> iterator = data.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<List>> entry = iterator.next();
                //新建sheet
                Sheet sheet = workBook.createSheet(entry.getKey());
                //sheet页内数据
                List<List> sheetData = entry.getValue();
                //向Excel中写新数据
                for (int j = 0; j < sheetData.size(); j++) {
                    // 创建一行
                    Row row = sheet.createRow(j);
                    // 录得到要插入的每一条记
                    List cellData = sheetData.get(j);

                    for (int k = 0; k < cellData.size(); k++) {
                        // 写入cell数据
                        row.createCell(k).setCellValue(cellData.get(k).toString());
                    }
                }
            }
            workBook.write(out);
            System.out.println("数据导出成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param file
     * @return
     * @throws IOException
     */

    public static Workbook getWorkbook(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断Excel的版本,创建Workbook
     *
     * @param file
     * @return
     * @throws IOException
     */

    public static Workbook createWorkbook(File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
            wb = new HSSFWorkbook();
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook();
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public static void checkExcelValid(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * @param cell 一个单元格的对象
     * @return 返回该单元格相应的类型的值
     */
    public static Object getRightTypeCell(Cell cell) {

        Object object = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: {
                object = cell.getStringCellValue();
                break;
            }
            case Cell.CELL_TYPE_NUMERIC: {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object = cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_FORMULA: {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object = cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_BLANK: {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                object = cell.getStringCellValue();
                break;
            }
        }
        return object;
    }
}
