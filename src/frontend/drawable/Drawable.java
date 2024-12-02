package frontend.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Drawable{
    default void draw(GraphicsContext gc, boolean isSelected){
        gc.setStroke(Color.TRANSPARENT);
        drawShadow(gc);
        drawGradient(gc);
        drawBezel(gc);
        gc.setStroke(isSelected ? Color.RED : Color.BLACK);
        drawShape(gc);
    }

    void drawShape(GraphicsContext gc);
    void drawShadow(GraphicsContext gc);
    void drawGradient(GraphicsContext gc);
    void drawBezel(GraphicsContext gc);
}
