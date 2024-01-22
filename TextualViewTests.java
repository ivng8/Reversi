import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.view.ReversiTextualView;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for Our TextualView.
 */
public class TextualViewTests {

  private Reversi game;
  private ReversiTextualView view;

  @Before
  public void setup() {
    game = new Reversi();
    game.startGame(4);
    view = new ReversiTextualView(game);
  }

  @Test
  public void testInitialBoardState() {
    String expected =
                    "     _ _ _ _ _ _\n" +
                    "    _ _ _ _ _ _ _\n" +
                    "   _ _ _ _ _ _ _ _\n" +
                    "  _ _ _ _ _ _ _ _ _\n" +
                    " _ _ _ _ B W _ _ _ _\n" +
                    "_ _ _ _ W _ B _ _ _ _\n" +
                    " _ _ _ _ B W _ _ _ _\n" +
                    "  _ _ _ _ _ _ _ _ _\n" +
                    "   _ _ _ _ _ _ _ _\n" +
                    "    _ _ _ _ _ _ _\n" +
                    "     _ _ _ _ _ _\n";

    assertEquals(expected, view.toString());
  }

  @Test
  public void testAfterOneMove() {
    game.placeTile(new CubicPosn(2, -1), DiskState.BLACK);
    String expected =
                    "     _ _ _ _ _ _\n" +
                    "    _ _ _ _ _ _ _\n" +
                    "   _ _ _ _ _ _ _ _\n" +
                    "  _ _ _ _ _ _ _ _ _\n" +
                    " _ _ _ _ B B B _ _ _\n" +
                    "_ _ _ _ W _ B _ _ _ _\n" +
                    " _ _ _ _ B W _ _ _ _\n" +
                    "  _ _ _ _ _ _ _ _ _\n" +
                    "   _ _ _ _ _ _ _ _\n" +
                    "    _ _ _ _ _ _ _\n" +
                    "     _ _ _ _ _ _\n";

    assertEquals(expected, view.toString());
  }

  @Test
  public void testBoardStateRadius2() {
    game.startGame(2);
    String expected =
                    "     _ _ _ _\n" +
                    "    _ _ _ _ _\n" +
                    "   _ _ B W _ _\n" +
                    "  _ _ W _ B _ _\n" +
                    "   _ _ B W _ _\n" +
                    "    _ _ _ _ _\n" +
                    "     _ _ _ _\n";

    assertEquals(expected, view.toString());
  }

}