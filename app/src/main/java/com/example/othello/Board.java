package com.example.othello;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap<Integer, Cell> boardMap = new HashMap<Integer, Cell>();

    public Board() {

    }

    void boardInit(Game game, TableLayout tableLayout, Context context) {
        // paddingを設定
        tableLayout.setPadding(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);
        tableLayout.setBackgroundColor(Constants.BOARD_LINE_COLOR);

        int cellId = 0;
        // TableRowとTextViewを作成してTableLayoutに追加する
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
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
        // TODO 8*8にしか対応してない
        if (cell.getId() == 27 || cell.getId() == 36) {
            cell.setBlack();
        } else if (cell.getId() == 28 || cell.getId() == 35) {
            cell.setWhite();
        }
    }

    public void putStone(Cell cell, Player player) {
        if (player == Player.BLACK) {
            cell.setBlack();
        } else if (player == Player.WHITE) {
            cell.setWhite();
        }
    }

    public void reverseStone(ArrayList<Cell> cells, Player player) {
        for (Cell cell : cells) {
            putStone(cell, player);
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

    public int getStoneCount(Player player) {
        int blackCount = 0;
        int whiteCount = 0;
        for (Cell cell : boardMap.values()) {
            if (cell.isStateBlack()) blackCount++;
            if (cell.isStateWhite()) whiteCount++;
        }
        if (player == Player.BLACK) return blackCount;
        if (player == Player.WHITE) return whiteCount;
        throw new Error("not found player");
    }

    public Cell getCell(int cellId) {
        return boardMap.get(cellId);
    }
}
