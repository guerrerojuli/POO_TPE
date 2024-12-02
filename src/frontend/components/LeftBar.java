package frontend.components;

import frontend.format.Format;
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
    private ToggleButton selectionButton = new ToggleButton("Seleccionar");
    private ToggleButton rectangleButton = new ToggleButton("Rectángulo");
    private ToggleButton circleButton = new ToggleButton("Círculo");
    private ToggleButton squareButton = new ToggleButton("Cuadrado");
    private ToggleButton ellipseButton = new ToggleButton("Elipse");
    private ToggleButton deleteButton = new ToggleButton("Borrar");

    // Titulo
    private Label formatLabel = new Label("Formato");

    // Sombras
    private ChoiceBox<Shadow> choiceShadow = new ChoiceBox<>(
            FXCollections.observableArrayList(
                    Shadow.NONE,
                    Shadow.SIMPLE,
                    Shadow.COLORED,
                    Shadow.SIMPLE_INVERSED,
                    Shadow.COLORED_INVERSED
            )
    );

    // Biselado
    private CheckBox beveledBox = new CheckBox("Biselado");

    // Copiar Formato
    private ToggleButton copyFmt = new ToggleButton("Copiar Fmt.");

    // Fomato
    private Format format = new Format(Shadow.NONE, Color.AQUA, Color.AZURE, false);

    private ColorPicker firstFillColorPicker = new ColorPicker(format.getFirstFillColor());
    private ColorPicker secondFillColorPicker = new ColorPicker(format.getSecondFillColor());

    public LeftBar(int spacing) {
        super(spacing);
        choiceShadow.setValue(format.getShadow());
        beveledBox.setSelected(format.hasBeveled());

        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(TOOL_MIN_WIDTH);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
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

    public void updateFormat(Format format) {
        this.format = format;
        choiceShadow.setValue(format.getShadow());
        beveledBox.setSelected(format.hasBeveled());
        firstFillColorPicker.setValue(format.getFirstFillColor());
        secondFillColorPicker.setValue(format.getSecondFillColor());
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

    public Format getFormat() {
        return format;
    }
}
