package cs3500.reversi.view.components;

import java.awt.Graphics;
import java.util.List;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.view.ReversiPanel;
import cs3500.reversi.view.shapes.IShape;

/**
 * The {@code ShowHint} class implements {@link IReversiComponent} to provide hint functionalities
 * in a Reversi game. It is designed to display strategic information on the game board, such as
 * possible moves or advantageous positions, based on the current game state and player.
 */
public class ShowHint implements IReversiComponent {
  private boolean visible = false;

  private final ReadOnlyReversiModel model;

  private ReversiPanel panel;

  private final DiskState player;

  /**
   * Constructs a ShowHint component for the Reversi game.
   *
   * @param model  The current game model from which hints will be derived.
   * @param player The player for whom the hints are being generated.
   */
  public ShowHint(ReadOnlyReversiModel model, DiskState player) {
    this.model = model;
    this.player = player;
  }

  @Override
  public void render(Graphics g) {
    if (visible) {
      try {
        int count = 0;
        for (List<CubicPosn> list : model.findAllSeams(panel.getSelectedPosn(), player)) {
          count += list.size() - 1;
        }
        IShape shape = panel.getShapeMap().get(panel.getSelectedPosn());
        g.drawString(String.valueOf(count),
                ((int) shape.getCenter().getX()), ((int) shape.getCenter().getY()) + 40);
      } catch (Exception ignore) {
        return;
      }
    }
  }

  @Override
  public void toggle() {
    visible = !visible;
  }

  @Override
  public void addUnderlay(ReversiPanel panel) {
    this.panel = panel;
  }
}
