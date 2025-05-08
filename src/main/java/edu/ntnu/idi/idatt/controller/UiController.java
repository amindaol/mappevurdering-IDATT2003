package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.components.SettingsContent;
import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Central UI controller that manages navigation from Home and settings pages into the actual board
 * game view.
 */
public class UiController {

  private final Stage stage;
  private final Scene homeScene;
  private final Scene loveAndLaddersScene;
  private Scene gameScene;
  private GameController gameController;

  public UiController(Stage stage) {
    this.stage = stage;

    HomeView homeView = new HomeView();
    homeScene = new Scene(homeView.getRoot());
    homeScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    homeView.getLoveAndLaddersButton()
        .setOnAction(e -> showLoveAndLaddersPage());
    homeView.getBestieBattlesButton()
        .setOnAction(event -> {
          System.out.println("Bestie Battles button clicked");
        });

    SettingsView loveAndLaddersView = new SettingsView(
        "Slayboard - Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        new SettingsContent(5).getRoot(),
        () -> {
          String name1 = "Player 1"; // TODO: Get from input
          String name2 = "Player 2"; // TODO: Get from input
          startGame("Love & Ladders", name1, name2);
        }
    );

    loveAndLaddersScene = new Scene(loveAndLaddersView.getRoot());
    loveAndLaddersScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

  }

  /**
   * Display the Home scene.
   */
  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
    stage.setMaximized(true);
  }


  public void showLoveAndLaddersPage() {
    stage.setTitle("Slayboard - Love & Ladders");
    stage.setScene(loveAndLaddersScene);
    stage.setMaximized(true);
  }

  public void showBestieBattlesPage() {
    // TODO: Implement this method
  }


  /**
   * Initializes the model and switches to the BoardView.
   */
  private void startGame(String gameType, String name1, String name2) {
    // TODO: 2-5 players
    BoardGame game = BoardGameFactory.createStandardBoardGame();
    game.addPlayer(new Player(name1, game));
    game.addPlayer(new Player(name2, game));

    BoardView boardView = new BoardView(9, 10, 2); // 90 tiles
    gameController = new GameController(game, boardView);

    boardView.setRollOnDice(gameController::onRollDice);

    gameScene = new Scene(boardView.getRoot());
    gameScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    stage.setTitle("Slayboard - " + gameType);
    stage.setScene(gameScene);
    stage.sizeToScene();
  }
}
