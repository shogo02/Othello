package com.example.othello.constants;

public enum Direction {
    TOP("top", -Constants.BOARD_SIZE),
    BOTTOM("bottom", Constants.BOARD_SIZE),
    LEFT("left", -1),
    RIGHT("right", 1),
    TOP_LEFT("top_left", -Constants.BOARD_SIZE - 1),
    TOP_RIGHT("top_right", -Constants.BOARD_SIZE + 1),
    BOTTOM_LEFT("bottom_left", Constants.BOARD_SIZE - 1),
    BOTTOM_RIGHT("bottom_right", Constants.BOARD_SIZE + 1);

    private String direction;
    private int number;

    Direction(String direction, int number) {
        this.direction = direction;
        this.number = number;
    }

    public String getDirection() {
        return direction;
    }

    public int getNumber() {
        return number;
    }
}
