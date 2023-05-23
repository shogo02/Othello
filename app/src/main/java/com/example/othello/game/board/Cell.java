package com.example.othello.game.board;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.othello.constants.Constants;
import com.example.othello.constants.StoneColor;
import com.example.othello.game.Game;

public class Cell {
    private Integer cellId;
    private StoneColor state;
    private TextView textView;

    public Cell() {

    }

    public Cell(Integer cellId, StoneColor state) {
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
                game.onClickCell(cell);
            }
        };
    }

    public boolean isStateBlack() {
        return state == StoneColor.BLACK;
    }

    public boolean isStateWhite() {
        return state == StoneColor.WHITE;
    }

    public boolean isEmpty() {
        return state == null;
    }


    public boolean isSameState(StoneColor state) {
        return this.state == state;
    }

    public StoneColor getEnemyState() {
        return isStateBlack() ? StoneColor.WHITE : StoneColor.BLACK;
    }

    public Integer getId() {
        return cellId;
    }

    public StoneColor getState() {
        return state;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setBlack() {
        state = StoneColor.BLACK;
//        MAIN_ACTIVITY.updateTextView(textView, "●");
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("●");
            }
        });
    }

    public void setWhite() {
        state = StoneColor.WHITE;
//        MAIN_ACTIVITY.updateTextView(textView, "○");
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("○");
            }
        });
    }

    public void setEmpty() {
        state = null;
//        MAIN_ACTIVITY.updateTextView(textView, "");
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("");
            }
        });
    }

    public void setHintCellId() {
//        MAIN_ACTIVITY.updateTextView(textView, String.valueOf(cellId));
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setHint(String.valueOf(cellId));
            }
        });
    }

    public void setHintCanPut() {
//        MAIN_ACTIVITY.updateTextView(textView, "・");
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setHint("・");
            }
        });
    }
}
