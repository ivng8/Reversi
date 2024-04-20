package cs3500.reversi.view.components;

import java.awt.Graphics;
import cs3500.reversi.view.ReversiPanel;

/**
 * The {@code IReversiComponent} interface defines the essential behaviors of components
 * used in the graphical representation of a Reversi game. Implementations of this interface
 * should provide specific functionalities such as rendering graphical elements, toggling
 * their visibility or state, and integrating with the main game panel.
 *
 * <p>Key functionalities include:
 * <ul>
 *   <li>Rendering the component onto the game board.</li>
 *   <li>Toggling the component's state or visibility.</li>
 *   <li>Adding an underlay to the main game panel.</li>
 * </ul>
 */
public interface IReversiComponent {

  /**
   * Renders this component on the provided {@link Graphics} object.
   * This method is responsible for drawing the component's visual representation on the game board.
   *
   * @param g The {@link Graphics} object on which the component is to be rendered.
   */
  void render(Graphics g);

  /**
   * Toggles the state or visibility of this component.
   * This method can be used to show or hide the component, or change its appearance based on the
   * game state.
   */
  void toggle();

  /**
   * Adds this component as an underlay to the specified {@link ReversiPanel}.
   * This method allows the component to be integrated into the main game panel,
   * ensuring that it is rendered appropriately within the game's graphical interface.
   *
   * @param panel The {@link ReversiPanel} to which this component will be added.
   */
  void addUnderlay(ReversiPanel panel);
}
