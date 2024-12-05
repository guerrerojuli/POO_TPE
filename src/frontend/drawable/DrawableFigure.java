package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public interface DrawableFigure extends Figure {
    double OFFSET = 20;

    DrawableFigure duplicate();
    ArrayList<DrawableFigure> divide();

    default void draw(GraphicsContext gc, boolean isSelected){
        gc.setStroke(Color.TRANSPARENT);
        drawShadow(gc);
        drawGradient(gc);
        drawBezel(gc);
        gc.setStroke(isSelected ? Color.RED : Color.BLACK);
        drawShape(gc);
    }

    void drawShape(GraphicsContext gc);
    void drawGradient(GraphicsContext gc);
    void drawBezel(GraphicsContext gc);
    default void drawShadow(GraphicsContext gc){
        getFormat().getShadow().drawShadow(gc, this, getFormat().getFirstFillColor());
    }

    void setFormat(Format format);
    Format getFormat();
}
