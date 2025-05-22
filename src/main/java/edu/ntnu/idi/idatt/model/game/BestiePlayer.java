package edu.ntnu.idi.idatt.model.game;

import java.time.LocalDate;

/**
 * Represents a player in the BestieBattles game. This class extends the Player class and adds
 * functionality specific to the BestieBattles game. It includes methods for managing coins and
 * stars.
 */
public class BestiePlayer extends Player {

  private int coins;
  private int stars;

  /**
   * Constructs a BestiePlayer with the specified name, token, and birth date.
   *
   * @param name      the name of the player
   * @param token     the token representing the player
   * @param birthDate the birth date of the player
   */
  public BestiePlayer(String name, Token token, LocalDate birthDate) {
    super(name, token, birthDate);
    this.coins = 0;
    this.stars = 0;
  }

  /**
   * Returns the number of coins the player has.
   *
   * @return the number of coins
   */
  public int getCoins() {
    return coins;
  }

  /**
   * Adds the specified amount of coins to the player's total.
   *
   * @param amount the amount of coins to add
   */
  public void addCoins(int amount) {
    this.coins += amount;
  }

  /**
   * Removes the specified amount of coins from the player's total. If the player has fewer coins
   * than the specified amount, sets coins to zero.
   *
   * @param amount the amount of coins to remove
   */
  public void removeCoins(int amount) {
    if (this.coins >= amount) {
      this.coins -= amount;
    } else {
      this.coins = 0;
    }
  }

  /**
   * Returns the number of stars the player has.
   *
   * @return the number of stars
   */
  public int getStars() {
    return stars;
  }

  /**
   * Adds a star to the player's total.
   */
  public void addStar() {
    this.stars++;
  }

  /**
   * Removes a star from the player's total. If the player has no stars, does nothing.
   */
  public void removeStar() {
    if (this.stars > 0) {
      this.stars--;
    }
  }
}
