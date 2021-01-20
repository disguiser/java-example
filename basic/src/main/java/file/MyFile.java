package file;

import java.io.File;

public class MyFile {
    public static void main(String[] args) {
        var path = MyFile.class.getClassLoader().getResource("file.txt").getPath();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
    }
}
