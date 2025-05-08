package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.components.SettingsContent;
import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import java.time.LocalDate;
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
  private final Scene bestiePointBattlesScene;
  private Scene gameScene;
  private SettingsContent loveAndLaddersSettings;
  private SettingsContent bestiePointBattlesSettings;

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
        .setOnAction(event -> showBestiePointBattlesPage());

    loveAndLaddersSettings = new SettingsContent(5);
    SettingsView loveAndLaddersView = new SettingsView(
        "Slayboard - Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        loveAndLaddersSettings.getRoot(),
        () -> startGame("Love & Ladders", loveAndLaddersSettings)
    );


    loveAndLaddersScene = new Scene(loveAndLaddersView.getRoot());
    loveAndLaddersScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "Could not find /css/styles.css"
        ).toExternalForm()
    );

    bestiePointBattlesSettings = new SettingsContent(5);
    SettingsView BestieView = new SettingsView(
        "Slayboard - Bestie PointBattles",
        this::showHomePage,
        () -> System.out.println("Help button clicked. "),
        bestiePointBattlesSettings.getRoot(),
        () -> startGame("Bestie PointBattles", bestiePointBattlesSettings)
    );

    bestiePointBattlesScene = new Scene(BestieView.getRoot());
    bestiePointBattlesScene.getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/css/styles.css"),
            "could not find /css/styles.css"
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

  public void showBestiePointBattlesPage() {
    stage.setTitle("Slayboard - Bestie PointBattles");
    stage.setScene(bestiePointBattlesScene);
    stage.setMaximized(true);
  }


  /**
   * Initializes the model and switches to the BoardView.
   */
  private void startGame(String gameType, SettingsContent settingsContent) {
    BoardGame game = BoardGameFactory.createStandardBoardGame();

    var names = settingsContent.getPlayerNames();
    var birthdays = settingsContent.getPlayerBirthdays();

    for (int i=0; i<names.size(); i++) {
      game.addPlayer(new Player(names.get(i),game, birthdays.get(i)));
    }

    BoardView boardView = new BoardView(9, 10);
    GameController controller = new GameController(game, boardView);
    boardView.setRollOnDice(controller::onRollDice);

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
