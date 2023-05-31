package com.example.othello.game;

import static com.example.othello.constants.EnumPlayer.RANDOM;
import static com.example.othello.constants.EnumPlayer.USER;
import static com.example.othello.constants.EnumStoneColor.BLACK;
import static com.example.othello.constants.EnumStoneColor.WHITE;

import android.util.ArrayMap;

import com.example.othello.constants.EnumStoneColor;
import com.example.othello.viewController.BoardViewControllerBase;
import com.example.othello.viewController.GameViewControllerBase;
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

    private GameViewControllerBase gameViewController;

    private BoardViewControllerBase boardViewController;

    /* Game */
    public int blackStoneCount;

    public int whiteStoneCount;

    public Player currentPlayer;

    public Player playerBlack;

    public Player playerWhite;


    public Game(GameViewControllerBase gameViewController, BoardViewControllerBase boardViewController) {
        this.gameViewController = gameViewController;
        this.boardViewController = boardViewController;
    }

    public void init() {
        BoardCheckService boardCheckService = new BoardCheckService();

        board = new Board(boardCheckService, boardViewController);
        board.init(this);

        gameViewController.setOnClickListener(this);
    }

    public void startGame() {
        initPlayer();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
        board.boardCheckService.check(board, currentPlayer.getStoneColor());
        board.setHintCanPut(board.boardCheckService.getAvailableCells(currentPlayer.getStoneColor()));
        gameThreadStart();
    }

    private void gameThreadStart() {
        new Thread(() -> {
            PlayerHandler playerHandler = new PlayerHandler(board);

            while (true) {
                Player passPlayer = null;
                if (!board.boardCheckService.isPass(currentPlayer.getStoneColor())) {
                    playerHandler.handler(currentPlayer);
                } else { // パス処理
                    passPlayer = currentPlayer;
                }

                toggleTurn();
                board.resetTextViewHint();
                board.boardCheckService.check(board, currentPlayer.getStoneColor());
                ArrayMap<Integer, CanPutCell> availableCells = board.boardCheckService.getAvailableCells(currentPlayer.getStoneColor());
                board.setHintCanPut(availableCells);
                updateStoneCount();

                if (board.boardCheckService.isGameEnd()) {
                    judgeWinner();
                    break;
                }

                if (passPlayer != null) {
                    gameViewController.postMakeToast(passPlayer.getStoneColor().getEnglish() + " Pass");
                }
            }
        }).start();
    }

    public void resetGame() {
        board.resetBoard();
        setFirstTurn();
        setTurnText();
        updateStoneCount();
        board.boardCheckService.check(board, currentPlayer.getStoneColor());
        board.setHintCanPut(board.boardCheckService.getAvailableCells(currentPlayer.getStoneColor()));
        gameThreadStart();
    }

    private void setFirstTurn() {
        this.currentPlayer = playerBlack;
    }

    private void initPlayer() {
        Player black = new UserPlayer(USER, BLACK);
        Player white = new RandomPlayer(RANDOM, WHITE);

        gameViewController.setBlackPlayerBtnText(String.valueOf(USER));
        gameViewController.setWhitePlayerBtnText(String.valueOf(RANDOM));

        playerBlack = black;
        playerWhite = white;
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

        if (currentPlayer.getStoneColor() == BLACK) {
            currentPlayer = playerWhite;
        } else if (currentPlayer.getStoneColor() == WHITE) {
            currentPlayer = playerBlack;
        }
        setTurnText();
    }

    private void updateStoneCount() {
        blackStoneCount = board.getStoneCount(BLACK);
        whiteStoneCount = board.getStoneCount(WHITE);
        gameViewController.setStoneCountText(BLACK.getEnglish() + "：" + blackStoneCount + "　　" + WHITE.getEnglish() + "：" + whiteStoneCount);
    }

    private void setTurnText() {
        gameViewController.setTurnText(currentPlayer.getStoneColor().getEnglish() + " Turn");
    }

    // TODO イケてない
    public void onClickPlayerBtn(EnumStoneColor enumStoneColor) {
        if (enumStoneColor == BLACK) {
            if (playerBlack.isUser()) {
                playerBlack = new RandomPlayer(RANDOM, BLACK);
                gameViewController.setBlackPlayerBtnText(String.valueOf(RANDOM));
            } else {
                playerBlack = new UserPlayer(USER, BLACK);
                gameViewController.setBlackPlayerBtnText(String.valueOf(USER));
            }
        } else if (enumStoneColor == WHITE) {
            if (playerWhite.isUser()) {
                playerWhite = new RandomPlayer(RANDOM, WHITE);
                gameViewController.setWhitePlayerBtnText(String.valueOf(RANDOM));
            } else {
                playerWhite = new UserPlayer(USER, WHITE);
                gameViewController.setWhitePlayerBtnText(String.valueOf(USER));
            }
        }
    }

    public void judgeWinner() {
        if (blackStoneCount > whiteStoneCount) {
            gameViewController.setTurnText("Black Win");
        } else if (blackStoneCount < whiteStoneCount) {
            gameViewController.setTurnText("White Win");
        } else {
            gameViewController.setTurnText("Draw");
        }
    }
}
