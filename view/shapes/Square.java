package cs3500.reversi.view.shapes;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Represents a square shape in a Reversi game view.
 * This class extends {@link Rectangle2D.Double} and implements the {@link IShape} interface.
 * A square is defined by its center point and its size (length of each side).
 */
public class Square extends Rectangle2D.Double implements IShape {

  private final Point2D center;
  private final double size;

  /**
   * Constructs a new Square instance.
   * The square is defined by the center coordinates and the size (length of each side).
   * The square's upper-left corner coordinates are calculated based in the center and size.
   *
   * @param centerX the x-coordinate of the square's center
   * @param centerY the y-coordinate of the square's center
   * @param size    the length of each side of the square
   */
  public Square(double centerX, double centerY, double size) {
    super(centerX - size / 2, centerY - size / 2, size, size);
    this.center = new Point2D.Double(centerX, centerY);
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Square)) {
      return false;
    }
    Square square = (Square) o;
    return square.size == size &&
            center == square.center;
  }

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
