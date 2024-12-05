package frontend.components;

import backend.model.Point;
import frontend.drawable.*;
import frontend.format.Format;
import frontend.format.Shadow;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class LeftBar extends VBox {
    // Dimensiones
    private static final int TOOL_MIN_WIDTH = 90,
            VBOX_PADDING = 5,
            VBOX_PREF_WIDTH = 100;

    private static final String VBOX_STYLE = "-fx-background-color: #999";

    // Botones Barra Izquierda
    private final ToggleGroup tools = new ToggleGroup();
    private final ToggleButton selectionButton = createToggleButton("Seleccionar");
    private final ToggleButton rectangleButton = createToggleButton("Rectángulo");
    private final ToggleButton circleButton = createToggleButton("Círculo");
    private final ToggleButton squareButton = createToggleButton("Cuadrado");
    private final ToggleButton ellipseButton = createToggleButton("Elipse");
    private final ToggleButton deleteButton = createToggleButton("Borrar");

    private final Map<Toggle, BiFunction<Point, Point, FormatedFigure>> buttonActions = new HashMap<>();

    // Formato
    private final Label formatLabel = new Label("Formato");
    private final ChoiceBox<Shadow> choiceShadow = new ChoiceBox<>(FXCollections.observableArrayList(
            Shadow.NONE, Shadow.SIMPLE, Shadow.COLORED, Shadow.SIMPLE_INVERSED, Shadow.COLORED_INVERSED
    ));
    private final CheckBox beveledBox = new CheckBox("Biselado");
    private final ColorPicker firstFillColorPicker = new ColorPicker();
    private final ColorPicker secondFillColorPicker = new ColorPicker();
    private final ToggleButton copyFmt = createToggleButton("Copiar Fmt.");
    private Format format = new Format(Shadow.NONE, Color.AQUA, Color.AZURE, false);


    public LeftBar(int spacing) {
        super(spacing);
        setupLayout();
        updateFormat(format);

    }

    private ToggleButton createToggleButton(String text) {
        ToggleButton button = new ToggleButton(text);
        button.setMinWidth(TOOL_MIN_WIDTH);
        button.setCursor(Cursor.HAND);
        return button;
    }

    private void setupLayout() {
        // Agrupar herramientas en un Toggle Group
        ToggleButton[] toolsArr = {
                selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton
        };
        for (ToggleButton tool : toolsArr) {
            tool.setToggleGroup(tools);
        }

        buttonActions.put(rectangleButton, (startPoint, endPoint) -> new FormatedFigure(new DrawableRectangle(startPoint, endPoint), format));
        buttonActions.put(circleButton, (startPoint, endPoint) -> new FormatedFigure(new DrawableCircle(startPoint, Math.abs(endPoint.getX() - startPoint.getX())), format));
        buttonActions.put(squareButton, (startPoint, endPoint) -> new FormatedFigure(new DrawableSquare(startPoint, Math.abs(endPoint.getX() - startPoint.getX())), format));
        buttonActions.put(ellipseButton, (startPoint, endPoint) ->new FormatedFigure(new DrawableEllipse(
                new Point((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2),
                Math.abs(endPoint.getX() - startPoint.getX()),
                Math.abs(endPoint.getY() - startPoint.getY())),
                format
        ));

        // Añadir elementos al panel
        this.getChildren().addAll(toolsArr);
        this.getChildren().addAll(
                formatLabel, choiceShadow, beveledBox, firstFillColorPicker, secondFillColorPicker, copyFmt
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

    // Getters
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
    public ToggleButton getSelectionButton() {
        return selectionButton;
    }
    public ToggleButton getCopyFmt() {
        return copyFmt;
    }
    public Format getFormat() { return format; }
    public FormatedFigure createFigure(Point startPoint, Point endPoint) {
        BiFunction<Point, Point, FormatedFigure> action = buttonActions.get(tools.getSelectedToggle());
        if (action == null) return null;
        return action.apply(startPoint, endPoint);
    }
}
