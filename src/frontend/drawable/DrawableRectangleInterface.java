package frontend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public interface DrawableRectangleInterface extends DrawableFigure{
    Point getTopLeft();
    Point getBottomRight();

    @Override
    default void drawShape(GraphicsContext gc){
        gc.fillRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
        gc.strokeRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
    }

    @Override
    default void drawGradient(GraphicsContext gc) {
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getFormat().getFirstFillColor()),
                new Stop(1, getFormat().getSecondFillColor()));
        gc.setFill(linearGradient);
    }

    @Override
    default void drawBezel(GraphicsContext gc) {
        if(getFormat().hasBeveled()){
            double x = this.getTopLeft().getX();
            double y = this.getTopLeft().getY();
            double width = Math.abs(x - this.getBottomRight().getX());
            double height = Math.abs(y - this.getBottomRight().getY());
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(x, y, x + width, y);
            gc.strokeLine(x, y, x, y + height);
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x + width, y, x + width, y + height);
            gc.strokeLine(x, y + height, x + width, y + height);
            gc.setLineWidth(1);
        }
    }


}
