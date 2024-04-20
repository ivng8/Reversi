package cs3500.reversi.view;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.view.shapes.IShape;
import cs3500.reversi.view.shapes.Square;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.util.Map;

import javax.swing.JPanel;

/**
 * {@code ReversiPanel} is a specialized panel that graphically represents the game board
 * of a Reversi game, specifically designed for rendering a hexagonal grid. It extends
 * {@link JPanel} to utilize the swing framework's capabilities for custom graphical rendering
 * and user interaction handling.
 *
 * <p>The panel visualizes each cell of the Reversi game as a hexagon. These hexagons are
 * dynamically generated based on the current game state provided by a {@link ReadOnlyReversiModel}.
 * Each hexagon can represent an empty cell, a black disk, or a white disk, reflecting the
 * ongoing state of the game. The class uses a {@link Map} to associate each {@link CubicPosn}
 * (representing the position of a cell on the hexagonal grid) with its corresponding
 * {@link IShape} object for efficient rendering and interaction processing.
 *
 * <p>Interaction with the hexagons is handled through mouse events. Users can click on
 * hexagons to interact with the game, such as placing a new disk on the board. The panel
 * also supports visual feedback for user actions, such as highlighting the selected hexagon.
 *
 * <p>Usage of this panel is primarily within a graphical user interface for Reversi, where it
 * acts as the central component displaying the game board. It is designed to be integrated
 * with other UI components like buttons and labels in a window or frame to create a complete
 * interactive game interface.
 */
public class SquareReversiPanel extends ReversiPanel {

  /**
   * constructor for the game panel.
   *
   * @param model the game
   */
  public SquareReversiPanel(ReadOnlyReversiModel model) {
    super(model);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    drawSquareBoard(g2d);
  }

  protected void drawSquareBoard(Graphics2D g2d) {
    double squareSize = 40; // Size of squares
    Dimension panelSize = getSize();

    // Calculate offset to center the grid in the panel
    double offsetX = (panelSize.getWidth() - model.getBoardSize() * squareSize) / 2;
    double offsetY = (panelSize.getHeight() - model.getBoardSize() * squareSize) / 2;

    for (Map.Entry<CubicPosn, DiskState> entry : model.getBoard().entrySet()) {
      CubicPosn coord = entry.getKey();
      DiskState state = entry.getValue();

      double centerX = offsetX + squareSize * coord.x;
      double centerY = offsetY + squareSize * coord.y;

      Square square = new Square(centerX, centerY, squareSize);
      shapeMap.put(coord, square);
      if (coord.equals(this.selectedPosn) && state == DiskState.EMPTY) {
        g2d.setColor(Color.GREEN);
      } else {
        g2d.setColor(Color.LIGHT_GRAY);
      }
      g2d.fill(square);
      g2d.setColor(Color.black);
      g2d.draw(square);
      switch (state) {
        case BLACK:
          g2d.setColor(Color.BLACK);
          drawCenteredCircle(g2d, centerX, centerY, squareSize / 2);
          break;
        case WHITE:
          g2d.setColor(Color.WHITE);
          drawCenteredCircle(g2d, centerX, centerY, squareSize / 2);
          break;
        default:
          break;
      }
    }
  }
}
