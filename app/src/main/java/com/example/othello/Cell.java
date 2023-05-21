package com.example.othello;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class Cell {
    private Integer cellId;
    private Player state;
    private TextView textView;

    public Cell() {

    }

    public Cell(Integer cellId, Player state) {
        this.cellId = cellId;
        this.state = state;
    }

    public void createCell(Game game, Board board, int cellId, TableRow.LayoutParams params, Context context) {
        textView = createTextView(cellId, params, context);
        textView.setOnClickListener(onClickCell(game, board));

        this.cellId = cellId;
        setEmpty();
    }

    protected TextView createTextView(int cellId, TableRow.LayoutParams params, Context context) {
        TextView textView = new TextView(context);
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
                Cell cell = board.getCell(textView.getId());
                game.putStone(cell);
            }
        };
    }

    public boolean isStateBlack() {
        return state == Player.BLACK;
    }

    public boolean isStateWhite() {
        return state == Player.WHITE;
    }

    public boolean isEmpty() {
        return state == null;
    }


    public boolean isSameState(Player state) {
        return this.state == state;
    }

    public Player getEnemyState() {
        return isStateBlack() ? Player.WHITE : Player.BLACK;
    }

    public Integer getId() {
        return cellId;
    }

    public Player getState() {
        return state;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setBlack() {
        state = Player.BLACK;
        textView.setText("●");
    }

    public void setWhite() {
        state = Player.WHITE;
        textView.setText("○");
    }

    public void setEmpty() {
        state = null;
        textView.setText("");
    }

    public void setHintCellId() {
        textView.setHint(String.valueOf(cellId));
    }

    public void setHintCanPut() {
        textView.setHint("・");
    }
}
