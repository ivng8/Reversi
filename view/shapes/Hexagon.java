package cs3500.reversi.view.shapes;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * represents a hexagon tile in the graphic view of the board.
 */
public class Hexagon extends Path2D.Double implements IShape {

  private final Point2D center;

  private final double size;

  /**
   * constructor for the hexagon.
   *
   * @param centerX the x-coordinate of the center
   * @param centerY the y-coordinate of the center
   * @param size the side length
   */
  public Hexagon(double centerX, double centerY, double size) {
    this.center = new Point2D.Double(centerX, centerY);
    this.size = size;
    for (int i = 0; i < 6; i++) {
      // Calculations for rotating "head" of path in progress
      double x = centerX + size * Math.cos(i * Math.PI / 3 + Math.PI / 2);
      double y = centerY + size * Math.sin(i * Math.PI / 3 + Math.PI / 2);

      if (i == 0) {
        moveTo(x, y);
      } else {
        lineTo(x, y);
      }
    }
    closePath(); // Closes path after 6 edges drawn
  }

  /**
   * overrides defintion of equality for a hexagon.
   * @param o object to be compared to
   * @return if the object is the same as the hexagon
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Hexagon)) {
      return false;
    }
    Hexagon hexagon = (Hexagon) o;
    return hexagon.size == size &&
            center == hexagon.center;
  }

  /**
   * hashcodes a hexagon.
   * @return a int hashcode of a hexagon
   */
  @Override
  public int hashCode() {
    return Objects.hash(center, size);
  }

  @Override
  public boolean containsPoint(int x, int y) {
    return this.contains(x, y);
  }

  @Override
  public Point2D getCenter() {
    return center;
  }
}