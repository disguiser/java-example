package _enum;

public enum Color {
    RED("红色", 1),
    GREEN("绿色", 2),
    BLACK("黑色", 3),
    YELLOW("黄色", 4);

    private String name;
    private int index;

    Color(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        Color[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Color c = arr$[i$];
            if (c.getIndex() == index) {
                return c.name;
            }
        }

        return null;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println(RED.toString());
    }

    public String toString() {
        return this.index + "_" + this.name;
    }
}
