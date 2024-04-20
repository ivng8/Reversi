package cs3500.reversi.view;

import cs3500.reversi.features.ViewEvent;
import cs3500.reversi.model.DiskState;

/**
 * The {@code IView} interface defines essential functionalities for a graphical view in a Reversi
 * game.
 * Implementations of this interface are responsible for visually representing the game state,
 * handling user interactions, and displaying game-specific information such as scores and current
 * player details.
 *
 * <p>This interface extends {@link ViewEvent}, allowing implementations to interact with
 * event listeners for processing user actions such as placing tiles or passing turns.
 */
public interface IView extends ViewEvent {

  /**
   * Refreshes the rendering of the view. This method should be called to update
   * the visual representation of the game state, such as after a move is made or
   * the game state changes.
   */
  void refresh();

  /**
   * Displays an error message to the user.
   *
   * @param s the error message as a string
   */
  void showError(String s);

  /**
   * Sets the visibility of the game board or view components.
   *
   * @param b {@code true} to make the board visible, {@code false} to hide it
   */
  void setVisible(boolean b);

  /**
   * Updates the score display for the specified player.
   *
   * @param state the state of the player (e.g., white or black) whose score needs updating
   */
  void updateScore(DiskState state);

  /**
   * Updates the player view with their disk color (white or black).
   *
   * @param state the disk state representing the player (white or black)
   */
  void updatePlayer(DiskState state);

  /**
   * Adds a component to the view that provides hints to the player.
   * This method is responsible for enhancing the user interface with helpful hints
   * or suggestions, potentially aiding in strategy development or move selection.
   */
  void addHintComponent();
}
