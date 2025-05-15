package edu.ntnu.idi.idatt.ui;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class PlayerValidator {
  public static boolean isValid(List<String> names, List<LocalDate> birthdays, List<String> tokens) {
    return names.stream().noneMatch(String::isBlank)
        && !birthdays.contains(null)
        && !tokens.contains(null)
        && new HashSet<>(tokens).size() == tokens.size();
  }
}
