package edu.ntnu.idi.idatt.controller;

import static edu.ntnu.idi.idatt.observer.BoardGameEvent.GAME_WON;
import static edu.ntnu.idi.idatt.observer.BoardGameEvent.PLAYER_MOVED;

import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BestiePlayer;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Tile;
import edu.ntnu.idi.idatt.observer.BoardGameEvent;
import edu.ntnu.idi.idatt.observer.BoardGameObserver;
import edu.ntnu.idi.idatt.view.components.PlayerIcon;
import edu.ntnu.idi.idatt.view.layouts.BestieBattlesView;
import java.util.List;
import javafx.scene.image.Image;

public class BestieBattlesController implements BoardGameObserver {

  private final BestiePointBattlesEngine engine;
  private final BestieBattlesView view;

  public BestieBattlesController(BestiePointBattlesEngine engine, BestieBattlesView view) {
    this.engine = engine;
    this.view = view;

    engine.addObserver(this);

    initializePlayers();
    setUpRollButton();
  }

  public void onGameStateChange(BoardGame game, BoardGameEvent event) {
    switch (event) {
      case DICE_ROLLED:
        // Handle dice rolled event;
        break;
      case PLAYER_MOVED:
        // Handle player moved event;
        break;
      case GAME_WON:
        // Handle game won event;
        break;
      default:
        break;
    }
  }

  private void initializePlayers() {
    for (Player player : engine.getPlayers()) {
      if (player instanceof BestiePlayer bp) {
        Tile startTile = bp.getCurrentTile();
        String iconPath = "/icons/players/" + bp.getToken().getIconFileName();
        Image icon = new Image(getClass().getResourceAsStream(iconPath));

        PlayerIcon playerIcon = new PlayerIcon(bp.getName(), icon);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);

        view.placePlayerIcon(bp.getName(), playerIcon, startTile.getTileId());
      }
    }

    view.updateCurrentPlayerList(engine.getCurrentPlayer());
    view.updateSidePanel(engine.getPlayers());
  }

  private void setUpRollButton() {
    view.setRollCallBack(() -> {
      BestiePlayer mover = (BestiePlayer) engine.getCurrentPlayer();

      List<Integer> roll = engine.getDice().roll();

      System.out.println(mover.getName() + " rolled " + roll + " landed on "
          + mover.getCurrentTile().getTileId());

      //view.showDiceRoll(roll);

      engine.handleTurn(engine.getDice().getRollValue());

      view.updateSidePanel(engine.getPlayers());
      view.updateCurrentPlayerList(engine.getCurrentPlayer());

    });
  }
}
