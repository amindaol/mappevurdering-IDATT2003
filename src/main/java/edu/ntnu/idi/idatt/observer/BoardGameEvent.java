package edu.ntnu.idi.idatt.observer;

/**
 * Enum representing the different events that can occur in a board game. This enum is used to
 * notify observers about specific game events.
 */
public enum BoardGameEvent {
  GAME_START,
  DICE_ROLLED,
  ROUND_PLAYED,
  PLAYER_MOVED,
  LADDER_CLIMBED,
  GAME_WON,
  GAME_ENDED,
}
