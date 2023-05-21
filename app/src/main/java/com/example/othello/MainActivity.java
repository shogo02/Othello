package com.example.othello;


import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity MAIN_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAIN_ACTIVITY = this;

        // ダークモードを無効化
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);

        TextView turnText = findViewById(R.id.turnText);
        TableLayout tableLayout = findViewById(R.id.board);
        Button resetBtn = findViewById(R.id.resetBtn);
        TextView stoneCountText = findViewById(R.id.stoneCountText);

        Board board = new Board();
        BoardCheckService boardCheckService = new BoardCheckService();
        Game game = new Game(turnText, stoneCountText, board, boardCheckService);

        board.boardInit(game, tableLayout, MAIN_ACTIVITY);
        resetBtn.setOnClickListener(view -> {
            game.resetGame();
        });

        game.startGame();
    }
}