package cs3500.reversi.controller.commands;

import cs3500.reversi.model.ReversiModel;

/**
 * Interface that represents game commands for the command pattern.
 */
public interface ReversiCommand {

  /**
   * Go allows for function objects to execute their function.
   * @param model the game to be executed on
   */
  void goStart(ReversiModel model);
}
