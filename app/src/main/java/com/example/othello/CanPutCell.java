package com.example.othello;

import android.util.ArrayMap;

import java.util.ArrayList;

class CanPutCell {
    public Cell cell; // 置けるセルを格納
    public ArrayMap<Direction, ArrayList<Cell>> reversibleCells; // 裏返るセルを格納

    public CanPutCell(Cell cell, ArrayMap<Direction, ArrayList<Cell>> reversibleCells) {
        this.cell = cell;
        this.reversibleCells = reversibleCells;
    }

    public ArrayList<Cell> getReversibleCells() {
        ArrayList<Cell> reversibleCells = new ArrayList<>();
        for (ArrayList<Cell> val :this.reversibleCells.values()) {
            reversibleCells.addAll(val);
        }
        return reversibleCells;
    }
}
