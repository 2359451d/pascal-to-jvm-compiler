package example;

public class MonadicOpTest {
    public static int intVar1,intVar2;
    public static float realVar;

    public static void main(String[] args) {
        intVar1 = 1;
        //-1
        System.out.println(-intVar1);

        intVar2 = 2;

        // 1
        System.out.println(-(intVar1 - intVar2));

        // -6
        System.out.println(-5 + 1 - 2);

        // -5
        System.out.println(-5-(1-1));

        // -7
        System.out.println(-5-1-1);

        // 4
        System.out.println(+5-1);

        // 4
        System.out.println(+5 - (+1));

        System.out.println(-5.0);
        float ii = -5.0F;
        float jj = 1.0F;
        int kk = 1;
        //-3.0
        System.out.println(ii + jj + (+kk));
        System.out.println(-5.0 + 1 + (+1.0));

        //-4.0
        System.out.println(-5.0*1+(1.0*1));
    }
}
