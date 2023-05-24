package com.example.othello.game.board;

import android.util.ArrayMap;

import com.example.othello.constants.EnumDirection;

import java.util.ArrayList;

public class CanPutCell {
    public Cell cell; // 置けるセルを格納
    public ArrayMap<EnumDirection, ArrayList<Cell>> reversibleCells; // 裏返るセルを格納

    public CanPutCell(Cell cell, ArrayMap<EnumDirection, ArrayList<Cell>> reversibleCells) {
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
