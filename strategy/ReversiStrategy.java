package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * represents a strategy for the game of Reversi.
 */
public interface ReversiStrategy {

  /**
   * chooses a move depending on the strategy or strategies.
   * first computers all possible moves for the given player.
   * then it calls on a filter method that filters the possible moves according to the strategy.
   * decides all tiebreakers by choosing the hex closest to the top left corner.
   * @param model the game
   * @param player the player finding the strategy for
   * @return a point on the board determined by the strategy
   * @throws IllegalStateException if there are no valid moves for the strategy
   */
  CubicPosn chooseMove(ReadOnlyReversiModel model, DiskState player) throws IllegalStateException;

  /**
   * filters a list of a possible moves according to the purpose of the strategy.
   * @param coords the list of possible moves
   * @param player the player its working for
   * @param model the model which the filter reads removing moves.
   */
  void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model);

}