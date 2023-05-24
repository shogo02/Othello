package com.example.othello.game.board;

import static com.example.othello.constants.Constants.*;
import static com.example.othello.constants.EnumStoneColor.*;

import android.util.ArrayMap;

import com.example.othello.viewController.BoardViewControllerBase;
import com.example.othello.constants.EnumDirection;
import com.example.othello.constants.EnumStoneColor;
import com.example.othello.game.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, Cell> boardMap = new HashMap<Integer, Cell>();

    public BoardCheckService boardCheckService;

    public BoardViewControllerBase boardViewController;

    public Board(BoardCheckService boardCheckService, BoardViewControllerBase boardViewController) {
        this.boardCheckService = boardCheckService;
        this.boardViewController = boardViewController;
    }

    public void init(Game game) {
        boardViewController.createBoard();
        boardViewController.setCellOnClickListner(game, this);
        setCell();
    }

    private void setCell() {
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            Cell cell = new Cell(i, boardViewController);
            boardMap.put(i, cell);
            setFirstStone(cell);
        }
    }

    public void setFirstStone(Cell cell) {
        int topRight = (BOARD_SIZE / 2) * (BOARD_SIZE - 1);
        int topLeft = topRight + EnumDirection.LEFT.getNumber();
        int bottomRight = (BOARD_SIZE / 2) * (BOARD_SIZE + 1);
        int bottomLeft = bottomRight + EnumDirection.LEFT.getNumber();

        if (cell.getId() == topLeft || cell.getId() == bottomRight) {
            cell.setWhite();
        } else if (cell.getId() == topRight || cell.getId() == bottomLeft) {
            cell.setBlack();
        }
    }

    public void putStone(Cell cell, EnumStoneColor enumStoneColor) {
        if (enumStoneColor == BLACK) {
            cell.setBlack();
        } else if (enumStoneColor == WHITE) {
            cell.setWhite();
        }
    }

    public void reverseStone(ArrayList<Cell> cells, EnumStoneColor enumStoneColor) {
        for (Cell cell : cells) {
            putStone(cell, enumStoneColor);
        }
    }

    public void setHintCanPut(ArrayMap<Integer, CanPutCell> canPutCells) {
        for (CanPutCell canPutCell : canPutCells.values()) {
            Cell cell = boardMap.get(canPutCell.cell.getId());
            cell.setHintCanPut();
        }
    }

    public void resetBoard() {
        for (Cell cell : boardMap.values()) {
            cell.setEmpty();
            setFirstStone(cell);
            cell.setHintCellId();
        }
    }

    public void resetTextViewHint() {
        for (Cell cell : boardMap.values()) {
            cell.setHintCellId();
        }
    }

    public java.util.Collection<Cell> getBoardMapValues() {
        return boardMap.values();
    }

    public int getStoneCount(EnumStoneColor enumStoneColor) {
        int blackCount = 0;
        int whiteCount = 0;
        for (Cell cell : boardMap.values()) {
            if (cell.isStateBlack()) blackCount++;
            if (cell.isStateWhite()) whiteCount++;
        }
        if (enumStoneColor == BLACK) return blackCount;
        if (enumStoneColor == WHITE) return whiteCount;
        throw new Error("not found player");
    }

    public Cell getCell(int cellId) {
        return boardMap.get(cellId);
    }
}
