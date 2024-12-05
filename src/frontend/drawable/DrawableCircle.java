package frontend.drawable;

import backend.model.Circle;
import backend.model.Point;

import java.util.ArrayList;

public class DrawableCircle extends Circle implements DrawableEllipseInterface {
    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius);
    }

    public DrawableFigure duplicate(){
        return new DrawableCircle(new Point(getCenterPoint().getX() + OFFSET,
                getCenterPoint().getY() + OFFSET), getRadius());
    }

    @Override
    public ArrayList<DrawableFigure> divide(){
        ArrayList<DrawableFigure> divided = new ArrayList<>();

        divided.add(dividend(- getDiagonalX() / 4));
        divided.add(dividend( getDiagonalX() / 4));

        return divided;
    }

    private DrawableCircle dividend(double offset){
        return new DrawableCircle(new Point(getCenterPoint().getX() + offset,
                getCenterPoint().getY()), getRadius() / 2);
    }
}
