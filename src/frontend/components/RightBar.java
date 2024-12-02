package frontend.components;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RightBar extends VBox {
    // Dimensiones
    private final int TOOL_MIN_WIDTH = 90,
            VBOX_PADDING = 5,
            VBOX_PREF_WIDTH = 100;

    private final String VBOX_STYLE = "-fx-background-color: #999";

    // Titulo
    Label actionLabel = new Label("Acciones");

    // Botones Barra Derecha
    ToggleButton rotationButton = new ToggleButton("Girar D");
    ToggleButton flipHButton = new ToggleButton("Voltear H");
    ToggleButton flipVButton = new ToggleButton("Voltear V");
    ToggleButton duplicateButton = new ToggleButton("Duplicar");
    ToggleButton divideButton = new ToggleButton("Dividir");


    public RightBar(int spacing) {
        super(spacing);
        ToggleButton[] actionsArr = {rotationButton, flipHButton, flipVButton, duplicateButton, divideButton};
        ToggleGroup actions = new ToggleGroup();
        for (ToggleButton action : actionsArr) {
            action.setMinWidth(TOOL_MIN_WIDTH);
            action.setToggleGroup(actions);
            action.setCursor(Cursor.HAND);
        }

        this.getChildren().add(actionLabel);
        this.getChildren().addAll(actionsArr);


        this.setPadding(new Insets(VBOX_PADDING));
        this.setStyle(VBOX_STYLE);
        this.setPrefWidth(VBOX_PREF_WIDTH);
    }

    // Getters para los botones de action


    public ToggleButton getRotationButton() {
        return rotationButton;
    }

    public ToggleButton getFlipHButton() {
        return flipHButton;
    }

    public ToggleButton getFlipVButton() {
        return flipVButton;
    }

    public ToggleButton getDuplicateButton() {
        return duplicateButton;
    }

    public ToggleButton getDivideButton() {
        return divideButton;
    }
}