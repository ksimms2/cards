package edu.cnm.deepdive;

import edu.cnm.deepdive.Deck.DeckEmptyException;
import edu.cnm.deepdive.Suit.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardTrick {

  // are these variable types
  private List<Card> redPile;
  private List<Card> blackPile;
  private Random rng;

  public CardTrick() {
    // assigned variables?
    rng = new Random();
    redPile = new ArrayList<>();
    blackPile = new ArrayList<>();


  }
  // new methods, steps to do
  public Deck prepareDeck(){
    Deck deck = new Deck();
    deck.shuffle(rng);
    return deck;
  }

  public void splitDeck(Deck deck){
    try {
      while (true){
        Card card1 = deck.deal();
        Card card2 = deck.deal();
        // writing with switch to show us, we could use an if
        switch (card1.getSuit().getColor()) {
          case RED:
            redPile.add(card2);
            break;
          case BLACK:
            blackPile.add(card2);
            break;
        }
      }
    } catch (DeckEmptyException e) {
      // Do nothing... with exception.
    }
  }

  public void swapCards(){
    int count = rng.nextInt(Math.min(redPile.size(), blackPile.size()) + 1);
    //int count = rng.nextInt(1 + ((redPile.size() > blackPile.size()) ? blackPile.size() : redPile.size()));
    // ternary operator above
    List<Card> redExtract = new LinkedList<>();
    List<Card> blackExtract = new LinkedList<>();
    System.out.printf("Swapping %d cards between red and black piles.%n", count);
    for (int i = 0; i < count; i++){
      redExtract.add(redPile.remove(0));
      blackExtract.add(blackPile.remove(0));

    }
    System.out.printf("Black extract: %s%n", blackExtract);
    System.out.printf("Red extract: %s%n", redExtract);
    redPile.addAll(blackExtract);
    blackPile.addAll(redExtract);
  }
  public void countAndReport() {
    int redCount = 0;
    int blackCount = 0;
    for (Card c : redPile) {
      if (c.getSuit().getColor() == Color.RED) {
        redCount++;
      }
    }
    for (Card c : blackPile) {
      if (c.getSuit().getColor() == Color.BLACK) {
        blackCount++;
      }
    }
    Collections.sort(redPile);
    Collections.sort(blackPile);
    System.out.printf("Black pile: %s%n", blackPile);
    System.out.printf("Red pile: %s%n", redPile);

    System.out.printf("Black: %d. Red: %d.%n", blackCount, redCount);
  }
  public static void main(String[] args) { //structure of the code. the main method to run the code
    CardTrick trick = new CardTrick(); // there's the instance!
    Deck deck = trick.prepareDeck();
    trick.splitDeck(deck);
    trick.swapCards();
    trick.countAndReport();

  }


}
