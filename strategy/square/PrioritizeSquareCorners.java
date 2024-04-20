package cs3500.reversi.strategy.square;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Again, follows the same pattern as the strategies intended for hexagonal Reversi. However, it
 * changes the computation for prioritizing the corners of the board.
 */
public class PrioritizeSquareCorners extends SquareCaptureMostPieces {

  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    int size = model.getBoardSize();
    ArrayList<CubicPosn> possible = new ArrayList<>();
    for (CubicPosn coord : coords) {
      if (coord.equals(new CubicPosn(size, 1))
              || coord.equals(new CubicPosn(1, size))
              || coord.equals(new CubicPosn(size, size))
              || coord.equals(new CubicPosn(1, 1))) {
        possible.add(coord);
      }
    }
    if (!possible.isEmpty()) {
      coords.clear();
      coords.addAll(possible);
    }
  }
}
