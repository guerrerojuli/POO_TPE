package frontend.drawable;

import backend.model.Figure;
import frontend.format.Format;
import javafx.scene.canvas.GraphicsContext;

public abstract class DrawableFigure<T extends Figure> implements Drawable{
    private final T figure;
    private final GraphicsContext gc;
    private Format format;

    public DrawableFigure(T figure, GraphicsContext gc, Format format){
        this.figure = figure;
        this.gc = gc;
        this.format = format;
    }

    public T getFigure(){
        return figure;
    }

    protected GraphicsContext getGc(){
        return gc;
    }

    public void setFormat(Format format){
        this.format = format;
    }

    public Format getFormat(){
        return format;
    }

    @Override
    public void drawShadow(){
        getFormat().getShadow().drawShadow(getGc(), this, getFormat().getFirstFillColor());
    }
}
