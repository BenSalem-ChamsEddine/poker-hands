import java.util.*;

public class PokerGame {

    public static void main(String[] args) {
        testParseHand();
        testRankHand();
        testCompareHands();
    }

    // Test parsing of hands
    static void testParseHand() {
        System.out.println("Testing parseHand:");
        List<Card> expected = List.of(
                new Card(Value.TWO, Suit.HEARTS),
                new Card(Value.THREE, Suit.DIAMONDS),
                new Card(Value.FIVE, Suit.SPADES),
                new Card(Value.NINE, Suit.CLUBS),
                new Card(Value.KING, Suit.DIAMONDS)
        );
        List<Card> actual = parseHand("2H 3D 5S 9C KD");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println();
    }

    // Test ranking of hands
    static void testRankHand() {
        System.out.println("Testing rankHand:");
        Hand hand = new Hand(List.of(
                new Card(Value.TWO, Suit.HEARTS),
                new Card(Value.THREE, Suit.DIAMONDS),
                new Card(Value.FIVE, Suit.SPADES),
                new Card(Value.NINE, Suit.CLUBS),
                new Card(Value.KING, Suit.DIAMONDS)
        ));
        String rank = hand.rankHand();
        System.out.println("Hand: " + hand);
        System.out.println("Rank: " + rank);
        System.out.println();
    }

    // Test comparing of hands
    static void testCompareHands() {
        System.out.println("Testing compareHands:");
        Hand blackHand = new Hand(List.of(
                new Card(Value.TWO, Suit.HEARTS),
                new Card(Value.THREE, Suit.DIAMONDS),
                new Card(Value.FIVE, Suit.SPADES),
                new Card(Value.NINE, Suit.CLUBS),
                new Card(Value.KING, Suit.DIAMONDS)
        ));
        Hand whiteHand = new Hand(List.of(
                new Card(Value.TWO, Suit.CLUBS),
                new Card(Value.THREE, Suit.HEARTS),
                new Card(Value.FOUR, Suit.SPADES),
                new Card(Value.EIGHT, Suit.CLUBS),
                new Card(Value.ACE, Suit.HEARTS)
        ));
        String result = Hand.compareHands(blackHand, whiteHand);
        System.out.println("Black hand: " + blackHand);
        System.out.println("White hand: " + whiteHand);
        System.out.println("Result: " + result);
    }

    // Function to parse a string representing a hand into a list of cards
    static List<Card> parseHand(String handString) {
        String[] cardStrings = handString.split(" ");
        List<Card> cards = new ArrayList<>();
        for (String cardString : cardStrings) {
            Value value = parseValue(cardString.charAt(0));
            Suit suit = parseSuit(cardString.charAt(1));
            cards.add(new Card(value, suit));
        }
        return cards;
    }

    // Function to parse a character representing a card value into the corresponding enum
    static Value parseValue(char valueChar) {
        return switch (valueChar) {
            case '2' -> Value.TWO;
            case '3' -> Value.THREE;
            case '4' -> Value.FOUR;
            case '5' -> Value.FIVE;
            case '6' -> Value.SIX;
            case '7' -> Value.SEVEN;
            case '8' -> Value.EIGHT;
            case '9' -> Value.NINE;
            case 'T' -> Value.TEN;
            case 'J' -> Value.JACK;
            case 'Q' -> Value.QUEEN;
            case 'K' -> Value.KING;
            case 'A' -> Value.ACE;
            default -> null;
        };
    }

    // Function to parse a character representing a card suit into the corresponding enum
    static Suit parseSuit(char suitChar) {
        return switch (suitChar) {
            case 'C' -> Suit.CLUBS;
            case 'D' -> Suit.DIAMONDS;
            case 'H' -> Suit.HEARTS;
            case 'S' -> Suit.SPADES;
            default -> null;
        };
    }
}
