/**
 * The Game of patience main class
 * @author Chris Loftus and Lynda Thomas
 * @version 2.0
 */
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import uk.ac.aber.dcs.cs12320.cards.gui.javafx.CardTable;
import java.util.Scanner;


public class Game extends Application {
    private CardTable cardTable;
    @Override
    public void start(Stage stage) {
        cardTable = new CardTable(stage);
        // The interaction with this game is from a command line
        //menu. We need to create a seperate non-GUI thread
        // to run this in. DO NOT REMOVE THIS
        Runnable commandLineTask = () -> {
            //Creates the instance of the Deck
            ArrayList<String> cardStrings = new ArrayList<>();
            Deck aDeck = new Deck(Deck.everySuit, Deck.everyRank);
            //Whilst there are cards in the deck a game will be played
            while (!aDeck.isEmpty()) {
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Show all the cards");
                System.out.println("2. Shuffle the cards");
                System.out.println("3. Deal a card");
                System.out.println("4. Make a move (last pile onto the previous pile");
                System.out.println("5.Make a move (last pile back over two piles");
                System.out.println("6. Combine middle cards");
                System.out.println("7. Print the displayed cards");
                System.out.println("8. Play for me once");
                System.out.println("9. Play for me multiple times");
                System.out.println("10. Display leaderboard");
                System.out.println("11. Quit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    // This case will Print all the cards into the GUI from the folder that the cards are in
                    case 1:
                            String folderpath = "C:/Uni work/Year 1/OOP/Project/patience-template/src/cards";
                            File folder = new File(folderpath);
                            for (File file : folder.listFiles()) {
                                cardStrings.add(file.getName());
                            }
                            Platform.runLater(() -> cardTable.cardDisplay(cardStrings));

                        break;
                    // This case will run the shuffle function on the deck instance
                    case 2:
                        aDeck.shuffle();
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                    // This case will run the deal cards function and update the GUI to show the new hand
                    case 3:
                        aDeck.dealCards();
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                        // This case will check if the last two piles can be combined if they can it will
                        // Remove the second card from the hand array then update the GUI
                    case 4:
                        if (aDeck.combinePiles()) {
                            int length = aDeck.hand.size();
                            aDeck.hand.remove(length - 2);
                        } else {
                            System.out.println("Not possible");
                        }
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                        // This case will check if the pile 2 piles away and the last card can be combined
                        //If they can it will replace the card 2 piles away with the last card and remove the
                        // Other Card from the array and update the GUi
                    case 5:
                        if (aDeck.combinePiles2()) {
                            int length = aDeck.hand.size();
                            Card storage = aDeck.hand.get(length - 1);
                            aDeck.hand.set(length - 4, aDeck.hand.get(length - 1));
                            aDeck.hand.set(length - 4, storage);
                            aDeck.hand.remove(length - 1);
                        } else {
                            System.out.println("Not possible");
                        }
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                        // This ill ask the user for the piles they want to combine it will then
                        // Check that the piles can be combined and will then combine them
                        // It will then update the Gui if possible otherwise it will Print an error
                    case 6:
                        Scanner sc2 = new Scanner(System.in);
                        System.out.println("What is the first pile you would like to select");
                        int pile1 = sc2.nextInt();
                        System.out.println("What is the second pile you would like to select");
                        int pile2 = sc2.nextInt();
                        if (pile1 == pile2 + 1 || pile1 == pile2 - 1 || pile1 == pile2 + 3 || pile1 == pile2 - 3 && pile1 < aDeck.hand.size() && pile2 <= aDeck.hand.size()) {
                            if (aDeck.combinePiles3(pile1, pile2)) {
                                Card storage = aDeck.hand.get(pile2 - 1);
                                aDeck.hand.set(pile1 - 1, aDeck.hand.get(pile2 - 1));
                                aDeck.hand.set(pile1 - 1, storage);
                                aDeck.hand.remove(pile2 - 1);
                            } else {
                                System.out.println("Not possible Piles do not match");
                            }

                        }
                        else {
                            System.out.println("Not possible piles slected arent applicable");
                        }
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                        // This will print all the cards in your hand
                    case 7:
                        System.out.println(aDeck.hand);
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                    // This will check if moves can be made if not it will draw a card otherwise it will
                    //run the function it can or it will ask the user for which option they would like
                    case 8:
                        aDeck.PlayOnce();
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                    // This will ask the user for how many turns they want to do
                    //It will then either run a function or deal a card if multiple
                    //functions can be run it will ask the user for their input
                    case 9:
                        aDeck.PlayMultiple();
                        cardStrings.clear();
                        aDeck.DisplayCards(cardStrings);
                        Platform.runLater(() -> cardTable.cardDisplay(cardStrings));
                        break;
                    // This runs the leaderboard function
                    case 10:
                        Leaderboard.GetLeaderboard();
                        break;
                        //This exits the game
                    case 11:
                        System.exit(0);
                }
                // Tells the user how many cards are left in the deck
                System.out.println("there are " + aDeck.cardsLeft() + " cards left in the deck");
            }
            // This will run at the end of the code to add the users score to the leaderboard
            Scanner sc3 = new Scanner(System.in);
            System.out.println("What is your name?");
            String name = sc3.nextLine();
            Leaderboard.AddName(name);
            int score = aDeck.hand.size();
            Leaderboard.AddScore(score);
            Leaderboard.AddLeaderboard();

        };
        Thread commandLineThread = new Thread(commandLineTask);
        commandLineThread.start();
    }

//Main function This just runs the application
    public static void main (String[] args) {
        Application.launch(args);
    }
};