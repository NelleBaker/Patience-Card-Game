/** This is my Class for creating a deck and the Deck logic
This also contains the majority of my functions on what to do
With the hand and deck
@author Nelle Baker
**/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
    // Defining all the Ranks the deck could contain
    public static final String[] everyRank = {
            "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Jack", "Queen", "King", "Ace"
    };

    // Defining all the Suits the deck could contain
    public static final String[] everySuit = {
            "Clubs", "Spades", "Hearts", "Diamonds"
    };

    // Defining my Card and Hand arrays
    private final ArrayList<Card> cards = new ArrayList<>();
    public final ArrayList<Card> hand = new ArrayList<>();

    // Creating Deck
    public Deck(String[] suits, String[] ranks) {
        for (String rank : ranks) {
            for (String suit : suits) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Check if empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Cards remaining
    public int cardsLeft() {
        return cards.size();
    }

    // Show what the deck contains
    @Override
    public String toString() {
        StringBuilder adeck = new StringBuilder("Deck contains:\n");
        for (Card c : cards) {
            adeck.append(c.toString()).append("\n");
        }
        return adeck.toString();
    }


    //shuffle Deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //Deal out cards
    public void dealCards() {
        hand.add(cards.remove(0));
    }

    //Check if you can Combine piles
    public boolean combinePiles() {
        int length = hand.size();
        Card lastCard = hand.get(length - 1);
        Card otherCard = hand.get(length - 2);
        return lastCard.placeable(otherCard);

    }

    //Check if you can Combine a pile two rows back
    public boolean combinePiles2() {
        int length = hand.size();
        Card lastCard = hand.get(length - 1);
        Card otherCard = hand.get(length - 4);
        return lastCard.placeable(otherCard);

    }
    //Check if you can Combine piles in the middle
    public boolean combinePiles3(int pile1, int pile2) {
        Card lastCard = hand.get(pile1 - 1);
        Card otherCard = hand.get(pile2 - 1);
        return lastCard.placeable(otherCard);
    }

    // Function for making a move for you
    public void PlayOnce() {
        int plays = 0;
        int pile1 = 1;
        int pile2 = 2;
        boolean complete = false;
        boolean runable1 = false;
        boolean runable2 = false;
        boolean runable3 = false;
        int length = hand.size();
        if (hand.size() >= 4) {
            //If the last two piles can be combined runable1 is updated
            if (combinePiles()) {
                plays = plays + 1;
                runable1 = true;
            }
            //If the pile 2 piles away from the last pile can be combined runable2 is updated
            if (combinePiles2()) {
                plays = plays + 1;
                runable2 = true;
            }
            //This goes through every card in the hand and checks if any of the piles can be combined in any way
            //If yes runable 3 is updated
            while (!complete) {
                if (pile1 == pile2 + 1 || pile1 == pile2 - 1 || pile1 == pile2 + 3 || pile1 == pile2 - 3 && pile1 <= hand.size() && pile2 <= hand.size()) {
                    if (combinePiles3(pile1, pile2)) {
                        plays = plays + 1;
                        complete = true;
                        runable3 = true;
                    } else {
                        if (pile1 < length) {
                            pile1 = pile1 + 1;
                            pile2 = pile2 + 1;
                        } else {
                            complete = true;
                            runable3 = false;
                        }
                    }

                } else {
                    break;
                }
            }


            //This checks how many plays can be made and if multiple can be it will ask the user
            //for their input
            if (plays > 1) {
                System.out.println("Multiple moves can be made");
                if (runable1) {
                    System.out.println("1. I can combine the last piles");
                }
                if (runable2) {
                    System.out.println("2. I can combine the second from last piles");
                }
                if (runable3) {
                    System.out.println("3. I can combine piles in the middle");
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number option for what you would like me to do");
                int option = sc.nextInt();
                switch (option) {
                    // Gets the input from the user and then does the task user selected
                    case 1:
                        if (combinePiles()) {
                            length = hand.size();
                            hand.remove(length - 2);
                            length = hand.size();
                        } else {
                            System.out.println("You chose an Invalid option");
                        }
                        break;
                    case 2:
                        if (combinePiles2()) {
                            length = hand.size();
                            Card storage = hand.get(length - 1);
                            hand.set(length - 4, hand.get(length - 1));
                            hand.set(length - 4, storage);
                            hand.remove(length - 1);
                            length = hand.size();
                        } else {
                            System.out.println("You chose an Invalid option");
                        }
                        break;

                    case 3:
                        if (combinePiles3(pile1, pile2)) {
                            length = hand.size();
                            Card storage = hand.get(pile2 - 1);
                            hand.set(pile1 - 1, hand.get(pile2 - 1));
                            hand.set(pile1 - 1, storage);
                            hand.remove(pile2 - 1);
                            length = hand.size();
                        } else {
                            System.out.println("You chose an Invalid option");
                        }

                }
            }
            // THis automatically runs the command if multiple moves cannot be made
            else if (plays == 1) {
                if (combinePiles()){
                if (runable1) {
                    length = hand.size();
                    hand.remove(length - 2);
                    length = hand.size();
                    System.out.println("Last 2 cards were combined");
                }
                }
                if (runable2) {
                    if (combinePiles2()){
                        length = hand.size();
                        Card storage = hand.get(length - 1);
                        hand.set(length - 1, hand.get(length - 1));
                        hand.set(length - 1, storage);
                        hand.remove(length - 2);
                        length = hand.size();
                        System.out.println("Last card was combined with a card 2 piles behind");
                }}
                if (runable3) {
                    if (combinePiles3(pile1, pile2)){
                        length = hand.size();
                        Card storage = hand.get(pile2 - 1);
                        hand.set(pile1 - 1, hand.get(pile2 - 1));
                        hand.set(pile1 - 1, storage);
                        hand.remove(pile2 - 1);
                        length = hand.size();
                        System.out.println("Piles in the middle were combined");
                    }
                }
                // Deals you a card if no moves can be made
                else {
                    System.out.println("You have been played a Card");
                    dealCards();
                    return;
                }
            }
        }
        else {
            dealCards();
            System.out.println("You have been played a Card");
        }
    }


        //Function to make multiple moves for user
        public void PlayMultiple (){
            Scanner sc4 = new Scanner(System.in);
            System.out.println("How many turns would you like me to play");
            int turns = sc4.nextInt();
            for (int i = 0; i < turns; i++) {
                if(hand.size()>=4) {
                    PlayOnce();
                }
                else{
                    dealCards();
                    System.out.println("You have been played a Card");
                }
            }
            turns = 0;
        }
        
    //Code to display hand on the screen GUI
    public void DisplayCards(ArrayList<String> cardStrings) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Ace")) {
                cardStrings.add("ah.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Ace")) {
                cardStrings.add("ac.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Ace")) {
                cardStrings.add("ad.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Ace")) {
                cardStrings.add("as.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Two")) {
                cardStrings.add("2h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Two")) {
                cardStrings.add("2c.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Two")) {
                cardStrings.add("2d.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Two")) {
                cardStrings.add("2s.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Three")) {
                cardStrings.add("3h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Three")) {
                cardStrings.add("3c.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Three")) {
                cardStrings.add("3d.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Three")) {
                cardStrings.add("3s.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Four")) {
                cardStrings.add("4h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Four")) {
                cardStrings.add("4c.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Four")) {
                cardStrings.add("4d.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Four")) {
                cardStrings.add("4s.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Five")) {
                cardStrings.add("5h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Five")) {
                cardStrings.add("5c.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Five")) {
                cardStrings.add("5d.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Five")) {
                cardStrings.add("5s.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Six")) {
                cardStrings.add("6h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Six")) {
                cardStrings.add("6c.gif");
            } else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Six")) {
                cardStrings.add("6d.gif");
            } else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Six")) {
                cardStrings.add("6s.gif");
            } else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Seven")) {
                cardStrings.add("7h.gif");
            } else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Seven")) {
                cardStrings.add("7c.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Seven")) {
                cardStrings.add("7d.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Seven")) {
                cardStrings.add("7s.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Eight")) {
                cardStrings.add("8h.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Eight")) {
                cardStrings.add("8c.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Eight")) {
                cardStrings.add("8d.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Eight")) {
                cardStrings.add("8s.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Nine")) {
                cardStrings.add("9h.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Nine")) {
                cardStrings.add("9c.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Nine")) {
                cardStrings.add("9d.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Nine")) {
                cardStrings.add("9s.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Ten")) {
                cardStrings.add("th.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Ten")) {
                cardStrings.add("tc.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Ten")) {
                cardStrings.add("td.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Ten")) {
                cardStrings.add("ts.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Jack")) {
                cardStrings.add("jh.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Jack")) {
                cardStrings.add("jc.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Jack")) {
                cardStrings.add("jd.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Jack")) {
                cardStrings.add("js.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("Queen")) {
                cardStrings.add("qh.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("Queen")) {
                cardStrings.add("qc.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("Queen")) {
                cardStrings.add("qd.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("Queen")) {
                cardStrings.add("qs.gif");
            }
            else if (hand.get(i).getSuit().equals("Hearts") && hand.get(i).getRank().equals("King")) {
                cardStrings.add("kh.gif");
            }
            else if (hand.get(i).getSuit().equals("Clubs") && hand.get(i).getRank().equals("King")) {
                cardStrings.add("kc.gif");
            }
            else if (hand.get(i).getSuit().equals("Diamonds") && hand.get(i).getRank().equals("King")) {
                cardStrings.add("kd.gif");
            }
            else if (hand.get(i).getSuit().equals("Spades") && hand.get(i).getRank().equals("King")) {
                cardStrings.add("ks.gif");
            }
        }
    }
}