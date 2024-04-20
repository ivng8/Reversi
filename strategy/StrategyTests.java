package cs3500.reversi.strategy;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.AvoidSecondRing;
import cs3500.reversi.strategy.CaptureMostPieces;
import cs3500.reversi.strategy.PrioritizeCorners;

import static org.junit.Assert.assertEquals;

/**
 * Tests for stragety without mocks.
 */
public class StrategyTests {

  Reversi game;

  @Before
  public void setup() {
    game = new Reversi(3);
  }

  @Test
  public void testGetsCorrectMove() {
    game.startGame();
    game.placeTile(new CubicPosn(1, -2));
    CubicPosn bestMove = new CaptureMostPieces().chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(1, -3));
  }

  @Test
  public void testTieBreaker() {
    game.startGame();
    game.placeTile(new CubicPosn(1, -2));
    game.placeTile(new CubicPosn(1, -3));
    game.placeTile(new CubicPosn(-1, 2));
    CubicPosn bestMove = new CaptureMostPieces().chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(-2, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testNoValidMoveStrategy() {
    ReversiModel game = new Reversi(2);
    game.startGame();
    game.placeTile(new CubicPosn(1, -2));
    game.placeTile(new CubicPosn(-1, -1));
    game.placeTile(new CubicPosn(-2, 1));
    game.placeTile(new CubicPosn(-1, 2));
    game.placeTile(new CubicPosn(1, 1));
    game.placeTile(new CubicPosn(2, -1));
    CubicPosn bestMove = new CaptureMostPieces().chooseMove(game, DiskState.WHITE);
  }

  @Test
  public void testAvoidsNextToCorners() {
    game.startGame();
    game.placeTile(new CubicPosn(1, -2));
    game.placeTile(new CubicPosn(1, 1));
    game.placeTile(new CubicPosn(-1, 2));
    CubicPosn bestMove = new AvoidSecondRing().chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(-2, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testOnlyNextToCorners() {
    ReversiModel game = new Reversi(2);
    game.startGame();
    CubicPosn bestMove = new AvoidSecondRing().chooseMove(game, DiskState.WHITE);
  }

  @Test
  public void testPrioritizeCorners() {
    game.startGame();
    game.placeTile(new CubicPosn(-1, 2));
    game.placeTile(new CubicPosn(-2, 3));
    game.placeTile(new CubicPosn(-1, 3));
    game.placeTile(new CubicPosn(1, -2));
    game.skipTurn();
    CubicPosn bestMove = new PrioritizeCorners().chooseMove(game, DiskState.WHITE);
    assertEquals(bestMove, new CubicPosn(-3, 3));
  }

  @Test
  public void testMinimax() {
    game.startGame();
    game.placeTile(new CubicPosn(-1, 2));
    game.placeTile(new CubicPosn(-2, 3));
    game.placeTile(new CubicPosn(-1, 3));
    game.placeTile(new CubicPosn(1, -2));
    game.skipTurn();
    game.placeTile(new CubicPosn(-2, 1));
    CubicPosn bestMove = new Minimax().chooseMove(game, DiskState.WHITE);
    assertEquals(bestMove, new CubicPosn(-3, 3));
  }

  @Test
  public void testLevel2() {
    game.startGame();
    game.placeTile(new CubicPosn(-1, 2));
    game.placeTile(new CubicPosn(-2, 3));
    game.placeTile(new CubicPosn(-1, -1));
    CubicPosn bestMove = new TwoStrategy(new AvoidSecondRing(),
            new CaptureMostPieces()).chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(1, -2));
  }

  @Test
  public void testLevel3() {
    game.startGame();
    game.placeTile(new CubicPosn(1, -2));
    game.placeTile(new CubicPosn(1, -3));
    game.placeTile(new CubicPosn(2, -3));
    game.placeTile(new CubicPosn(1, 1));
    game.placeTile(new CubicPosn(1, 2));
    game.placeTile(new CubicPosn(2, 1));
    game.placeTile(new CubicPosn(-1, 2));
    game.placeTile(new CubicPosn(-2, 1));
    game.placeTile(new CubicPosn(-3, 2));
    game.placeTile(new CubicPosn(-2, 3));
    game.placeTile(new CubicPosn(-1, 3));
    CubicPosn bestMove = new TwoStrategy(new TwoStrategy(new AvoidSecondRing(),
            new PrioritizeCorners()), new CaptureMostPieces()).chooseMove(game, DiskState.BLACK);
    assertEquals(bestMove, new CubicPosn(0, 3));
  }
}
