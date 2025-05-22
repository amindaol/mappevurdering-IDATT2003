package edu.ntnu.idi.idatt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.factory.TokenFactory;
import edu.ntnu.idi.idatt.io.reader.BoardFileReaderGson;
import edu.ntnu.idi.idatt.io.reader.PlayerFileReaderCsv;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.Dice;
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
import edu.ntnu.idi.idatt.util.StyleUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
                () -> AlertUtil.showHelpDialog())
        ));

    // Settings for Bestie Point Battles
    Router.registerRoute(
        new Route("bbSettings",
            () -> {
              SettingsContent content = new SettingsContent(GameMode.BESTIE_POINT_BATTLES);
              return new SettingsView(content.getRoot(), () -> {
                if (!validateAndStartGame(content)) {
                  return;
                }
                Router.navigateTo("bbPage");
              });
            },
            () -> new NavBar("Bestie Point Battles",
                () -> Router.navigateTo("home"),
                () -> AlertUtil.showHelpDialog())
        ));

    // Play Love & Ladders from JSON+CSV
    Router.registerRoute(
        new Route("lalPage",
            () -> {
              try {
                // 1) pick up the JSON filename the user selected
                String boardFile = AppState.getSelectedBoardFile();        // guaranteed non-null now
                URL boardRes = Main.class.getResource("/boards/" + boardFile);
                if (boardRes == null) {
                  throw new IllegalArgumentException("Missing board file: " + boardFile);
                }

                JsonObject root;
                try (InputStream in = boardRes.openStream()) {
                  Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                  root = JsonParser.parseReader(reader).getAsJsonObject();
                }

                Board board = new BoardFileReaderGson().parseBoard(root);

                // 3) pick up the players list (assuming you already put it in AppState
                //    via your validateAndStartGame logic, possibly using PlayerFileReaderCsv)
                List<Player> players = AppState.getSelectedPlayers();
                if (players == null || players.isEmpty()) {
                  throw new IllegalStateException("No players configured");
                }

                // 4) build the game
                Dice dice = new Dice(2);
                BoardGame game = new BoardGame(board, dice);
                players.forEach(game::addPlayer);

                // 5) wire it up
                GameEngine engine = new LoveAndLaddersEngine(game, dice);
                BoardView view = new BoardView(board.getRows(), board.getCols(),
                    dice.getDiceAmount());
                view.drawBoard(board);
                view.initializePlayerList(players);
                GameController gc = new GameController(engine);
                new BoardController(gc, view);
                engine.startGame();

                return view.getRoot();

              } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to start Love & Ladders", e);
                AlertUtil.showError("Load Error",
                    "Could not start Love & Ladders:\n" + e.getMessage());
                return new HomeController().getView();
              }
            },
            () -> new NavBar("Love & Ladders",
                () -> Router.navigateTo("home"),
                () -> AlertUtil.showGameHelp("LoveAndLadders"))
        ));

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
                () -> AlertUtil.showGameHelp("BestiePointBattles"))
        ));

    // Kick things off
    Router.navigateTo("home");
    primaryStage.show();
    logger.info("UI shown – ready for interaction");
  }

  private static boolean validateAndStartGame(SettingsContent content) {
    List<Player> players;
    int count = content.getSelectedPlayers();

    if (AppState.isLoadPlayersFromFile()) {
      try {
        URL playersUrl = Main.class.getClassLoader()
            .getResource("players/premadePlayers.csv");
        if (playersUrl == null) {
          throw new
              IllegalArgumentException("Missing players file: premadePlayers.csv");
        }
        Path csvPath = Paths.get(playersUrl.toURI());
        List<Player> all = new PlayerFileReaderCsv().readPlayers(csvPath);

        if (all.size() < count) {
          new Alert(Alert.AlertType.ERROR,
              "Not enough players in the CSV file. Please select fewer players.")
              .showAndWait();
          return false;
        }
        players = new ArrayList<>(all.subList(0, count));
      } catch (Exception e) {
        new Alert(AlertType.ERROR,
            "Failed to load players from CSV: " + e.getMessage()).showAndWait();
        return false;
      }

    } else {
      content.getPlayerSettingsContainer().validateAllInputs();

      players = new ArrayList<>();
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
    }
    AppState.setSelectedPlayers(players);
    return true;

  }

}
