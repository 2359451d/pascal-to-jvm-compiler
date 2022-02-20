package example;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class RelationalOpTest {
    public static int intVar1, intVar2;
    public static float realVar1,realVar2;
    public static boolean boolVar1, boolVar2;
    public static char charVar1, charVar2;
    public static String str1, str2;

    public static void main(String[] args) {
        intVar1 = 1;
        intVar2 = 2;

        System.out.println(intVar1<intVar2);
        realVar1 = 9.0F;
        boolVar1 = realVar1 >= intVar1;

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
