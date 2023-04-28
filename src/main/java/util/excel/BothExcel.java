package util.excel;

import mode.CompanyDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.PropertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BothExcel {
    public PropertiesUtil propertiesUtil;

    public static void main(String[] args) throws Exception {
        String url = "D:\\ZTY\\每周学习计划\\录回访\\aa.xls";
        List<CompanyDetail> allCompanyDetail = BothExcel.getAllCompanyDetail(url);
        for (CompanyDetail companyDetail : allCompanyDetail) {
            System.out.println(companyDetail);
        }
//        BothExcel.doExecute(url);
    }

    public static void doExecute(String url) {
        //excel文件路径
        String excelPath = url;

        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: " + firstRowIndex);
                System.out.println("lastRowIndex: " + lastRowIndex);

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                System.out.println(cell.toString());
                            }
                        }
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void personalExecute(String path) throws IOException, InvalidFormatException {
        File excel = new File(path);
        if (excel.isFile() && excel.exists()) {

            String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
            Workbook wb;
            //根据文件后缀（xls/xlsx）进行判断
            if ("xls".equals(split[1])) {
                FileInputStream fis = new FileInputStream(excel);   //文件流对象
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(excel);
            } else {
                System.out.println("文件类型错误!");
                return;
            }
            int numberOfSheets = wb.getNumberOfSheets();
            System.out.println("numberOfSheets is: " + numberOfSheets);

            for (int i = 0; i < numberOfSheets; i++) {

                //开始解析
                Sheet sheet = wb.getSheetAt(i);     //遍历sheet
                String sheetName = sheet.getSheetName();
                int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: " + firstRowIndex);
                System.out.println("lastRowIndex: " + lastRowIndex);

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);

                            if (cell != null) {
                                String message = cell.toString();
                                message = message.replaceAll(" ", "");
                                System.out.printf("%s\t", message);
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println("表[" + sheetName + "]遍历完毕。");
            }
        } //判断文件是否存在
        else {
            System.out.println("找不到指定的文件");
        }
    }

    //获取所有的公司的信息
    public static List<CompanyDetail> getAllCompanyDetail(String path) throws Exception {
        Boolean add_time = Boolean.valueOf(PropertiesUtil.properties.getProperty("add_time"));
        List<CompanyDetail> allCompanyDetails = new ArrayList<>();

        File excel = new File(path);
        if (excel.isFile() && excel.exists()) {

            String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
            Workbook wb;
            //根据文件后缀（xls/xlsx）进行判断
            if ("xls".equals(split[1])) {
                FileInputStream fis = new FileInputStream(excel);   //文件流对象
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(excel);
            } else {
                throw new Exception("文件类型错误");
            }
            int numberOfSheets = wb.getNumberOfSheets();
            System.out.println("numberOfSheets is: " + numberOfSheets);

            for (int i = 0; i < numberOfSheets; i++) {

                //开始解析
                Sheet sheet = wb.getSheetAt(i);     //遍历sheet
                String sheetName = sheet.getSheetName();
                int firstRowIndex;
                int companyNameIndex = 0;
                int addTimeIndex = -1;
                if (add_time) {
                    firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
//                读取第一行找到日期列
                    Row firstRow = sheet.getRow(sheet.getFirstRowNum());
                    if (firstRow != null) {
                        int firstCellIndex = firstRow.getFirstCellNum();
                        int lastCellIndex = firstRow.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex <= lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = firstRow.getCell(cIndex);
                            if (cell != null) {
                                String message = cell.toString();
                                message = message.replaceAll(" ", "");
                                if ("公司".equals(message) || "公司名称".equals(message)) {
                                    companyNameIndex = cIndex;
                                }
                                if ("日期".equals(message) || "时间".equals(message)) {
                                    addTimeIndex = cIndex;
                                }

                            }
                        }
                    }
                } else {
                    firstRowIndex = sheet.getFirstRowNum();   //第一行就是数据
                }

                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("开始行数: " + firstRowIndex);
                System.out.println("接受行数: " + lastRowIndex);
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                    System.out.println("第[" + rIndex + "]行");
                    Row row = sheet.getRow(rIndex);
//                    行不为空
                    if (!Objects.isNull(row)) {
                        if (add_time) {
                            CompanyDetail companyDetail = new CompanyDetail();
                            Cell companyName = row.getCell(companyNameIndex);
                            if (companyName != null) {
                                String message = companyName.toString();
                                message = message.replaceAll(" ", "");
                                companyDetail.setCompanyName(message.trim());
                            }
                            Cell addTime = row.getCell(addTimeIndex);
                            if (addTime != null) {
                                String message = ExcelDataExecuteUtil.getAddTime(addTime);
                                companyDetail.setAddTime(message);
                            }
                            if (!StringUtils.isBlank(companyDetail.getCompanyName())) {
                                allCompanyDetails.add(companyDetail);
                            }
                        } else {
                            CompanyDetail companyDetail = new CompanyDetail();
                            Cell companyName = row.getCell(0);
                            if (companyName != null) {
                                String message = companyName.toString();
                                message = message.replaceAll(" ", "");
                                companyDetail.setCompanyName(message);
                            }
                            if (!StringUtils.isBlank(companyDetail.getCompanyName())) {
                                allCompanyDetails.add(companyDetail);
                            }
                        }

                    }
                }
                System.out.println("表[" + sheetName + "]遍历完毕。");
            }
        } //判断文件是否存在
        else {
            System.out.println("找不到指定的文件");
        }

        return allCompanyDetails;
    }
}
