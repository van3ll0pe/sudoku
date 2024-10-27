package org.sudoku;

public class Grid {
    private int width;
    private int height;

    private Number[][] tab;

    public Grid(int width, int height) throws Exception {
        if (width <= 0 || height <= 0)
            throw new Exception("negative value for Grid");

        this.width = width;
        this.height = height;
        tab = new Number[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                tab[i][j] = Number.NONE;
            }
        }
    }

    public Grid(Number[][] tab) throws Exception{
        if (tab.length <= 0)
            throw new Exception("invalid Height size");

        this.height = tab.length;
        int size = tab[0].length;
        for (int i = 1; i < this.height; i++) {
            if (size != tab[i].length)
                throw new Exception("Invalid Grid, Width different");
        }
        this.width = size;
        this.tab = tab.clone();
    }

    public boolean set(int x, int y, Number value) {
        if ((x < 0 || x >= this.width) || (y < 0 || y >= this.height))
            return false;

        this.tab[y][x] = value;
        return true;
    }

    public Number get(int x, int y) throws Exception {
        if (!(x >= 0 && x < this.width) || !(y >= 0 && y < this.height))
            throw new Exception("Out of bound grid ("+x+","+y+")");

        return this.tab[y][x];
    }

    public void display() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print("--");
            }
            System.out.println("-");
            for (int j = 0; j < this.width; j++) {
                System.out.print("|"+this.tab[i][j].getNumber());
            }
            System.out.println("|");
        }
        for (int j = 0; j < this.width; j++) {
            System.out.print("--");
        }
        System.out.println("-");
    }

    public int get_width() {
        return this.width;
    }

    public int get_height() {
        return this.height;
    }

    public Number[][] get_map() {
        return this.tab.clone();
    }
}
