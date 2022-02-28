package example;

public class RepeatTest {
    private static int a;

    public static void main(String[] args) {

        a = 10;
        do {
            a = a + 1;
            System.out.println(a);
        } while (a != 13); // reverse operator of source Pascal program
    }
}
