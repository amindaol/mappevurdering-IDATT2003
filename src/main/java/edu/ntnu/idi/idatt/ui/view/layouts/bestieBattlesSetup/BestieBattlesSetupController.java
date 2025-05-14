package edu.ntnu.idi.idatt.ui.view.layouts.bestieBattlesSetup;

import static java.awt.SystemColor.infoText;

public class BestieBattlesSetupController {

  private final BestieBattlesSetupView view;

  public BestieBattlesSetupView() {
    this.view = new BestieBattlesSetupView();
    setupEventHandlers();
  }

  private void handleNextTurn() {
    game.playOneTurn();
    infoText.setText("Turn played. Check scores!");
    updatePlayerInfo();
  }
}
