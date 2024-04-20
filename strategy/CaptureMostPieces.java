package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * represents the strategy of flipping the most pieces (tiebreaker of moving to top left).
 */
public class CaptureMostPieces implements ReversiStrategy {

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
      int distanceOfAns = Math.abs(ans.getX()) + ans.getY() - model.getBoardSize();
      int distanceOfCurrent = Math.abs(candidate.getX()) + candidate.getY() - model.getBoardSize();
      if (distanceOfCurrent < distanceOfAns) {
        ans = candidate;
      }
    }
    return ans;
  }

  /**
   * filters by computing the number of disks flipped by the hypothetical move.
   * then it determines the highest number and filters out all moves that don't create this outcome.
   * @param coords the list of possible moves
   * @param player the player its working for
   */
  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    HashMap<CubicPosn, Integer> points = new HashMap<>();
    for (CubicPosn spot : coords) {
      List<List<CubicPosn>> seams = model.findAllSeams(spot, player);
      int i = 0;
      for (List<CubicPosn> seam : seams) {
        i += seam.size() - 1;
      }
      points.put(spot, i);
    }
    int max = Collections.max(points.values());
    coords.removeIf(point -> points.get(point) != max);
  }
}
