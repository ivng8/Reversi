package cs3500.reversi.player;

import cs3500.reversi.controller.ReversiController;

/**
 * The {@code Player} interface represents a player in a Reversi game. It defines the
 * methods necessary for interacting with the game's controller, allowing for both
 * automated and human-driven moves.
 *
 * <p>Implementations of this interface can represent different types of players in the game,
 * such as human players who make moves based on user input, or automated players (bots)
 * that calculate their own moves. The {@link ReversiController} interacts with {@code Player}
 * objects to dispatch game actions, depending on the type of player and the current state
 * of the game.
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>{@link #makeMove()}: For automated players, this method triggers the calculation
 *       and execution of a move. For human players, this method does not have any effect,
 *       as their moves are driven by user input.</li>
 *   <li>{@link #placePiece()}: For human players, this method is invoked by the controller
 *       in response to user input received through the game's view. It represents the action
 *       of placing a piece on the board at a chosen location.</li>
 *   <li>{@link #addControllerListener(ReversiController)}: Registers a {@link ReversiController}
 *       with the player, allowing the player to notify the controller of its actions, such as
 *       making a move or passing the turn.</li>
 * </ul>
 */
public interface Player {

  /**
   * makes automated move for a player (no reaction for human player).
   */
  void makeMove();

  /**
   * makes a move through an observer (for human players).
   */
  void placePiece();

  /**
   * adds a controller listener to a player that acts as an observer for commands.
   * @param cont the listener
   */
  void addControllerListener(ReversiController cont);
}