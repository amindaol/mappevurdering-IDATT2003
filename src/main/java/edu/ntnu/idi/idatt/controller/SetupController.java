package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.config.GameMode;
import edu.ntnu.idi.idatt.view.components.SettingsContent;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class SetupController {

  public boolean hasValidInput(SettingsContent settingsContent) {
    List<String> names = settingsContent.getPlayerNames();
    List<LocalDate> birthdays = settingsContent.getPlayerBirthdays();
    List<String> tokens = settingsContent.getSelectedTokens();

    boolean missing = names.stream().anyMatch(String::isBlank)
        || birthdays.contains(null)
        || tokens.contains(null);

    boolean duplicates = new HashSet<>(tokens).size() < tokens.size();

    return !missing && !duplicates;
  }

  public void printSummary(SettingsContent settingsContent, GameMode gameMode) {
    System.out.println("Game setup complete for: " + gameMode);
    System.out.println("Names: " + settingsContent.getPlayerNames());
    System.out.println("Birthdays: " + settingsContent.getPlayerBirthdays());
    System.out.println("Tokens: " + settingsContent.getSelectedTokens());
  }

}
