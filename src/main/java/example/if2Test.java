package example;

public class if2Test {
    private static int a;

    public static void main(String[] args) {
        a = 100;
        boolean b = a == 10;
        boolean c = a == 20;
        boolean d = a == 30;


        boolean flag = false;
        if (flag==true) {
            System.out.println("a is 10");
        } else if (a==20) {
            System.out.println("a is 20");
        } else if (a==30) {
            System.out.println("a is 30");
        }

        //else {
        //    System.out.println("None of the values is matching");
        //}
        System.out.println("Exact value of a is: " + a);

    }
}
