package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import edu.ntnu.idi.idatt.controller.GameController;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Central UI controller that manages navigation from Home and
 * settings pages into the actual board game view.
 */
public class UiController {

  private final Stage stage;
  private final Scene homeScene;

  public UiController(Stage stage) {
    this.stage = stage;
    stage.setMaximized(true);

    HomeView homeView = new HomeView();
    homeScene = new Scene(homeView.getRoot());
    homeScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    homeView.getLoveAndLaddersButton()
        .setOnAction(e -> showLoveAndLaddersSettings());
    homeView.getBestieBattlesButton()
        .setOnAction(e -> showBestieBattlesSettings());

    showHomePage();
  }

  /**
   * Display the Home scene.
   */
  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
  }

  /**
   * Show settings page for Love & Ladders, then start the game.
   */
  private void showLoveAndLaddersSettings() {
    showSettingsPage("Love & Ladders");
  }

  /**
   * Show settings page for Bestie Battles.
   */
  private void showBestieBattlesSettings() {
    showSettingsPage("Bestie Battles");
  }

  /**
   * Common settings page builder.
   */
  private void showSettingsPage(String gameType) {
    SettingsView settings = new SettingsView(
        gameType,
        this::showHomePage,
        () -> System.out.println("Help clicked for " + gameType),
        createSettingsControls(gameType)
    );

    Scene settingsScene = new Scene(settings.getRoot());
    settingsScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    stage.setTitle("Slayboard - " + gameType + " Settings");
    stage.setScene(settingsScene);
    stage.sizeToScene();
    stage.centerOnScreen();

    stage.setMaximized(true);
    stage.centerOnScreen();
  }

  /**
   * Create the VBox with player name fields and Start button.
   */
  private VBox createSettingsControls(String gameType) {
    Label lbl1 = new Label("Player 1:");
    TextField tf1 = new TextField();
    Label lbl2 = new Label("Player 2:");
    TextField tf2 = new TextField();

    Button startBtn = new Button("Start " + gameType);
    startBtn.getStyleClass().add("nav-button");
    startBtn.setOnAction(e -> startGame(gameType, tf1.getText(), tf2.getText()));

    VBox box = new VBox(10, lbl1, tf1, lbl2, tf2, startBtn);
    box.setAlignment(Pos.CENTER);
    return box;
  }

  /**
   * Initializes the model and switches to the BoardView.
   */
  private void startGame(String gameType, String name1, String name2) {
    BoardGame game = BoardGameFactory.createStandardBoardGame();
    game.addPlayer(new Player(name1, game));
    game.addPlayer(new Player(name2, game));

    BoardView boardView = new BoardView(9, 10); // 90 tiles
    GameController controller = new GameController(game, boardView);

    Button rollBtn = new Button("Roll Dice");
    rollBtn.getStyleClass().add("nav-button");
    rollBtn.setOnAction(evt -> controller.onRollDice());

    VBox root = new VBox(10, boardView.getRoot(), rollBtn);
    root.setAlignment(Pos.CENTER);

    Scene gameScene = new Scene(root);
    gameScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    stage.setTitle("Slayboard - " + gameType);
    stage.setScene(gameScene);
    stage.sizeToScene();
    stage.centerOnScreen();
  }
}
