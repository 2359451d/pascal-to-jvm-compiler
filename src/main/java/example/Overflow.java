package example;

public class Overflow {
    private static int i;

    public static void main(String[] args) {
        int j = 1;
        i = Integer.MAX_VALUE+j;
    }
}
