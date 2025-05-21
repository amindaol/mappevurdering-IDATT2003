package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.factory.TokenFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
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
import edu.ntnu.idi.idatt.util.AlertUtil;
import edu.ntnu.idi.idatt.util.DialogUtil;
import edu.ntnu.idi.idatt.util.StyleUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    // Set root logger level (console handler is already attached by default)
    Logger root = Logger.getLogger("");
    root.setLevel(Level.INFO);

    logger.info("Launching application");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    logger.info("start() called – initializing application");

    Router.setStage(primaryStage);
    PrimaryScene primaryScene = new PrimaryScene();
    StyleUtil.applyStyleSheet(primaryScene, "/styles/styles.css");
    Router.setScene(primaryScene);

    // Home route
    Router.registerRoute(
        new Route("home",
            () -> new HomeController().getView(),
            () -> null
        )
    );

    // Settings for Love & Ladders
    Router.registerRoute(
        new Route("lalSettings",
            () -> {
              SettingsContent content = new SettingsContent(GameMode.LOVE_AND_LADDERS);
              return new SettingsView(content.getRoot(), () -> {
                if (!validateAndStartGame(content)) {
                  return;
                }
                Router.navigateTo("lalPage");
              });
            },
            () -> new NavBar("Love & Ladders",
                () -> Router.navigateTo("home"),
                Main::showHelpDialog)
        )
    );

    // Play Love & Ladders from JSON+CSV
    Router.registerRoute(
        new Route("lalPage",
            () -> {
              try {

                String boardFile = AppState.getSelectedBoardFile();
                URL boardUrl = Main.class.getClassLoader()
                    .getResource("boards/" + boardFile);

                URL playersUrl = Main.class.getClassLoader()
                    .getResource("players/love_and_ladders.csv");

                if (boardUrl == null) {
                  throw new
                      IllegalArgumentException("Board or players resource not found");
                }

                Path boardJson = Paths.get(boardUrl.toURI());
                Path playersCsv = Paths.get(playersUrl.toURI());

                BoardGame game = BoardGameFactory.createFromFiles(boardJson, playersCsv);

                GameEngine engine = new LoveAndLaddersEngine(game, game.getDice());
                BoardView boardView = new BoardView(9, 10, 2);
                GameController gc = new GameController(engine);
                new BoardController(gc, boardView);
                engine.startGame();

                return boardView.getRoot();
              } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to load Love & Ladders files", e);
                AlertUtil.showError("Load Error",
                    "Could not start Love & Ladders:\n" + e.getMessage());
                return new HomeController().getView();
              }
            },
            () -> new NavBar("Love & Ladders",
                () -> Router.navigateTo("home"),
                Main::showHelpDialog)
        )
    );

    // Play Bestie Point Battles from JSON+CSV
    Router.registerRoute(
        new Route("bbPage",
            () -> {
              try {
                Path boardJson = Paths.get(
                    getClass().getResource("/boards/bestie_point_battles.json").toURI());
                Path playersCsv = Paths.get(
                    getClass().getResource("/players/bestie_point_battles.csv").toURI());
                BoardGame game = BoardGameFactory.createFromFiles(boardJson, playersCsv);

                GameEngine engine = new BestiePointBattlesEngine(game, game.getDice());
                BoardView boardView = new BoardView(9, 10, 2);
                GameController gc = new GameController(engine);
                new BoardController(gc, boardView);
                engine.startGame();

                return boardView.getRoot();
              } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to load Bestie files", e);
                AlertUtil.showError("Load Error",
                    "Could not start Bestie Point Battles:\n" + e.getMessage());
                return new HomeController().getView();
              }
            },
            () -> new NavBar("Bestie Point Battles",
                () -> Router.navigateTo("home"),
                Main::showHelpDialog)
        )
    );

    // Kick things off
    Router.navigateTo("home");
    primaryStage.show();
    logger.info("UI shown – ready for interaction");
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

  private static void showHelpDialog() {
    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setTitle("Help");
    a.setHeaderText("How to set up the game");
    a.setContentText("""
        1. Choose a board.
        2. Select number of players.
        3. Fill in name and birthday for each.
        4. Choose a unique token per player.
        5. Click 'Start game'!
        """);
    a.showAndWait();
  }
}
