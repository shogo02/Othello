package com.example.othello.game.board;

import com.example.othello.BoardViewController;
import com.example.othello.constants.StoneColor;

public class Cell {
    private Integer cellId;
    private StoneColor stoneColor;

    private BoardViewController boardViewController;

    public Cell(Integer cellId, BoardViewController boardViewController) {
        this.cellId = cellId;
        this.boardViewController = boardViewController;
    }

    public boolean isStateBlack() {
        return stoneColor == StoneColor.BLACK;
    }

    public boolean isStateWhite() {
        return stoneColor == StoneColor.WHITE;
    }

    public boolean isEmpty() {
        return stoneColor == null;
    }


    public boolean isSameState(StoneColor state) {
        return this.stoneColor == state;
    }

    public Integer getId() {
        return cellId;
    }


    public void setBlack() {
        stoneColor = StoneColor.BLACK;
        boardViewController.setCellText(cellId, "●");
    }

    public void setWhite() {
        stoneColor = StoneColor.WHITE;
        boardViewController.setCellText(cellId, "○");
    }

    public void setEmpty() {
        stoneColor = null;
        boardViewController.setCellText(cellId, "");
    }

    public void setHintCellId() {
        boardViewController.setHintText(cellId, String.valueOf(cellId));
    }

    public void setHintCanPut() {
        boardViewController.setHintText(cellId, "・");
    }
}
