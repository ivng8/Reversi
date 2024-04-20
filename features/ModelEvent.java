package cs3500.reversi.features;

import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a model event listener with the capability to add itself to a data structure.
 * This is a features interface.
 */
public interface ModelEvent {

  /**
   * adds a model event listener to this.
   * @param listener the listener
   */
  void addModelEventListener(ModelEventListener listener);

  /**
   * represents a listener for events that affect the model to relay commands to other structures.
   */
  interface ModelEventListener {

    /**
     * syncs all data structures (view) to its corresponding response that the game has started.
     * @param model the game itself
     * @param radius the size of the game
     */
    void startGame(ReversiModel model, int radius);

    /**
     * identifies a specific player to make a move.
     * @param turn the player whose turn it is to move
     */
    void onPlayer(DiskState turn);

    /**
     * updates the view based on player actions in the model.
     */
    void onAction();
  }
}
