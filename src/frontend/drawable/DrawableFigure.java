package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableFigure extends Drawable, Figure{
    void setFormat(Format format);
    Format getFormat();

    @Override
    default void drawShadow(GraphicsContext gc){
        getFormat().getShadow().drawShadow(gc, this, getFormat().getFirstFillColor());
    }
}
