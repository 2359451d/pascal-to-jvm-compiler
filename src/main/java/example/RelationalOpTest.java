package example;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class RelationalOpTest {
    public static int intVar1, intVar2;
    public static double realVar1;
    public static float realVar2;
    public static boolean boolVar1, boolVar2;
    public static char charVar1, charVar2;
    public static String str1, str2;

    public static void main(String[] args) {
        intVar1 = 1;
        intVar2 = 2;
        boolVar1 = intVar1>=intVar2;

        charVar1 = 'b';
        boolVar1 = 'a'>charVar1;

        realVar1 = 1.0D;
        boolVar1 = realVar1 >= intVar1;

        // ab < a = 1<0 = false
        //int compare = ;
        boolVar1 = StringUtils.compare("ab", String.valueOf('a')) < 0;
        //System.out.println("compare = " + compare);
        System.out.println("boolVar1 = " + boolVar1);
        //boolVar1 = compare;

        //charVar1 = 'a';
        ////'a'<'abc'
        //int compare = String.valueOf('a').compareTo("abc");
        //if (compare >= 0) {
        //    // < not satisfied
        //    System.out.println(false);
        //}else System.out.println(true);

        //System.out.println(StringUtils.compare("a", "abc"));

    }
}
