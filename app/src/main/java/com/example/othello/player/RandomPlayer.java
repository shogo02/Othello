package com.example.othello.player;

import android.util.ArrayMap;

import com.example.othello.constants.EnumPlayer;
import com.example.othello.constants.EnumStoneColor;
import com.example.othello.game.board.Board;
import com.example.othello.game.board.BoardCheckService;
import com.example.othello.game.board.CanPutCell;

public class RandomPlayer extends Player{


    public RandomPlayer(EnumPlayer playerType, EnumStoneColor enumStoneColor) {
        super(playerType, enumStoneColor);
    }

    public boolean putRandomStone(Board board) {
        BoardCheckService check = board.boardCheckService;
        ArrayMap<Integer, CanPutCell> availableCells = check.getAvailableCells(getStoneColor());
        int randomIndex = (int) (Math.random() * availableCells.size());

        if (availableCells.size() <= 0) return false;

        CanPutCell canPutCell = availableCells.valueAt(randomIndex);
        putStone(canPutCell.cell, canPutCell.getReversibleCells(), board);
        return true;
    }
}
