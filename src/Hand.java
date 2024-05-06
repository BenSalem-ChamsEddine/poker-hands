import java.util.*;

public record Hand(List<Card> cards) {

    // Function to rank the hand
    public String rankHand() {
        // Sort the cards by value
        List<Card> sortedList = new ArrayList<>(cards);
        sortedList.sort(Comparator.comparing(Card::value));

        // Check for different hand rankings
        if (isStraightFlush()) {
            return "Straight Flush";
        } else if (isFourOfAKind()) {
            return "Four of a Kind";
        } else if (isFullHouse()) {
            return "Full House";
        } else if (isFlush()) {
            return "Flush";
        } else if (isStraight()) {
            return "Straight";
        } else if (isThreeOfAKind()) {
            return "Three of a Kind";
        } else if (isTwoPairs()) {
            return "Two Pairs";
        } else if (isPair()) {
            return "Pair";
        } else {
            return "High Card";
        }
    }

    // Helper method to check if the hand is a straight flush
    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    // Helper method to check if the hand is a four of a kind
    private boolean isFourOfAKind() {
        Map<Value, Integer> valueCount = getValueCountMap();
        for (int count : valueCount.values()) {
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if the hand is a full house
    private boolean isFullHouse() {
        Map<Value, Integer> valueCount = getValueCountMap();
        boolean threeOfAKind = false;
        boolean pair = false;
        for (int count : valueCount.values()) {
            if (count == 3) {
                threeOfAKind = true;
            } else if (count == 2) {
                pair = true;
            }
        }
        return threeOfAKind && pair;
    }

    // Helper method to check if the hand is a flush
    private boolean isFlush() {
        Suit suit = cards.get(0).suit();
        for (Card card : cards) {
            if (card.suit() != suit) {
                return false;
            }
        }
        return true;
    }

    // Helper method to check if the hand is a straight
    private boolean isStraight() {
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).value().ordinal() != cards.get(i - 1).value().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    // Helper method to check if the hand is three of a kind
    private boolean isThreeOfAKind() {
        Map<Value, Integer> valueCount = getValueCountMap();
        for (int count : valueCount.values()) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if the hand is two pairs
    private boolean isTwoPairs() {
        Map<Value, Integer> valueCount = getValueCountMap();
        int pairCount = 0;
        for (int count : valueCount.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    // Helper method to check if the hand is a pair
    private boolean isPair() {
        Map<Value, Integer> valueCount = getValueCountMap();
        for (int count : valueCount.values()) {
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    // Helper method to count the occurrences of each value in the hand
    private Map<Value, Integer> getValueCountMap() {
        Map<Value, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            Value value = card.value();
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }
        return valueCount;
    }

    // Function to compare two hands and determine the winner
    public static String compareHands(Hand blackHand, Hand whiteHand) {
        String blackRank = blackHand.rankHand();
        String whiteRank = whiteHand.rankHand();

        // Compare ranks
        int rankComparison = rankComparison(blackRank, whiteRank);
        if (rankComparison != 0) {
            // Hands have different ranks
            return rankComparison > 0 ? "Black wins." : "White wins.";
        } else {
            // Same rank, compare values
            return compareValues(blackHand, whiteHand);
        }
    }

    // Function to compare the rankings of two hands
    private static int rankComparison(String blackRank, String whiteRank) {
        return switch (blackRank.compareTo(whiteRank)) {
            case 1 -> 1;
            case -1 -> -1;
            default -> 0;
        };
    }

    // Function to compare the values of two hands
    private static String compareValues(Hand blackHand, Hand whiteHand) {
        List<Card> blackCards = new ArrayList<>(blackHand.cards);
        List<Card> whiteCards = new ArrayList<>(whiteHand.cards);
        Collections.sort(blackCards, Comparator.comparing(card -> card.value()));
        Collections.sort(whiteCards, Comparator.comparing(card -> card.value()));

        // Compare cards from highest to lowest
        for (int i = blackCards.size() - 1; i >= 0; i--) {
            int comparison = blackCards.get(i).value().compareTo(whiteCards.get(i).value());
            if (comparison != 0) {
                return comparison > 0 ? "Black wins." : "White wins.";
            }
        }

        // If all cards are equal, it's a tie
        return "Tie.";
    }

    // Function to parse a string representing a hand into a list of cards
    public static List<Card> parseHand(String handString) {
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
    private static Value parseValue(char valueChar) {
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
    private static Suit parseSuit(char suitChar) {
        return switch (suitChar) {
            case 'C' -> Suit.CLUBS;
            case 'D' -> Suit.DIAMONDS;
            case 'H' -> Suit.HEARTS;
            case 'S' -> Suit.SPADES;
            default -> null;
        };
    }
}
