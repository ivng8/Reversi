package cs3500.reversi.controller.commands;

import cs3500.reversi.model.ReversiModel;

/**
 * Object function to pass in game.
 */
public class Pass implements ReversiCommand {

  /**
   * Executes passing in game.
   * @param model the game
   */
  @Override
  public void goStart(ReversiModel model) {
    model.skipTurn();
  }
}
