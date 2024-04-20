package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cs3500.reversi.features.ModelEvent;

/**
 * The {@code Reversi} class represents the model for a game of Reversi, specifically
 * adapted for a hexagonal board. It maintains the game state, handles game logic, and
 * communicates changes to registered listeners.
 *
 * <p>Game state is represented using a HashMap where keys are {@link CubicPosn} objects
 * representing positions on the hexagonal board, and values are {@link DiskState} enums
 * representing the state (empty, black, or white) of each tile. This structure allows
 * for efficient access and manipulation of board state during gameplay.
 *
 * <p>The class provides methods to start a game, place tiles, check for valid moves,
 * calculate scores, and determine game over conditions. It follows the rules of standard
 * Reversi, adapted for a hexagonal layout, making it suitable for creating a variety of
 * Reversi-based games or simulations.
 *
 * <p>Additionally, {@code Reversi} implements the {@link ReversiModel} interface,
 * exposing a set of methods for interacting with the game state. This includes observational
 * methods to query the current state without altering it. The class also supports
 * event-driven communication with external components (like views or controllers) through
 * the {@link ModelEvent.ModelEventListener} interface. Listeners can be registered to
 * receive updates about game progress, player turns, and other actions.
 *
 * <p>Example usage:
 * <pre>
 *   Reversi game = new Reversi();
 *   game.addModelEventListener(listener);
 *   game.startGame(5); // start a game with a radius of 5
 *   game.placeTile(new CubicPosn(0, 0)); // place a tile at position (0, 0)
 * </pre>
 */

public class Reversi implements ReversiModel {

  protected List<ModelEvent.ModelEventListener> listeners = new ArrayList<>();

  protected HashMap<CubicPosn, DiskState> board;

  protected final int radius;

  protected boolean start;

  protected DiskState turn;

  protected boolean lastMovePass;

  protected boolean gameOver;

  protected boolean alreadyUsedDisk;

  /**
   * constructor for a game of reversi.
   */
  public Reversi(int radius) {
    if (radius < 2) {
      throw new IllegalArgumentException("not a valid parameter for a game");
    }
    this.board = new HashMap<>();
    this.radius = radius;
    this.start = false;
    this.turn = DiskState.WHITE;
    this.lastMovePass = false;
  }

  /**
   * constructor for a game of reversi with a custom game-state instead of a default start.
   * @param board the custom game state
   */
  public Reversi(HashMap<CubicPosn, DiskState> board, int radius) {
    if (radius < 2) {
      throw new IllegalArgumentException("not a valid parameter for a game");
    }
    this.board = board;
    this.radius = radius;
    this.start = true;
    this.turn = DiskState.WHITE;
    this.lastMovePass = false;
  }

  /**
   * adds a listener that looks for events and updates the model.
   * @param listener the listener
   */
  @Override
  public void addModelEventListener(ModelEvent.ModelEventListener listener) {
    listeners.add(listener);
  }

  /**
   * INVARIANTS:
   * - SideLength has to be greater than 1 in order to start a valid game.
   * - Always starts the innermost ring with alternating black and white tiles.
   * - Innermost tile always starts as empty.
   */
  @Override
  public void startGame() {
    if (this.start) {
      throw new IllegalStateException("game has already started");
    }
    for (int x = -radius; x <= radius; x++) {
      for (int y = -radius; y <= radius; y++) {
        if (Math.abs(-x - y) <= radius) {
          board.put(new CubicPosn(x, y), DiskState.EMPTY);
        }
      }
    }
    board.replace(new CubicPosn(-1, 0), DiskState.WHITE);
    board.replace(new CubicPosn(1, -1), DiskState.WHITE);
    board.replace(new CubicPosn(0, 1), DiskState.WHITE);
    board.replace(new CubicPosn(1, 0), DiskState.BLACK);
    board.replace(new CubicPosn(0, -1), DiskState.BLACK);
    board.replace(new CubicPosn(-1, 1), DiskState.BLACK);
    this.start = true;
    this.notifyPlayersTurn();
    for (ModelEvent.ModelEventListener listener : listeners) {
      listener.startGame(this, radius);
    }
  }

  @Override
  public void placeTile(CubicPosn pos) {
    if (!this.start) {
      throw new IllegalStateException("game has not started");
    }
    if (this.turn.equals(DiskState.EMPTY)) {
      throw new IllegalArgumentException("Should not be putting down an empty tile.");
    }
    if (!board.containsKey(pos)) {
      throw new IllegalArgumentException("Not valid point in the board");
    }
    List<List<CubicPosn>> seams = this.findAllSeams(pos, this.turn);
    if (!this.isValidMove(pos, this.turn)) {
      throw new IllegalStateException("Not valid move");
    }
    for (List<CubicPosn> seam : seams) {
      this.flipTiles(seam, this.turn);
    }
    this.turn = (this.turn == DiskState.WHITE) ? DiskState.BLACK : DiskState.WHITE;
    this.lastMovePass = false;
    this.fullRefresh();
    this.notifyPlayersTurn();
  }

