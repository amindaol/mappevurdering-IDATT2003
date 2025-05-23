package edu.ntnu.idi.idatt.app;

import edu.ntnu.idi.idatt.config.GameConfiguration;
import edu.ntnu.idi.idatt.config.GameInformation;
import edu.ntnu.idi.idatt.config.GameSetup;
import edu.ntnu.idi.idatt.model.core.GameRegister;
import edu.ntnu.idi.idatt.model.core.GameRegisterInitializer;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.Board;
import edu.ntnu.idi.idatt.model.game.Player;
import edu.ntnu.idi.idatt.model.game.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Terminal based application for playing board game Used for checking game logic and testing.
 *
 * @author Aminda Lunde
 * @author Ingrid Opheim
 * @version 1.0
 */
public class BoardGameApp {

  private static final Logger logger = Logger.getLogger(BoardGameApp.class.getName());

  /**
   * Main method to run the application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    GameRegister register = new GameRegister();
    GameRegisterInitializer.initialize(register);

    logger.info("Welcome to Board Game App!");
    logger.info("Choose a game: ");

    List<GameInformation> gameInfos = register.getAll();
    for (int i = 0; i < gameInfos.size(); i++) {
      logger.log(Level.INFO, "{0}: {1}", new Object[]{i + 1, gameInfos.get(i).getName()});
    }

    int choice;
    while (true) {
      logger.info("Press game number: ");
      try {
        choice = Integer.parseInt(scanner.nextLine()) - 1;
        if (choice >= 0 && choice < gameInfos.size()) {
          break;
        }
      } catch (NumberFormatException e) {
        logger.warning("Invalid input: Please enter a valid number.");
      }
      logger.info("Invalid choice. Try again.");
    }

    GameInformation selectedGame = gameInfos.get(choice);
    logger.info("Chosen: " + selectedGame.getName());
    logger.info(selectedGame.getRules());

    List<Board> boards = selectedGame.getBoardOptions();
    Board board = boards.get(0);

    int maxPlayers = selectedGame.getPlayerMax();
    int minPlayers = selectedGame.getPlayerMin();
    List<Player> players = new ArrayList<>();

    System.out.printf("Register players (%d–%d):%n", minPlayers, maxPlayers);
    while (players.size() < maxPlayers) {
      logger.info("Write name of player " + (players.size() + 1) + " (or ENTER to quit): ");
      String name = scanner.nextLine();
      if (name.isBlank()) {
        if (players.size() >= minPlayers) {
          break;
        } else {
          logger.info("You have to have at least " + minPlayers + " players.");
          continue;
        }
      }

      Player player = new Player(name, new Token("heart", "heart.png"), null);
      players.add(player);
    }

    GameSetup setup = new GameSetup(selectedGame, board, players);
    GameConfiguration config = setup.build();

    GameEngine engine = config.getEngine();

    logger.info("\n🚀 Starting game...\n");

    engine.playGame();

    Player winner = engine.checkWinCondition();
    if (winner != null) {
      logger.info("\n🏆 The winner is: " + winner.getName() + "!");
    } else {
      logger.info("\n❌ No winner found.");
    }

    logger.info("\nThank you for playing!");
  }
}