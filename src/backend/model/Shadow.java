package backend.model;


public enum Shadow {
    NONE(false, false),
    SIMPLE(false, false),
    COLORED(false, true),
    SIMPLE_INVERSED(true, false),
    COLORED_INVERSED(true, true);

    private final int offset;
    private final boolean isColored;
    private final int OFFSET = 10;

    Shadow(boolean isInversed, boolean isColored){
        this.offset = isInversed ? -1 * OFFSET : OFFSET;
        this.isColored = isColored;
    }

    public int getOffset() {
        return offset;
    }

    public boolean isColored() {
        return isColored;
    }

    @Override
    public String toString(){
        return name();
    }
}
