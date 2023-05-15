package com.example.othello;

import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.HashMap;

public class Board {

    public HashMap<String, Cell> boardMap = new HashMap<String, Cell>();

    public Board() {

    }

    void boardInit(Game game, TableLayout tableLayout) {
        // paddingを設定
        tableLayout.setPadding(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);
        tableLayout.setBackgroundColor(Constants.BOARD_LINE_COLOR);

        int cellId = 0;
        // TableRowとTextViewを作成してTableLayoutに追加する
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            TableRow tableRow = new TableRow(MainActivity.mainActivity);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                layoutParams.setMargins(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);

                Cell cell = new Cell();
                cell.createCell(game, this, cellId, layoutParams);

                boardMap.put(String.valueOf(cellId), cell);

                tableRow.addView(cell.textView);

                cellId++;
            }

            tableLayout.addView(tableRow);
        }
    }

    public void resetBoard() {
        for (Cell cell : boardMap.values()) {
            cell.textView.setText("");
            cell.state = "";
        }
    }

    public int getStoneCount(String player) {
        int blackCount = 0;
        int whiteCount = 0;
        for (Cell cell : boardMap.values()) {
            if (cell.state == Constants.PLAYER_BLACK) blackCount++;
            if (cell.state == Constants.PLAYER_WHITE) whiteCount++;
        }
        if (player == Constants.PLAYER_BLACK) return blackCount;
        if (player == Constants.PLAYER_WHITE) return whiteCount;
        throw new Error("not found player");
    }
}
