package cs3500.reversi.model;

import java.util.HashMap;

import cs3500.reversi.features.ModelEvent;

/**
 * Represents primary model for game of reversi that includes game logic and mutable actions.
 */
public interface ReversiModel extends ReadOnlyReversiModel, ModelEvent {

  /**
   * Starts a new game of Reversi provided that it is given a valid size.
   *
   * @throws IllegalArgumentException if the size is invalid
   * @throws IllegalStateException    if the game has already started
   */
  void startGame();

  /**
   * getter for the HashMap of the board.
   *
   * @return a HashMap of the board that encapsulates the board
   * @throws IllegalStateException if the game hasn't started
   */
  HashMap<CubicPosn, DiskState> getBoard();

  /**
   * attempts to place tile in the board.
   *
   * @param pos position attempted
   * @throws IllegalStateException    if the game hasn't started, the
   *                                  coordinate on the board is already taken by a tile,
   *                                  or it's not a valid move
   * @throws IllegalArgumentException if the tile being place is an empty tile,
   *                                  or the coordinate doesn't in the board
   */
  void placeTile(CubicPosn pos);

  /**
   * skips turn of the current player.
   */
  void skipTurn();

  /**
   * assigns the turn to the controller.
   * @return a representation of the player
   */
  DiskState assignDisk();
}