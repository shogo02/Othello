package com.example.othello.game;

import android.os.Handler;
import android.util.ArrayMap;
import android.widget.Toast;

import com.example.othello.viewController.BoardViewController;
import com.example.othello.MainActivity;
import com.example.othello.viewController.GameViewController;
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

    private GameViewController gameViewController;

    private BoardViewController boardViewController;

    /* Game */
    public StoneColor currentStoneColor;

    public int blackStoneCount;

    public int whiteStoneCount;

    public Player currentPlayer;

    public Player playerBlack;

    public Player playerWhite;


    public Game(GameViewController gameViewController, BoardViewController boardViewController) {
        this.gameViewController = gameViewController;
        this.boardViewController = boardViewController;
    }

    public void init() {
        BoardCheckService boardCheckService = new BoardCheckService();

        board = new Board(boardCheckService, boardViewController);
        board.init(this);

        gameViewController.setOnClickListner(this);
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

    private void gameThreadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PlayerHandler playerHandler = new PlayerHandler(board);
                Handler uiHandler = MainActivity.getUiHandler();

                while (true) {
                    if (!board.boardCheckService.isPass(currentStoneColor)) {
                        playerHandler.handler(currentPlayer);
                    } else { // パス処理
                        board.boardCheckService.bothPlayerCheck(board);

                        if (board.boardCheckService.isGameEnd()) {
                            if (blackStoneCount > whiteStoneCount) {
                                gameViewController.setTurnText("Black Win");
                            } else if (blackStoneCount < whiteStoneCount) {
                                gameViewController.setTurnText("White Win");
                            } else {
                                gameViewController.setTurnText("Draw");
                            }
                            break;
                        }
                        if (currentPlayer.getStoneColor() == StoneColor.BLACK) {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            MainActivity.getMainActivity(),
                                            StoneColor.BLACK + " Pass",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });
                        } else {
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            MainActivity.getMainActivity(),
                                            StoneColor.WHITE + " Pass",
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

    private void setFirstTurn() {
        this.currentStoneColor = StoneColor.BLACK;
        this.currentPlayer = playerBlack;
    }

    private void initPlayer() {
        Player black = new UserPlayer(EnumPlayer.USER, StoneColor.BLACK);
//        Player black = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.BLACK);
        Player white = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.WHITE);
//        Player white = new UserPlayer(EnumPlayer.USER, StoneColor.WHITE);

        gameViewController.setBlackPlayerBtnText(String.valueOf(EnumPlayer.USER));
        gameViewController.setWhitePlayerBtnText(String.valueOf(EnumPlayer.RANDOM));

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

    private void updateStoneCount() {
        blackStoneCount = board.getStoneCount(StoneColor.BLACK);
        whiteStoneCount = board.getStoneCount(StoneColor.WHITE);
        gameViewController.setStoneCountText("Black：" + blackStoneCount + "　　" + "White：" + whiteStoneCount);
    }

    private void setTurnText() {
        gameViewController.setTurnText(currentStoneColor + " Turn");
    }

    public void onClickBlackPlayerBtn() {
        if (playerBlack.isUser()) {
            playerBlack = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.BLACK);
            gameViewController.setBlackPlayerBtnText(String.valueOf(EnumPlayer.RANDOM));
        } else {
            playerBlack = new UserPlayer(EnumPlayer.USER, StoneColor.BLACK);
            gameViewController.setBlackPlayerBtnText(String.valueOf(EnumPlayer.USER));
        }
    }

    public void onClickWhitePlayerBtn() {
        if (playerWhite.isUser()) {
            playerWhite = new RandomPlayer(EnumPlayer.RANDOM, StoneColor.WHITE);
            gameViewController.setWhitePlayerBtnText(String.valueOf(EnumPlayer.RANDOM));
        } else {
            playerWhite = new UserPlayer(EnumPlayer.USER, StoneColor.WHITE);
            gameViewController.setWhitePlayerBtnText(String.valueOf(EnumPlayer.USER));
        }
    }
}
