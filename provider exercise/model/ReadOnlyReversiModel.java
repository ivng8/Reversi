package cs3500.reversi.model;

import java.util.HashMap;
import java.util.List;

/**
 * represents an interface that only supports non-mutable, purely observatory actions on a model.
 */
public interface ReadOnlyReversiModel {

  /**
   * gets the size of the board.
   * @return int radius of the board
   */
  int getBoardSize();

  /**
   * gets the state of a point in the board. Not used, but required in assignment 6.
   * @param posn the point
   * @return the DiskState of the given point
   */
  DiskState getState(CubicPosn posn);

  /**
   * determines if a given move is valid.
   * @param posn the point of the move
   * @param color the color of the disk being placed
   * @return a boolean answer of if the given move was valid
   */
  boolean isValidMove(CubicPosn posn, DiskState color);

  /**
   * gets the score of a given player.
   * @param disk represents the player
   * @return the player's score as an int
   */
  int getScore(DiskState disk);

  /**
   * checks if the given player as a valid move to make.
   * @param state represents the player
   * @return a boolean of if the player has a valid move to make
   */
  boolean anyValidMoves(DiskState state);

  /**
   * checks if the game is over (either two consecutive passes or no valid moves).
   * @return a boolean of if the game is over
   */
  boolean isGameOver();

  /**
   * encapsulated method that gives a map representation of the board state in a game.
   * @return a hashmap that represents the game state
   */
  HashMap<CubicPosn, DiskState> getBoard();

  /**
   * checks if it is the current player's turn.
   * @param diskState the player
   * @return a boolean on if it is the player's turn
   */
  boolean isCurrentPlayer(DiskState diskState);

  /**
   * Finds all the valid seams from the given pos, color.
   * @param posn the position of the new tile
   * @param color the color of that tile
   * @return a list of seams (see previous method for definition of seam)
   */
  List<List<CubicPosn>> findAllSeams(CubicPosn posn, DiskState color);
}
