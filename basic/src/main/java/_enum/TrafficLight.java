package _enum;

/**
 * Created by zhoum on 2016/5/13.
 */
enum Singnal{
    GREEN,YELLOW,RED
}
public class TrafficLight {
    Singnal color = Singnal.RED;

    public void change(){
        switch (color){
            case RED:
                color = Singnal.GREEN;
                break;
            case YELLOW:
                color = Singnal.RED;
                break;
            case GREEN:
                color = Singnal.YELLOW;
                break;
        }
    }
    public static void main(String[] args) {
        TrafficLight tl = new TrafficLight();
        tl.change();
        System.out.println(tl.color);
    }
}
