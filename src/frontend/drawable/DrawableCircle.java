package frontend.drawable;

import backend.model.Circle;
import backend.model.Point;
import frontend.format.Format;

public class DrawableCircle extends Circle implements DrawableEllipseInterface {
    private Format format;

    public DrawableCircle(Point centerPoint, double radius, Format format) {
        super(centerPoint, radius);
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
