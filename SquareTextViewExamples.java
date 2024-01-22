package cs3500.reversi;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.view.SquareReversiTextualView;

import static org.junit.Assert.assertEquals;


/**
 * Testing for the text views of Square Reversi.
 */
public class SquareTextViewExamples {

  private SquareReversi game;

  private SquareReversiTextualView view;

  @Before
  public void setup() {
    game = new SquareReversi(6);
    game.startGame();
    view = new SquareReversiTextualView(game);
  }

  @Test
  public void testInitialBoardState() {
    String expected =
            "_ _ _ _ _ _ \b\n" +
                    "_ _ _ _ _ _ \b\n" +
                    "_ _ B W _ _ \b\n" +
                    "_ _ W B _ _ \b\n" +
                    "_ _ _ _ _ _ \b\n" +
                    "_ _ _ _ _ _ \b\n";
    assertEquals(expected, view.toString());
  }

  @Test
  public void testAfterOneMove() {
    game.placeTile(new CubicPosn(4, 2));
    String expected =
            "_ _ _ _ _ _ \b\n" +
                    "_ _ _ _ _ _ \b\n" +
                    "_ _ B W _ _ \b\n" +
                    "_ _ W W _ _ \b\n" +
                    "_ _ _ W _ _ \b\n" +
                    "_ _ _ _ _ _ \b\n";

    assertEquals(expected, view.toString());
  }
}
