package path;

public class Path {
    public static void main(String[] args) {
        var path1 = Path.class.getClassLoader().getResource("").getPath();
        System.out.println(path1);
        var path2 = System.getProperty("user.dir");
        System.out.println(path2);
    }
}
