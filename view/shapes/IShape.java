package cs3500.reversi.view.shapes;

import java.awt.geom.Point2D;

/**
 * The {@code IShape} interface defines the essential behaviors of a shape in the context of the
 * Reversi game. This interface is primarily focused on spatial properties of shapes, such as
 * determining whether a point lies within a shape and getting the center point of the shape.
 */
public interface IShape {

  /**
   * Checks if the given point (x, y) is contained within this shape.
   *
   * @param x the x-coordinate of the point
   * @param y the y-coordinate of the point
   * @return {@code true} if the point is inside the shape, {@code false} otherwise
   */
  boolean containsPoint(int x, int y);

  /**
   * Retrieves the center point of this shape.
   *
   * @return A {@link Point2D} object representing the center of the shape.
   */
  Point2D getCenter();
}
