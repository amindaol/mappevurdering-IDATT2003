package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.components.SettingsContent;
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
        .setOnAction(event -> {
          System.out.println("Bestie Battles button clicked");
        });

    SettingsView loveAndLaddersView = new SettingsView(
        "Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        new SettingsContent(4).getRoot()
    );

    loveAndLaddersScene = new Scene(loveAndLaddersView.getRoot());
    loveAndLaddersScene.getStylesheets().add(
        Objects.requireNonNull(
                getClass().getResource("/css/styles.css"),
                "Could not find /css/loveAndLadders.css in the classpath"
            )
            .toExternalForm()
    );

  }

  /**
   * Display the Home scene.
   */
  public void showHomePage() {
    stage.setTitle("Slayboard - Home");
    stage.setMaximized(true);
    stage.setScene(homeScene);
  }


  public void showLoveAndLaddersPage() {
    stage.setTitle("Slayboard - Love & Ladders");
    stage.setMaximized(true);
    stage.setScene(loveAndLaddersScene);

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
