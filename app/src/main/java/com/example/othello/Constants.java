package com.example.othello;


import android.graphics.Color;

public class Constants {
    public static final int BOARD_SIZE = 8;

    public static final int BOARD_MAX_ID = BOARD_SIZE * BOARD_SIZE;
    public static final int BOARD_LINE = 5;
    public static final int BOARD_LINE_COLOR = Color.BLACK;
    public static final int BOARD_BACKGROUND = Color.GREEN;
}

enum Player {
    BLACK,
    WHITE
}

enum Direction {
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