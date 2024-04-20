package cs3500.reversi.model;

/**
 * represents the 3D axial coordinates in a hexagonal board.
 */
public class CubicPosn {
  public final int x;
  public final int y;

  /**
   * constructor for a CubicPosn.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public CubicPosn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * overrides definition of equality for CubicPosn.
   *
   * @param obj an object
   * @return if object is the same as the CubicPosn
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CubicPosn other = (CubicPosn) obj;
    return x == other.x && y == other.y;
  }

  /**
   * hashcode a CubicPosn.
   *
   * @return int hashCode
   */
  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  /**
   * getter for x coordinate.
   *
   * @return x coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * getter for y coordinate.
   *
   * @return y coordinate
   */
  public int getY() {
    return this.y;
  }

  /**
   * converts the CubicPosn to a string representation.
   * @return a string representation of the coordinate
   */
  @Override
  public String toString() {
    return this.x + " " + this.y;
  }
}
