package cs3500.reversi.strategy.square;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.strategy.CaptureMostPieces;
import cs3500.reversi.strategy.TwoStrategy;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the new strategies that are meant for a Square game of Reversi.
 */
public class SquareStrategyTests {

  SquareReversi game;

  @Before
  public void setup() {
    game = new SquareReversi(8);
  }

  @Test
  public void testTieBreaker() {
    game.startGame();
    game.placeTile(new CubicPosn(5, 3));
    game.placeTile(new CubicPosn(4, 3));
    CubicPosn bestMove = new SquareCaptureMostPieces().chooseMove(game, DiskState.WHITE);
    assertEquals(bestMove, new CubicPosn(3, 5));
  }

  @Test
  public void testCorrectMove() {
    game.startGame();
    game.placeTile(new CubicPosn(5, 3));
    game.placeTile(new CubicPosn(4, 3));
    game.placeTile(new CubicPosn(3, 2));
    game.skipTurn();
    CubicPosn bestMove = new CaptureMostPieces().chooseMove(game, DiskState.WHITE);
    assertEquals(bestMove, new CubicPosn(3, 5));
  }

  @Test (expected = IllegalStateException.class)
  public void testNoValidMoveStrategy() {
    SquareReversi game = new SquareReversi(4);
    game.startGame();
    game.placeTile(new CubicPosn(2, 4));
    game.placeTile(new CubicPosn(1, 4));
    game.placeTile(new CubicPosn(1, 3));
    game.placeTile(new CubicPosn(1, 2));
    game.placeTile(new CubicPosn(1, 1));
    game.placeTile(new CubicPosn(3, 1));
    game.placeTile(new CubicPosn(2, 1));
    game.placeTile(new CubicPosn(3, 4));
    game.placeTile(new CubicPosn(4, 4));
    game.placeTile(new CubicPosn(4, 3));
    game.placeTile(new CubicPosn(4, 2));
    game.placeTile(new CubicPosn(4, 1));
    CubicPosn bestMove = new CaptureMostPieces().chooseMove(game, DiskState.WHITE);
  }

  @Test(expected = IllegalStateException.class)
  public void testOnlyNextToCorners() {
    ReversiModel game = new SquareReversi(4);
    game.startGame();
    CubicPosn bestMove = new AvoidSquareRings().chooseMove(game, DiskState.WHITE);
  }

  @Test
  public void testPrioritizeCorners() {
    SquareReversi game = new SquareReversi(6);
    game.startGame();
    game.placeTile(new CubicPosn(3, 5));
    game.placeTile(new CubicPosn(2, 5));
    game.placeTile(new CubicPosn(2, 4));
    game.placeTile(new CubicPosn(2, 3));
    game.placeTile(new CubicPosn(2, 2));
    game.placeTile(new CubicPosn(4, 2));
    game.placeTile(new CubicPosn(3, 2));
    game.placeTile(new CubicPosn(4, 5));
    game.placeTile(new CubicPosn(5, 5));
    game.placeTile(new CubicPosn(5, 4));
    game.placeTile(new CubicPosn(5, 3));
    game.placeTile(new CubicPosn(5, 2));
    game.skipTurn();
    CubicPosn bestMove = new PrioritizeSquareCorners().chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(6, 6));
  }

  @Test
  public void testLevel2() {
    SquareReversi game = new SquareReversi(6);
    game.startGame();
    game.placeTile(new CubicPosn(3, 5));
    game.placeTile(new CubicPosn(2, 5));
    game.placeTile(new CubicPosn(2, 4));
    game.placeTile(new CubicPosn(2, 3));
    game.placeTile(new CubicPosn(2, 2));
    game.placeTile(new CubicPosn(4, 2));
    game.placeTile(new CubicPosn(3, 2));
    game.placeTile(new CubicPosn(4, 5));
    game.placeTile(new CubicPosn(5, 5));
    game.placeTile(new CubicPosn(5, 4));
    game.placeTile(new CubicPosn(5, 3));
    game.placeTile(new CubicPosn(5, 2));
    CubicPosn bestMove = new TwoStrategy(new AvoidSquareRings(),
            new SquareCaptureMostPieces()).chooseMove(game, DiskState.WHITE);
    assertEquals(bestMove, new CubicPosn(1, 4));
  }

  @Test
  public void testLevel3() {
    SquareReversi game = new SquareReversi(6);
    game.startGame();
    game.placeTile(new CubicPosn(3, 5));
    game.placeTile(new CubicPosn(2, 5));
    game.placeTile(new CubicPosn(2, 4));
    game.placeTile(new CubicPosn(2, 3));
    game.placeTile(new CubicPosn(2, 2));
    game.placeTile(new CubicPosn(4, 2));
    game.placeTile(new CubicPosn(3, 2));
    game.placeTile(new CubicPosn(4, 5));
    game.placeTile(new CubicPosn(5, 5));
    game.placeTile(new CubicPosn(5, 4));
    game.placeTile(new CubicPosn(5, 3));
    game.placeTile(new CubicPosn(5, 2));
    game.skipTurn();
    CubicPosn bestMove = new TwoStrategy(new TwoStrategy(new AvoidSquareRings(),
            new PrioritizeSquareCorners()), new CaptureMostPieces()).
            chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(1, 1));
  }
}
