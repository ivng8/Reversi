package cs3500.reversi.view;

import java.io.IOException;
import java.util.HashMap;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.Reversi;

/**
 * A simple text-based rendering of the Reversi game.
 */
public class ReversiTextualView implements TextView {

  private final Reversi model;
  private Appendable out;

  /**
   * Constructs a new ReversiTextualView.
   *
   * @param model The Reversi instance to be rendered.
   */
  public ReversiTextualView(Reversi model) {
    this.model = model;
  }

  /**
   * Constructs a new ReversiTextualView.
   *
   * @param model The Reversi instance to be rendered.
   * @param out   Appendable output to be displayed.
   */
  public ReversiTextualView(Reversi model, Appendable out) {
    this.out = out;
    this.model = model;
  }

  /**
   * Converts the Reversi board into a string representation.
   *
   * @return A string containing the board state.
   */
  @Override
  public String toString() {
    StringBuilder boardContents = new StringBuilder();
    HashMap<CubicPosn, DiskState> board = model.getBoard();
    int radius = model.getBoardSize();
    for (int y = -radius; y <= radius; y += 1) {
      boardContents.append(" ".repeat(Math.max(0, Math.abs(y) + 1)));
      for (int x = Math.max(-radius, -radius - y); x <= Math.min(radius, radius - y); x += 1) {
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

  /**
   * Appends a view to a current game state.
   */
  public void render() throws IOException {
    out.append(this.toString());
  }
}
