package example;

import org.apache.bcel.classfile.Method;
import org.objectweb.asm.Type;

import java.lang.invoke.StringConcatFactory;

public class PrintProcTest {
    public static int intVar1;
    public static void main(String[] args) {
        //printX(1);


        char charVar = 999;
        Character charVar2 = 'ϧ'; //ϧ int value = 999
        System.out.println("charVar2 = " + Integer.valueOf(charVar2));
        System.out.println("charVar = " + charVar);

        String methodDescriptor = Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE);
        System.out.println("methodDescriptor = " + methodDescriptor);
        int i = 50;
        intVar1 = 5*i;
        //printX(intVar1);
        // concat (I)
        System.out.println("concate" + intVar1);

        String str = "hhhhhh";
        // catWithConstants", "(Ljava/lang/String;)
        System.out.println(i+"concat" + str + -111*3);
        System.out.println(i*2+"999"+ true);

        String ss = "hhhhhhhhhhhhhhhhhhhhhhh";


        char a = 'a';
        System.out.println("con" + a);

        System.out.println();

    }
    // pass value
    private static void printX(int x, boolean y, char z) {
        int intVar1 = 0;
        boolean boolVar = false;

        int var = 99999;
        //char charVar = (char) var;
        char charVar = 999;

        System.out.println(intVar1);
        System.out.println(x +""+ y + z);
    }
}
