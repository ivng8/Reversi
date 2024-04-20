package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.reversi.features.ModelEvent;

/**
 * Represents primary model for game of reversi that includes game logic and mutable actions.
 */
public interface ReversiModel extends ReadOnlyReversiModel, ModelEvent {

  /**
   * Starts a new game of Reversi provided that it is given a valid size.
   *
   * @param radius radius of the board
   * @throws IllegalArgumentException if the size is invalid
   * @throws IllegalStateException    if the game has already started
   */
  void startGame(int radius);

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
   * Finds the valid seam from the given pos, color, dimension it's parsing, and direction.
   * Let it be known that a seam is a list of tiles that needs to be flipped after a move.
   *
   * @param pos       the position of the new tile to be placed
   * @param color     the color of that tile
   * @param dim       the x, y, or z dimension it's parsing
   * @param direction the direction of that dimension that it's parsing (negative or positive)
   * @return a list of coordinates that represents a seam, will return empty if not valid
   */
  ArrayList<CubicPosn> findSeam(CubicPosn pos, DiskState color, String dim, int direction);

  DiskState assignDisk();
}