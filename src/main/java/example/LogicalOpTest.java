package example;

import org.apache.commons.lang3.BooleanUtils;

public class LogicalOpTest {
    public static boolean bool1, falseVar=false, trueVar=true;

    public static void main(String[] args) {
        //bool1 = falseVar && trueVar;
        //System.out.println(bool1);
        //falseVar = false;
        //boolean[] booleans = {true, falseVar};
        //bool1 = BooleanUtils.and(booleans);
        //System.out.println("bool1 = " + bool1);
        bool1 = falseVar & trueVar;
        System.out.println("bool1 = " + bool1);

        System.out.println(false & falseVar);
        System.out.println(true);

        System.out.println(!trueVar);

    }
}
