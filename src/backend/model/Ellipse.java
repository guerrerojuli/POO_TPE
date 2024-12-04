package backend.model;

public class Ellipse implements Figure {

    private Point centerPoint;
    private double diagonalX, diagonalY;

    public Ellipse(Point centerPoint, double diagonalX, double diagonalY) {
        this.centerPoint = centerPoint;
        this.diagonalX = diagonalX;
        this.diagonalY = diagonalY;
    }

    @Override
    public void move(double dx, double dy) {
        centerPoint.move(dx, dy);
    }

    // Nota: Fórmula aproximada. No es necesario corregirla.
    @Override
    public boolean contains(Point p) {
        return ((Math.pow(p.getX() - this.centerPoint.getX(), 2) / Math.pow(this.diagonalX, 2)) +
                (Math.pow(p.getY() - this.centerPoint.getY(), 2) / Math.pow(this.diagonalY, 2))) <= 0.30;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getDiagonalX() {
        return diagonalX;
    }

    public double getDiagonalY() {
        return diagonalY;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, diagonalX, diagonalY);
    }

    @Override
    public void rotate() {
        double aux = diagonalX;
        diagonalX = diagonalY;
        diagonalY = aux;
    }

    @Override
    public void flipH() {
        centerPoint = new Point(centerPoint.getX() + diagonalX, centerPoint.getY());
    }

    @Override
    public void flipV() {
        centerPoint = new Point(centerPoint.getX(), centerPoint.getY() + diagonalY);
    }
}
