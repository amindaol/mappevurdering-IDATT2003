package edu.ntnu.idi.idatt.view.components;

import edu.ntnu.idi.idatt.view.AppState;
import edu.ntnu.idi.idatt.config.GameMode;
import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * A GUI component that displays all game setup options, including: - Board selection - Number of
 * players - Player name, birthday, and token inputs - Option to load players from file
 * <p>
 * This component is used during the game setup phase before the game starts. It dynamically updates
 * player fields based on selected number of players.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class SettingsContent {

  private final VBox root;
  private PlayerSettingsContainer playerSettingsContainer;
  private final GameMode gameMode;

  /**
   * Creates a new settings content view for the given game mode.
   *
   */
  public SettingsContent(GameMode gameMode) {
    int maxPlayers = 5;
    root = new VBox();
    root.getStyleClass().add("settings-content");
    root.setSpacing(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));

    this.gameMode = gameMode;

    Label boardLabel = new Label("Choose a board:");
    boardLabel.getStyleClass().add("settings-content-label");

    RadioButton boardButton1 = new RadioButton("Board 1");
    RadioButton boardButton2 = new RadioButton("Board 2");
    RadioButton boardButton3 = new RadioButton("Board 3");

    ToggleGroup boardGroup = new ToggleGroup();
    boardButton1.setToggleGroup(boardGroup);
    boardButton2.setToggleGroup(boardGroup);
    boardButton3.setToggleGroup(boardGroup);

    boardButton1.getStyleClass().add("settings-content-radio-button");
    boardButton2.getStyleClass().add("settings-content-radio-button");
    boardButton3.getStyleClass().add("settings-content-radio-button");

    if (gameMode == GameMode.LOVE_AND_LADDERS) {
      boardButton1.setUserData("ladderBoard1.json");
      boardButton2.setUserData("ladderBoard2.json");
      boardButton3.setUserData("ladderBoard3.json");
    } else if (gameMode == GameMode.BESTIE_POINT_BATTLES) {
      boardButton1.setUserData("bestieBoard1.json");
      boardButton2.setUserData("bestieBoard2.json");
      boardButton3.setUserData("bestieBoard3.json");
    } else {
      throw new IllegalArgumentException("Unsupported game mode: " + gameMode);
    }

    boardGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        AppState.setSelectedBoardFile(newValue.getUserData().toString());
      }
    });

    boardButton1.setSelected(true);
    AppState.setSelectedBoardFile(boardButton1.getUserData().toString());

    HBox boardButtons = new HBox(boardButton1, boardButton2, boardButton3);
    boardButtons.setSpacing(12);
    boardButtons.setAlignment(Pos.CENTER);

    BorderPane playerSettings = new BorderPane();
    playerSettingsContainer = new PlayerSettingsContainer(2);
    FlowPane playerSettingsPane = (FlowPane) playerSettingsContainer.getAsNode();
    playerSettingsPane.setPrefWrapLength(800);

    ScrollPane scrollPane = new ScrollPane(playerSettingsPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(350);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    playerSettings.setBottom(scrollPane);

    HBox playersButtons = new HBox();
    playersButtons.setSpacing(12);
    playersButtons.setAlignment(Pos.CENTER);
    playersButtons.setPadding(new Insets(8, 0, 12, 0));

    CheckBox csvCheck = new CheckBox("Load players from file");
    csvCheck.getStyleClass().add("settings-content-checkbox");
    csvCheck.setOnAction(e -> {
      boolean useCsv = csvCheck.isSelected();
      AppState.setLoadPlayersFromFile(useCsv);
      playerSettingsContainer.getAsNode().setDisable(useCsv);
    });

    Label playersLabel = new Label("Number of players:");
    playersLabel.getStyleClass().add("settings-content-label");

    playersLabel.setAlignment(Pos.CENTER);

    VBox playerSelectionBox = new VBox(8, playersLabel, playersButtons);
    playerSelectionBox.setAlignment(Pos.CENTER);

    ToggleGroup playersGroup = new ToggleGroup();
    for (int i = 2; i <= maxPlayers; i++) {
      RadioButton playerButton = new RadioButton(String.valueOf(i));
      playerButton.setUserData(i);
      if (i == 2) {
        playerButton.setSelected(true);
      }
      playersButtons.getChildren().add(playerButton);
      playerButton.setToggleGroup(playersGroup);
      playerButton.getStyleClass().add("settings-content-radio-button");

      playerButton.setOnAction(event -> {
        int selectedPlayers = (int) playerButton.getUserData();
        PlayerSettingsContainer newplayerSettingsContainer =
            new PlayerSettingsContainer(selectedPlayers);
        playerSettingsContainer = newplayerSettingsContainer;
        playerSettings.setBottom(newplayerSettingsContainer.getAsNode());

      });

    }

    playersButtons.getChildren().add(csvCheck);
    playersButtons.setAlignment(Pos.CENTER);

    playerSettings.setTop(playerSelectionBox);
    playersButtons.setSpacing(8);

    root.getChildren().addAll(boardLabel, boardButtons, playerSettings);
  }

  /**
   * Returns the root node containing all layout elements.
   *
   * @return the root node for this settings view
   */
  public Node getRoot() {
    return root;
  }

  /**
   * Returns the list of player names entered in the settings form.
   *
   * @return list of player names
   */
  public List<String> getPlayerNames() {
    return playerSettingsContainer.getPlayerNames();
  }

  /**
   * Returns the list of player birthdays entered in the settings form.
   *
   * @return list of player birthdays
   */
  public List<LocalDate> getPlayerBirthdays() {
    return playerSettingsContainer.getPlayerBirthdays();
  }

  /**
   * Returns the list of selected tokens per player.
   *
   * @return list of token image file names
   */
  public List<String> getSelectedTokens() {
    return playerSettingsContainer.getSelectedTokens();
  }

  /**
   * Returns the container managing player-specific settings.
   *
   * @return the PlayerSettingsContainer instance
   */
  public PlayerSettingsContainer getPlayerSettingsContainer() {
    return playerSettingsContainer;
  }

  /**
   * Returns the number of selected players based on the current state.
   *
   * @return selected number of players
   */
  public int getSelectedPlayers() {
    return getSelectedTokens().size();
  }
}
