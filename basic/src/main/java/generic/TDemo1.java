package generic;

import java.util.ArrayList;
import java.util.List;

/**
 * T、E、K、V、？ 实际上这几个其实没啥区别，只不过是一个约定好的字母标识 增加了代码的可读性。
 * List<? extends T>可以接受任何继承自T的类型的List，
 * List<? super T>可以接受任何T的父类构成的List。
 * 例如List<? extends Number>可以接受List<Integer>或List<Float>。
 * @param <T>
 */
public class TDemo1<T> {
    public static void main(String[] args){

        // 获取String类型
        TDemo1<String> tDemo = new TDemo1<>();  // 限制"T"为String类型
        List<String> array = new ArrayList<>();
        array.add("aaa");
        array.add("bbb");
        String str =  tDemo.getListFirst(array);
        System.out.println(str);

        // 获取Number类型
        TDemo1<Integer> tDemo1 = new TDemo1<>();  // 限制"T"为Integer类型
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        Integer num = tDemo1.getListFirst(nums);
        System.out.println(num);
    }
    // 单独的T表示限制参数的类型。
    private T getListFirst(List<T> data){
        if(data == null || data.size() == 0){
            return null;
        }
        return data.get(0);
    }
}
