package org.sudoku;

import java.util.Scanner;
import org.javatuples.Triplet;
import java.io.File;

public class GameClass {
    private Grid grid;
    private boolean quit;
    private MoveStack undoStack;
    private MoveStack redoStack;
    private Scanner scan;

    public GameClass() throws Exception{
        this.grid = new Grid(9, 9);
        this.quit = false;
        this.undoStack = new MoveStack();
        this.redoStack = new MoveStack();
        scan = new Scanner(System.in);
    }

    public GameClass(Number[][] tab) throws Exception{
        this.grid = new Grid(tab);
        this.quit = false;
        this.undoStack = new MoveStack();
        this.redoStack = new MoveStack();
        scan = new Scanner(System.in);
    }

    public GameClass(File file) throws Exception {
        Scanner scanFile = new Scanner(file);

        int cptr = 0;
        Number[][] tab = new Number[9][9];

        while (scanFile.hasNextLine()) {
            if (cptr >= 9)
                throw new Exception("Too many line in file");

            String line = scanFile.nextLine();
            if (line.length() != 9)
                throw new Exception("Not enough element in line " + cptr);

            for (int i= 0; i < 9; i++) {
                char c = line.charAt(i);
                int num = Character.getNumericValue(c);
                tab[cptr][i] = Number.GetStaticNumberFromInt(num);
            }
            cptr += 1;
        }

        scanFile.close();

        if (cptr < 9)
            throw new Exception("Not enough line in file");

        this.grid = new Grid(tab);
        this.quit = false;
        this.undoStack = new MoveStack();
        this.redoStack = new MoveStack();
        this.scan = new Scanner(System.in);
    }

    public void quit() {
        this.quit = true;
        System.out.println("BOOOh, you leave the game !!\n U suck !");
    }

    public void getAction() throws Exception {

        System.out.println("[q/Q] : Quit game\n[s/S] : solver\n[m/M] : Move element in game\n[r/R] : redo\n[u/U] : undo");

        switch(scan.next().charAt(0)) {
            case 'q' : quit();
                        break;
            case 'Q' : quit();
                        break;
            case 's' : solver();
                        break;
            case 'S' : solver();
                        break;
            case 'm' : Triplet<Integer, Integer, Number> m = getInput();
                        move(m.getValue0(), m.getValue1(), m.getValue2());
                        break;
            case 'r': redo();
                        break;
            case 'R': redo();
                        break;
            case 'u': undo();
                        break;
            case 'U': undo();
                        break;
            default: break;
        }

    }

    private Triplet<Integer, Integer, Number> getInput() {

        System.out.println("Choose X position : (1 - " + (this.grid.get_width()) + ") : ");

        int x = 0;
        do {
            x = scan.nextInt(); //the x position
        } while(!(x >= 1 && x <= 9));
        x -= 1;

        System.out.println("Choose Y position : (1 - " + (this.grid.get_height()) + ") : ");
        int y = 0;
        do {
            y = scan.nextInt(); //the x position
        } while(!(y >= 1 && y <= 9));
        y -= 1;

        System.out.println("Choose value : (1 - 9) (anything else to not select) : ");
        char c = scan.next().charAt(0);
        Number val = switch (c) {
            case '1' -> Number.ONE;
            case '2' -> Number.TWO;
            case '3' -> Number.THREE;
            case '4' -> Number.FOUR;
            case '5' -> Number.FIVE;
            case '6' -> Number.SIX;
            case '7' -> Number.SEVEN;
            case '8' -> Number.HEIGHT;
            case '9' -> Number.NINE;
            default -> Number.NONE;
        };

        return new Triplet<>(x, y, val);
    }

    private boolean checkLine(int y, Number val) throws Exception{
        if (!(y >= 0 && y < this.grid.get_height()))
            throw new Exception("Error: out of bound access");

        if (val == Number.NONE)
            return true;

        for (int i = 0; i < this.grid.get_width(); i++) {
            if (this.grid.get(i, y).getNumber() == val.getNumber()) //check if number is equal to the number of static number with same value
                return false;
        }

        return true;
    }

    private boolean checkRow(int x, Number val) throws Exception {
        if (!(x >= 0 && x < this.grid.get_width()))
            throw new Exception("Error: out of bound access");

        if (val == Number.NONE)
            return true;

        for (int i = 0; i < this.grid.get_height(); i++) {
            if (this.grid.get(x, i).getNumber() == val.getNumber())
                return false;
        }
        return true;
    }

