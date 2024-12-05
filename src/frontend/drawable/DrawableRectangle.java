package frontend.drawable;

import backend.model.Point;
import backend.model.Rectangle;

import java.util.ArrayList;

public class DrawableRectangle extends Rectangle implements DrawableRectangleInterface {
    public DrawableRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public DrawableFigure duplicate(){
        return new DrawableRectangle(new Point(getTopLeft().getX() + OFFSET, getTopLeft().getY() + OFFSET),
                new Point(getBottomRight().getX() + OFFSET, getBottomRight().getY() + OFFSET));
    }

    @Override
    public ArrayList<DrawableFigure> divide(){
        ArrayList<DrawableFigure> divided = new ArrayList<>();
        double base = getBottomRight().getX() - getTopLeft().getX();
        double height = getBottomRight().getY() - getTopLeft().getY();

        divided.add(new DrawableRectangle(new Point(getTopLeft().getX(), getTopLeft().getY() + height / 4),
                new Point(getBottomRight().getX() - base / 2, getBottomRight().getY() - height / 4)));

        divided.add(new DrawableRectangle(new Point(getTopLeft().getX() + base / 2, getTopLeft().getY() + height / 4),
                new Point(getBottomRight().getX(), getBottomRight().getY() - height / 4)));

        return divided;
    }
}
