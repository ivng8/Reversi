package cs3500.reversi.view;

import cs3500.reversi.model.SquareReversi;

/**
 * Serves as the entry point and initialization hub for the Reversi textual view representation.
 */
public class MainToString {

  /**
   * The main method that serves as the entry point of the Reversi TextualView.
   *
   * @param args Command-line arguments used to configure the game.
   */
  public static void main(String[] args) {
    SquareReversi game = new SquareReversi(6);
    game.startGame();
    SquareReversiTextualView view = new SquareReversiTextualView(game);
    System.out.print(view);
  }
}
