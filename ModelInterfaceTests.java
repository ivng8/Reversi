package cs3500.reversi;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.Reversi;

/**
 * Testing class for surface level clear testing of our Model, simple functionality.
 */
public class ModelInterfaceTests {
  Reversi game;

  @Before
  public void setup() {
    game = new Reversi();
  }

  @Test
  public void testStartGame() {
    game.startGame(4);
    assertEquals(4, game.getRadius());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidRadius() {
    game.startGame(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameTwice() {
    game.startGame(4);
    game.startGame(5);
  }

  @Test
  public void testValidPlaceTile() {
    game.startGame(4);
    game.placeTile(new CubicPosn(2, -1), DiskState.WHITE);
    assertEquals(DiskState.WHITE, game.getBoard().get(new CubicPosn(2, -1)));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceTileBeforeGameStarts() {
    game.placeTile(new CubicPosn(2, -1), DiskState.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceEmptyTile() {
    game.startGame(4);
    game.placeTile(new CubicPosn(2, -1), DiskState.EMPTY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceTileOutsideBoard() {
    game.startGame(4);
    game.placeTile(new CubicPosn(5, 5), DiskState.BLACK);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceTileOnOccupiedPosition() {
    game.startGame(4);
    game.placeTile(new CubicPosn(1, 0), DiskState.BLACK);  // This position already has a tile
  }
}
