package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public interface DrawableFigure extends Figure {
    double OFFSET = 20;

    DrawableFigure duplicate();
    ArrayList<DrawableFigure> divide();

    void draw(GraphicsContext gc);
    void drawGradient(GraphicsContext gc, Format format);
    void drawBezel(GraphicsContext gc);

}
