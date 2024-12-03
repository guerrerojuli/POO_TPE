package frontend.drawable;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;

public interface DrawableEllipseInterface extends DrawableFigure {
    Point getCenterPoint();
    double getDiagonalX();
    double getDiagonalY();

    @Override
    default void drawShape(GraphicsContext gc) {
        gc.strokeOval(this.getCenterPoint().getX() - (this.getDiagonalX() / 2), this.getCenterPoint().getY() - (this.getDiagonalY() / 2), this.getDiagonalX(), this.getDiagonalY());
        gc.fillOval(this.getCenterPoint().getX() - (this.getDiagonalX() / 2), this.getCenterPoint().getY() - (this.getDiagonalY() / 2), this.getDiagonalX(), this.getDiagonalY());
    }

    @Override
    default void drawGradient(GraphicsContext gc) {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getFormat().getFirstFillColor()),
                new Stop(1, getFormat().getSecondFillColor()));
        gc.setFill(radialGradient);
    }

    @Override
    default void drawBezel(GraphicsContext gc) {
        if(getFormat().hasBeveled()){
            double arcX = this.getCenterPoint().getX() - this.getDiagonalX()/2;
            double arcY = this.getCenterPoint().getY() - this.getDiagonalY()/2;
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, this.getDiagonalX(), this.getDiagonalY(), 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, this.getDiagonalX(), this.getDiagonalY(), 225, 180, ArcType.OPEN);
            gc.setLineWidth(1);
        }
    }

    @Override
    default DrawableFigure duplicate(){
            return new DrawableEllipse(new Point(getCenterPoint().getX() + OFFSET,
                    getCenterPoint().getY() + OFFSET), getDiagonalX(), getDiagonalY(), getFormat());
        }

    @Override
    default ArrayList<DrawableFigure> divide(){
        ArrayList<DrawableFigure> divided = new ArrayList<>();
        double newDiagonalX = getDiagonalX() / 2;
        double newDiagonalY = getDiagonalY() / 2;

        divided.add(new DrawableEllipse(new Point(getCenterPoint().getX() - newDiagonalX / 2,
                getCenterPoint().getY()), newDiagonalX, newDiagonalY, getFormat()));

        divided.add(new DrawableEllipse(new Point(getCenterPoint().getX() + newDiagonalX / 2,
                getCenterPoint().getY()), newDiagonalX, newDiagonalY, getFormat()));

        return divided;
    }
}
