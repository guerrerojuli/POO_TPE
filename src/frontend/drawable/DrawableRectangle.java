package frontend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DrawableRectangle extends Rectangle implements Drawable {
    private Format format;
    public DrawableRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public void drawShape(GraphicsContext gc) {
        format.getShadow().drawShadow(gc, this, );
        gc.fillRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
        gc.strokeRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
    }

    @Override
    public Paint getFill(Color color1, Color color2){

    }

    @Override
    public void setFormat(Format format){

    }

    @Override
    public Format getFormat(){

    }
}