    private boolean checkSquare(int x, int y, Number val) throws Exception{
        if (!(x >= 0 && x < 9) || !(y >= 0 && y < 9))
            throw new Exception("Error : out of bound access");

        if (val == Number.NONE)
            return true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.grid.get(x - (x % 3) + j, y - (y % 3) + i).getNumber() == val.getNumber())
                    return false;
            }
        }
        return true;
    }

    private boolean restricted_set(int x, int y, Number val) throws Exception{
        if (!(x >= 0 && x < 9) || !(y >= 0 && y < 9))
            throw new Exception("Error : out of bound");

        if (val == Number.STATIC_ONE || val == Number.STATIC_TWO || val == Number.STATIC_THREE || val == Number.STATIC_FOUR || val == Number.STATIC_FIVE || val == Number.STATIC_SIX ||
                val == Number.STATIC_SEVEN || val == Number.STATIC_HEIGHT || val == Number.STATIC_NINE)
            return false;

        Number n = this.grid.get(x, y);
        if (n == Number.STATIC_ONE || n == Number.STATIC_TWO || n == Number.STATIC_THREE || n == Number.STATIC_FOUR || n == Number.STATIC_FIVE || n == Number.STATIC_SIX ||
                n == Number.STATIC_SEVEN || n == Number.STATIC_HEIGHT || n == Number.STATIC_NINE)
            return false;

        this.grid.set(x, y, val);
        return true;
    }

    private boolean move(int x, int y, Number val) throws Exception {
        if (!(x >= 0 && x < 9) || !(y >= 0 && y < 9))
            throw new Exception("Error: out of bound");

        if (!this.checkLine(y, val))
            return false;

        if (!this.checkRow(x, val))
            return false;

        if (!this.checkSquare(x, y, val))
            return false;

        Number old = this.grid.get(x, y);
        if (this.restricted_set(x, y, val)) {
            Move m = new Move(x, y, old, val);
            this.undoStack.add(m);
            this.redoStack.clear();
        } else {
            return false;
        }

        return true;
    }

    public void display() {
        this.grid.display();
    }

    private boolean isLineValid(int y) throws Exception {
        if (!(y >= 0 && y < 9))
            throw new Exception("Error: out of bound");

        int[] n = new int[10];
        for (int i = 0; i < 9; i++) {
            int index = this.grid.get(i, y).getNumber();
            n[index] += 1;

            if (n[0] > 0)
                return false;

            if ((index >= 1 && index <= 9) && (n[index] == 0 || n[index] > 1))
                return false;
        }

        return true;
    }

    private boolean isRowValid(int x) throws Exception{
        if (!(x >= 0 && x < 9))
            throw new Exception("Error: out of bound");

        int[] n = new int[10];
        for (int i = 0; i < 9; i++) {
            int index = this.grid.get(x, i).getNumber();
            n[index] += 1;

            if (n[0] > 0)
                return false;

            if ((index >= 1 && index <= 9) && (n[index] == 0 || n[index] > 1))
                return false;
        }

        return true;
    }
    private boolean isSquareValid(int x, int y) throws Exception {
        if (!(x >= 0 && x < 9) || !(y >= 0 && y < 9))
            throw new Exception("Error: out of bound");

        int[] n = new int[10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int index = this.grid.get(x - (x % 3) + j, y - (y % 3) + i).getNumber();
                n[index] += 1;

                if (n[0] > 0)
                    return false;

                if ((index >= 1 && index <= 9) && (n[index] == 0 || n[index] > 1))
                    return false;
            }
        }

        return true;
    }

    private boolean isOver() throws Exception{
        for (int i = 0; i < 9; i++) {
            if (!this.isLineValid(i) || !this.isRowValid(i))
                return false;
            if (i % 3 == 0) {
                for (int j = 0; j < 9; j += 3) {
                    if (!this.isSquareValid(j, i))
                        return false;
                }
            }
        }

        System.out.println("You're the GOAT, U finished the map");
        return true;
    }

    public void gameLoop() throws Exception{
        while (!this.isOver() && !this.quit) {
            display();
            getAction();
        }
        this.scan.close();
        if (!this.quit)
            display();
    }

    public boolean backtracking(int x, int y) throws Exception{
        if (y >= 9)
            return true;

        if (x >= 9)
            return backtracking(0, y+1);

        Number n = grid.get(x, y);
        if (n == Number.STATIC_ONE || n == Number.STATIC_TWO || n == Number.STATIC_THREE || n == Number.STATIC_FOUR || n == Number.STATIC_FIVE || n == Number.STATIC_SIX ||
                n == Number.STATIC_SEVEN || n == Number.STATIC_HEIGHT || n == Number.STATIC_NINE)
            return backtracking(x+1, y);

        for (int i = 1; i <= 9; i++) {
            Number val = Number.GetNumberFromInt(i);
            if (checkLine(y, val) && checkRow(x, val) && checkSquare(x, y, val)) {

                grid.set(x, y, val);
                if (backtracking(x + 1, y))
                    return true;

                grid.set(x, y, n);
            }
        }

        return false;
    }

    public boolean solver() throws Exception{
        this.clearMap();
        return backtracking(0, 0);
    }

    public void redo() {
        if (this.redoStack.isEmpty())
            return;

        Move current = this.redoStack.get();
        this.redoStack.pop();

        this.undoStack.add(new Move(current.get_x(), current.get_y(), current.get_newVal(), current.get_oldVal()));

        grid.set(current.get_x(), current.get_y(), current.get_oldVal());
    }

    public void undo() {
        if (this.undoStack.isEmpty())
            return;

        Move current = this.undoStack.get();
        this.undoStack.pop();

        this.redoStack.add(new Move(current.get_x(), current.get_y(), current.get_newVal(), current.get_oldVal()));

        grid.set(current.get_x(), current.get_y(), current.get_oldVal());
    }

    private void clearMap() throws Exception{
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Number n = grid.get(j, i);
                if (n == Number.STATIC_ONE || n == Number.STATIC_TWO || n == Number.STATIC_THREE || n == Number.STATIC_FOUR || n == Number.STATIC_FIVE || n == Number.STATIC_SIX ||
                        n == Number.STATIC_SEVEN || n == Number.STATIC_HEIGHT || n == Number.STATIC_NINE)
                    continue;

                grid.set(j, i, Number.NONE);
            }
        }
    }
}

