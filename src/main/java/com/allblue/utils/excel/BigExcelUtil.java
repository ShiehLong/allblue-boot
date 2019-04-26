package com.allblue.utils.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class BigExcelUtil {

    enum xssfDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
    }

    public static final int ERROR = 1;
    public static final int BOOLEAN = 1;
    public static final int NUMBER = 2;
    public static final int STRING = 3;
    public static final int DATE = 4;
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";


    //	private DataFormatter formatter = new DataFormatter();
    private InputStream sheet;
    private XMLReader parser;
    private InputSource sheetSource;
    private int index = 0;

    /**
     * 读大数据量Excel
     *
     * @param filename 文件名
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public BigExcelUtil(String filename) throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(filename);
        init(pkg);
    }

    /**
     * 读大数据量Excel
     *
     * @param file Excel文件
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public BigExcelUtil(File file) throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(file);
        init(pkg);
    }

    /**
     * 读大数据量Excel
     *
     * @param in Excel文件输入流
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public BigExcelUtil(InputStream in) throws IOException, OpenXML4JException, SAXException {
        OPCPackage pkg = OPCPackage.open(in);
        init(pkg);
    }

    /**
     * 初始化 将Excel转换为XML
     *
     * @param pkg
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    private void init(OPCPackage pkg) throws IOException, OpenXML4JException, SAXException {
        XSSFReader xssfReader = new XSSFReader(pkg);
        SharedStringsTable sharedStringsTable = xssfReader.getSharedStringsTable();
        StylesTable stylesTable = xssfReader.getStylesTable();
        sheet = xssfReader.getSheet("rId1");
        parser = fetchSheetParser(sharedStringsTable, stylesTable);
        sheetSource = new InputSource(sheet);
    }

    /**
     * 执行解析操作
     *
     * @return 读取的Excel行数
     */
    public int parse() {
        try {
            parser.parse(sheetSource);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        } finally {
            if (sheet != null) {
                try {
                    sheet.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return index;
    }

    private XMLReader fetchSheetParser(SharedStringsTable sharedStringsTable, StylesTable stylesTable) throws SAXException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "org.apache.xerces.parsers.SAXParser"
                );
        ContentHandler handler = new SheetHandler(sharedStringsTable, stylesTable);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * SAX解析的处理类
     * 每解析一行数据后通过outputRow(String[] datas, int[] rowTypes, int rowIndex)方法进行输出
     *
     * @author zpin
     */
    private class SheetHandler extends DefaultHandler {
        private SharedStringsTable sharedStringsTable; // 存放映射字符串
        private StylesTable stylesTable;// 存放单元格样式
        private String readValue;// 存放读取值
        private xssfDataType dataType;// 单元格类型
        private String[] rowDatas;// 存放一行中的所有数据
        private int[] rowTypes;// 存放一行中所有数据类型
        private int colIdx;// 当前所在列

        private short formatIndex;
//		private String formatString;// 对数值型的数据直接读为数值，不对其格式化，所以隐掉此处

        private SheetHandler(SharedStringsTable sst, StylesTable stylesTable) {
            this.sharedStringsTable = sst;
            this.stylesTable = stylesTable;
        }

        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            if (name.equals("c")) {// c > 单元格
                colIdx = getColumn(attributes);
                String cellType = attributes.getValue("t");
                String cellStyle = attributes.getValue("s");

                this.dataType = xssfDataType.NUMBER;
                if ("b".equals(cellType)) {
                    this.dataType = xssfDataType.BOOL;
                } else if ("e".equals(cellType)) {
                    this.dataType = xssfDataType.ERROR;
                } else if ("inlineStr".equals(cellType)) {
                    this.dataType = xssfDataType.INLINESTR;
                } else if ("s".equals(cellType)) {
                    this.dataType = xssfDataType.SSTINDEX;
                } else if ("str".equals(cellType)) {
                    this.dataType = xssfDataType.FORMULA;
                } else if (cellStyle != null) {
                    int styleIndex = Integer.parseInt(cellStyle);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
//		            this.formatString = style.getDataFormatString();
                }
            }
            // 解析到一行的开始处时，初始化数组
            else if (name.equals("row")) {
                int cols = getColsNum(attributes);// 获取该行的单元格数
                rowDatas = new String[cols];
                rowTypes = new int[cols];
            }
            readValue = "";
        }

        public void endElement(String uri, String localName, String name)
                throws SAXException {
            if (name.equals("v")) { // 单元格的值
                switch (this.dataType) {
                    case BOOL: {
                        char first = readValue.charAt(0);
                        rowDatas[colIdx] = first == '0' ? "FALSE" : "TRUE";
                        rowTypes[colIdx] = BOOLEAN;
                        break;
                    }
                    case ERROR: {
                        rowDatas[colIdx] = "ERROR:" + readValue;
                        rowTypes[colIdx] = ERROR;
                        break;
                    }
                    case INLINESTR: {
                        rowDatas[colIdx] = new XSSFRichTextString(readValue).toString();
                        rowTypes[colIdx] = STRING;
                        break;
                    }
                    case SSTINDEX: {
                        int idx = Integer.parseInt(readValue);
                        rowDatas[colIdx] = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString();
                        rowTypes[colIdx] = STRING;
                        break;
                    }
                    case FORMULA: {
                        rowDatas[colIdx] = readValue;
                        rowTypes[colIdx] = STRING;
                        break;
                    }
                    case NUMBER: {
                        // 判断是否是日期格式
                        if (HSSFDateUtil.isADateFormat(formatIndex, readValue)) {
                            Double d = Double.parseDouble(readValue);
                            Date date = HSSFDateUtil.getJavaDate(d);
                            rowDatas[colIdx] = DateFormatUtils.format(date, DATE_FORMAT_STR);
                            rowTypes[colIdx] = DATE;
                        }
//			            else if (formatString != null){
//			            	cellData.value = formatter.formatRawCellContents(Double.parseDouble(cellValue), formatIndex, formatString);
//			            	cellData.dataType = NUMBER;
//			            }
                        else {
                            rowDatas[colIdx] = readValue;
                            rowTypes[colIdx] = NUMBER;
                        }
                        break;
                    }
                }
            }
            // 当解析的一行的末尾时，输出数组中的数据
            else if (name.equals("row")) {
                outputRow(rowDatas, rowTypes, index++);
            }
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            readValue += new String(ch, start, length);
        }
    }

    private List<String[]> dataList = new ArrayList<>();

    /**
     * 输出每一行的数据
     *
     * @param datas    数据
     * @param rowTypes 数据类型
     * @param rowIndex 所在行
     */
    protected void outputRow(String[] datas, int[] rowTypes, int rowIndex) {
        dataList.add(datas);
        // 此处输出每一行的数据
//        System.out.println(Arrays.toString(datas));
    }

    public List<String[]> getDataList() {
        return dataList;
    }

    private int getColumn(Attributes attributes) {
        String name = attributes.getValue("r");
        int column = -1;
        for (int i = 0; i < name.length(); ++i) {
            if (Character.isDigit(name.charAt(i))) {
                break;
            }
            int c = name.charAt(i);
            column = (column + 1) * 26 + c - 'A';
        }
        return column;
    }

    private int getColsNum(Attributes attributes) {
        String spans = attributes.getValue("spans");
        String cols = spans.substring(spans.indexOf(":") + 1);
        return Integer.parseInt(cols);
    }

    /**
     * @Description: 使用缓存文件导出 excel
     * @Param: rowsNum
     * @return: void
     * @Author: Xone
     * @Date: 2019/4/15
     **/
    public static void processExcelWithCache(List<Map<String, String>> data, String path) {
        try {
            long startTime = System.currentTimeMillis();

            SXSSFWorkbook wb = null;
            try {
                wb = new SXSSFWorkbook();
                wb.setCompressTempFiles(true); //压缩临时文件，很重要，否则磁盘很快就会被写满
                Sheet sh = wb.createSheet();

                int index = 0;
                for (Map<String, String> map : data) {
                    Row row = sh.createRow(index);
                    Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                    int index2 = 0;
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = iterator.next();
                        Cell cell = row.createCell(index2);
                        cell.setCellValue(entry.getValue());
                        index2++;
                    }
                    index++;
                }

                FileOutputStream out = new FileOutputStream(path);
                wb.write(out);
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (wb != null) {
                    wb.dispose();// 删除临时文件，很重要，否则磁盘可能会被写满
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("process data spent time:" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\Xone\\Desktop\\report.xlsx";
        BigExcelUtil reader = new BigExcelUtil(filePath);
        // 执行解析
        reader.parse();
        List<String[]> data = reader.getDataList();
        for (String[] map : data) {
            System.out.println(Arrays.toString(map));
        }
        System.out.println("--over--");
    }
}