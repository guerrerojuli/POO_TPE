package frontend.drawable;

import backend.model.Ellipse;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableEllipse<T extends Ellipse> extends DrawableFigure<T> {
    public DrawableEllipse(T figure, GraphicsContext gc, Format format){
        super(figure, gc, format);
    }

    @Override
    public void drawShape() {
        Ellipse ellipse = getFigure();
        getGc().strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        getGc().fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    public void drawGradient() {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getFormat().getFirstFillColor()),
                new Stop(1, getFormat().getSecondFillColor()));
        getGc().setFill(radialGradient);
    }

    @Override
    public void drawBezel() {
        if(getFormat().hasBeveled()){
            double arcX = getFigure().getCenterPoint().getX() - getFigure().getsMayorAxis()/2;
            double arcY = getFigure().getCenterPoint().getY() - getFigure().getsMinorAxis()/2;
            getGc().setLineWidth(10);
            getGc().setStroke(Color.LIGHTGRAY);
            getGc().strokeArc(arcX, arcY, getFigure().getsMayorAxis(), getFigure().getsMinorAxis(), 45, 180, ArcType.OPEN);
            getGc().setStroke(Color.BLACK);
            getGc().strokeArc(arcX, arcY, getFigure().getsMayorAxis(), getFigure().getsMinorAxis(), 225, 180, ArcType.OPEN);
            getGc().setLineWidth(1);
        }
    }
}
