package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * strategy that avoids the hexes adjacent to a corner.
 */
public class AvoidSecondRing extends CaptureMostPieces {

  /**
   * removes all possible moves that are adjacent to a corner.
   * @param coords the list of possible moves
   * @param player the player its working for
   */
  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    int size = model.getBoardSize();
    coords.removeIf(coord -> coord.equals(new CubicPosn(size, -1))
            || coord.equals(new CubicPosn(size - 1, 0))
            || coord.equals(new CubicPosn(size - 1, 1))
            || coord.equals(new CubicPosn(1, size - 1))
            || coord.equals(new CubicPosn(0, size - 1))
            || coord.equals(new CubicPosn(-1, size))
            || coord.equals(new CubicPosn(-size, size - 1))
            || coord.equals(new CubicPosn(-size + 1, size - 1))
            || coord.equals(new CubicPosn(-size + 1, size))
            || coord.equals(new CubicPosn(-size, 1))
            || coord.equals(new CubicPosn(-size + 1, 0))
            || coord.equals(new CubicPosn(-size + 1, -1))
            || coord.equals(new CubicPosn(0, -size + 1))
            || coord.equals(new CubicPosn(-1, -size + 1))
            || coord.equals(new CubicPosn(1, -size))
            || coord.equals(new CubicPosn(size, -size + 1))
            || coord.equals(new CubicPosn(size, -size + 1))
            || coord.equals(new CubicPosn(size - 1, -size))
            || coord.equals(new CubicPosn(size - 1, -size + 1)));
  }
}
