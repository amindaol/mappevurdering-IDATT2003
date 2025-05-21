package edu.ntnu.idi.idatt.app;

import edu.ntnu.idi.idatt.config.*;
import edu.ntnu.idi.idatt.core.GameRegister;
import edu.ntnu.idi.idatt.core.GameRegisterInitializer;
import edu.ntnu.idi.idatt.model.engine.GameEngine;
import edu.ntnu.idi.idatt.model.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Terminal based application for playing board game Used for checking game logic and testing
 */
public class BoardGameApp {

  /**
   * Main method to run the application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    GameRegister register = new GameRegister();
    GameRegisterInitializer.initialize(register);

    System.out.println("Welcome to Board Game App!");
    System.out.println("Choose a game: ");

    List<GameInformation> gameInfos = register.getAll();
    for (int i = 0; i < gameInfos.size(); i++) {
      System.out.printf("%d: %s\n", i + 1, gameInfos.get(i).getName());
    }

    int choice;
    while (true) {
      System.out.print("Press game number: ");
      try {
        choice = Integer.parseInt(scanner.nextLine()) - 1;
        if (choice >= 0 && choice < gameInfos.size()) {
          break;
        }
      } catch (NumberFormatException ignored) {
      }
      System.out.println("Invalid choice. Try again.");
    }

    GameInformation selectedGame = gameInfos.get(choice);
    System.out.println("Chosen: " + selectedGame.getName());
    System.out.println(selectedGame.getRules());

    List<Board> boards = selectedGame.getBoardOptions();
    Board board = boards.get(0);

    int maxPlayers = selectedGame.getPlayerMax();
    int minPlayers = selectedGame.getPlayerMin();
    List<Player> players = new ArrayList<>();

    System.out.printf("Register players (%d–%d):\n", minPlayers, maxPlayers);
    while (players.size() < maxPlayers) {
      System.out.print("Write name of player " + (players.size() + 1) + " (or ENTER to quit): ");
      String name = scanner.nextLine();
      if (name.isBlank()) {
        if (players.size() >= minPlayers) {
          break;
        } else {
          System.out.println("You have to have at least " + minPlayers + " players.");
          continue;
        }
      }

      Player player = new Player(name, new Token("heart", "heart.png"), null);
      players.add(player);
    }

    GameSetup setup = new GameSetup(selectedGame, board, players);
    GameConfiguration config = setup.build();

    GameEngine engine = config.getGameEngine();

    System.out.println("\n🚀 Starting game...\n");

    engine.playGame();

    Player winner = engine.checkWinCondition();
    if (winner != null) {
      System.out.println("\n🏆 The winner is: " + winner.getName() + "!");
    } else {
      System.out.println("\n❌ No winner found.");
    }

    System.out.println("\nThank you for playing!");
  }
}