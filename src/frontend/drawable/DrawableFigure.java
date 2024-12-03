package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface DrawableFigure extends Drawable, Figure{
    double OFFSET = 20;
    void setFormat(Format format);
    Format getFormat();

    @Override
    default void drawShadow(GraphicsContext gc){
        getFormat().getShadow().drawShadow(gc, this, getFormat().getFirstFillColor());
    }

    DrawableFigure duplicate();
    ArrayList<DrawableFigure> divide();

}
