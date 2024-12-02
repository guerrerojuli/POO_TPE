package frontend.drawable;

import backend.model.Ellipse;
import backend.model.Point;
import frontend.format.Format;

public class DrawableEllipse extends Ellipse implements DrawableEllipseInterface {
    private Format format;

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Format format) {
        super(centerPoint, sMayorAxis, sMinorAxis);
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
