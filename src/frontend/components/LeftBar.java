package frontend.components;

import frontend.format.Shadow;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LeftBar extends VBox {
    // Dimensiones
    private final int TOOL_MIN_WIDTH = 90,
            VBOX_PADDING = 5,
            VBOX_PREF_WIDTH = 100;

    private final String VBOX_STYLE = "-fx-background-color: #999";

    // Botones Barra Izquierda
    ToggleButton selectionButton = new ToggleButton("Seleccionar");
    ToggleButton rectangleButton = new ToggleButton("Rectángulo");
    ToggleButton circleButton = new ToggleButton("Círculo");
    ToggleButton squareButton = new ToggleButton("Cuadrado");
    ToggleButton ellipseButton = new ToggleButton("Elipse");
    ToggleButton deleteButton = new ToggleButton("Borrar");

    // Titulo
    Label formatLabel = new Label("Formato");

    // Sombras
    ChoiceBox<Shadow> choiceShadow = new ChoiceBox<>(
            FXCollections.observableArrayList(
                    Shadow.NONE,
                    Shadow.SIMPLE,
                    Shadow.COLORED,
                    Shadow.SIMPLE_INVERSED,
                    Shadow.COLORED_INVERSED
            )
    );

    // Biselado
    CheckBox beveledBox = new CheckBox("Biselado");

    // Copiar Formato
    ToggleButton copyFmt = new ToggleButton("Copiar Fmt.");

    // Selector de color de relleno
    Color defaultFirstFillColor = Color.YELLOW;
    Color defaultSecondFillColor = Color.RED;

    ColorPicker firstFillColorPicker = new ColorPicker(defaultFirstFillColor);
    ColorPicker secondFillColorPicker = new ColorPicker(defaultSecondFillColor);

    public LeftBar(int spacing) {
        super(spacing);
        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(TOOL_MIN_WIDTH);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        choiceShadow.setValue(Shadow.NONE);
        this.getChildren().addAll(toolsArr);
        this.getChildren().addAll(
                formatLabel,
                choiceShadow,
                beveledBox,
                firstFillColorPicker,
                secondFillColorPicker,
                copyFmt
        );
        this.setPadding(new Insets(VBOX_PADDING));
        this.setStyle(VBOX_STYLE);
        this.setPrefWidth(VBOX_PREF_WIDTH);
    }

    // Getters utilizados en PaintPane

    public ColorPicker getFirstFillColorPicker() {
        return firstFillColorPicker;
    }

    public ChoiceBox<Shadow> getChoiceShadow() {
        return choiceShadow;
    }

    public ColorPicker getSecondFillColorPicker() {
        return secondFillColorPicker;
    }

    public CheckBox getBeveledBox() {
        return beveledBox;
    }

    public ToggleButton getDeleteButton() {
        return deleteButton;
    }

    public ToggleButton getEllipseButton() {
        return ellipseButton;
    }

    public ToggleButton getSquareButton() {
        return squareButton;
    }

    public ToggleButton getCircleButton() {
        return circleButton;
    }

    public ToggleButton getRectangleButton() {
        return rectangleButton;
    }

    public ToggleButton getSelectionButton() {
        return selectionButton;
    }

    public ToggleButton getCopyFmt() {
        return copyFmt;
    }
}
