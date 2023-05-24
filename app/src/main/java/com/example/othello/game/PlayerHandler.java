package com.example.othello.game;

import com.example.othello.game.board.Board;
import com.example.othello.player.Player;
import com.example.othello.player.RandomPlayer;
import com.example.othello.player.UserPlayer;

public class PlayerHandler {

    Board board;

    boolean isPass;

    public PlayerHandler(Board board) {
        this.board = board;
    }

    public void handler(Player player) {
        switch (player.getPlayerType()) {
            case USER:
                UserPlayer((UserPlayer) player);
                break;
            case RANDOM:
                RandomPlayer((RandomPlayer) player);
                break;
        }
    }

    public void UserPlayer(UserPlayer player) {
        // ユーザー入力待ち
        while (!player.getIsPutStone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void RandomPlayer(RandomPlayer player) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isPass = player.putRandomStone(board);
    }
}
