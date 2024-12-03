package frontend.components;

import backend.CanvasState;
import backend.model.*;
import frontend.drawable.*;
import frontend.format.Format;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.util.function.Consumer;

public class PaintPane extends BorderPane {
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 600;
	private static final int LINE_WIDTH = 1;
	private static final int VBOX_SPACING = 10;
	private static final int HBOX_SPACING = 10;

	private final LeftBar leftBar;
    private final RightBar rightBar;
	private final TopBar topBar;
	private final Canvas canvas;
	private final GraphicsContext gc;

	private final CanvasState<DrawableFigure> canvasState;
	private DrawableFigure selectedFigure;
	private Point startPoint;
	private Format copiedFormat;

	public PaintPane(CanvasState<DrawableFigure> canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.leftBar = new LeftBar(VBOX_SPACING);
        this.rightBar = new RightBar(VBOX_SPACING);
		this.topBar = new TopBar(HBOX_SPACING);
		this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		gc.setLineWidth(LINE_WIDTH);
		setupCanvasEvents(statusPane);
		setupTopBarEvents();
		setupLeftBarEvents();
        setupRightBarEvents();


		setTop(topBar);
		setLeft(leftBar);
        setRight(rightBar);
		setCenter(canvas);
	}

	private void setupCanvasEvents(StatusPane statusPane) {
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			if(startPoint == null)	return ;

			Point endPoint = new Point(event.getX(), event.getY());
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) 	return;

			DrawableFigure newFigure = createFigure(startPoint, endPoint, getCurrentFormat());

