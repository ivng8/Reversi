package cs3500.reversi.controller;

import cs3500.reversi.model.ReversiModel;

/**
 * A interface representing different controllers to be used in a game of Reversi.
 */
public interface ReversiController {

  /**
   * Primary way to play and end game.
   *
   * @param model  the game model
   * @param radius the size of the game
   */
  void startGame(ReversiModel model, int radius);

  /**
   * places a piece down in the game.
   */
  void placePiece();

}
