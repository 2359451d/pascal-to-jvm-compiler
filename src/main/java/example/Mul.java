package example;

public class Mul {
    static int intVar;
    static float realVar;

    public static void main(String[] args) {
        int i = 1;
        int j = 2;
        intVar = i * j;
        System.out.println("intVar = " + intVar);
        // 2

        float k = 1.0F;
        int l = 2;
        realVar = k * l;
        System.out.println("realVar = " + realVar);
        // 2.0

        realVar = i*j;
        System.out.println("realVar = " + realVar);
        // 2.0


        //WriteLn(1*(2*3.0));
        int one = 1;
        int two = 2;
        float three = 3;
        System.out.println("one*(two*three) = " + one*(two*three));

    }
}
