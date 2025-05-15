package edu.ntnu.idi.idatt.ui.view.layouts;

import edu.ntnu.idi.idatt.ui.route.Router;

public class HomeController {

  private final HomeView view;

  public HomeController() {
    this.view = new HomeView();
    setupEventHandlers();
  }

  public HomeView getView() {
    return view;
  }

  private void setupEventHandlers() {
    view.setOnClickLoveAndLaddersButton(() -> Router.navigateTo("lalPage"));
    view.setOnClickBestieBattlesButton(() -> Router.navigateTo("bbPage"));
  }

}
