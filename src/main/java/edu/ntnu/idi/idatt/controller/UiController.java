package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.game.GameMode;
import edu.ntnu.idi.idatt.view.components.SettingsContent;
import edu.ntnu.idi.idatt.view.layouts.BestieBattlesView;
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

    loveAndLaddersSettings = new SettingsContent(GameMode.LOVE_AND_LADDERS);
    SettingsView loveAndLaddersView = new SettingsView(
        "Slayboard - Love & Ladders",
        this::showHomePage,
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

    bestiePointBattlesSettings = new SettingsContent(GameMode.BESTIE_POINT_BATTLES);
    SettingsView BestieView = new SettingsView(
        "Slayboard - Bestie PointBattles",
        this::showHomePage,
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
    try {
      System.out.println("startGame() called for: " + gameType);

      // Validate inputs
      settingsContent.getPlayerSettingsContainer().validateAllInputs();

      List<String> names = settingsContent.getPlayerNames();
      List<LocalDate> birthdays = settingsContent.getPlayerBirthdays();
      List<String> tokens = settingsContent.getSelectedIcons();
      GameMode gameMode = settingsContent.getSelectedGameMode();

      // Validate player data
      for (int i = 0; i < names.size(); i++) {
        if (names.get(i).isBlank() || birthdays.get(i) == null || tokens.get(i) == null) {
          showAlert("Missing input", "Please fill out all fields correctly.");
          return;
        }
      }

      // Check for duplicate tokens
      Set<String> uniqueTokens = new HashSet<>(tokens);
      if (uniqueTokens.size() < tokens.size()) {
        showAlert("Duplicate tokens", "Each player must choose a unique token.");
        return;
      }

      // Success alert before starting the game
      Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
      successAlert.setTitle("Game ready!");
      successAlert.setContentText("All players are ready. Let's slay! 🎉");
      successAlert.showAndWait();

      // Debug print
      System.out.println("Names: " + names);
      System.out.println("Birthdays: " + birthdays);
      System.out.println("Tokens: " + tokens);
      System.out.println("GameMode: " + gameMode);

      // Create game
      BoardGame game = BoardGameFactory.createGame(gameMode);
      System.out.println("Game created: " + game);
      System.out.println("Board: " + game.getBoard());
      System.out.println("Tiles: " + game.getBoard().getTiles().size());

      // Add players
      for (int i = 0; i < names.size(); i++) {
        Player player = new Player(names.get(i), game, birthdays.get(i));
        player.setToken(tokens.get(i));
        game.addPlayer(player);
      }

      // Sort players by birthday and set first player
      game.getPlayers().sort(Comparator.comparing(Player::getBirthday));
      Player first = game.getPlayers().get(0);

      // Start alert
      Alert startAlert = new Alert(Alert.AlertType.INFORMATION);
      startAlert.setTitle("Game Start");
      startAlert.setContentText(first.getName() + " starts first – they have the earliest birthday 🎂");
      startAlert.showAndWait();

      // Debug scene setup
      System.out.println("Scene set with gameMode: " + gameMode);

      // Switch scenes based on the game mode
      switch (gameMode) {
        case LOVE_AND_LADDERS -> {
          BoardView boardView = new BoardView(9, 10, 2);
          GameController controller = new GameController(game, boardView);
          boardView.setRollOnDice(controller::onRollDice);

          gameScene = new Scene(boardView.getRoot());
        }
        case BESTIE_POINT_BATTLES -> {
          BestieBattlesView bestieView = new BestieBattlesView(game);
          gameScene = new Scene(bestieView);
        }
      }

      // Final setup of the scene
      gameScene.getStylesheets().add(
          Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm()
      );
      stage.setTitle("Slayboard - " + gameType);
      stage.setScene(gameScene);
      stage.sizeToScene();  // Adjust the window size

      // Show the stage
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Error", "An error occurred while starting the game: " + e.getMessage());
    }
  }


  private void showAlert(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
