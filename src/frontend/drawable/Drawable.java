package frontend.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Drawable{
    default void draw(GraphicsContext gc, boolean isSelected){
        gc.setStroke(isSelected ? Color.RED : Color.BLACK);
        drawShadow(gc);
        drawGradient(gc);
        drawBezel(gc);
        drawShape(gc);
    }

    void drawShape(GraphicsContext gc);
    void drawShadow(GraphicsContext gc);
    void drawGradient(GraphicsContext gc);
    void drawBezel(GraphicsContext gc);
}
