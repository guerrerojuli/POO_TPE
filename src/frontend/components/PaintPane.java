package frontend.components;

import backend.CanvasState;
import backend.model.*;
import frontend.drawable.*;
import frontend.format.Format;
import frontend.format.Shadow;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {
	// Dimensiones
	private final int CANVAS_WIDTH = 800,
			CANVAS_HEIGHT = 600,
			TOOL_MIN_WIDTH = 90,
			VBOX_SPACING = 10,
			VBOX_PADDING = 5,
			LINE_WIDTH = 1,
			VBOX_PREF_WIDTH = 100;

	private final String VBOX_STYLE = "-fx-background-color: #999";

	// BackEnd
	CanvasState<DrawableFigure> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color defaultFirstFillColor = Color.YELLOW;
	Color defaultSecondFillColor = Color.RED;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");


	/* Formato */
	// Etiqueta
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

	// Selector de color de relleno
	ColorPicker firstFillColorPicker = new ColorPicker(defaultFirstFillColor);
	ColorPicker secondFillColorPicker = new ColorPicker(defaultSecondFillColor);

	// Copiar Formato
	ToggleButton copyFmt = new ToggleButton("Copiar Fmt.");


	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	DrawableFigure selectedFigure;

	// StatusBar
	StatusPane statusPane;


	public PaintPane(CanvasState<DrawableFigure> canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(TOOL_MIN_WIDTH);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		choiceShadow.setValue(Shadow.NONE);
		VBox buttonsBox = new VBox(VBOX_SPACING);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(
				formatLabel,
				choiceShadow,
				beveledBox,
				firstFillColorPicker,
				secondFillColorPicker,
				copyFmt
		);
		buttonsBox.setPadding(new Insets(VBOX_PADDING));
		buttonsBox.setStyle(VBOX_STYLE);
		buttonsBox.setPrefWidth(VBOX_PREF_WIDTH);
		gc.setLineWidth(LINE_WIDTH);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());

			if(startPoint == null) {
				return ;
			}

			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}

			Format newFormat =  new Format(
						choiceShadow.getValue(),
						firstFillColorPicker.getValue(),
						secondFillColorPicker.getValue(),
						beveledBox.isSelected()
					);

			// Crear la nueva figura según el botón seleccionado
			DrawableFigure newFigure = createFigure(startPoint, endPoint, newFormat);

			if (newFigure == null) {
				return;
			}

			canvasState.add(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			// Busca la primer figura que contenga al punto
			String label = canvasState.stream()
					.filter(figure -> figure.contains(eventPoint))
					.map(Figure::toString)
					.findFirst()
					.orElse(eventPoint.toString());

			// Actualiza status pane
			statusPane.updateStatus(label);
		});

		canvas.setOnMouseClicked(event -> {
			if (!selectionButton.isSelected()) {
				return;
			}

			Point eventPoint = new Point(event.getX(), event.getY());

			Format copiedFormat = null;
			if (copyFmt.isSelected() && selectedFigure != null) {
				copiedFormat = selectedFigure.getFormat();
				copyFmt.setSelected(false);
			}

			// Busca la primer figura que contenga al punto
			selectedFigure = canvasState.stream()
					.filter(figure -> figure.contains(eventPoint))
					.findFirst()
					.orElse(null);

			// Actualiza el status pane basado en la selección
			if (selectedFigure != null && copiedFormat != null) {
				selectedFigure.setFormat(copiedFormat);
			}

			String status = (selectedFigure != null)
					? "Se seleccionó: " + selectedFigure
					: "Ninguna figura encontrada";

			statusPane.updateStatus(status);
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if(!selectionButton.isSelected() || selectedFigure == null) {
				return;
			}
			Point eventPoint = new Point(event.getX(), event.getY());
			double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
			double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
			selectedFigure.move(diffX, diffY);
			redrawCanvas();
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure == null) {
				return;
			}
			canvasState.remove(selectedFigure);
			selectedFigure = null;
			redrawCanvas();
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		// Limpio el canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Dibuja cada figura, le dice si está seleccionada para que cambie su borde
		canvasState.forEach(figure -> figure.draw(gc, figure == selectedFigure));
	}

	private DrawableFigure createFigure(Point startPoint, Point endPoint, Format format) {
		if (rectangleButton.isSelected()) {
			return new DrawableRectangle(startPoint, endPoint, format);
		} else if (circleButton.isSelected()) {
			double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableCircle(startPoint, circleRadius, format);
		} else if (squareButton.isSelected()) {
			double size = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableSquare(startPoint, size, format);
		} else if (ellipseButton.isSelected()) {
			Point centerPoint = new Point(
					(startPoint.getX() + endPoint.getX()) / 2,
					(startPoint.getY() + endPoint.getY()) / 2
			);
			double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
			double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
			return new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis, format);
		} else {
			return null; // Si no se selecciona ninguna figura, retornar null
		}
	}

}

