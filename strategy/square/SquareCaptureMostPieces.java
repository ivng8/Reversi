package cs3500.reversi.strategy.square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.strategy.CaptureMostPieces;

/**
 * Extends the CaptureMostPieces class that is intended for hexagonal Reversi in order to reuse
 * useful computation. The way it filters is exactly the same and the only thing different that is
 * executed in the chooseMove is the tiebreaker at the end to calculate the distance of a point
 * from the top left corner of the board.
 */
public class SquareCaptureMostPieces extends CaptureMostPieces {

  @Override
  public CubicPosn chooseMove(ReadOnlyReversiModel model, DiskState player) {
    HashMap<CubicPosn, DiskState> board = model.getBoard();
    Set<CubicPosn> coordinates = board.keySet();
    Set<CubicPosn> coordinatesAdjusted = new HashSet<>(coordinates);
    for (CubicPosn coord : coordinates) {
      List<List<CubicPosn>> seams = model.findAllSeams(coord, player);
      if (seams.isEmpty()) {
        coordinatesAdjusted.remove(coord);
      }
    }
    if (coordinatesAdjusted.isEmpty()) {
      throw new IllegalStateException("No valid moves");
    }
    ArrayList<CubicPosn> coords = new ArrayList<>(coordinatesAdjusted);
    this.filter(coords, player, model);
    if (coords.isEmpty()) {
      throw new IllegalStateException("Strategy has no good moves that fit this strategy");
    }
    CubicPosn ans = coords.get(0);
    for (CubicPosn candidate : coords) {
      int distanceOfAns = model.getBoardSize() - ans.getY() + ans.x - 1;
      int distanceOfCurrent = model.getBoardSize() - candidate.getY() + candidate.getX() - 1;
      if (distanceOfCurrent < distanceOfAns) {
        ans = candidate;
      }
    }
    return ans;
  }
}
