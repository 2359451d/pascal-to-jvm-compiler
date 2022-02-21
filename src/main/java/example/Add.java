package example;

import java.util.*;

public class Add {

    public static int first, second, sum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        first= sc.nextInt();
        second = sc.nextInt();

        sum = first + second + first;
        //System.out.println(" sum");
        //sum = ;
        StringBuilder sb = new StringBuilder();
        //System.out.println();
        System.out.println(sb.append("sum is: ").append(sum));

        /**
         * System.out.println(11+21);
         */
        //int i=11, j=21;
        //System.out.println(11+21);
        //
        //System.out.println(11 + first);
        StringBuilder var3 = new StringBuilder();
        System.out.println(var3.append("sum+11 is").append(sum + 11));
        ////System.out.println("sum is: " + String.valueOf(sum) + 1 + "world");
        //System.out.println("hello"+"world");
        int i = 1;
        int j = 2;
        int k = 3;
        int l = 4;
        //System.out.println(i-j); // -11
        System.out.println((i-j)*(k+l)); // -7

        StringBuilder var6 = new StringBuilder();
        System.out.print(var6.append((1 - 2) * (3 + 4)));
    }
}
