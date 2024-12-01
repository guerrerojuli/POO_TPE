package backend.model;


public enum Shadow {
    SIMPLE(false, false),
    COLORED(false, true),
    SIMPLE_INVERSED(true, false),
    COLORED_INVERSED(true, true);

    final int offset;
    final boolean isColored;
    final int OFFSET = 10;

    Shadow(boolean isInversed, boolean isColored){
        this.offset = isInversed ? -1 * OFFSET : OFFSET;
        this.isColored = isColored;
    }

    @Override
    public String toString(){
        return name();
    }
}
