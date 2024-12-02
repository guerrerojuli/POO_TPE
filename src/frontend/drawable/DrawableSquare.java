package frontend.drawable;

import backend.model.Point;
import backend.model.Square;
import frontend.format.Format;

public class DrawableSquare extends Square implements DrawableRectangleInterface {
    private Format format;

    public DrawableSquare(Point topLeft, double size, Format format) {
        super(topLeft, size);
        this.format = format;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public void setFormat(Format format) {
        this.format = format;
    }
}
