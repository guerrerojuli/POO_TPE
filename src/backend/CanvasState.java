package backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import backend.model.Figure;
import backend.model.Layer;
import backend.model.Point;

public class CanvasState<T extends Figure> extends ArrayList<Layer<T>> {
    private static final int STARTING_LAYERS = 3;
    private static final int DEFAULT_LAYER = 0;
    private int currentLayerId;

    public CanvasState() {
        for (int i = 0; i < STARTING_LAYERS; i++) {
            add(new Layer<>());
        }
        currentLayerId = DEFAULT_LAYER;
    }

    public void setCurrentLayer(Layer<T> l){
        int newIdx = this.indexOf(l);
        // Ensure the layer belongs to this CanvasState
        this.currentLayerId = newIdx < 0 ? this.currentLayerId : newIdx;
    }

    public Layer<T> getCurrentLayer() {
        return this.get(currentLayerId);
    }

    public List<Layer<T>> getLayers() {
        return this;
    }

    public void addLayer() {
        Layer<T> newLayer = new Layer<>();
        add(newLayer);
        currentLayerId = size() - 1;
    }

    public void deleteLayer(){
        if (currentLayerId >= STARTING_LAYERS) {
            remove(currentLayerId);
            currentLayerId = Math.min(size() - 1, currentLayerId);
        }
    }

    public void bringToFront(T figure){
        Layer<T> layer = get(currentLayerId);
        if(layer.isVisible()){
            layer.bringToFront(figure);
        }
    }

    public void moveToBack(T figure){
        Layer<T> layer = get(currentLayerId);
        if(layer.isVisible()){
            layer.moveToBack(figure);
        }
    }

    public void addFigure(T figure) {
        Layer<T> layer = get(currentLayerId);
        if(layer.isVisible()){
            layer.addFigure(figure);
        }
    }
    public void deleteFigure(T figure, int layerId) {
        layerId -= 1;
        if (layerId >= 0 && layerId < size()) {
            get(layerId).deleteFigure(figure);
        }
    }

    public Iterable<T> figures() {
        return this
                .stream()
                .filter(Layer::isVisible)
                .flatMap(l -> l.figures().stream())
                .toList();
    }

    public Stream<T> intersectingFigures(Point location) {
        return this.stream()
                .sorted(Comparator.reverseOrder())
                .filter(Layer::isVisible)
                .flatMap(l -> l.figures().reversed().stream())
                .filter(f -> f.contains(location) );
    }

}
