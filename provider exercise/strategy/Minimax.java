package cs3500.reversi.strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiModel;

/**
 * a strategy that aims to create the worst outcome for your opponent.
 */
public class Minimax extends CaptureMostPieces {

  /**
   * filters a list of possible moves by first creating a simulation of the current game.
   * for each possible move it enacts it onto the simulation and then computes for the opponent.
   * the move(s) that creates the worst outcome for your opponent stays in the list.
   * @param coords the list of possible moves
   * @param player the player its working for
   */
  @Override
  public void filter(List<CubicPosn> coords, DiskState player, ReadOnlyReversiModel model) {
    HashMap<CubicPosn, Integer> moves = new HashMap<>();
    for (CubicPosn coord : coords) {
      HashMap<CubicPosn, DiskState> board = model.getBoard();
      ReversiModel gameCopy = new Reversi(board);
      if (player.equals(DiskState.BLACK)) {
        gameCopy.skipTurn();
      }
      gameCopy.placeTile(coord);
      List<List<CubicPosn>> seams;
      if (player.equals(DiskState.BLACK)) {
        seams = gameCopy.findAllSeams(new CaptureMostPieces().chooseMove(gameCopy,
                DiskState.WHITE), DiskState.WHITE);
      } else {
        seams = gameCopy.findAllSeams(new CaptureMostPieces().chooseMove(gameCopy,
                DiskState.BLACK), DiskState.BLACK);
      }
      int i = 0;
      for (List<CubicPosn> seam : seams) {
        i += seam.size();
      }
      moves.put(coord, i);
    }
    int min = Collections.min(moves.values());
    coords.removeIf(move -> moves.get(move) != min);
  }
}