package com.example.othello;

import android.util.ArrayMap;

import java.util.ArrayList;

// 置けるセルと裏返るセルを格納するクラス
class BoardCheckService {
    // プレイヤー黒が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> blackAvailableCells = new ArrayMap<>();

    // プレイヤー白が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> whiteAvailableCells = new ArrayMap<>();


    public void check(Board board, Player currentTurn) {
        blackAvailableCells = new ArrayMap<>();
        whiteAvailableCells = new ArrayMap<>();
        for (Cell cell : board.getBoardMapValues()) {

            // 裏返せる石を格納する配列
            ArrayMap<Direction, ArrayList<Cell>> reversibleCells = new ArrayMap<>();

            // 8方向を探索
            for (Direction direction : Direction.values()) {
                DirectionCheck directionCheck = new DirectionCheck(cell, direction, currentTurn);
                directionCheck.execute(board);
                if(directionCheck.isReversible()) {
                    reversibleCells.put(direction, directionCheck.getReversibleCells());
                }
            }

            if (reversibleCells.size() > 0) {
                CanPutCell canPutCell = new CanPutCell(cell, reversibleCells);

                // 探索結果を格納
                setAvailableCells(cell, canPutCell, currentTurn);
            }
        }
    }

    private void setAvailableCells(Cell cell, CanPutCell canPutCells, Player currentTurn) {
        if (currentTurn.equals(Player.BLACK)) {
            blackAvailableCells.put(cell.getId(), canPutCells);
        } else {
            whiteAvailableCells.put(cell.getId(), canPutCells);
        }
    }

    public ArrayList<Cell> getReversibleCells(Cell cell, Player currentTurn) {
        if (currentTurn.equals(Player.BLACK)) {
            return blackAvailableCells.get(cell.getId()).getReversibleCells();
        } else {
            return whiteAvailableCells.get(cell.getId()).getReversibleCells();
        }
    }

    public ArrayMap<Integer, CanPutCell> getAvailableCells(Player currentTurn) {
        if (currentTurn.equals(Player.BLACK)) {
            return blackAvailableCells;
        } else {
            return whiteAvailableCells;
        }
    }

    public boolean isAvailableCell(Cell cell, Player currentTurn) {
        ArrayMap<Integer, CanPutCell> availableCells = getAvailableCells(currentTurn);
        for (CanPutCell availableCell : availableCells.values()) {
            if (availableCell.cell.getId() == cell.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPass(Player player) {
        if (player.equals(Player.BLACK)) {
            return blackAvailableCells.size() == 0;
        } else {
            return whiteAvailableCells.size() == 0;
        }
    }

    public boolean isGameEnd() {
        return blackAvailableCells.size() == 0 && whiteAvailableCells.size() == 0;
    }
}
