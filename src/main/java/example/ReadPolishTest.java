package example;

import java.util.Scanner;

public class ReadPolishTest {
    private static int intVar;
    private static boolean boolVar;
    private static double realVar;
    private static char charVar;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        intVar = Integer.parseInt(scanner.next());
        charVar = scanner.next().charAt(0);
        System.out.println("charVar = " + charVar);
        System.out.println("intVar = " + intVar);
        System.out.println("realVar = " + realVar);

    }
}
