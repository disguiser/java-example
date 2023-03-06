/**
 * @author SargerasWang
 */
package excelutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * The <code>TestImportMap</code>	
 * 
 * @author SargerasWang
 * Created at 2014年9月21日 下午5:06:17
 */
public class TestImportMap {
  @SuppressWarnings("rawtypes")
  public static void main(String[] args) throws FileNotFoundException {
    File f = new File("/损益表_财通资管财享道集合资产管理计划_201603.xls");
    System.out.println(f.exists());
    InputStream inputStream= new FileInputStream(f);
    
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    
    for(Map m : importExcel){
      System.out.println(m);
    }
  }
}
