package cs3500.reversi.strategy.square;

import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Follows the same pattern of the previous strategies for hexagonal Reversi by extending the most
 * basic strategy and changing the computation for filtering out tiles adjacent to corners.
 */
public class AvoidSquareRings extends SquareCaptureMostPieces {

  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    int size = model.getBoardSize();
    coords.removeIf(coord -> coord.equals(new CubicPosn(1, 2))
            || coord.equals(new CubicPosn(2, 1))
            || coord.equals(new CubicPosn(2, 2))
            || coord.equals(new CubicPosn(1, size - 1))
            || coord.equals(new CubicPosn(2, size))
            || coord.equals(new CubicPosn(2, size - 1))
            || coord.equals(new CubicPosn(size - 1, size))
            || coord.equals(new CubicPosn(size - 1, size - 1))
            || coord.equals(new CubicPosn(size, size - 1))
            || coord.equals(new CubicPosn(size - 1, 1))
            || coord.equals(new CubicPosn(size, 2))
            || coord.equals(new CubicPosn(size - 1, 2)));
  }
}
