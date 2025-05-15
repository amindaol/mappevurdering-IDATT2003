package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.factory.BoardFactory;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.factory.TokenFactory;
import edu.ntnu.idi.idatt.model.engine.BestiePointBattlesEngine;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.engine.LoveAndLaddersEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
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
import edu.ntnu.idi.idatt.util.DialogUtil;
import edu.ntnu.idi.idatt.util.StyleUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    Router.setStage(primaryStage);

    PrimaryScene primaryScene = new PrimaryScene();
    StyleUtil.applyStyleSheet(primaryScene, "/styles/styles.css");
    Router.setScene(primaryScene);

    Router.registerRoute(
        new Route("home", () -> null, () -> new HomeController().getView())
    );

    Router.registerRoute(
        new Route("lalSettings", () -> null, () -> {
          SettingsContent content = new SettingsContent(GameMode.LOVE_AND_LADDERS);

          return new SettingsView(
              "Love & Ladders",
              () -> Router.navigateTo("home"),
              content.getRoot(),
              () -> {
                content.getPlayerSettingsContainer().validateAllInputs();

                List<Player> players = new ArrayList<>();
                boolean hasError = false;

                for (int i = 0; i < content.getPlayerNames().size(); i++) {
                  String name = content.getPlayerNames().get(i);
                  LocalDate birthday = content.getPlayerBirthdays().get(i);
                  String iconName = content.getSelectedIcons().get(i);

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
                  return;
                }

                AppState.setSelectedPlayers(players);
                Router.navigateTo("lalPage");
              }

          );
        })
    );

    Router.registerRoute(
        new Route("bbSettings", () -> null, () -> {
          SettingsContent content = new SettingsContent(GameMode.BESTIE_POINT_BATTLES);

          return new SettingsView(
              "Bestie Point Battles",
              () -> Router.navigateTo("home"),
              content.getRoot(),
              () -> {
                content.getPlayerSettingsContainer().validateAllInputs();

                List<Player> players = new ArrayList<>();
                boolean hasError = false;

                for (int i = 0; i < content.getPlayerNames().size(); i++) {
                  String name = content.getPlayerNames().get(i);
                  LocalDate birthday = content.getPlayerBirthdays().get(i);
                  String iconName = content.getSelectedIcons().get(i);

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
                  return;
                }


                AppState.setSelectedPlayers(players);
                Router.navigateTo("bbPage");
              }
          );
        })
    );

    Router.registerRoute(
        new Route("lalPage", () -> null, () -> {
          List<Player> players = AppState.getSelectedPlayers();
          BoardGame game = BoardGameFactory.createLoveAndLaddersGame(players);
          GameEngine engine = new LoveAndLaddersEngine(game, game.getDice());
          BoardView boardView = new BoardView(9,10,game.getDice().getDiceAmount());
          GameController gameController = new GameController(engine);
          new BoardController(gameController, boardView);
          return boardView.getRoot();
    })
    );

    Router.registerRoute(
        new Route("bbPage", () -> null, () -> {
          List<Player> players = AppState.getSelectedPlayers();
          BoardGame game = BoardGameFactory.createBestiePointBattlesGame(players);
          GameEngine engine = new BestiePointBattlesEngine(game, game.getDice());
          BoardView boardView = new BoardView(9, 10, game.getDice().getDiceAmount());
          GameController gameController = new GameController(engine);
          new BoardController(gameController, boardView);
          return boardView.getRoot();
    })
    );

    Router.navigateTo("home");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
