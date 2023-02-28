package util.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

public class ExcelUtil {

    public static void main(String[] args) throws Exception {
        String url = "D:\\ZTY\\每周学习计划\\录回访\\aa.xls";
        ExcelUtil.readExcel(url);
    }

    private static void readExcel(String url) throws Exception {

		/*  url：表格文档路径及名字
		eg:"文档路径...\\文档名.xls"      */

        //1:创建workbook
        Workbook workbook = Workbook.getWorkbook(new File(url));   //这里输入表格文档的路径及名字
        //2:获取第一个工作表sheet
        Sheet sheet = workbook.getSheet(0);  //下标从0开始表示第一个工作表
        //3:获取数据
        for (int i = 1; i < sheet.getRows(); i++)    //行数
        {
            for (int j = 0; j < sheet.getColumns(); j++)   //列数
            {
                Cell cell = sheet.getCell(j, i);  //这是一个单元格  这里注意j i的顺序不要颠倒
                System.out.print(cell.getContents() + " "); //获取单元格内容并输出
            }
            System.out.println(); //换行
        }
        //4：关闭资源
        workbook.close();
    }

}
