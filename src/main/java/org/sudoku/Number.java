package org.sudoku;

public enum Number {
    ONE(1),
    STATIC_ONE(1),
    TWO(2),
    STATIC_TWO(2),
    THREE(3),
    STATIC_THREE(3),
    FOUR(4),
    STATIC_FOUR(4),
    FIVE(5),
    STATIC_FIVE(5),
    SIX(6),
    STATIC_SIX(6),
    SEVEN(7),
    STATIC_SEVEN(7),
    HEIGHT(8),
    STATIC_HEIGHT(8),
    NINE(9),
    STATIC_NINE(9),
    NONE(0);

    private final int numero;

    Number(int numero) {
        this.numero = numero;
    }

    public int getNumber() {
        return this.numero;
    }

    public static Number GetNumberFromInt(int i) {
        return switch(i) {
            case 1 -> Number.ONE;
            case 2 -> Number.TWO;
            case 3 -> Number.THREE;
            case 4 -> Number.FOUR;
            case 5 -> Number.FIVE;
            case 6 -> Number.SIX;
            case 7 -> Number.SEVEN;
            case 8 -> Number.HEIGHT;
            case 9 -> Number.NINE;
            default -> Number.NONE;
        };
    }

    public static Number GetStaticNumberFromInt(int i) {
        return switch(i) {
            case 1 -> Number.STATIC_ONE;
            case 2 -> Number.STATIC_TWO;
            case 3 -> Number.STATIC_THREE;
            case 4 -> Number.STATIC_FOUR;
            case 5 -> Number.STATIC_FIVE;
            case 6 -> Number.STATIC_SIX;
            case 7 -> Number.STATIC_SEVEN;
            case 8 -> Number.STATIC_HEIGHT;
            case 9 -> Number.STATIC_NINE;
            default -> Number.NONE;
        };
    }

}
