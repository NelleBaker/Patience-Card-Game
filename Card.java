/**This is my Class for making a Card
 @author Nelle Baker
 */

public class Card {
    private final String rank;
    private final String suit;

    // Creating the Card class
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    //Function to get rank
    public String getRank() {
        return rank;
    }

    //Function to get suit
    public String getSuit() {
        return suit;
    }

    // This will print out rank and suit when called
    public String toString() {
        return rank + " of " + suit;
    }


    //This will check if the cards can go on each other
    public boolean placeable(Card otherCard) {
        boolean sameSuit = this.getSuit().equals(otherCard.suit);
        boolean sameRank = this.getRank().equals(otherCard.rank);
        return sameSuit || sameRank;
    }
}

