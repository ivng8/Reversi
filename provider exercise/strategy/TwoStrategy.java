package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * represents a combination of two or more strategies (tree-based combination).
 */
public class TwoStrategy extends CaptureMostPieces {

  private final ReversiStrategy strat1;

  private final ReversiStrategy strat2;

  /**
   * constructor for a combination of strategy.
   * @param strat1 a strategy or combination of strategies
   * @param strat2 a strategy or combination of strategies
   */
  public TwoStrategy(ReversiStrategy strat1, ReversiStrategy strat2) {
    this.strat1 = strat1;
    this.strat2 = strat2;
  }

  /**
   * filters a list of possible coords by using of all filters from its strategies.
   * @param coords the list of possible moves
   * @param player the player its working for
   */
  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    this.strat1.filter(coords, player, model);
    this.strat2.filter(coords, player, model);
  }
}
