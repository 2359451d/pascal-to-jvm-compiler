package example;

import java.util.Scanner;

public class RecursiveFuncTest {
    private static int num, f;

    private static int fact(int x) {
        if (x == 0) {
            return 1;
        } else {
            return x * fact(x - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter a number: ");
        Scanner scanner = new Scanner(System.in);
        num = Integer.parseInt(scanner.nextLine());
        f = fact(num);
        System.out.println("Factorial "+num+" is: "+f);

        System.out.println(fact(10));
    }
}
