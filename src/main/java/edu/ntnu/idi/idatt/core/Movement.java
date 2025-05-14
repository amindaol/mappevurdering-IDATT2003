package edu.ntnu.idi.idatt.core;

import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;

public interface Movement {

  void move(Player player, int steps);

}
