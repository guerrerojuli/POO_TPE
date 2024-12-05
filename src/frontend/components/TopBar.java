package frontend.components;

import backend.model.Layer;
import frontend.drawable.DrawableFigure;
import frontend.drawable.FormatedFigure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TopBar extends HBox {
    private final String HBOX_STYLE = "-fx-background-color: #999";
    private final int OFFSETS_VALUE = 5;

    private final Label layerLabel = new Label("Capas");
    private final ObservableList<Layer<FormatedFigure>> layers = FXCollections.observableArrayList();
    private final ChoiceBox<Layer<FormatedFigure>> layerOptions = new ChoiceBox<>(layers);
    private final RadioButton showButton = new RadioButton("Mostrar");
    private final RadioButton hideButton = new RadioButton("Ocultar");
    private final ToggleButton addLayerButton = new ToggleButton("Agregar Capa");
    private final ToggleButton deleteLayerButton = new ToggleButton("Eliminar Capa");
    private final ToggleButton bringToFrontButton = new ToggleButton("Traer al Frente");
    private final ToggleButton moveToBackButton = new ToggleButton("Enviar al Fondo");

    public TopBar(int spacing){
        super(spacing);

        Collection<Node> topButtons = new ArrayList<>(List.of(bringToFrontButton, moveToBackButton, layerLabel,
                layerOptions,
                showButton, hideButton,
                addLayerButton, deleteLayerButton));
        getChildren().addAll(topButtons);
        setPadding(new Insets(OFFSETS_VALUE));
        setStyle(HBOX_STYLE);
        setAlignment(Pos.CENTER);
    }

    //getters

    public ToggleButton getMoveToBackButton() {
        return moveToBackButton;
    }

    public ToggleButton getBringToFrontButton() {
        return bringToFrontButton;
    }

    public ToggleButton getDeleteLayerButton() {
        return deleteLayerButton;
    }

    public ToggleButton getAddLayerButton() {
        return addLayerButton;
    }

    public RadioButton getHideButton() {
        return hideButton;
    }

    public RadioButton getShowButton() {
        return showButton;
    }

    public ChoiceBox<Layer<FormatedFigure>> getLayerOptions() {
        return layerOptions;
    }

    public ObservableList<Layer<FormatedFigure>> getLayers() {
        return layers;
    }
}
