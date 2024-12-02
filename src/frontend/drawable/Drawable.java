package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface Drawable extends Figure {
    default void draw(GraphicsContext gc){
        // Get all the figures features
        Format format = this.getFormat();
        // Draw the corresponding shade type
        format.getShadow().drawShade(gc, this, format.getFirstFillColor() );
        // Set the gradient fill
        gc.setFill(this.getFill(format.getFirstFillColor(), format.getSecondFillColor()));
        // Draw the figure
        this.drawShape(gc);
    }

    void drawShape(GraphicsContext gc);
    Paint getFill(Color color1, Color color2);
    void setFormat(Format format);
    Format getFormat();
}
