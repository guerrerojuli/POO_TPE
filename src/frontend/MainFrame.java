package frontend;

import backend.CanvasState;
import frontend.components.AppMenuBar;
import frontend.components.PaintPane;
import frontend.components.StatusPane;
import frontend.drawable.FormatedFigure;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState<FormatedFigure> canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        getChildren().add(new PaintPane(canvasState, statusPane));
        getChildren().add(statusPane);
    }

}
