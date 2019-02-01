package edu.cnm.deepdive;

/**
 * This enum implements suits of standard playing cards. Each enumerated value
 * uses the standard symbol for that suit (taken from unicode), as its string
 * representation.
 *
 * @author Kevin Simms &amp; Deep Dive Coding Java + Android Bootcamp cohort 6
 * @version 1.0
 */

public enum Suit {


  CLUBS("\u2663", Color.BLACK),
  DIAMONDS("\u2662", Color.RED),
  HEARTS("\u2661", Color.RED),
  SPADES("\u2660", Color.BLACK);

  private final String symbol;
  private final Color color;

  Suit(String symbol, Color color){

    this.symbol = symbol;
    this.color = color;

  }

  public Color getColor(){
    return color;

  }

  @Override
  public String toString() {
    return symbol;
  }

  public enum Color {
    RED, BLACK;

  }

}

