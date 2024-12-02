package frontend.format;


import frontend.drawable.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum Shadow {
    NONE(0, false){
        @Override
        public void drawShade(GraphicsContext gc, Drawable figure, Color color) {}
    },
    SIMPLE(10, false),
    COLORED(10, true),
    SIMPLE_INVERSED(-10, false),
    COLORED_INVERSED(-10, true);

    private static final Color DEFAULT_COLOR = Color.GRAY;
    final int offset;
    final boolean isColored;

    Shadow(int offset, boolean isColored){
        this.offset = offset;
        this.isColored = isColored;
    }

    public void drawShade(GraphicsContext gc, Drawable figure, Color figureColor) {
        gc.setFill( this.isColored ? figureColor.darker(): DEFAULT_COLOR);
        figure.move(this.offset, this.offset);
        figure.drawShape(gc);
        figure.move(-this.offset, -this.offset);
    }

    @Override
    public String toString(){
        return name();
    }
}