  @Override
  public List<CubicPosn> findSeam(CubicPosn pos, DiskState color, String dim, int direction) {
    int a = 0;
    int b = 0;
    switch (dim) {
      case "x":
        a = direction;
        break;
      case "y":
        b = direction;
        break;
      case "z":
        a = direction;
        b = -direction;
        break;
      default:
        break;
    }
    List<CubicPosn> seam = new ArrayList<>();
    int x = pos.getX();
    int y = pos.getY();
    seam.add(new CubicPosn(x, y));
    x += a;
    y += b;
    while (Math.abs(x) <= this.radius && Math.abs(y) <= this.radius) {
      if (!this.board.containsKey(new CubicPosn(x, y))) {
        return new ArrayList<>();
      }
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
    return new ArrayList<>();
  }

  @Override
  public List<List<CubicPosn>> findAllSeams(CubicPosn posn, DiskState color) {
    List<List<CubicPosn>> seams = new ArrayList<>();
    if (board.get(posn).equals(DiskState.EMPTY)) {
      seams.add(this.findSeam(posn, color, "x", -1));
      seams.add(this.findSeam(posn, color, "x", 1));
      seams.add(this.findSeam(posn, color, "y", -1));
      seams.add(this.findSeam(posn, color, "y", 1));
      seams.add(this.findSeam(posn, color, "z", -1));
      seams.add(this.findSeam(posn, color, "z", 1));
      seams.removeIf(seam -> seam.size() < 2);
    }
    return seams;
  }

  /**
   * Mutates the board with the given seam and tile (Flips input tiles).
   *
   * @param seam  The seam on the board to flip.
   * @param color Color to flip to.
   */
  protected void flipTiles(List<CubicPosn> seam, DiskState color) {
    for (CubicPosn point : seam) {
      this.board.replace(point, color);
    }
  }

  @Override
  public void skipTurn() {
    this.gameOver = this.lastMovePass;
    this.turn = (this.turn == DiskState.WHITE) ? DiskState.BLACK : DiskState.WHITE;
    lastMovePass = true;
    this.fullRefresh();
    this.notifyPlayersTurn();
  }

  // Observational methods

  @Override
  public DiskState getState(CubicPosn posn) {
    return board.get(posn);
  }

  @Override
  public boolean isValidMove(CubicPosn posn, DiskState color) {
    return !this.findAllSeams(posn, color).isEmpty();
  }

  @Override
  public int getScore(DiskState toScore) {
    int count = 0;
    for (DiskState state : board.values()) {
      if (toScore.equals(state)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public boolean anyValidMoves(DiskState state) {
    Set<CubicPosn> allCoords = this.board.keySet();
    for (CubicPosn coord : allCoords) {
      if (isValidMove(coord, state)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isGameOver() {
    if (this.gameOver) {
      return true;
    } else {
      return this.anyValidMoves(DiskState.WHITE) || this.anyValidMoves(DiskState.BLACK);
    }
  }

  @Override
  public HashMap<CubicPosn, DiskState> getBoard() {
    /*
    if (!this.start) {
      throw new IllegalStateException("game has not started");
    }
     */
    // Proxy to prevent unwanted mutability
    return new HashMap<>(this.board);
  }

  @Override
  public boolean isCurrentPlayer(DiskState diskState) {
    return turn == diskState;
  }

  @Override
  public int getBoardSize() {
    if (!this.start) {
      throw new IllegalStateException("game has not started");
    }
    return this.radius;
  }

  /**
   * notifies the listeners on which player's turn it is (targets that player's listener).
   */
  protected void notifyPlayersTurn() {
    for (ModelEvent.ModelEventListener listener : listeners) {
      listener.onPlayer(turn);
    }
  }

  /**
   * relays information to listener to refresh data structures affecting by player move behavior.
   */
  protected void fullRefresh() {
    for (ModelEvent.ModelEventListener listener : listeners) {
      listener.onAction();
    }
  }

  @Override
  public DiskState assignDisk() {
    if (alreadyUsedDisk) {
      return DiskState.BLACK;
    } else {
      this.alreadyUsedDisk = true;
      return DiskState.WHITE;
    }
  }
}