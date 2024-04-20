package cs3500.reversi.player;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * represents an AI player that is embedded into the game, makes self determined moves.
 */
public class MachinePlayer implements Player {

  // This cannot be readOnly, as the way we designed our model we believe that moving within the
  // player allows for ease of calculation, without cluttering up code and threading through to the
  // controller for dispatch. If we did, we would be caught relaying code back and forth between
  // the controller and the player.
  private final ReversiModel model;

  private final ReversiStrategy strategy;

  /**
   * constructor for an AI player.
   * @param model the game it is in
   * @param strategy the strategy(ies) that it uses
   */
  public MachinePlayer(ReversiModel model, ReversiStrategy strategy) {
    this.model = model;
    this.strategy = strategy;
  }

  @Override
  public void makeMove() {
    DiskState state = DiskState.BLACK;
    if (model.isCurrentPlayer(DiskState.WHITE)) {
      state = DiskState.WHITE;
    } try {
      model.placeTile(strategy.chooseMove(model, state));
    } catch (Exception e) {
      model.skipTurn();
    }
  }

  @Override
  public void placePiece() {
    // Our method does not need to recieve a place piece method, as we do not need to dispatch
    // back to our controller for the turn checking, this is an invariant as our onPlayer
    // listener only dispatches when the player is correct.
  }

  @Override
  public void addControllerListener(ReversiController cont) {
    // doesnt need a observer, as in our implementation our player is automated.
  }
}
