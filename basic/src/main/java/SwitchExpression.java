/**
 * @author yinhui
 * 2022/6/16 2:26
 */
public class SwitchExpression {
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
    public static void main(String[] args) {
        Day day = Day.FRIDAY;
        int numLetters = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY                -> 7;
            default      -> {
                String s = day.toString();
                int result = s.length();
                yield result;
            }
        };
        int k = 3;
        System.out.println(
                switch (k) {
                    case  1 -> "one";
                    case  2 -> "two";
                    default -> "many";
                }
        );
    }
}
