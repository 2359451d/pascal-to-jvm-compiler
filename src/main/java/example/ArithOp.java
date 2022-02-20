package example;

public class ArithOp {
    public static int staticVar = 14;
    public static float i = 2.0F;
    static int j = 5;
    static double k = 1.0;
    static int l = 1;

    public static void main(String[] args) {
        //System.out.println(staticVar);
        staticVar = 999;
        //int i = 1;
        //int j = 2;
        //float k = 1.0F;
        //
        //System.out.println(i/j); // int division
        //System.out.println((float) i/j); // real div
        //System.out.println(k/j); //fLOAD

        /**
         * two ints - real division(return real)
         * GETSTATIC,I2F
         * GETSTATIC,I2F
         * FDIV
         */
        System.out.println((float) j/l);
        System.out.println(j/i); // 1 float, 1 int - real division
        System.out.println((float) 1/2);

        /**
         * two ints - integer division(must return int)
         * IDIV (No operands type conversions )
         */
        System.out.println(j/l); // j div l, 5
        System.out.println(1/2); // 1 div 2, 0
        float floatVar;
        floatVar = j / l;
        System.out.println("floatVar = " + floatVar);
    }
}
