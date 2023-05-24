package com.example.othello.game.board;

import static com.example.othello.constants.Constants.*;

import android.util.ArrayMap;

import com.example.othello.viewController.BoardViewController;
import com.example.othello.constants.Direction;
import com.example.othello.constants.StoneColor;
import com.example.othello.game.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, Cell> boardMap = new HashMap<Integer, Cell>();

    public BoardCheckService boardCheckService;

    public BoardViewController boardViewController;

    public Board(BoardCheckService boardCheckService, BoardViewController boardViewController) {
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
        int topLeft = topRight + Direction.LEFT.getNumber();
        int bottomRight = (BOARD_SIZE / 2) * (BOARD_SIZE + 1);
        int bottomLeft = bottomRight + Direction.LEFT.getNumber();

        if (cell.getId() == topLeft || cell.getId() == bottomRight) {
            cell.setWhite();
        } else if (cell.getId() == topRight || cell.getId() == bottomLeft) {
            cell.setBlack();
        }
    }

    public void putStone(Cell cell, StoneColor stoneColor) {
        if (stoneColor == StoneColor.BLACK) {
            cell.setBlack();
        } else if (stoneColor == StoneColor.WHITE) {
            cell.setWhite();
        }
    }

    public void reverseStone(ArrayList<Cell> cells, StoneColor stoneColor) {
        for (Cell cell : cells) {
            putStone(cell, stoneColor);
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

    public int getStoneCount(StoneColor stoneColor) {
        int blackCount = 0;
        int whiteCount = 0;
        for (Cell cell : boardMap.values()) {
            if (cell.isStateBlack()) blackCount++;
            if (cell.isStateWhite()) whiteCount++;
        }
        if (stoneColor == StoneColor.BLACK) return blackCount;
        if (stoneColor == StoneColor.WHITE) return whiteCount;
        throw new Error("not found player");
    }

    public Cell getCell(int cellId) {
        return boardMap.get(cellId);
    }
}
