package example;

public class ModTest {

    public static int intVar1,intVar2;
    public static float realVar;

    public static void main(String[] args) {
        intVar1 = 10;
        intVar2 = 2;
        //0
        System.out.println(intVar1 % intVar2);

        //2
        System.out.println(8 % 3);

        realVar = 8 % 3;
        //2.0
        System.out.println(realVar);
    }

}
