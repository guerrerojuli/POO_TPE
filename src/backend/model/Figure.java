package backend.model;

import javafx.scene.canvas.GraphicsContext;

public interface Figure {
    void draw(GraphicsContext gc);

    void drawBeveled(GraphicsContext gc);
}
