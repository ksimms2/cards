package edu.cnm.deepdive;


import edu.cnm.deepdive.Deck.DeckEmptyException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class RecycleWar extends SimpleWar {

  private List<Card> pile1;
  private List<Card> pile2;

  public RecycleWar(Random rng) {

    super(rng);

    pile1 = new ArrayList<>();
    pile2 = new ArrayList<>();

    System.out.printf("Start Game:%n");

    try {
      while (true){
        pile1.add(getDeck().deal());
        pile2.add(getDeck().deal());
      }
    } catch (DeckEmptyException e) {
      System.out.printf("hand1: %d, hand2: %d\n", pile1.size(), pile2.size());
    }
  }

  @Override
  public void play() throws GameOverException {
    List<Card> warPile = new LinkedList<>();

    Card card1 = null;
    Card card2 = null;

    int comparison = 0;
    boolean PLAYERS_TIED = false;
    int winner = 0;
    String winType;

    // Run the code and only re-run the code if there is a tie
    do {

      card1 = null;
      card2 = null;

      // *** Both players try to play a card *** //

      // Player 1 tries to put down a card if they have one
      try {
        card1 = pile1.remove(0);
      } catch (IndexOutOfBoundsException e) {
      }

      // Player 2 tries to put down a card if they have one
      try {
        card2 = pile2.remove(0);
      } catch (IndexOutOfBoundsException e) {
      }



      // *** Check to see if one of the players is out of cards *** //

      if (card2 == null) {
        if (card1 != null) {
          // If Player 2 didn't have a card to play
          // Add Player 1's card to the play pile and add the whole play pile to Player 1's pile
          warPile.add(card1);
          pile1.addAll(warPile);

          System.out.printf("# Player 2 ran out of cards! #%n");

          // If one of the players is out of cards the game is over
          throw new GameOverException();
        }

      } else if (card1 == null) {
        // If Player 1 didn't have a card to play
        // Add Player 2's card to the play pile and add the whole play pile to Player 2's pile
        warPile.add(card2);
        pile2.addAll(warPile);

        System.out.printf("# Player 1 ran out of cards! #%n");

        // If one of the players is out of cards the game is over
        throw new GameOverException();
      }



      // If both players were able to play a card - compare the cards to see if there was a tie or who won

      warPile.add(card1);
      warPile.add(card2);

      comparison = getReferree().compare(card1, card2);


      if (comparison == 0) {
        System.out.printf("P1: %02d (played - %s), P2: %02d (played - %s) -- TIE!%n", pile1.size(), card1, pile2.size(), card2);

        PLAYERS_TIED = true;

        try {
          for (int i = 0; i < 3; i++) {
            card1 = pile1.remove(0);
            warPile.add(card1);

            card2 = pile2.remove(0);
            warPile.add(card2);

            System.out.println("  ?  :   ?");
          }
        } catch (Exception e) {

          System.out.printf("# One of the players ran out of cards during a tie #%n");

          // If one of the players is out of cards the game is over
          throw new GameOverException();
        }
      } else{
        if (comparison > 0) {
          pile1.addAll(warPile);
          warPile.clear();
          winner = 1;
        } else {
          pile2.addAll(warPile);
          warPile.clear();
          winner = 2;
        }

        if(PLAYERS_TIED) {
          winType = "tie";
        } else {
          winType = "round";
        }

        System.out.printf("P1: %02d (played - %s), P2: %02d (played - %s) -- Player %d wins %s%n", pile1.size(), card1, pile2.size(), card2, winner, winType);

        PLAYERS_TIED = false;
      }
    } while (PLAYERS_TIED);
  }

  public static void main(String[] args) {
    RecycleWar war = new RecycleWar(new SecureRandom());

    try {
      // Run until one of the players runs out of cards
      while(true) {
        war.play();
      }
    } catch (GameOverException e) {
      //Do Nothing
    } finally {
      war.setTally1(war.pile1.size());
      war.setTally2(war.pile2.size());

      System.out.printf("- Game Over -%n%n");

      if(war.getTally1() > war.getTally2()){
        System.out.println("Player 1 wins!");
      } else if (war.getTally1() < war.getTally2()){
        System.out.println("Player 2 wins!");
      } else {
        System.out.println("Tie!");
      }
    }
  }

  private static class GameOverException extends RuntimeException {


  }
}
