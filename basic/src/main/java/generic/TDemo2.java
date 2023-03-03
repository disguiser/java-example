package generic;

import java.util.ArrayList;
import java.util.List;

public class TDemo2 {
    public static void main(String[] args){
        TDemo2 tDemo2 = new TDemo2();

        // 获取String类型
        List<String> array = new ArrayList<>();
        array.add("aaa");
        array.add("bbb");
        String str =  tDemo2.getListFirst(array);
        System.out.println(str);

        // 获取Number类型
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        Integer num = tDemo2.getListFirst(nums);
        System.out.println(num);
    }

    /**
     * <T> T表示返回值T是泛型，T只是一个占位符，用来告诉编译器，这个东西先给我留着，等我编译的时候再告诉你是什么类型。
     * @param data
     * @param <T>
     * @return
     */
    private <T> T getListFirst(List<T> data){
        if(data == null || data.size() == 0){
            return null;
        }
        return data.get(0);
    }
}
