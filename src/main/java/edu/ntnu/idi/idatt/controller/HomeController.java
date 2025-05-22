package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.view.layouts.HomeView;
import edu.ntnu.idi.idatt.view.route.Router;

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
    view.setOnClickLoveAndLaddersButton(() -> Router.navigateTo("lalSettings"));
    view.setOnClickBestieBattlesButton(() -> Router.navigateTo("bbSettings"));
  }

}
