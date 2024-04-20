package cs3500.reversi.player;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a human player in a game of reversi, instead of opting to directly implement our two
 * high level functions, placing a piece and passing, we opted to seperate our method into
 * makemove, and placePiece, which dispatches to our controller to be mutated, as moving
 * our input from our view seems redundant and unnecessary. Further explanation is given in the
 * methods themselves.
 */
public class HumanPlayer implements Player {

  private ReversiController observer;

  /**
   * the game the human player belongs to.
   * @param model the game
   */
  public HumanPlayer(ReversiModel model) {
    // No need to use model in this case, it is only for the main code given to us, as our
    // implementation dispatches the move into the controller for turn checking.
  }

  public void makeMove() {
    // This method is empty, as our HumanPlayer does not need to "make" a move, as it is
    // waiting for a input from the view.
  }

  public void placePiece() {
    observer.placePiece();
  }

  @Override
  public void addControllerListener(ReversiController cont) {
    this.observer = cont;
  }
}