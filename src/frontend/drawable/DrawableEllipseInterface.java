package frontend.drawable;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public interface DrawableEllipseInterface extends DrawableFigure {
    Point getCenterPoint();
    double getsMayorAxis();
    double getsMinorAxis();

    @Override
    default void drawShape(GraphicsContext gc) {
        gc.strokeOval(this.getCenterPoint().getX() - (this.getsMayorAxis() / 2), this.getCenterPoint().getY() - (this.getsMinorAxis() / 2), this.getsMayorAxis(), this.getsMinorAxis());
        gc.fillOval(this.getCenterPoint().getX() - (this.getsMayorAxis() / 2), this.getCenterPoint().getY() - (this.getsMinorAxis() / 2), this.getsMayorAxis(), this.getsMinorAxis());
    }

    @Override
    default void drawGradient(GraphicsContext gc) {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getFormat().getFirstFillColor()),
                new Stop(1, getFormat().getSecondFillColor()));
        gc.setFill(radialGradient);
    }

    @Override
    default void drawBezel(GraphicsContext gc) {
        if(getFormat().hasBeveled()){
            double arcX = this.getCenterPoint().getX() - this.getsMayorAxis()/2;
            double arcY = this.getCenterPoint().getY() - this.getsMinorAxis()/2;
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, this.getsMayorAxis(), this.getsMinorAxis(), 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, this.getsMayorAxis(), this.getsMinorAxis(), 225, 180, ArcType.OPEN);
            gc.setLineWidth(1);
        }
    }
}
