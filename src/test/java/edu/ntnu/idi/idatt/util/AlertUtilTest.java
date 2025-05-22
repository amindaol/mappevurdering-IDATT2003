package edu.ntnu.idi.idatt.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AlertUtilTest {

  @BeforeAll
  static void disableAlerts() {
    AlertUtil.disableAlertsForTesting = true;
  }

  @Test void testShowWarning() {
    AlertUtil.showWarning("test");
  }

  @Test void testShowInfo() {
    AlertUtil.showInfo("title", "test");
  }

  @Test void testShowError() {
    AlertUtil.showError("err", "test");
  }

  @Test void testShowGameHelpLoveAndLadders() {
    AlertUtil.showGameHelp("LoveAndLadders");
  }

  @Test void testShowGameHelpPointBattles() {
    AlertUtil.showGameHelp("PointBattles");
  }

  @Test void testShowGameHelpUnknownMode() {
    AlertUtil.showGameHelp("Nope");
  }

  @Test void testShowHelpDialog() {
    AlertUtil.showHelpDialog();
  }
}
