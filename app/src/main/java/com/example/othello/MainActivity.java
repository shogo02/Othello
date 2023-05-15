package com.example.othello;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        TextView turnText = findViewById(R.id.turnText);
        TableLayout tableLayout = findViewById(R.id.board);
        Button resetBtn = findViewById(R.id.resetBtn);
        TextView stoneCountText = findViewById(R.id.stoneCountText);

        Game game = new Game(turnText, stoneCountText);
        Board board = new Board();
        game.setBoard(board);

        board.boardInit(game, tableLayout);
        resetBtn.setOnClickListener(view -> {
            game.resetGame();
        });

        game.startGame();
    }
}