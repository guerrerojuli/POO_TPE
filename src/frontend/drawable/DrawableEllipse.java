package frontend.drawable;

import backend.model.Ellipse;
import backend.model.Point;

import java.util.ArrayList;

public class DrawableEllipse extends Ellipse implements DrawableEllipseInterface {
    public DrawableEllipse(Point centerPoint, double diagonalX, double diagonalY) {
        super(centerPoint, diagonalX, diagonalY);
    }

    public DrawableFigure duplicate(){
        return new DrawableEllipse(new Point(getCenterPoint().getX() + OFFSET,
                getCenterPoint().getY() + OFFSET), getDiagonalX(), getDiagonalY());
    }

    @Override
    public ArrayList<DrawableFigure> divide(){
        ArrayList<DrawableFigure> divided = new ArrayList<>();

        divided.add(dividend(- getDiagonalX() / 4));
        divided.add(dividend( getDiagonalX() / 4));

        return divided;
    }


    private DrawableEllipse dividend(double offset){
        return new DrawableEllipse(new Point(getCenterPoint().getX() + offset,
                getCenterPoint().getY()), getDiagonalX() / 2, getDiagonalY() / 2);
    }
}
