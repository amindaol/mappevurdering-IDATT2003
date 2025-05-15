package edu.ntnu.idi.idatt.ui.controller;

import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import edu.ntnu.idi.idatt.ui.view.layouts.BestieBattlesView;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.ui.view.components.SettingsContent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;

public class GameLauncher {

  private final Stage stage;

  public GameLauncher(Stage stage) {
    this.stage = stage;
  }

  public void launchGame(SettingsContent settingsContent, GameMode gameMode) {
    try {
      List<String> names = settingsContent.getPlayerNames();
      List<LocalDate> birthdays = settingsContent.getPlayerBirthdays();
      List<String> tokens = settingsContent.getSelectedIcons();

      if (hasInvalidInput(names, birthdays, tokens)) {
        showAlert("Missing Input", "Please ensure all names, birthdays, and tokens are filled out.");
        return;
      }

      if (hasDuplicateTokens(tokens)) {
        showAlert("Duplicate Tokens", "Each player must choose a unique token.");
        return;
      }

      BoardGame game = BoardGameFactory.createGame(gameMode);

      for (int i = 0; i < names.size(); i++) {
        String iconName = tokens.get(i);
        Token token = new Token(iconName.replace(".png", ""), iconName);
        Player player = new Player(names.get(i), token, birthdays.get(i));
        game.addPlayer(player);
      }

      game.getPlayers().sort(Comparator.comparing(Player::getBirthday));
      Player first = game.getPlayers().get(0);

      showAlert("Game Start", first.getName() + " goes first – earliest birthday 🎂");

      GameEngine engine = switch (gameMode) {
        case LOVE_AND_LADDERS -> new LoveAndLaddersEngine(game, game.getDice());
        case BESTIE_POINT_BATTLES -> new BestiePointBattlesEngine(game, game.getDice());
      };
      GameController controller = new GameController(engine);

      Scene gameScene = switch (gameMode) {
        case LOVE_AND_LADDERS -> {
          BoardView boardView = new BoardView(9, 10, 2);
          new BoardController(controller, boardView);
          yield new Scene(boardView.getRoot());
        }
        case BESTIE_POINT_BATTLES -> new Scene(new BestieBattlesView(game));
      };

      gameScene.getStylesheets().add(
          Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm()
      );

      stage.setTitle("Slayboard – " + gameMode.name().replace("_", " "));
      stage.setScene(gameScene);
      stage.sizeToScene();
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error", "An error occurred while starting the game: " + e.getMessage());
    }
  }

  private boolean hasInvalidInput(List<String> names, List<LocalDate> birthdays, List<String> tokens) {
    return names.stream().anyMatch(String::isBlank)
        || birthdays.contains(null)
        || tokens.contains(null);
  }

  private boolean hasDuplicateTokens(List<String> tokens) {
    return new HashSet<>(tokens).size() < tokens.size();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
