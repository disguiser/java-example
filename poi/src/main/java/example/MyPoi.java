package example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class MyPoi {
    public static void main(String[] args) throws IOException {
        Workbook wb1 = WorkbookFactory.create(new FileInputStream(MyPoi.class.getClassLoader().getResource("test.xls").getPath()));
        Sheet sheet1 = wb1.getSheetAt(0);
        Row row1 = sheet1.getRow(0);
        System.out.println(row1.getLastCellNum());
    }
}
