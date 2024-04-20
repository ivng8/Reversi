package cs3500.reversi.controller.commands;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.ReversiModel;

/**
 * Object function to place a piece.
 */
public class Place implements ReversiCommand {

  private CubicPosn toPlace;

  /**
   * Constructor for Place.
   * @param posn coordinate to place on
   */
  public Place(CubicPosn posn) {
    this.toPlace = posn;
  }

  /**
   * Executes placing the piece in the game.
   * @param model the game
   */
  @Override
  public void goStart(ReversiModel model) {
    model.placeTile(this.toPlace);
  }
}
