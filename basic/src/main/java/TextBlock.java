/**
 * @author yinhui
 * 2022/6/16 2:17
 */
public class TextBlock {
    public static void main(String[] args) {
        String twoLine = """
                Hello
                World
                """;
        String singleLine = """
                Hello \
                World
                """;
        System.out.println(twoLine);
        System.out.println(singleLine);
    }
}
