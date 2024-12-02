package frontend.drawable;

public interface Drawable{
    default void draw(){
        drawShadow();
        drawGradient();
        drawBezel();
        drawShape();
    }

    void drawShape();
    void drawShadow();
    void drawGradient();
    void drawBezel();
}
