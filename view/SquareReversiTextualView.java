package cs3500.reversi.view;

import java.util.HashMap;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.SquareReversi;

/**
 * A quick string view that represents the game state of a game of Square Reversi.
 */
public class SquareReversiTextualView {

  private final SquareReversi model;

  /**
   * constructor for Square Reversi.
   * @param model the game itself
   */
  public SquareReversiTextualView(SquareReversi model) {
    this.model = model;
  }

  /**
   * constructor for Square Reversi.
   * @param model the game
   * @param out the appendable
   */
  public SquareReversiTextualView(SquareReversi model, Appendable out) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder boardContents = new StringBuilder();
    HashMap<CubicPosn, DiskState> board = model.getBoard();
    int radius = model.getBoardSize();
    for (int y = radius; y > 0; y -= 1) {
      for (int x = 1; x <= radius; x += 1) {
        CubicPosn pos = new CubicPosn(x, y);
        DiskState state = board.get(pos);
        switch (state) {
          case EMPTY:
            boardContents.append("_ ");
            break;
          case BLACK:
            boardContents.append("B ");
            break;
          case WHITE:
            boardContents.append("W ");
            break;
          default:
            break;
        }
      }
      boardContents.append("\b\n");
    }
    return boardContents.toString();
  }
}