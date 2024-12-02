package frontend.components;

import backend.model.Layer;
import frontend.drawable.DrawableFigure;
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

    final Label layerLabel = new Label("Capas");
    final ObservableList<Layer<DrawableFigure>> layers = FXCollections.observableArrayList();
    final ChoiceBox<Layer<DrawableFigure>> layerOptions = new ChoiceBox<>(layers);
    final RadioButton showButton = new RadioButton("Mostrar");
    final RadioButton hideButton = new RadioButton("Ocultar");
    final ToggleButton addLayerButton = new ToggleButton("Agregar Capa");
    final ToggleButton deleteLayerButton = new ToggleButton("Eliminar Capa");
    final ToggleButton bringToFrontButton = new ToggleButton("Traer al Frente");
    final ToggleButton moveToBackButton = new ToggleButton("Enviar al Fondo");

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
}
