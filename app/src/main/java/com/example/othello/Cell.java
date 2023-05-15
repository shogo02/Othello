package com.example.othello;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class Cell {
    public int cellId;
    public String state;
    protected TextView textView;


    public void createCell(Game game, Board board, int cellId, TableRow.LayoutParams params) {
        textView = createTextView(cellId, params);
        textView.setOnClickListener(onClickCell(game, board)); // OnClickListenerを追加する

        this.cellId = cellId;
        this.state = "";
    }

    protected TextView createTextView(int cellId, TableRow.LayoutParams params) {
        TextView textView = new TextView(MainActivity.mainActivity);
        textView.setLayoutParams(params);
        textView.setTextSize(40);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Constants.BOARD_BACKGROUND);
        textView.setId(cellId);
        textView.setHint(String.valueOf(cellId));
        textView.setHintTextColor(Color.argb(50, 10, 10, 10));
        return textView;
    }

    protected View.OnClickListener onClickCell(Game game, Board board) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                if(textView.getText() == "") {
                    textView.setText(game.currentTurn == Constants.PLAYER_WHITE ? "O" : "X");
                    Cell cell = board.boardMap.get(String.valueOf(textView.getId()));
                    cell.state = game.currentTurn == Constants.PLAYER_WHITE ? Constants.PLAYER_WHITE : Constants.PLAYER_BLACK;
                    board.boardMap.replace(String.valueOf(textView.getId()), cell);
                    game.toggleTurn();
                    game.updateStoneCount();
                }
            }
        };
    }
}
