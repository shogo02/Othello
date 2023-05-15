package com.example.othello;


import android.widget.TextView;

public class Game {
    public TextView turnText;

    public TextView stoneCountText;
    public String currentTurn;

    public int blackStoneCount;
    public int whiteStoneCount;

    protected Board board;

    public Game(TextView turnText, TextView stoneCountText) {
        this.turnText = turnText;
        this.stoneCountText = stoneCountText;
    }

    public void startGame() {
        setFirstTurn();
        setTurnText();
    }

    public void resetGame() {
        board.resetBoard();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
    }

    public void setFirstTurn() {
        currentTurn = Constants.PLAYER_BLACK;
    }

    public String toggleTurn() {
        if (currentTurn == Constants.PLAYER_BLACK) {
            currentTurn = Constants.PLAYER_WHITE;
        } else if (currentTurn == Constants.PLAYER_WHITE) {
            currentTurn = Constants.PLAYER_BLACK;
        }
        setTurnText();
        return currentTurn;
    }

    public void updateStoneCount() {
        blackStoneCount = board.getStoneCount(Constants.PLAYER_BLACK);
        whiteStoneCount = board.getStoneCount(Constants.PLAYER_WHITE);
        stoneCountText.setText("白：" + whiteStoneCount + "　　" + "黒：" + blackStoneCount);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setTurnText() {
        turnText.setText(currentTurn + "の番です");
    }
}
