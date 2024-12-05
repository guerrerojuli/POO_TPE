package backend.model;

public class Rectangle implements Figure {

    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void move(double dx, double dy) {
        topLeft.move(dx, dy);
        bottomRight.move(dx, dy);
    }

    @Override
    public boolean contains(Point p) {
       return p.getX() > getTopLeft().getX() && p.getX() < getBottomRight().getX() &&
               p.getY() > getTopLeft().getY() && p.getY() < getBottomRight().getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }


    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public void rotate() {

        double distX = bottomRight.getX() - topLeft.getX();
        double distY = bottomRight.getY() - topLeft.getY();

        double resp = (distX - distY) / 2;

        topLeft = new Point(topLeft.getX() + resp, topLeft.getY() - resp);
        bottomRight = new Point(bottomRight.getX() - resp, bottomRight.getY() + resp);

    }

    @Override
    public void flipH() {
        double incX = bottomRight.getX() - topLeft.getX();
        bottomRight = new Point(bottomRight.getX() + incX, bottomRight.getY());
        topLeft = new Point(topLeft.getX() + incX, topLeft.getY());
    }

    @Override
    public void flipV() {
        double incY = bottomRight.getY() - topLeft.getY();
        bottomRight = new Point(bottomRight.getX(), bottomRight.getY() + incY);
        topLeft = new Point(topLeft.getX(), topLeft.getY() + incY);
    }
}
