package com.example.othello.player;

import com.example.othello.constants.EnumPlayer;
import com.example.othello.constants.EnumStoneColor;
import com.example.othello.game.board.Board;
import com.example.othello.game.board.Cell;

import java.util.ArrayList;

public abstract class Player {
    // プレイヤーの種類
    private EnumPlayer playerType;

    // プレイヤーの石の色
    private EnumStoneColor enumStoneColor;

    // 自分のターンで石を置いたかどうか
    private boolean isPutStone;

    Player(EnumPlayer playerType, EnumStoneColor enumStoneColor) {
        this.playerType = playerType;
        this.enumStoneColor = enumStoneColor;
    }

    public EnumPlayer getPlayerType() {
        return playerType;
    }

    public boolean isUser() {
        return playerType == EnumPlayer.USER;
    }

    public boolean isRandom() {
        return playerType == EnumPlayer.RANDOM;
    }

    public boolean getIsPutStone() {
        return isPutStone;
    }

    public void toggleIsPutStone() {
        isPutStone = !isPutStone;
    }

    public void setIsPutStone(boolean isPutStone) {
        this.isPutStone = isPutStone;
    }

    public EnumStoneColor getStoneColor() {
        return enumStoneColor;
    }

    public void putStone(Cell cell, ArrayList<Cell> reversibleCells, Board board) {
        board.putStone(cell, getStoneColor());
        board.reverseStone(reversibleCells, getStoneColor());
        setIsPutStone(true);
    }
}
