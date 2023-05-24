package com.example.othello.viewController;

import static com.example.othello.constants.Constants.*;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.othello.R;
import com.example.othello.game.Game;
import com.example.othello.game.board.Board;
import com.example.othello.game.board.Cell;

public class BoardViewControllerBase extends ViewControllerBase {
    private TableLayout board;

    public void init() {
        board = mainActivity.findViewById(R.id.board);
    }

    public void createBoard() {
        board.setPadding(BOARD_LINE, BOARD_LINE, BOARD_LINE, BOARD_LINE);
        board.setBackgroundColor(BOARD_LINE_COLOR);
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
        params.setMargins(BOARD_LINE, BOARD_LINE, BOARD_LINE, BOARD_LINE);

        TextView textView = new TextView(mainActivity);
        textView.setLayoutParams(params);
        textView.setTextSize(40);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(BOARD_BACKGROUND);
        textView.setId(cellId);
        textView.setText("");
        textView.setHint(String.valueOf(cellId));
        textView.setHintTextColor(Color.argb(50, 10, 10, 10));
        return textView;
    }

    public void setCellOnClickListner(Game game, Board board) {
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            TextView cellView = mainActivity.findViewById(i);
            cellView.setOnClickListener(view -> {
                Cell cell = board.getCell(view.getId());
                game.onClickCell(cell);
            });
        }
    }

    public void setCellText(Integer cellId, String text) {
        TextView cellView = mainActivity.findViewById(cellId);
        postViewSetText(cellView, text);
    }
    public void setHintText(Integer cellId, String text) {
        TextView cellView = mainActivity.findViewById(cellId);
        postViewSetHint(cellView, text);
    }
}
