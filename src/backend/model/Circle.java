package backend.model;

public class Circle extends Ellipse{

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }

    // Se sobreescribe para usar una formula más precisa
    @Override
    public boolean contains(Point p) {
        return Math.sqrt(Math.pow(this.getCenterPoint().getX() - p.getX(), 2) +
                Math.pow(this.getCenterPoint().getY() - p.getY(), 2)) < this.getRadius();
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, sMayorAxis);
    }

    public double getRadius() {
        return getsMayorAxis()/2;
    }

}
