package excelutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class TestExportBean {
    public static void main(String[] args) throws IOException {
        
        String[] headers = {"a","b","c"};
        Collection<Object> dataset=new ArrayList<Object>();
        dataset.add(new Model("a1", "b1", "c1"));
        dataset.add(new Model("a2", "b2", "c2"));
        dataset.add(new Model("a3", "b3", "c3"));
        System.out.println(TestExportBean.class.getClassLoader().getResource("").getPath() + "test2.xls");
        File f = new File(TestExportBean.class.getClassLoader().getResource("").getPath() + "test2.xls");
        OutputStream out = new FileOutputStream(f);

        ExcelUtil.exportExcel(headers, dataset, out);
        out.close();
    }
}
