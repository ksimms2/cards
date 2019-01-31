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

    try {
      while (true){
        pile1.add(getDeck().deal());
        pile2.add(getDeck().deal());

      }
    } catch (DeckEmptyException e) {
      // Do Nothing!
    }
  }

  @Override
  public void play() throws GameOverException {
    List<Card> warPile = new LinkedList<>();
    boolean tied = false;
    do {
      Card card1 = null;
      Card card2 = null;
      try {
        card1 = pile1.remove(0);
      } catch (IndexOutOfBoundsException e) {
        // Do nothing until we see what happens with card2

      }

      try {
        card2 = pile2.remove(0);
      } catch (IndexOutOfBoundsException e) {
      }
      if (card2 == null) {
        if (card1 != null){
          warPile.add(card1);
          pile1.addAll(warPile);
          throw new GameOverException();
        }
      }else if (card1 == null){
        warPile.add(card2);
        pile2.addAll(warPile);
        throw new GameOverException();

      }
      warPile.add(card1);
      warPile.add(card2);
      System.out.printf("%s : %s%n", card1, card2);
      int comparison = getReferree().compare(card1, card2);

      if(comparison == 0){
        tied = true;
        try {
          for (int i = 0; i < 3; i++) {
            card1 = pile1.remove(0);
            warPile.add(card1);
            card2 = pile2.remove(0);
            warPile.add(card2);
          }
        } catch (Exception e) {
          //Do Nothing
        }
      } else if (comparison > 0) {
        pile1.addAll(warPile);
      }else {
        pile2.addAll(warPile);
      }
    } while (tied);
  }

  public static void main(String[] args) {
    RecycleWar war = new RecycleWar(new SecureRandom());
    try {
      while (true){
        war.play();
      }
    } catch (GameOverException e) {
      //Do Nothing
    } finally {
      war.setTally1(war.pile1.size());
      war.setTally2(war.pile2.size());
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
