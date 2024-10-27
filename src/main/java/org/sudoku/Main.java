package org.sudoku;
import org.javatuples.Triplet;

import java.io.File;
import java.security.cert.Extension;

public class Main {
    public static void main(String[] args) {
        GameClass sudoku;

        try {
            if (args.length == 0) {
                Number[][] map = {{Number.NONE, Number.STATIC_HEIGHT, Number.STATIC_FOUR, Number.SIX, Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_ONE},
                        {Number.STATIC_TWO, Number.STATIC_FIVE, Number.STATIC_SEVEN, Number.NONE, Number.NONE, Number.STATIC_ONE, Number.STATIC_SIX, Number.NONE, Number.NONE},
                        {Number.NONE, Number.NONE, Number.STATIC_THREE, Number.STATIC_FIVE, Number.STATIC_FOUR, Number.NONE, Number.NONE, Number.NONE, Number.NONE},
                        {Number.STATIC_ONE, Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_FOUR, Number.NONE, Number.STATIC_FIVE, Number.NONE},
                        {Number.STATIC_SEVEN, Number.NONE, Number.NONE, Number.STATIC_THREE, Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.NONE},
                        {Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_SEVEN, Number.STATIC_FOUR, Number.STATIC_NINE, Number.NONE},
                        {Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_ONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_TWO},
                        {Number.STATIC_FIVE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_TWO, Number.STATIC_HEIGHT, Number.NONE, Number.STATIC_SEVEN, Number.NONE},
                        {Number.NONE, Number.STATIC_TWO, Number.NONE, Number.NONE, Number.NONE, Number.NONE, Number.STATIC_ONE, Number.NONE, Number.NONE}};
                sudoku = new GameClass(map);
            } else if (args.length == 1) {
                File file = new File(args[0]);
                sudoku = new GameClass(file);
            } else {
                return;
            }

            sudoku.gameLoop();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        return;
    }
}
