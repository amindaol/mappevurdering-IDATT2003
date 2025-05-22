package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.route.Router;

/**
 * Controller for the home screen. Connects HomeView with navigation logic.
 * Handles button clicks for starting different game modes.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class HomeController {

  private final HomeView view;

  /**
   * Creates a new HomeController and sets up event handlers for the view.
   */
  public HomeController() {
    this.view = new HomeView();
    setupEventHandlers();
  }

  /**
   * Returns the view associated with this controller.
   *
   * @return the HomeView
   */
  public HomeView getView() {
    return view;
  }

  /**
   * Sets up navigation actions for the game mode buttons in the view.
   */
  private void setupEventHandlers() {
    view.setOnClickLoveAndLaddersButton(() -> Router.navigateTo("lalSettings"));
    view.setOnClickBestieBattlesButton(() -> Router.navigateTo("bbSettings"));
  }

}