			if (newFigure != null) {
				canvasState.addFigure(newFigure);
				redrawCanvas();
			}
			startPoint = null;
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());

			String label = canvasState.intersectingFigures(eventPoint)
					.filter(figure -> figure.contains(eventPoint))
					.map(Figure::toString)
					.findFirst()
					.orElse(eventPoint.toString());

			statusPane.updateStatus(label);
		});

		canvas.setOnMouseClicked(event -> {
			if (!leftBar.getSelectionButton().isSelected())	return;

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
					leftBar.getCopyFmt().setSelected(false);
				}

				leftBar.updateFormat(selectedFigure.getFormat());
			}

			statusPane.updateStatus(status);
			redrawCanvas();
		});

		canvas.setOnMouseDragged(event -> {
			if (!leftBar.getSelectionButton().isSelected() || selectedFigure == null)	return;
			selectedFigure.move(event.getX() - startPoint.getX(), event.getY() - startPoint.getY());
			startPoint = new Point(event.getX(), event.getY());
			redrawCanvas();
		});
	}

	private void setupLeftBarEvents() {
		leftBar.getDeleteButton().setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure, canvasState.getCurrentLayer().getLayerId());
				selectedFigure = null;
				redrawCanvas();
			}
		});

		leftBar.getCopyFmt().setOnAction(event -> {
			// Si se está desseleccionando se remueve la copia
			if (!leftBar.getCopyFmt().isSelected()) {
				copiedFormat = null;
				return;
			}
			// Si no hay nada seleccionado no hace nada
			if (selectedFigure == null)	{
				leftBar.getCopyFmt().setSelected(false);
				return;
			}
			copiedFormat = selectedFigure.getFormat().getCopy();
		});

		leftBar.getChoiceShadow().getSelectionModel().selectedItemProperty()
				.addListener((obs, oldVal, newVal) -> applyFormatChange(format -> format.setShadow(newVal)));

		leftBar.getBeveledBox().selectedProperty()
				.addListener((obs, oldVal, newVal) -> applyFormatChange(format -> format.setBeveled(newVal)));

		leftBar.getFirstFillColorPicker().valueProperty()
				.addListener((obs, oldVal, newVal) -> applyFormatChange(format -> format.setFirstFillColor(newVal)));

		leftBar.getSecondFillColorPicker()
				.valueProperty()
				.addListener((obs, oldVal, newVal) -> applyFormatChange(format -> format.setSecondFillColor(newVal)));
	}

    private void setupRightBarEvents() {
        rightBar.getRotationButton().setOnAction(event -> {
            selectedFigure.rotate();
            rightBar.getRotationButton().setSelected(false);
			redrawCanvas();
        });

        rightBar.getFlipHButton().setOnAction(event -> {
            selectedFigure.flipH();
            rightBar.getFlipHButton().setSelected(false);
			redrawCanvas();
        });

    	rightBar.getFlipVButton().setOnAction(event -> {
            selectedFigure.flipV();
            rightBar.getFlipVButton().setSelected(false);
			redrawCanvas();
        });

        rightBar.getDuplicateButton().setOnAction(event -> {
            /*canvasState.add(selectedFigure.duplicate());*/
            rightBar.getDuplicateButton().setSelected(false);
			redrawCanvas();
        });

        rightBar.getDivideButton().setOnAction(event -> {
            /*canvasState.addAll(selectedFigure.divide());*/
            rightBar.getDivideButton().setSelected(false);
			redrawCanvas();
        });
    }

	private void setupTopBarEvents() {
		topBar.getLayerOptions().setOnAction( event -> {
			canvasState.setCurrentLayer(topBar.getLayerOptions().getValue());
			setCurrentLayerMode();
		});

		topBar.getLayers().addAll(canvasState.getLayers());
		topBar.getLayerOptions().setValue(canvasState.getLayers().getFirst());

		bindButtonToRedraw(topBar.getShowButton(), () -> setCurrentLayerMode(true));
		bindButtonToRedraw(topBar.getHideButton(), () -> {
			leftBar.getSelectionButton().setSelected(false);
			setCurrentLayerMode(false);
			redrawCanvas();
		});
		bindButtonToLayerAction(topBar.getAddLayerButton(), canvasState::addLayer);
		bindButtonToLayerActionAndRedraw(topBar.getDeleteLayerButton(), canvasState::deleteLayer);

		bindButtonToRedraw(topBar.getBringToFrontButton(), () -> {
			canvasState.bringToFront(selectedFigure);
			topBar.getBringToFrontButton().setSelected(false);
		});

		bindButtonToRedraw(topBar.getMoveToBackButton(), () -> {
			canvasState.moveToBack(selectedFigure);
			topBar.getMoveToBackButton().setSelected(false);
		});

		setCurrentLayerMode();
	}

	private void applyFormatChange(Consumer<Format> formatUpdater) {
		formatUpdater.accept(leftBar.getFormat());
		if (selectedFigure != null)	selectedFigure.setFormat(leftBar.getFormat());
		redrawCanvas();
	}

	private Format getCurrentFormat() {
		return new Format(
				leftBar.getChoiceShadow().getValue(),
				leftBar.getFirstFillColorPicker().getValue(),
				leftBar.getSecondFillColorPicker().getValue(),
				leftBar.getBeveledBox().isSelected()
		);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		canvasState.figures().forEach(figure -> figure.draw(gc, figure == selectedFigure));
	}

	private DrawableFigure createFigure(Point startPoint, Point endPoint, Format format) {
		if (leftBar.getRectangleButton().isSelected())	return new DrawableRectangle(startPoint, endPoint, format);
		if (leftBar.getCircleButton().isSelected())	return new DrawableCircle(startPoint, Math.abs(endPoint.getX() - startPoint.getX()), format);
		if (leftBar.getSquareButton().isSelected())	return new DrawableSquare(startPoint, Math.abs(endPoint.getX() - startPoint.getX()), format);
		if (leftBar.getEllipseButton().isSelected()) {
			return new DrawableEllipse(
					new Point((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2),
					Math.abs(endPoint.getX() - startPoint.getX()),
					Math.abs(endPoint.getY() - startPoint.getY()),
					format
			);
		}
		return null;
	}

	private void setCurrentLayerMode(){
		this.setCurrentLayerMode(canvasState.getCurrentLayer().isVisible());
	}
	private void setCurrentLayerMode(boolean visible) {
		canvasState.getCurrentLayer().setVisible(visible);
		topBar.getShowButton().setSelected(visible);
		topBar.getHideButton().setSelected(!visible);
	}

	private void bindButtonToRedraw(ButtonBase button, Runnable action) {
		button.setOnAction((x) -> { action.run(); redrawCanvas(); });
	}

	private void bindButtonToLayerAction(ToggleButton button, Runnable action) {
		button.setOnAction(x -> {
			action.run();
			topBar.getLayers().setAll(canvasState.getLayers());
			topBar.getLayerOptions().setValue(canvasState.getCurrentLayer());
			setCurrentLayerMode();
			button.setSelected(false);
		});
	}

	private void bindButtonToLayerActionAndRedraw(ToggleButton button, Runnable action) {
		bindButtonToLayerAction(button, () -> { action.run(); redrawCanvas(); });
	}
}