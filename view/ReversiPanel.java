package cs3500.reversi.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JPanel;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.view.shapes.Hexagon;
import cs3500.reversi.view.shapes.IShape;

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
public class ReversiPanel extends JPanel {

  protected final ReadOnlyReversiModel model;

  protected CubicPosn selectedPosn;

  protected final Map<CubicPosn, IShape> shapeMap = new HashMap<>();


  /**
   * constructor for the game panel.
   *
   * @param model the game
   */
  ReversiPanel(ReadOnlyReversiModel model) {
    this.model = model;
  }

  /**
   * composes hexagons to be placed in GUI.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    this.drawBoard(g2d); // Example coordinates and size
  }

  /**
   * renders the entire accumulation of the game board.
   *
   * @param g2d the images from the image library
   */
  protected void drawBoard(Graphics2D g2d) {
    double hexSize = 30; // Size of hexagon
    double hexWidth = Math.sqrt(3) * hexSize;
    double hexHeight = 2 * hexSize;

    Dimension panelSize = getSize();


    // Calculate the offset to center the grid in the panel
    double offsetX = (panelSize.getWidth()) / 2;
    double offsetY = (panelSize.getHeight()) / 2;

    for (Map.Entry<CubicPosn, DiskState> entry : model.getBoard().entrySet()) {
      CubicPosn coord = entry.getKey();
      DiskState state = entry.getValue();
      double centerX = offsetX + hexWidth * (coord.x + coord.y / 2.0);
      double centerY = offsetY + hexHeight * (3.0 / 4.0) * coord.y;

      Hexagon hex = new Hexagon(centerX, centerY, hexSize);
      shapeMap.put(coord, hex);
      if (coord.equals(this.selectedPosn) && state == DiskState.EMPTY) {
        g2d.setColor(Color.GREEN);
      } else {
        g2d.setColor(Color.LIGHT_GRAY);
      }
      g2d.fill(hex);
      g2d.setColor(Color.black);
      g2d.draw(hex);
      switch (state) {
        case BLACK:
          g2d.setColor(Color.BLACK);
          drawCenteredCircle(g2d, centerX, centerY, hexSize / 2);
          break;
        case WHITE:
          g2d.setColor(Color.WHITE);
          drawCenteredCircle(g2d, centerX, centerY, hexSize / 2);
          break;
        default:
          // No circle for empty hexagons
          break;
      }
    }
  }

  /**
   * renders the pieces that are placed in the board.
   *
   * @param g      the image
   * @param x      x-coordinate
   * @param y      y-coordinate
   * @param radius radius of the circle
   */
  protected void drawCenteredCircle(Graphics2D g, double x, double y, double radius) {
    Ellipse2D.Double circle = new Ellipse2D.Double(x - radius, y - radius,
            2 * radius, 2 * radius);
    g.fill(circle);
  }

  /**
   * returns the selected hex.
   *
   * @return the selected hex
   */
  public CubicPosn getSelectedPosn() {
    return this.selectedPosn;
  }

  /**
   * renders the hex selected by a user.
   *
   * @param x the x-coord
   * @param y the y-coord
   */
  public void renderSelection(int x, int y) {
    boolean inPanel = false;
    CubicPosn tempPosn = new CubicPosn(Integer.MAX_VALUE, Integer.MIN_VALUE);
    for (Map.Entry<CubicPosn, IShape> entry : shapeMap.entrySet()) {
      if (entry.getValue().containsPoint(x, y)) {
        tempPosn = entry.getKey();
        System.out.println(entry.getKey().toString());
        inPanel = true;
        break;
      }
    }

    // Dealing with out of bounds click or clicking on already placed tile.
    if (model.getBoard().get(tempPosn) != DiskState.EMPTY && inPanel) {
      return;
    } else if (tempPosn.equals(this.selectedPosn)) {
      this.selectedPosn = new CubicPosn(Integer.MAX_VALUE, Integer.MIN_VALUE);
    } else {
      this.selectedPosn = tempPosn;
    }
    repaint();
  }


  /**
   * Retrieves a copy of the current mapping between positions and shapes on the game board.
   * This map is used to represent the layout of various shapes (like game pieces or markers)
   * at positions on the game board. Each key in the map is a {@link CubicPosn} that denotes
   * a unique position on the board, and its corresponding value is an {@link IShape} representing
   * the shape at that position.
   *
   * <p>Returning a copy of the map ensures that the original map remains unaltered by external
   * modifications,
   * preserving the integrity of the game's internal state.
   *
   * @return A new {@link Map} object containing a copy of the position-to-shape mappings.
   */
  public Map<CubicPosn, IShape> getShapeMap() {
    return new HashMap<>(shapeMap);
  }
}
