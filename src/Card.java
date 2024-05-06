public record Card(Value value, Suit suit) {
    // Override toString method to display card value and suit
    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

