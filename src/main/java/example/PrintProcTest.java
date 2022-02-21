package example;

public class PrintProcTest {
    public static Integer intVar1=0;

    // pass value
    private static void printX(Integer x, boolean y, char z) {
        System.out.println(x+1);
        System.out.println(y);
        System.out.println(z);
        x = 999;
        System.out.println("x = " + x);
        //int intVar1 = 0;
        //boolean boolVar = false;

        //int var = 99999;
        //char charVar = (char) var;
        //char charVar = 999;

        //System.out.println(intVar1);
        //System.out.println(x +""+ y + z);
    }
    public static void main(String[] args) {
        boolean bool = false;
        char c = 65535;
        char c2 = 32768;
        int cValue = (int) c;
        System.out.println("cValue = " + cValue);
        System.out.println("c = " + c);
        printX(intVar1, bool, c);

        System.out.println("intVar1 = " + intVar1);

        //char charVar = 999;
        //Character charVar2 = 'ϧ'; //ϧ int value = 999
        //System.out.println("charVar2 = " + Integer.valueOf(charVar2));
        //System.out.println("charVar = " + charVar);

        //int i = 50;
        //intVar1 = 5*i;
        ////printX(intVar1);
        //// concat (I)
        //System.out.println("concate" + intVar1);
        //
        //String str = "hhhhhh";
        //// catWithConstants", "(Ljava/lang/String;)
        //System.out.println(i+"concat" + str + -111*3);
        //System.out.println(i*2+"999"+ true);
        //
        //String ss = "hhhhhhhhhhhhhhhhhhhhhhh";
        //
        //
        //char a = 'a';
        //System.out.println("con" + a);
        //
        //System.out.println();

    }

}
