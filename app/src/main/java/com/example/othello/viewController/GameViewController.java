package com.example.othello.viewController;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.othello.MainActivity;
import com.example.othello.R;
import com.example.othello.game.Game;

public class GameViewController {

    private final MainActivity mainActivity = MainActivity.getMainActivity();

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

    public void setOnClickListner(Game game) {
        resetBtn.setOnClickListener(view -> {
            game.resetGame();
        });

        blackPlayerBtn.setOnClickListener(view -> {
            game.onClickBlackPlayerBtn();
        });

        whitePlayerBtn.setOnClickListener(view -> {
            game.onClickWhitePlayerBtn();
        });
    }

    public void setTurnText(String text) {
        turnText.post(new Runnable() {
            @Override
            public void run() {
                turnText.setText(text);
            }
        });
    }

    public void setStoneCountText(String text) {
        stoneCountText.post(new Runnable() {
            @Override
            public void run() {
                stoneCountText.setText(text);
            }
        });
    }

    public void setBlackPlayerBtnText(String text) {
        blackPlayerBtn.post(new Runnable() {
            @Override
            public void run() {
                blackPlayerBtn.setText(text);
            }
        });
    }

    public void setWhitePlayerBtnText(String text) {
        whitePlayerBtn.post(new Runnable() {
            @Override
            public void run() {
                whitePlayerBtn.setText(text);
            }
        });
    }
}
