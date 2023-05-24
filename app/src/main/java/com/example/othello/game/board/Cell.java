package com.example.othello.game.board;

import com.example.othello.viewController.BoardViewControllerBase;
import com.example.othello.constants.EnumStoneColor;

public class Cell {
    private Integer cellId;
    private EnumStoneColor enumStoneColor;

    private BoardViewControllerBase boardViewController;

    public Cell(Integer cellId, BoardViewControllerBase boardViewController) {
        this.cellId = cellId;
        this.boardViewController = boardViewController;
    }

    public boolean isStateBlack() {
        return enumStoneColor == EnumStoneColor.BLACK;
    }

    public boolean isStateWhite() {
        return enumStoneColor == EnumStoneColor.WHITE;
    }

    public boolean isEmpty() {
        return enumStoneColor == null;
    }


    public boolean isSameState(EnumStoneColor state) {
        return this.enumStoneColor == state;
    }

    public Integer getId() {
        return cellId;
    }


    public void setBlack() {
        enumStoneColor = EnumStoneColor.BLACK;
        boardViewController.setCellText(cellId, "●");
    }

    public void setWhite() {
        enumStoneColor = EnumStoneColor.WHITE;
        boardViewController.setCellText(cellId, "○");
    }

    public void setEmpty() {
        enumStoneColor = null;
        boardViewController.setCellText(cellId, "");
    }

    public void setHintCellId() {
        boardViewController.setHintText(cellId, String.valueOf(cellId));
    }

    public void setHintCanPut() {
        boardViewController.setHintText(cellId, "・");
    }
}
