package edu.ntnu.idi.idatt.factory;

import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import java.time.LocalDate;

public class PlayerFactory {

  public static Player create(String name, String tokenName, LocalDate birthday) {
    return new Player(name, new Token(tokenName, tokenName + ".png"), birthday);
  }
}


