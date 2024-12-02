package frontend.components;

import backend.CanvasState;
import backend.model.*;
import frontend.drawable.*;
import frontend.format.Format;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

public class PaintPane extends BorderPane {
	// Dimensiones
	private final int CANVAS_WIDTH = 800,
			CANVAS_HEIGHT = 600,
			LINE_WIDTH = 1,
			VBOX_SPACING = 10;

	// Barra lateral izquierda
	LeftBar barraIzq = new LeftBar(VBOX_SPACING);

	// Barra lateral derecha
	RightBar barraDer = new RightBar(VBOX_SPACING);

	// BackEnd
	CanvasState<DrawableFigure> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	DrawableFigure selectedFigure;

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
						barraIzq.getChoiceShadow().getValue(),
						barraIzq.getFirstFillColorPicker().getValue(),
						barraIzq.getSecondFillColorPicker().getValue(),
						barraIzq.getBeveledBox().isSelected()
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
			if (!barraIzq.getSelectionButton().isSelected()) {
				return;
			}

			Point eventPoint = new Point(event.getX(), event.getY());

			Format copiedFormat = null;
			if(selectedFigure != null) {
				if (barraIzq.getCopyFmt().isSelected()) {
					copiedFormat = selectedFigure.getFormat();
					barraIzq.getCopyFmt().setSelected(false);
				} else if (barraDer.getRotationButton().isSelected()) {
					selectedFigure.rotate();
					barraDer.getRotationButton().setSelected(false);
				} else if (barraDer.getFlipHButton().isSelected()) {
					/*selectedFigure.flipH();*/
					barraDer.getFlipHButton().setSelected(false);
				} else if (barraDer.getFlipVButton().isSelected()) {
					/*selectedFigure.flipV();*/
					barraDer.getFlipVButton().setSelected(false);
				} else if (barraDer.getDuplicateButton().isSelected()) {
					/*canvasState.add(selectedFigure.duplicate());*/
					barraDer.getDuplicateButton().setSelected(false);
				} else if (barraDer.getDivideButton().isSelected()) {
					/*canvasState.addAll(selectedFigure.divide());*/
					barraDer.getDivideButton().setSelected(false);
				}
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
			if(!barraIzq.getSelectionButton().isSelected() || selectedFigure == null) {
				return;
			}
			Point eventPoint = new Point(event.getX(), event.getY());
			double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
			double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
			selectedFigure.move(diffX, diffY);
			redrawCanvas();
		});

		barraIzq.getDeleteButton().setOnAction(event -> {
			if (selectedFigure == null) {
				return;
			}
			canvasState.remove(selectedFigure);
			selectedFigure = null;
			redrawCanvas();
		});

		setLeft(barraIzq);
		setCenter(canvas);
		setRight(barraDer);
	}

	void redrawCanvas() {
		// Limpio el canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Dibuja cada figura, le dice si está seleccionada para que cambie su borde
		canvasState.forEach(figure -> figure.draw(gc, figure == selectedFigure));
	}

	private DrawableFigure createFigure(Point startPoint, Point endPoint, Format format) {
		if (barraIzq.getRectangleButton().isSelected()) {
			return new DrawableRectangle(startPoint, endPoint, format);
		} else if (barraIzq.getCircleButton().isSelected()) {
			double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableCircle(startPoint, circleRadius, format);
		} else if (barraIzq.getSquareButton().isSelected()) {
			double size = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawableSquare(startPoint, size, format);
		} else if (barraIzq.getEllipseButton().isSelected()) {
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