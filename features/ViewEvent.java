package cs3500.reversi.features;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;

/**
 * Represents a view event listener with the capability to add itself to a data structure.
 * This is a features interface.
 */
public interface ViewEvent {

  /**
   * adds a view listener to this.
   * @param listener the listener
   */
  void addViewEventListener(ViewEventListener listener);

  /**
   * represents a listener for events in the view to relay commands to other structures.
   */
  interface ViewEventListener {

    /**
     * syncs data structures (player on correct turn) to corresponding commands to placing a piece.
     * @param posn the hex to place a piece on
     */
    void onMove(CubicPosn posn);

    /**
     * syncs data structures (model) to corresponding mutation upon passing.
     */
    void onPass();

    /**
     * Gives the host of the listener access to the turn, necessary for score and other turn based
     * decorators in order to not break encapsulation in those classes.
     */
    DiskState getTurn();
  }
}
