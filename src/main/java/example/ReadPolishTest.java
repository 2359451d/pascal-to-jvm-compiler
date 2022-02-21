package example;

import java.util.Scanner;

public class ReadPolishTest {
    private static int intVar;
    private static boolean boolVar;
    private static double realVar;
    private static char charVar;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        realVar = Double.parseDouble(s);
        System.out.println("realVar = " + realVar);

    }
}
