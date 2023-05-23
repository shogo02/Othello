package com.example.othello.game;


import static com.example.othello.MainActivity.MAIN_ACTIVITY;
import static com.example.othello.MainActivity.uiHandler;

import android.util.ArrayMap;
import android.widget.TextView;
import android.widget.Toast;

import com.example.othello.constants.EnumPlayer;
import com.example.othello.constants.StoneColor;
import com.example.othello.game.board.Board;
import com.example.othello.game.board.BoardCheckService;
import com.example.othello.game.board.CanPutCell;
import com.example.othello.game.board.Cell;
import com.example.othello.player.Player;
import com.example.othello.player.RandomPlayer;
import com.example.othello.player.UserPlayer;

import java.util.ArrayList;

public class Game {
    /* DI */
    private Board board;

    /* View */
    public TextView turnText;

    public TextView stoneCountText;

    /* Game */
    public StoneColor currentStoneColor;

    public int blackStoneCount;

    public int whiteStoneCount;

    public Player currentPlayer;

    public Player playerBlack;

    public Player playerWhite;


    public Game(TextView turnText, TextView stoneCountText, Board board) {
        this.turnText = turnText;
        this.stoneCountText = stoneCountText;
        this.board = board;
    }

    public void startGame() {
        initPlayer();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
        board.boardCheckService.check(board, currentStoneColor);
        board.setHintCanPut(board.boardCheckService.getAvailableCells(currentStoneColor));
        gameThreadStart();
    }

    public void gameThreadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PlayerHandler playerHandler = new PlayerHandler(board);

                while (true) {
                    if (!board.boardCheckService.isPass(currentStoneColor)) {
                        playerHandler.handler(currentPlayer);
                    } else { // パス処理
                        board.boardCheckService.bothPlayerCheck(board);

                        if (board.boardCheckService.isGameEnd()) {
                            if (blackStoneCount > whiteStoneCount) {
                                turnText.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        turnText.setText("黒の勝ちです");
                                    }
                                });
                            } else if (blackStoneCount < whiteStoneCount) {
                                turnText.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        turnText.setText("白の勝ちです");
                                    }
                                });
                            } else {
                                turnText.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        turnText.setText("引き分けです");
                                    }
                                });
                            }
                            break;
                        }
                        if (currentPlayer.getStoneColor() == StoneColor.BLACK) {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            MAIN_ACTIVITY,
                                            StoneColor.BLACK + "パス",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });
                        } else {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            MAIN_ACTIVITY,
                                            StoneColor.WHITE + "パス",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });
                        }

                        toggleTurn();
                        board.resetTextViewHint();
                        board.boardCheckService.check(board, currentStoneColor);
                        ArrayMap<Integer, CanPutCell> availableCells = board.boardCheckService.getAvailableCells(currentStoneColor);
                        board.setHintCanPut(availableCells);

                    }

                    if (currentPlayer.getIsPutStone()) {
                        toggleTurn();
                        board.resetTextViewHint();
                        board.boardCheckService.check(board, currentStoneColor);
                        ArrayMap<Integer, CanPutCell> availableCells = board.boardCheckService.getAvailableCells(currentStoneColor);
                        board.setHintCanPut(availableCells);
                        updateStoneCount();
                    }
                }
            }
        }).start();
    }

    public void resetGame() {
        board.resetBoard();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
        board.boardCheckService.check(board, currentStoneColor);
        board.setHintCanPut(board.boardCheckService.getAvailableCells(currentStoneColor));
        gameThreadStart();
    }

    public void setFirstTurn() {
        this.currentStoneColor = StoneColor.BLACK;
        this.currentPlayer = playerBlack;
    }

    public void initPlayer() {
        Player black = new UserPlayer(EnumPlayer.USER, StoneColor.BLACK);
//        Player black = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.BLACK);
        Player white = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.WHITE);
//        Player white = new UserPlayer(EnumPlayer.USER, StoneColor.WHITE);

        this.playerBlack = black;
        this.playerWhite = white;
    }

    public void onClickCell(Cell cell) {
        if (currentPlayer.isUser()) {
            BoardCheckService check = board.boardCheckService;
            boolean isAvailableCell = check.isAvailableCell(cell, currentPlayer.getStoneColor());

            if (cell.isEmpty() && isAvailableCell) {
                ArrayList<Cell> reversibleCells = check.getReversibleCells(cell, currentPlayer.getStoneColor());
                currentPlayer.putStone(cell, reversibleCells, board);
            }
        }
    }

    private void toggleTurn() {
        currentPlayer.toggleIsPutStone();

        if (currentStoneColor == StoneColor.BLACK) {
            currentStoneColor = StoneColor.WHITE;
            currentPlayer = playerWhite;
        } else if (currentStoneColor == StoneColor.WHITE) {
            currentStoneColor = StoneColor.BLACK;
            currentPlayer = playerBlack;
        }
        setTurnText();
    }

    public void updateStoneCount() {
        blackStoneCount = board.getStoneCount(StoneColor.BLACK);
        whiteStoneCount = board.getStoneCount(StoneColor.WHITE);
        stoneCountText.post(new Runnable() {
            @Override
            public void run() {
                stoneCountText.setText("白：" + whiteStoneCount + "　　" + "黒：" + blackStoneCount);
            }
        });
    }

    public void setTurnText() {
        turnText.post(new Runnable() {
            @Override
            public void run() {
                turnText.setText(currentStoneColor + "の番です");
            }
        });
    }
}
