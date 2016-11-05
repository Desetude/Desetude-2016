package org.devathon.contest2016.menu;

public enum Rows {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private final int size;

    Rows(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

}
