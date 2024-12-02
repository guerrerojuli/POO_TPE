package frontend.components;

import backend.CanvasState;
import backend.model.*;
import frontend.drawable.*;
import frontend.format.Format;
import frontend.format.Shadow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {
	// Dimensiones
	private final int CANVAS_WIDTH = 800,
			CANVAS_HEIGHT = 600,
			LINE_WIDTH = 1,
			VBOX_SPACING = 10;

	// Barra lateral izquierda
	LeftBar leftBar = new LeftBar(VBOX_SPACING);

	// BackEnd
	CanvasState<DrawableFigure> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	DrawableFigure selectedFigure;

	Format copiedFormat = null;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState<DrawableFigure> canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

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
						leftBar.getChoiceShadow().getValue(),
						leftBar.getFirstFillColorPicker().getValue(),
						leftBar.getSecondFillColorPicker().getValue(),
						leftBar.getBeveledBox().isSelected()
					);

			// Crear la nueva figura según el botón seleccionado
			DrawableFigure newFigure = createFigure(startPoint, endPoint, newFormat);

			if (newFigure == null) {
				return;
			}

			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			// Busca la primer figura que contenga al punto
			String label = canvasState.intersectingFigures(eventPoint)
					.filter(figure -> figure.contains(eventPoint))
					.map(Figure::toString)
					.findFirst()
					.orElse(eventPoint.toString());

			// Actualiza status pane
			statusPane.updateStatus(label);
		});

		canvas.setOnMouseClicked(event -> {
			if (!leftBar.getSelectionButton().isSelected()) {
				return;
			}

			Point eventPoint = new Point(event.getX(), event.getY());

			// Busca la primer figura que contenga al punto
			selectedFigure = canvasState.intersectingFigures(eventPoint).findFirst().orElse(null);

			// Actualiza el status pane basado en la selección
			String status = "Ninguna figura encontrada";
			if (selectedFigure != null) {
				status = "Se selecionó: " + selectedFigure.toString();

				if (copiedFormat != null) {
					selectedFigure.setFormat(copiedFormat);
					copiedFormat = null;
				}

				leftBar.updateFormat(selectedFigure.getFormat());
			}

			statusPane.updateStatus(status);
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if(!leftBar.getSelectionButton().isSelected() || selectedFigure == null) {
				return;
			}
			Point eventPoint = new Point(event.getX(), event.getY());
			double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
			double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
			selectedFigure.move(diffX, diffY);
			redrawCanvas();
		});

		leftBar.getDeleteButton().setOnAction(event -> {
			if (selectedFigure == null) {
				return;
			}
			canvasState.remove(selectedFigure);
			selectedFigure = null;
			redrawCanvas();
		});

		leftBar.getChoiceShadow()
				.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					leftBar.getFormat().setShadow(newValue);
					if (selectedFigure != null) {
						selectedFigure.setFormat(leftBar.getFormat());
					}
					redrawCanvas();
				});

		leftBar.getBeveledBox()
				.selectedProperty()
				.addListener((observable, oldValue, newValue) -> {
					leftBar.getFormat().setBeveled(newValue);
					if (selectedFigure != null) {
						selectedFigure.setFormat(leftBar.getFormat());
					}
					redrawCanvas();
				});

		leftBar.getFirstFillColorPicker()
				.valueProperty()
				.addListener((observable, oldValue, newValue) -> {
					leftBar.getFormat().setFirstFillColor(newValue);
					if (selectedFigure != null) {
						selectedFigure.setFormat(leftBar.getFormat());
					}
					redrawCanvas();
				});

		leftBar.getSecondFillColorPicker()
				.valueProperty()
				.addListener((observable, oldValue, newValue) -> {
					leftBar.getFormat().setSecondFillColor(newValue);
					if (selectedFigure != null) {
						selectedFigure.setFormat(leftBar.getFormat());
					}
					redrawCanvas();
				});


		leftBar.getCopyFmt().setOnAction(event -> {
			if (selectedFigure == null) {
				return;
			}
			copiedFormat = selectedFigure.getFormat();
		});

		setLeft(leftBar);
		setCenter(canvas);
	}

	void redrawCanvas() {
		// Limpio el canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Dibuja cada figura, le dice si está seleccionada para que cambie su borde
		canvasState.figures().forEach(figure -> figure.draw(gc, figure == selectedFigure));
	}

	private DrawableFigure createFigure(Point startPoint, Point endPoint, Format format) {
		if (leftBar.getRectangleButton().isSelected()) {
			return new DrawableRectangle(startPoint, endPoint, format);
		} else if (leftBar.getCircleButton().isSelected()) {
			double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableCircle(startPoint, circleRadius, format);
		} else if (leftBar.getSquareButton().isSelected()) {
			double size = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableSquare(startPoint, size, format);
		} else if (leftBar.getEllipseButton().isSelected()) {
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