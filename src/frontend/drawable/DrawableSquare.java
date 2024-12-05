package frontend.drawable;

import backend.model.Point;
import backend.model.Square;

import java.util.ArrayList;

public class DrawableSquare extends Square implements DrawableRectangleInterface {
    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, size);
    }

    @Override
    public DrawableFigure duplicate(){
        return new DrawableSquare(new Point(getTopLeft().getX() + OFFSET, getTopLeft().getY() + OFFSET), getSize());

    }

    @Override
    public ArrayList<DrawableFigure> divide(){
        ArrayList<DrawableFigure> divided = new ArrayList<>();
        double base = getBottomRight().getX() - getTopLeft().getX();
        double height = getBottomRight().getY() - getTopLeft().getY();

        divided.add(new DrawableSquare(new Point(getTopLeft().getX(), getTopLeft().getY() + height / 4), getSize() / 2));
        divided.add(new DrawableSquare(new Point(getTopLeft().getX() + base / 2, getTopLeft().getY() + height / 4), getSize() / 2));

        return divided;
    }
}
