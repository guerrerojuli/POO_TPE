package frontend.drawable;

import backend.model.Figure;
import backend.model.Point;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class FormatedFigure implements Figure {
    private final DrawableFigure figure;
    private Format format;

    public FormatedFigure(DrawableFigure figure, Format format) {
        this.figure = figure;
        this.format = format;
    }

    public void draw(GraphicsContext gc, boolean isSelected) {
        gc.setStroke(Color.TRANSPARENT);
        drawShadow(gc);
        figure.drawGradient(gc, format);
        if (format.hasBeveled())    figure.drawBezel(gc);
        gc.setStroke(isSelected ? Color.RED : Color.BLACK);
        figure.draw(gc);
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    private void drawShadow(GraphicsContext gc) {
        format.getShadow().drawShadow(gc, figure, format.getFirstFillColor());
    }

    public FormatedFigure duplicate() {
        return new FormatedFigure(figure.duplicate(), format.getCopy());
    }

    public ArrayList<FormatedFigure> divide() {
        ArrayList<FormatedFigure> result = new ArrayList<>();
        ArrayList<DrawableFigure> figures = figure.divide();
        for (DrawableFigure figure : figures) {
            result.add(new FormatedFigure(figure, format.getCopy()));
        }
        return result;
    }

    @Override
    public boolean contains(Point p) {
        return figure.contains(p);
    }

    @Override
    public void rotate() {
        figure.rotate();
    }

    @Override
    public void flipH() {
        figure.flipH();
    }

    @Override
    public void flipV() {
        figure.flipV();
    }

    @Override
    public void move(double dx, double dy) {
        figure.move(dx, dy);
    }

    @Override
    public String toString() {
        return figure.toString();
    }
}