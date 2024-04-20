package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.reversi.features.ModelEvent;

/**
 * The {@code SquareReversi} class represents the model for a classic game of Reversi.
 * It maintains the game state, handles game logic and communicates changes to registered listeners.
 *
 * <p>Game state is represented using a HashMap where keys are {@link CubicPosn} objects
 * representing positions on the square board, and values are {@link DiskState} enums
 * representing the state (empty, black, or white) of each tile. This structure allows
 * for efficient access and manipulation of board state during gameplay.
 *
 * <p>The class provides methods to start a game, place tiles, check for valid moves,
 * calculate scores, and determine game over conditions. It follows the rules of standard
 * Reversi, making it suitable for creating a variety of Reversi-based games or simulations.
 */
public class SquareReversi extends Reversi {

  /**
   * constructor for a square reversi game.
   * @param diameter the width or height of the game
   */
  public SquareReversi(int diameter) {
    super(diameter);
    if (diameter < 4 || diameter % 2 != 0) {
      throw new IllegalArgumentException("not a valid parameter for a game");
    }
    this.board = new HashMap<>();
    this.start = false;
    this.turn = DiskState.WHITE;
    this.lastMovePass = false;
  }

  /**
   * starts the game of square reversi with the middle 4 spots being set up in classic fashion.
   */
  @Override
  public void startGame() {
    if (this.start) {
      throw new IllegalStateException("game has already started");
    }
    for (int x = 1; x <= radius; x += 1) {
      for (int y = 1; y <= radius; y += 1) {
        board.put(new CubicPosn(x, y), DiskState.EMPTY);
      }
    }
    board.replace(new CubicPosn(radius / 2, radius / 2), DiskState.WHITE);
    board.replace(new CubicPosn(radius / 2, radius / 2 + 1), DiskState.BLACK);
    board.replace(new CubicPosn(radius / 2 + 1, radius / 2), DiskState.BLACK);
    board.replace(new CubicPosn(radius / 2 + 1, radius / 2 + 1), DiskState.WHITE);
    this.start = true;
    this.notifyPlayersTurn();
    for (ModelEvent.ModelEventListener listener : listeners) {
      listener.startGame(this, radius);
    }
  }

  /**
   * works the same as the findAllSeams in Reversi except it has 8 directions to parse through.
   * @param posn the position of the new tile
   * @param color the color of that tile
   * @return
   */
  @Override
  public List<List<CubicPosn>> findAllSeams(CubicPosn posn, DiskState color) {
    List<List<CubicPosn>> seams = new ArrayList<>();
    if (board.get(posn).equals(DiskState.EMPTY)) {
      seams.add(this.findSeam(posn, color, "", 1));
      seams.add(this.findSeam(posn, color, "", 2));
      seams.add(this.findSeam(posn, color, "", 3));
      seams.add(this.findSeam(posn, color, "", 4));
      seams.add(this.findSeam(posn, color, "", 5));
      seams.add(this.findSeam(posn, color, "", 6));
      seams.add(this.findSeam(posn, color, "", 7));
      seams.add(this.findSeam(posn, color, "", 8));
      seams.removeIf(seam -> seam.size() < 2);
    }
    return seams;
  }

  /**
   * Works the same as the findSeam in Reversi except its directions are now solely represented
   * by a number that represents the cardinal value. (1 is up, 2 is up-right, etc.)
   * @param posn       the position of the new tile to be placed
   * @param color     the color of that tile
   * @param dim       the x, y, or z dimension it's parsing
   * @param direction the direction of that dimension that it's parsing (negative or positive)
   * @return
   */
  @Override
  public List<CubicPosn> findSeam(CubicPosn posn, DiskState color, String dim, int direction) {
    int a = 0;
    int b = 0;
    switch (direction) {
      case 1:
        b = 1;
        break;
      case 2:
        a = 1;
        b = 1;
        break;
      case 3:
        a = 1;
        break;
      case 4:
        a = 1;
        b = -1;
        break;
      case 5:
        b = -1;
        break;
      case 6:
        a = -1;
        b = -1;
        break;
      case 7:
        a = -1;
        break;
      case 8:
        a = -1;
        b = 1;
        break;
      default:
        break;
    }
    List<CubicPosn> seam = new ArrayList<>();
    int x = posn.getX();
    int y = posn.getY();
    seam.add(new CubicPosn(x, y));
    x += a;
    y += b;
    while (x > 0 && x <= this.radius && y > 0 && y <= this.radius) {
      CubicPosn point = new CubicPosn(x, y);
      if (this.board.get(point).equals(color)) {
        return seam;
      } else if (this.board.get(point).equals(DiskState.EMPTY)) {
        return new ArrayList<>();
      } else {
        seam.add(point);
      }
      x += a;
      y += b;
    }
    return seam;
  }
}