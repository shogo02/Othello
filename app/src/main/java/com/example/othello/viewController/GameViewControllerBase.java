package com.example.othello.viewController;

import static com.example.othello.constants.EnumStoneColor.BLACK;
import static com.example.othello.constants.EnumStoneColor.WHITE;

import android.widget.Button;
import android.widget.TextView;

import com.example.othello.R;
import com.example.othello.game.Game;

public class GameViewControllerBase extends ViewControllerBase {
    private TextView turnText;

    private Button resetBtn;

    private TextView stoneCountText;

    private Button blackPlayerBtn;

    private Button whitePlayerBtn;


    public void init() {
        turnText = mainActivity.findViewById(R.id.turnText);
        resetBtn = mainActivity.findViewById(R.id.resetBtn);
        stoneCountText = mainActivity.findViewById(R.id.stoneCountText);
        blackPlayerBtn = mainActivity.findViewById(R.id.blackPlayerBtn);
        whitePlayerBtn = mainActivity.findViewById(R.id.whitePlayerBtn);
    }

    public void setOnClickListener(Game game) {
        resetBtn.setOnClickListener(view -> {
            game.resetGame();
        });

        blackPlayerBtn.setOnClickListener(view -> {
            game.onClickPlayerBtn(BLACK);
        });

        whitePlayerBtn.setOnClickListener(view -> {
            game.onClickPlayerBtn(WHITE);
        });
    }


    public void setTurnText(String text) {
        postViewSetText(turnText, text);
    }

    public void setStoneCountText(String text) {
        postViewSetText(stoneCountText, text);
    }

    public void setBlackPlayerBtnText(String text) {
        postViewSetText(blackPlayerBtn, text);
    }

    public void setWhitePlayerBtnText(String text) {
        postViewSetText(whitePlayerBtn, text);
    }
}
