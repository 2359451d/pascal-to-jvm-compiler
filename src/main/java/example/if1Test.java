package example;

public class if1Test {
    private static int intvar;
    public static void main(String[] args) {
        intvar = 9;
        double d1 = 10;
        double d2 = 10;


        System.out.println(intvar > 10);

        boolean b = intvar >= 10;
        if (b) {
            System.out.println("greater than 10");
        } else {
            System.out.println("<10");
        }

    }
}
