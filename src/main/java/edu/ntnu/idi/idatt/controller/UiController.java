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
  private final Scene loveAndLaddersScene;

  public UiController(Stage stage) {
    this.stage = stage;

    stage.setMaximized(true);

    HomeView homeView = new HomeView();
    homeScene = new Scene(homeView.getRoot());
    homeScene.getStylesheets().add(
        Objects.requireNonNull(
                getClass().getResource("/css/styles.css"),
                "Could not find /css/home.css in the classpath"
            )
            .toExternalForm()
    );

    homeView.getLoveAndLaddersButton()
        .setOnAction(event -> showLoveAndLaddersPage());
    homeView.getBestieBattlesButton()
        .setOnAction(event -> {
          System.out.println("Bestie Battles button clicked");
        });

    SettingsView loveAndLaddersView = new SettingsView(
        "Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        new Label("Love & Ladders content")
    );

    loveAndLaddersScene = new Scene(loveAndLaddersView.getRoot());
    // TODO: Add .css styling to the loveAndLaddersScene

  }

  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setScene(homeScene);
  }

  public void showLoveAndLaddersPage() {
    SettingsView settings = new SettingsView(
        "Bestie Battles",
        this::showHomePage,
        () -> System.out.println("Help clicked"),
        new Label("Bestie Battles coming soon!")
    );
    Scene settingsScene = new Scene(settings.getRoot());
    settingsScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );
    stage.setTitle("Slayboard - Bestie Battles Settings");
    stage.setScene(settingsScene);
  }

  public void showBestieBattlesPage() {
    // TODO: Implement this method
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

    BoardView boardView = new BoardView(5, 6);
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
  }

}
