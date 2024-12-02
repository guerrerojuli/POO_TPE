package frontend.format;

import javafx.scene.paint.Color;

public class Format {
    private Shadow shadow;
    private Color firstFillColor, secondFillColor;
    private boolean withBeveled;

    public Format(Shadow shadow, Color firstFillColor, Color secondFillColor, boolean withBeveled) {
        this.firstFillColor = firstFillColor;
        this.secondFillColor = secondFillColor;
        this.shadow = shadow;
        this.withBeveled = withBeveled;
    }

    public boolean hasBeveled() {
        return withBeveled;
    }

    public void setBeveled(boolean withBeveled) {
        this.withBeveled = withBeveled;
    }

    public Color getFirstFillColor() {
        return firstFillColor;
    }

    public void setFirstFillColor(Color firstFillColor) {
        this.firstFillColor = firstFillColor;
    }

    public Color getSecondFillColor() {
        return secondFillColor;
    }

    public void setSecondFillColor(Color secondFillColor) {
        this.secondFillColor = secondFillColor;
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public Format getCopy() {
        return new Format(shadow, firstFillColor, secondFillColor, withBeveled);
    }
}
