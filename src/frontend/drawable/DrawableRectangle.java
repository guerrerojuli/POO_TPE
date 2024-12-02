package frontend.drawable;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.format.Format;

public class DrawableRectangle extends Rectangle implements DrawableRectangleInterface {
    private Format format;

    public DrawableRectangle(Point topLeft, Point bottomRight, Format format) {
        super(topLeft, bottomRight);
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
