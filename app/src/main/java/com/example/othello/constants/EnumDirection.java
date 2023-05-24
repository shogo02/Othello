package com.example.othello.constants;

public enum EnumDirection {
    TOP(-Constants.BOARD_SIZE),
    BOTTOM(Constants.BOARD_SIZE),
    LEFT(-1),
    RIGHT(1),
    TOP_LEFT(-Constants.BOARD_SIZE - 1),
    TOP_RIGHT(-Constants.BOARD_SIZE + 1),
    BOTTOM_LEFT(Constants.BOARD_SIZE - 1),
    BOTTOM_RIGHT(Constants.BOARD_SIZE + 1);

    private int number;

    EnumDirection(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
