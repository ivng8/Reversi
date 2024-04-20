package cs3500.reversi.view;

import cs3500.reversi.features.ViewEvent;
import cs3500.reversi.model.DiskState;

/**
 * The {@code IView} interface defines the essential functionalities of a graphical view
 * for a Reversi game. Implementations of this interface are responsible for visually
 * representing the game state, handling user interactions, and displaying game-specific
 * information such as scores and current player details.
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>Refreshing the view to update the visual representation of the game state.</li>
 *   <li>Displaying error messages to inform users of invalid actions or game states.</li>
 *   <li>Controlling the visibility of the game board or view components.</li>
 *   <li>Updating the score display for each player.</li>
 *   <li>Indicating which player's turn it is (e.g., white or black).</li>
 * </ul>
 *
 * <p>Implementations of this interface should provide a concrete graphical user interface
 * (GUI) for players to interact with the Reversi game. This includes rendering the game
 * board, providing controls for game actions, and displaying relevant game information.
 *
 * <p>This interface extends {@link ViewEvent}, allowing implementations to interact with
 * event listeners for processing user actions such as placing tiles or passing turns.
 *
 */
public interface IView extends ViewEvent {

  /**
   * refreshes the rendering.
   */
  void refresh();

  /**
   * shows a given error.
   * @param s the error as a string
   */
  void showError(String s);

  /**
   * sets the visibility of the board.
   * @param b boolean on if the board should be visible or not
   */
  void setVisible(boolean b);

  /**
   * updates score display of the given player.
   * @param state the player
   */
  void updateScore(DiskState state);

  /**
   * updates players view with their disk (white or black player).
   * @param state their disk
   */
  void updatePlayer(DiskState state);
}
