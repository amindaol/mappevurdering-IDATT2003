package edu.ntnu.idi.idatt.ui.view.components;


import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.config.LadderBoardVariant;
import edu.ntnu.idi.idatt.config.PointBoardVariant;
import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsContent {

  private final VBox root;
  private PlayerSettingsContainer playerSettingsContainer;
  private final GameMode gameMode;

  private ToggleGroup boardGroup;
  private LadderBoardVariant selectedLadderBoard;
  private PointBoardVariant selectedPointBoard;

  public SettingsContent(GameMode gameMode) {
    this.gameMode = gameMode;
    this.boardGroup = new ToggleGroup();
    int maxPlayers = 5;

    root = new VBox();
    root.getStyleClass().add("settings-content");
    root.setSpacing(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));

    Label boardLabel = new Label("Choose a board:");
    boardLabel.getStyleClass().add("settings-content-label");

    RadioButton boardButton1 = new RadioButton("Board 1");
    RadioButton boardButton2 = new RadioButton("Board 2");
    RadioButton boardButton3 = new RadioButton("Board 3");

    boardButton1.setToggleGroup(boardGroup);
    boardButton2.setToggleGroup(boardGroup);
    boardButton3.setToggleGroup(boardGroup);
    boardButton1.setSelected(true);

    boardButton1.getStyleClass().add("settings-content-radio-button");
    boardButton2.getStyleClass().add("settings-content-radio-button");
    boardButton3.getStyleClass().add("settings-content-radio-button");

    boardButton1.setOnAction(e -> {
      if (gameMode == GameMode.LOVE_AND_LADDERS) {
        selectedLadderBoard = LadderBoardVariant.BOARD1;
      } else {
        selectedPointBoard = PointBoardVariant.BOARD1;
      }
    });

    boardButton2.setOnAction(e -> {
      if (gameMode == GameMode.LOVE_AND_LADDERS) {
        selectedLadderBoard = LadderBoardVariant.BOARD2;
      } else {
        selectedPointBoard = PointBoardVariant.BOARD2;
      }
    });

    boardButton3.setOnAction(e -> {
      if (gameMode == GameMode.LOVE_AND_LADDERS) {
        selectedLadderBoard = LadderBoardVariant.BOARD3;
      } else {
        selectedPointBoard = PointBoardVariant.BOARD3;
      }
    });

    HBox boardButtons = new HBox(boardButton1, boardButton2, boardButton3);
    boardButtons.setSpacing(12);
    boardButtons.setAlignment(Pos.CENTER);

    // PLAYER SELECTION
    BorderPane playerSettings = new BorderPane();
    playerSettingsContainer = new PlayerSettingsContainer(2);
    FlowPane playerSettingsPane = (FlowPane) playerSettingsContainer.getAsNode();
    playerSettingsPane.setPrefWrapLength(800);

    ScrollPane scrollPane = new ScrollPane(playerSettingsPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(350);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    playerSettings.setBottom(scrollPane);

    Label playersLabel = new Label("Number of players:");
    playersLabel.getStyleClass().add("settings-content-label");

    HBox playersButtons = new HBox();
    playersButtons.setSpacing(12);
    playersButtons.setAlignment(Pos.CENTER);
    playersButtons.setPadding(new Insets(8, 0, 12, 0));

    ToggleGroup playersGroup = new ToggleGroup();
    for (int i = 2; i <= maxPlayers; i++) {
      RadioButton playerButton = new RadioButton(String.valueOf(i));
      playerButton.setUserData(i);
      if (i == 2) playerButton.setSelected(true);

      playerButton.setToggleGroup(playersGroup);
      playerButton.getStyleClass().add("settings-content-radio-button");

      playerButton.setOnAction(event -> {
        int selectedPlayers = (int) playerButton.getUserData();
        PlayerSettingsContainer newContainer = new PlayerSettingsContainer(selectedPlayers);
        playerSettingsContainer = newContainer;
        playerSettings.setBottom(newContainer.getAsNode());
      });

      playersButtons.getChildren().add(playerButton);
    }

    VBox playerSelectionBox = new VBox(8, playersLabel, playersButtons);
    playerSelectionBox.setAlignment(Pos.CENTER);
    playerSettings.setTop(playerSelectionBox);

    root.getChildren().addAll(boardLabel, boardButtons, playerSettings);
  }

  public Node getRoot() {
    return root;
  }

  public List<String> getPlayerNames() {
    return playerSettingsContainer.getPlayerNames();
  }

  public List<LocalDate> getPlayerBirthdays() {
    return playerSettingsContainer.getPlayerBirthdays();
  }

  public List<String> getSelectedTokens() {
    return playerSettingsContainer.getSelectedTokens();
  }

  public PlayerSettingsContainer getPlayerSettingsContainer() {
    return playerSettingsContainer;
  }

  public GameMode getSelectedGameMode() {
    return gameMode;
  }


  public LadderBoardVariant getSelectedLadderBoardVariant() {
    RadioButton selected = (RadioButton) boardGroup.getSelectedToggle();
    if (selected == null) return LadderBoardVariant.BOARD1;
    return switch (selected.getText()) {
      case "Board 1" -> LadderBoardVariant.BOARD1;
      case "Board 2" -> LadderBoardVariant.BOARD2;
      case "Board 3" -> LadderBoardVariant.BOARD3;
      default -> LadderBoardVariant.BOARD1;
    };
  }


  public PointBoardVariant getSelectedPointBoardVariant() {
    RadioButton selected = (RadioButton) boardGroup.getSelectedToggle();
    if (selected == null) return PointBoardVariant.BOARD1;
    return switch (selected.getText()) {
      case "Board 1" -> PointBoardVariant.BOARD1;
      case "Board 2" -> PointBoardVariant.BOARD2;
      case "Board 3" -> PointBoardVariant.BOARD3;
      default -> PointBoardVariant.BOARD1;
    };
  }




}
