package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.components.SettingsContent;
import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.layouts.SettingsView;
import edu.ntnu.idi.idatt.factory.BoardGameFactory;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.view.layouts.BoardView;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        .setOnAction(event -> showBestiePointBattlesPage());

    loveAndLaddersSettings = new SettingsContent(5);
    SettingsView loveAndLaddersView = new SettingsView(
        "Slayboard - Love & Ladders",
        this::showHomePage,
        () -> System.out.println("Help button clicked"),
        loveAndLaddersSettings.getRoot(),
        () -> {
          List<String> names = loveAndLaddersSettings.getPlayerNames();
          List<LocalDate> birthdays = loveAndLaddersSettings.getPlayerBirthdays();
          List<String> tokens = loveAndLaddersSettings.getSelectedTokens();

          boolean missingInput = names.stream().anyMatch(String::isBlank)
              || birthdays.contains(null)
              || tokens.contains(null);

          if (missingInput) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all player names, birthdays, and choose unique tokens.");
            alert.showAndWait();
            return;
          }

          startGame("Love & Ladders", loveAndLaddersSettings);
        }

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
        () -> {
          List<String> names = bestiePointBattlesSettings.getPlayerNames();
          List<LocalDate> birthdays = bestiePointBattlesSettings.getPlayerBirthdays();
          List<String> tokens = bestiePointBattlesSettings.getSelectedTokens();

          boolean missingInput = names.stream().anyMatch(String::isBlank)
              || birthdays.contains(null)
              || tokens.contains(null);

          if (missingInput) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all player names, birthdays, and choose unique tokens.");
            alert.showAndWait();
            return;
          }

          startGame("Bestie PointBattles", bestiePointBattlesSettings);
        }
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
    settingsContent.getPlayerSettingsContainer().validateAllInputs();

    List<String> names = settingsContent.getPlayerNames();
    List<LocalDate> birthdays = settingsContent.getPlayerBirthdays();
    List<String> tokens = settingsContent.getSelectedIcons();

    for (int i = 0; i < names.size(); i++) {
      if (names.get(i).isBlank()) {
        showAlert("Missing player name", "Please enter a name for each player.");
        return;
      }
      if (birthdays.get(i) == null) {
        showAlert("Missing birthday", "Please select a birthday for each player.");
        return;
      }
      if (tokens.get(i) == null) {
        showAlert("Missing token", "Please select a token for each player.");
        return;
      }
    }
    Set<String> uniqueTokens = new HashSet<>(tokens);
    if (uniqueTokens.size() < tokens.size()) {
      showAlert("Duplicate tokens", "Each player must choose a unique token.");
      return;
    }

    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
    successAlert.setTitle("Game ready!");
    successAlert.setHeaderText(null);
    successAlert.setContentText("All players are ready. Let's slay! 🎉");
    successAlert.showAndWait();

    BoardGame game = BoardGameFactory.createStandardBoardGame();

    for (int i=0; i<names.size(); i++) {
      Player player = new Player(names.get(i), game, birthdays.get(i));
      player.setToken(tokens.get(i));
      game.addPlayer(player);
    }

    game.getPlayers().sort(Comparator.comparing(Player::getBirthday));

    Player first = game.getPlayers().get(0);

    Alert startAlert = new Alert(Alert.AlertType.INFORMATION);
    startAlert.setTitle("Game Start");
    startAlert.setHeaderText("Let the games begin!");
    startAlert.setContentText(first.getName() + " starts first – they have the earliest birthday 🎂");
    startAlert.showAndWait();


    BoardView boardView = new BoardView(9, 10, 2);
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

  private void showAlert(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
