package com.example.othello;


import static com.example.othello.MainActivity.MAIN_ACTIVITY;

import android.util.ArrayMap;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Game {
    public TextView turnText;

    public TextView stoneCountText;
    public Player currentTurn;

    public int blackStoneCount;
    public int whiteStoneCount;

    private Board board;

    private BoardCheckService boardCheckService;

    public Game(TextView turnText, TextView stoneCountText, Board board, BoardCheckService boardCheckService) {
        this.turnText = turnText;
        this.stoneCountText = stoneCountText;
        this.board = board;
        this.boardCheckService = boardCheckService;
    }

    public void startGame() {
        setFirstTurn();
        setTurnText();
        updateStoneCount();

        boardCheckService.check(board, currentTurn);
        board.setHintCanPut(boardCheckService.getAvailableCells(currentTurn));
    }

    public void resetGame() {
        board.resetBoard();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
        boardCheckService.check(board, currentTurn);
        board.setHintCanPut(boardCheckService.getAvailableCells(currentTurn));
    }

    public void setFirstTurn() {
        currentTurn = Player.BLACK;
    }

    public void putStone(Cell cell) {
        boolean isAvailableCell = boardCheckService.isAvailableCell(cell, currentTurn);

        if (cell.isEmpty() && isAvailableCell) {
            ArrayList<Cell> reversibleCells = boardCheckService.getReversibleCells(cell, currentTurn);
            board.putStone(cell, currentTurn);
            board.reverseStone(reversibleCells, currentTurn);

            updateStoneCount();

            toggleTurn();
            board.resetTextViewHint();
            boardCheckService.check(board, currentTurn);
            ArrayMap<Integer, CanPutCell> availableCells = boardCheckService.getAvailableCells(currentTurn);
            board.setHintCanPut(availableCells);

            if (boardCheckService.isPass(currentTurn)) {
                // トースト設定
                Toast passToast = Toast.makeText(
                        MAIN_ACTIVITY,
                        currentTurn + "パス",
                        Toast.LENGTH_SHORT
                );

                toggleTurn();
                board.resetTextViewHint();
                boardCheckService.check(board, currentTurn);
                availableCells = boardCheckService.getAvailableCells(currentTurn);
                board.setHintCanPut(availableCells);

                if (boardCheckService.isGameEnd()) {
                    if (blackStoneCount > whiteStoneCount) {
                        turnText.setText("黒の勝ちです");
                    } else if (blackStoneCount < whiteStoneCount) {
                        turnText.setText("白の勝ちです");
                    } else {
                        turnText.setText("引き分けです");
                    }
                } else {
                    passToast.show();
                }
            }
        } else {

        }
    }

    private void toggleTurn() {
        if (currentTurn == Player.BLACK) {
            currentTurn = Player.WHITE;
        } else if (currentTurn == Player.WHITE) {
            currentTurn = Player.BLACK;
        }
        setTurnText();
    }

    public void updateStoneCount() {
        blackStoneCount = board.getStoneCount(Player.BLACK);
        whiteStoneCount = board.getStoneCount(Player.WHITE);
        stoneCountText.setText("白：" + whiteStoneCount + "　　" + "黒：" + blackStoneCount);
    }

    public void setTurnText() {
        turnText.setText(currentTurn + "の番です");
    }
}
