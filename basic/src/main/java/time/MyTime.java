package time;

import java.time.LocalDate;

public class MyTime {
    public static void main(String[] args) {
        var localDate1 = LocalDate.now();
        var localDate2 = LocalDate.of(2020, 1, 1);
        System.out.println(localDate1.isAfter(localDate2));
    }
}
