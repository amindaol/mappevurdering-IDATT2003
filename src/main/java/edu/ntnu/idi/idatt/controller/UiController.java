package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import edu.ntnu.idi.idatt.controller.GameController;
import javafx.scene.control.ChoiceBox;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    Label lblGame = new Label("Select variant:");
    ChoiceBox<String> cbGame = new ChoiceBox<>();
    cbGame.getItems().addAll("Love & Ladders", "Bestie Battles");
    cbGame.getSelectionModel().selectFirst();

    Label lblP1 = new Label("Player 1 Name:");
    TextField tf1 = new TextField("Alice");
    Label lblP2 = new Label("Player 2 Name:");
    TextField tf2 = new TextField("Bob");

    Button startBtn = new Button("Start Game");
    startBtn.setOnAction(e -> {
      try {

        String variant = cbGame.getValue();

        URL boardUrl = getClass().getResource("/boards/" + variant + ".json");
        Path boardJson = Paths.get(boardUrl.toURI());

        Path playersCsv = Paths.get("src/main/resources/players.csv");

        BoardGame game = BoardGameFactory.createGameFromConfig(boardJson, playersCsv);
        showGameScene(game, variant, tf1.getText(), tf2.getText());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });

    VBox box = new VBox(
        12,      // spacing
        lblGame, cbGame,
        lblP1, tf1,
        lblP2, tf2,
        startBtn
    );
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

  private void showGameScene(BoardGame game, String variant, String p1, String p2) {
    BoardView boardView = new BoardView(9, 10);
    GameController ctrl = new GameController(game, boardView);

    Scene gameScene = new Scene(boardView.getRoot());
    gameScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Kunne ikke finne styles.css"
        ).toExternalForm()
    );

    stage.setTitle("Slayboard – " + variant);
    stage.setScene(gameScene);
  }
}
