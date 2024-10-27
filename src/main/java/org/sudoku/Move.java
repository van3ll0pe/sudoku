package org.sudoku;

public class Move {
    private final int x;
    private final int y;
    private final Number old_val;
    private final Number new_val;

    public Move(int x, int y, Number old_val, Number new_val) {
        this.x = x;
        this.y = y;
        this.old_val = old_val;
        this.new_val = new_val;
    }

    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }

    public Number get_oldVal() {
        return old_val;
    }

    public Number get_newVal() {
        return new_val;
    }
}
