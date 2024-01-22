import org.junit.Test;

import cs3500.reversi.controller.ReversiControllerMVC;
import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ReversiGraphicsView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * TestController, due to how our controller does not have a large amount of code, we will be
 * testing more of the listeners instead and its interaction to the model.
 */
public class ControllerTests {

  private ReversiControllerMVC controller;

  private ReversiModel model;

  Player humanPlayer;


  // Tests that startGame for model starts the controller, and that a immediate move without intake
  // fails.
  @Test
  public void testMoveWithoutSelectionDoesntThrowH() {
    model = new Reversi(3);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.placePiece();
    assertTrue(model.isCurrentPlayer(DiskState.WHITE));
  }

  // Tests valid place piece, for human expecting input, tests listener OnMove
  @Test
  public void testPlacePieceFromViewOnMoveH() {
    model = new Reversi(3);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onMove(new CubicPosn(2, -1));
    controller.placePiece();
    assertEquals(model.getState(new CubicPosn(2, -1)), DiskState.WHITE);
  }

  @Test
  public void testPlacePieceInvalidDoesntPlaceH() {
    model = new Reversi(4);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onMove(new CubicPosn(3, -1));
    controller.placePiece();
    assertEquals(model.getState(new CubicPosn(2, -1)), DiskState.EMPTY);
  }

  @Test
  public void testPlaceInvalidPieceDoesNotCrashH() {
    model = new Reversi(4);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onMove(new CubicPosn(3, -1));
    controller.placePiece();
    assertEquals(model.getState(new CubicPosn(2, -1)), DiskState.EMPTY);
  }

  @Test
  public void testOnPlayerH() {
    model = new Reversi(4);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    ReversiModel modelTemp = new Reversi(4);
    modelTemp.startGame();
    model.startGame();
    controller.onPlayer(DiskState.WHITE);
    // Make move should not invoke error, and because it is a player it should wait for view input.
    assertEquals(model.getBoard(), modelTemp.getBoard());
  }

  @Test
  public void testOnPassH() {
    model = new Reversi(4);
    IView view = new ReversiGraphicsView(model, "hexagon");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onPass();
    // Make move should not invoke error, and because it is a player it should wait for view input.
    assertFalse(model.isCurrentPlayer(DiskState.WHITE));
    // Test that passing when not on your turn doesn't pass. (Player Enforce)
    model = new Reversi(3);
    humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    model.skipTurn();
    controller.onPass();
    assertTrue(model.isCurrentPlayer(DiskState.BLACK));
  }

  @Test
  public void testPlayerEnforcePlaceDoesntPlaceH() {
    model = new Reversi(3);
    IView view = new ReversiGraphicsView(model, "hexagon");
    humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    model.skipTurn();
    controller.onMove(new CubicPosn(2, -1));
    controller.placePiece();
    assertNotEquals(model.getState(new CubicPosn(2, -1)), DiskState.WHITE);
  }

  @Test
  public void testMoveWithoutSelectionDoesntThrowS() {
    model = new SquareReversi(8);
    IView view = new ReversiGraphicsView(model, "square");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.placePiece();
    assertTrue(model.isCurrentPlayer(DiskState.WHITE));
  }

  // Tests valid place piece, for human expecting input, tests listener OnMove
  @Test
  public void testPlacePieceFromViewOnMoveS() {
    model = new SquareReversi(8);
    IView view = new ReversiGraphicsView(model, "square");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onMove(new CubicPosn(5, 3));
    controller.placePiece();
    assertEquals(model.getState(new CubicPosn(5, 3)), DiskState.WHITE);
  }

  @Test
  public void testPlacePieceInvalidDoesntPlaceS() {
    model = new SquareReversi(4);
    IView view = new ReversiGraphicsView(model, "square");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onMove(new CubicPosn(4, 4));
    controller.placePiece();
    assertEquals(model.getState(new CubicPosn(4, 4)), DiskState.EMPTY);
  }

  @Test
  public void testOnPlayerS() {
    model = new SquareReversi(4);
    IView view = new ReversiGraphicsView(model, "square");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    ReversiModel modelTemp = new SquareReversi(4);
    modelTemp.startGame();
    model.startGame();
    controller.onPlayer(DiskState.WHITE);
    // Make move should not invoke error, and because it is a player it should wait for view input.
    assertEquals(model.getBoard(), modelTemp.getBoard());
  }

  @Test
  public void testOnPassS() {
    model = new SquareReversi(4);
    IView view = new ReversiGraphicsView(model, "square");
    Player humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    controller.onPass();
    // Make move should not invoke error, and because it is a player it should wait for view input.
    assertFalse(model.isCurrentPlayer(DiskState.WHITE));
    // Test that passing when not on your turn doesn't pass. (Player Enforce)
    model = new SquareReversi(4);
    humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    model.skipTurn();
    controller.onPass();
    assertTrue(model.isCurrentPlayer(DiskState.BLACK));
  }

  @Test
  public void testPlayerEnforcePlaceDoesntPlaceS() {
    model = new SquareReversi(4);
    IView view = new ReversiGraphicsView(model, "square");
    humanPlayer = new HumanPlayer(model);
    controller = new ReversiControllerMVC(model, humanPlayer, view);
    model.startGame();
    model.skipTurn();
    controller.onMove(new CubicPosn(1, 3));
    controller.placePiece();
    assertNotEquals(model.getState(new CubicPosn(1, 3)), DiskState.WHITE);
  }
}