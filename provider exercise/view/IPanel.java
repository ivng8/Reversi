package cs3500.reversi.view;

import cs3500.reversi.model.CubicPosn;

/**
 * manages rendering the hexagons and circles for the game.
 */
public interface IPanel {

  /**
   * getter for the CubicPosn selected.
   * @return the point on the board
   */
  public CubicPosn getSelectedPosn();
}
