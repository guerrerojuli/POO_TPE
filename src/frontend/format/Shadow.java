package frontend.format;

import frontend.drawable.DrawableFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum Shadow {
    NONE("Sin sombra",0, false){
        @Override
        public void drawShadow(GraphicsContext gc, DrawableFigure figure, Color color) {}
    },
    SIMPLE("Simple", 10, false),
    COLORED("Coloreada",10, true),
    SIMPLE_INVERSED("Simple inversa",-10, false),
    COLORED_INVERSED("Coloreada inversa",-10, true);

    private static final Color DEFAULT_COLOR = Color.GRAY;
    private final int offset;
    private final boolean isColored;
    private final String name;

    Shadow(String name, int offset, boolean isColored){
        this.name = name;
        this.offset = offset;
        this.isColored = isColored;
    }

    public void drawShadow(GraphicsContext gc, DrawableFigure figure, Color figureColor) {
        gc.setFill( this.isColored ? figureColor.darker(): DEFAULT_COLOR);
        figure.move(this.offset, this.offset);
        figure.draw(gc);
        figure.move(-this.offset, -this.offset);
    }

    @Override
    public String toString(){
        return name;
    }
}
