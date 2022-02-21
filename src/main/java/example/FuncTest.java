package example;

public class FuncTest {
    private static int a;
    private static float b;

    private static int addOne(int x) {
        int resultasdhkjwqhriuk=0;
        a = a + 1;
        resultasdhkjwqhriuk = a; //set result addOne:=a
        a = 1;
        return resultasdhkjwqhriuk;
    }

    private static float n;
    public static void main(String[] args) {
        System.out.println(a);
        a = 0;
        n=addOne(99999); // n = 1.0
        System.out.println(n); //1.0
        System.out.println("A->"+a);
    }
}
