package com.example.othello;

import android.widget.Button;
import android.widget.TextView;

import com.example.othello.game.Game;

public class GameViewController {

    private final MainActivity mainActivity = MainActivity.getMainActivity();

    private TextView turnText;

    private Button resetBtn;

    private TextView stoneCountText;

    public void init() {
        turnText = mainActivity.findViewById(R.id.turnText);
        resetBtn = mainActivity.findViewById(R.id.resetBtn);
        stoneCountText = mainActivity.findViewById(R.id.stoneCountText);
    }

    public void setOnClickListner(Game game) {
        resetBtn.setOnClickListener(view -> {
            game.resetGame();
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
}
