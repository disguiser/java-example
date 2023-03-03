package lambda;

import java.util.Arrays;

public class Lambda {
    public static void main(String[] args) {
        var ids = new Integer[]{1, 2, 3, 4};
//        Arrays.asList(ids).forEach(System.out::println);
        Arrays.asList(ids).forEach(id->{
            System.out.println(id);
        });
    }
}
