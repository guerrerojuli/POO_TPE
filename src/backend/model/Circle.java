package backend.model;

public class Circle extends Ellipse{

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }

    // Se sobreescribe para usar una formula más precisa
    @Override
    public boolean contains(Point p) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - p.getX(), 2) +
                Math.pow(getCenterPoint().getY() - p.getY(), 2)) < this.getRadius();
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getDiagonalX());
    }

    public double getRadius() {
        return getDiagonalX()/2;
    }

}
