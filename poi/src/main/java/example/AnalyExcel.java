package example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AnalyExcel {
    public List<String[]> analyExcelRes(String path, int ignoreRows) throws IOException {
        List<String[]> list = new ArrayList<String[]>();
//		System.out.println(path);
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        InputStream in = new FileInputStream(path);
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            //忽略前ignoreRows行
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int rowSize = st.getRow(2).getLastCellNum() - 1; //row.getLastCellNum() 获取列数，结果比最后一列列标大1
                if (rowSize < 0) rowSize = 0;
                String[] temp = new String[rowSize];
                for (int columnIndex = 0; columnIndex < rowSize; columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码
                        //    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case _NONE:
                                break;
                            case NUMERIC:
//		                    	 System.out.println(cell.getNumericCellValue()+" type: numeric");
                                //	 value = new DecimalFormat("0.0000").format(cell.getNumericCellValue());
                                value = r0trim(new DecimalFormat("0.000000").format(cell.getNumericCellValue()));
                                break;
                            case FORMULA:
//		                    	 System.out.println(cell.getStringCellValue()+" type: formula");
                                try {
                                    value = cell.getStringCellValue();
                                } catch (IllegalStateException e) {
                                    value = r0trim(new DecimalFormat("0.000000").format((cell.getNumericCellValue())));
                                }
                                break;

                            case BOOLEAN:
//		                    	 System.out.println(cell.getBooleanCellValue()+" type: boolean");
                                value = (cell.getBooleanCellValue() == true ? "Y"
                                        : "N");
                                break;
                            default:
                                value = "";
                        }
                    }
                    temp[columnIndex] = value;
                }
                list.add(temp);
            }
        }
        return list;
    }

    /*
     * 去掉小数最右边多余的零，例如123.1200改为123.12，
     * 111.00改为111，但121100还是121100，
     */
    public String r0trim(String resource) {
        if (resource == null) {
            return null;
        }
        if (resource.indexOf(".") == -1) {
            return resource;
        }

        char x = '0';
        while (x == '0') {
            int length = resource.length();
            x = resource.charAt(length - 1);
            if (x == '0') {
                resource = resource.substring(0, length - 1);
            }
        }
        if (resource.charAt(resource.length() - 1) == '.')
            resource = resource.substring(0, resource.length() - 1);
        return resource;
    }

}

