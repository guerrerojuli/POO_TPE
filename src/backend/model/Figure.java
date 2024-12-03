package backend.model;

public interface Figure extends Movable {
    boolean contains(Point p);
    void rotate();
    void flipH();
    void flipV();
}
