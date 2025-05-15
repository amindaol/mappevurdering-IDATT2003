package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.config.GameConfiguration;
import edu.ntnu.idi.idatt.ui.controller.GameController;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.BoardGame;
import edu.ntnu.idi.idatt.ui.controller.GameViewController;
import edu.ntnu.idi.idatt.ui.view.layouts.BoardView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiGameStarter {

  public static void start(GameConfiguration config, Stage stage, int diceAmount) {
    GameEngine engine = config.getGameEngine();
    BoardGame boardGame = config.getBoardGame();

    BoardView boardView = new BoardView(9, 10, diceAmount);
    GameController controller = new GameController(engine);
    GameViewController viewController = new GameViewController(controller, boardView);

    boardGame.addObserver(viewController);
    boardView.setRollOnDice(controller::playTurn);

    Scene gameScene = new Scene(boardView.getRoot());
    gameScene.getStylesheets().add(
        GuiGameStarter.class.getResource("/styles/styles.css").toExternalForm()
    );

    stage.setTitle("Slayboard");
    stage.setScene(gameScene);
    stage.show();
  }

}
