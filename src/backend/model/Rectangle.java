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
       return p.getX() > this.getTopLeft().getX() && p.getX() < this.getBottomRight().getX() &&
               p.getY() > this.getTopLeft().getY() && p.getY() < this.getBottomRight().getY();
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
       /* double centerX = (topLeft.getX() + bottomRight.getX()) / 2;
        double centerY = (topLeft.getY() + bottomRight.getY()) / 2;

        double x1 = topLeft.getX() - centerX;
        double y1 = topLeft.getY() - centerY;
        double x2 = bottomRight.getX() - centerX;
        double y2 = bottomRight.getY() - centerY;

        double newX1 = -y1;
        double newY1 = x1;
        double newX2 = -y2;
        double newY2 = x2;

        topLeft = new Point(newX1 + centerX, newY1 + centerY);
        bottomRight = new Point(newX2 + centerX, newY2 + centerY);*/

/*        double distX = bottomRight.getX() - topLeft.getX();
        double distY = bottomRight.getY() - topLeft.getY();

        double resp = (distX - distY) / 2;

        topLeft = new Point(topLeft.getX() + distX, topLeft.getY() - distY);
        bottomRight = new Point(bottomRight.getX() - distX, bottomRight.getY() + distY);*/

    }
}
