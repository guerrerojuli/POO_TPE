package backend.model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public void move(double dx, double dy) {
        topLeft.move(dx, dy);
        bottomRight.move(dx, dy);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }
}
