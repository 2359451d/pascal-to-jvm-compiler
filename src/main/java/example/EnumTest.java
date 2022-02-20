package example;

import java.util.Arrays;

public class EnumTest {
    // private static enumerated class
    // defined in type definition
    private enum CardSuit{
        Clubs,
        Diamonds,
        HEARTS,
        SPADES
        ;
        // default constructors
    }

    // turn on capitalise no matter the identifier of source code
    private enum CardSuit2{
        Clubs2,
        Diamonds2,
        HEARTS2,
        SPADES2
        ;
        // default constructors
    }

    public static void main(String[] args) {
        //CardSuit[] values = CardSuit.values();
        //Arrays.stream(values).forEach(each -> System.out.println(each.name() + " " + each.ordinal()));
        CardSuit card = CardSuit.Clubs; //card:= Clubs;
        System.out.println(card.ordinal()); //Write(ord(card));

        CardSuit2 cardSuit2 = CardSuit2.Clubs2;// cardsuit2:= clubs2;
        System.out.println(cardSuit2.ordinal()); // Write(ord(cardsuit2));
    }

}
