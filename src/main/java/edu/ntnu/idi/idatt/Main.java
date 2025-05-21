package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.config.LadderBoardVariant;
import edu.ntnu.idi.idatt.config.PointBoardVariant;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.factory.TokenFactory;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import edu.ntnu.idi.idatt.ui.controller.BoardController;
import edu.ntnu.idi.idatt.ui.controller.GameController;
import edu.ntnu.idi.idatt.ui.route.PrimaryScene;
import edu.ntnu.idi.idatt.ui.route.Route;
import edu.ntnu.idi.idatt.ui.route.Router;
import edu.ntnu.idi.idatt.ui.view.AppState;
import edu.ntnu.idi.idatt.ui.view.components.NavBar;
import edu.ntnu.idi.idatt.ui.view.components.SettingsContent;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import edu.ntnu.idi.idatt.ui.view.layouts.HomeController;
import edu.ntnu.idi.idatt.ui.view.layouts.setup.SettingsView;
import edu.ntnu.idi.idatt.util.StyleUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    Router.setStage(primaryStage);

    PrimaryScene primaryScene = new PrimaryScene();
    StyleUtil.applyStyleSheet(primaryScene, "/styles/styles.css");
    Router.setScene(primaryScene);

    Router.registerRoute(
        new Route(
            "home",
            () -> new HomeController().getView(),
            () -> null)
    );

    Router.registerRoute(
        new Route("lalSettings",
            () -> {
              SettingsContent content = new SettingsContent(GameMode.LOVE_AND_LADDERS);
              return new SettingsView(content.getRoot(), () -> {
                if (!validateAndStartGame(content)) return;
                AppState.setSelectedLadderBoard(content.getSelectedLadderBoardVariant());
                Router.navigateTo("lalPage");
              });
            },
            () -> new NavBar("Love & Ladders", () -> Router.navigateTo("home"), Main::showHelpDialog)
        ));

    Router.registerRoute(
        new Route("bbSettings",
            () -> {
              SettingsContent content = new SettingsContent(GameMode.BESTIE_POINT_BATTLES);
              return new SettingsView(content.getRoot(), () -> {
                if (!validateAndStartGame(content)) return;
                AppState.setSelectedPointBoard(content.getSelectedPointBoardVariant());
                Router.navigateTo("bbPage");
              });
            },
            () -> new NavBar("Bestie Point Battles", () -> Router.navigateTo("home"), Main::showHelpDialog)
        ));

    Router.registerRoute(
        new Route(
            "lalPage",
          () -> {
          List<Player> players = AppState.getSelectedPlayers();
          LadderBoardVariant variant = AppState.getSelectedLadderBoard();
          BoardGame game = BoardGameFactory.createLoveAndLaddersGame(players, variant);
            return startGameView(game, new LoveAndLaddersEngine(game, game.getDice()));
    }, () -> new NavBar("Love & Ladders", () -> Router.navigateTo("home"), Main::showHelpDialog)
    ));

    Router.registerRoute(
        new Route("bbPage",
            () -> {
          List<Player> players = AppState.getSelectedPlayers();
              PointBoardVariant variant = AppState.getSelectedPointBoard();
          BoardGame game = BoardGameFactory.createBestiePointBattlesGame(players, variant);
              return startGameView(game, new BestiePointBattlesEngine(game, game.getDice()));
    },
            () -> new NavBar("Bestie Point Battles", () -> Router.navigateTo("home"), Main::showHelpDialog)
        ));

    Router.navigateTo("home");
    primaryStage.show();
  }

  private static boolean validateAndStartGame(SettingsContent content) {
    content.getPlayerSettingsContainer().validateAllInputs();

    List<Player> players = new ArrayList<>();
    boolean hasError = false;

    for (int i = 0; i < content.getPlayerNames().size(); i++) {
      String name = content.getPlayerNames().get(i);
      LocalDate birthday = content.getPlayerBirthdays().get(i);
      String iconName = content.getSelectedTokens().get(i);

      if (name.isBlank() || birthday == null || iconName == null) {
        hasError = true;
        break;
      }

      Token token = TokenFactory.fromIcon(iconName);
      players.add(new Player(name, token, birthday));
    }

    if (hasError) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Missing Information");
      alert.setHeaderText("Some player fields are incomplete");
      alert.setContentText("Make sure each player has a name, birthday, and selected token.");
      alert.showAndWait();
      return false;
    }

    AppState.setSelectedPlayers(players);
    return true;
  }

  private static Parent startGameView(BoardGame game, GameEngine engine) {
    BoardView boardView = new BoardView(9, 10, game.getDice().getDiceAmount());
    GameController gameController = new GameController(engine);
    new BoardController(gameController, boardView);
    return boardView.getRoot();
  }


  private static void showHelpDialog() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Help");
    alert.setHeaderText("How to set up the game");
    alert.setContentText("""
        1. Choose a board.
        2. Select number of players.
        3. Fill in name and birthday for each.
        4. Choose a unique token per player.
        5. Click 'Start game'!
        """);
    alert.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

