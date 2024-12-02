package frontend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DrawableRectangle<T extends Rectangle> extends DrawableFigure<T>{
    public DrawableRectangle(T figure, GraphicsContext gc, Format format) {
        super(figure, gc, format);
    }

    @Override
    public void drawShape(){
        Rectangle rectangle = getFigure();
        getGc().fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        getGc().strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

    @Override
    public void drawGradient() {
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getFormat().getFirstFillColor()),
                new Stop(1, getFormat().getSecondFillColor()));
        getGc().setFill(linearGradient);
    }

    @Override
    public void drawBezel() {
        if(getFormat().hasBeveled()){
            double x = getFigure().getTopLeft().getX();
            double y = getFigure().getTopLeft().getY();
            double width = Math.abs(x - getFigure().getBottomRight().getX());
            double height = Math.abs(y - getFigure().getBottomRight().getY());
            getGc().setLineWidth(10);
            getGc().setStroke(Color.LIGHTGRAY);
            getGc().strokeLine(x, y, x + width, y);
            getGc().strokeLine(x, y, x, y + height);
            getGc().setStroke(Color.BLACK);
            getGc().strokeLine(x + width, y, x + width, y + height);
            getGc().strokeLine(x, y + height, x + width, y + height);
            getGc().setLineWidth(1);
        }
    }


}
