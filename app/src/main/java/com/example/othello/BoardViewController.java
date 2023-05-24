package com.example.othello;

import static com.example.othello.constants.Constants.BOARD_SIZE;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.othello.constants.Constants;
import com.example.othello.game.Game;
import com.example.othello.game.board.Cell;

public class BoardViewController {

    private TableLayout board;

    private final MainActivity mainActivity = MainActivity.getMainActivity();

    public void init() {
        board = mainActivity.findViewById(R.id.board);
    }

    public void createBoard() {
        board.setPadding(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);
        board.setBackgroundColor(Constants.BOARD_LINE_COLOR);
        int cellId = 0;
        // TableRowとTextViewを作成してTableLayoutに追加する
        for (int i = 0; i < BOARD_SIZE; i++) {
            TableRow tableRow = createBoardRow();
            for (int j = 0; j < BOARD_SIZE; j++) {
                tableRow.addView(createCellView(cellId));
                cellId++;
            }
            board.addView(tableRow);
        }
    }

    public TableRow createBoardRow() {
        TableRow tableRow = new TableRow(mainActivity);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
        return tableRow;
    }

    public TextView createCellView(int cellId) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE, Constants.BOARD_LINE);

        TextView textView = new TextView(mainActivity);
        textView.setLayoutParams(params);
        textView.setTextSize(40);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Constants.BOARD_BACKGROUND);
        textView.setId(cellId);
        textView.setText("");
        textView.setHint(String.valueOf(cellId));
        textView.setHintTextColor(Color.argb(50, 10, 10, 10));
        return textView;
    }

    public void setCellOnClickListner(Game game) {
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            TextView cellView = mainActivity.findViewById(i);
            cellView.setOnClickListener(view -> {
                Cell cell = game.getBoard().getCell(view.getId());
                game.onClickCell(cell);
            });
        }
    }

    public void setCellText(Integer cellId, String text) {
        TextView cellView = mainActivity.findViewById(cellId);
        cellView.post(new Runnable() {
            @Override
            public void run() {
                cellView.setText(text);
            }
        });
    }
    public void setHintText(Integer cellId, String text) {
        TextView cellView = mainActivity.findViewById(cellId);
        cellView.post(new Runnable() {
            @Override
            public void run() {
                cellView.setHint(text);
            }
        });
    }
}
