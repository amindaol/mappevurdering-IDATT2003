package edu.ntnu.idi.idatt;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.ntnu.idi.idatt.view.UiController;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    UiController controller = new UiController();
    controller.showHomePage();

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}