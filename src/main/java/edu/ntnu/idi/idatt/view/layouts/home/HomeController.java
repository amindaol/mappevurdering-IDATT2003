package edu.ntnu.idi.idatt.view.layouts.home;

import edu.ntnu.idi.idatt.core.Router;

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
