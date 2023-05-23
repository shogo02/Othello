package com.example.othello.game.board;

import static com.example.othello.constants.Constants.*;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.othello.constants.Constants;
import com.example.othello.constants.Direction;
import com.example.othello.constants.StoneColor;
import com.example.othello.game.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, Cell> boardMap = new HashMap<Integer, Cell>();

    public BoardCheckService boardCheckService;

    public Board(BoardCheckService boardCheckService) {
        this.boardCheckService = boardCheckService;
    }

    public void boardInit(Game game, TableLayout tableLayout, Context context) {
        // paddingを設定
        tableLayout.setPadding(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);
        tableLayout.setBackgroundColor(Constants.BOARD_LINE_COLOR);

        int cellId = 0;
        // TableRowとTextViewを作成してTableLayoutに追加する
        for (int i = 0; i < BOARD_SIZE; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            for (int j = 0; j < BOARD_SIZE; j++) {
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                layoutParams.setMargins(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);

                Cell cell = new Cell();
                cell.createCell(game, this, cellId, layoutParams, context);
                setFirstStone(cell);

                boardMap.put(cellId, cell);

                tableRow.addView(cell.getTextView());

                cellId++;
            }

            tableLayout.addView(tableRow);
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
