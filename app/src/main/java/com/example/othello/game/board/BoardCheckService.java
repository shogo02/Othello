package com.example.othello.game.board;

import android.util.ArrayMap;

import com.example.othello.constants.Direction;
import com.example.othello.constants.StoneColor;

import java.util.ArrayList;

// 置けるセルと裏返るセルを格納するクラス
public class BoardCheckService {
    // プレイヤー黒が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> blackAvailableCells = new ArrayMap<>();

    // プレイヤー白が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> whiteAvailableCells = new ArrayMap<>();


    public void check(Board board, StoneColor currentStoneColor) {
        clearAvairableCells(currentStoneColor);
        for (Cell cell : board.getBoardMapValues()) {
            if (!cell.isEmpty()) {
                continue;
            }

            // 裏返せる石を格納する配列
            ArrayMap<Direction, ArrayList<Cell>> reversibleCells = new ArrayMap<>();

            // 8方向を探索
            for (Direction direction : Direction.values()) {
                DirectionCheck directionCheck = new DirectionCheck(cell, direction, currentStoneColor);
                directionCheck.execute(board);
                if(directionCheck.isReversible()) {
                    reversibleCells.put(direction, directionCheck.getReversibleCells());
                }
            }

            if (reversibleCells.size() > 0) {
                CanPutCell canPutCell = new CanPutCell(cell, reversibleCells);

                // 探索結果を格納
                setAvailableCells(cell, canPutCell, currentStoneColor);
            }
        }
    }

    public void bothPlayerCheck(Board board) {
        check(board, StoneColor.BLACK);
        check(board, StoneColor.WHITE);
    }

    private void clearAvairableCells(StoneColor stoneColor) {
        if (stoneColor.equals(StoneColor.BLACK)) {
            blackAvailableCells = new ArrayMap<>();
        } else {
            whiteAvailableCells = new ArrayMap<>();
        }
    }

    private void setAvailableCells(Cell cell, CanPutCell canPutCells, StoneColor currentStoneColor) {
        if (currentStoneColor.equals(StoneColor.BLACK)) {
            blackAvailableCells.put(cell.getId(), canPutCells);
        } else {
            whiteAvailableCells.put(cell.getId(), canPutCells);
        }
    }

    public ArrayList<Cell> getReversibleCells(Cell cell, StoneColor currentStoneColor) {
        if (currentStoneColor.equals(StoneColor.BLACK)) {
            return blackAvailableCells.get(cell.getId()).getReversibleCells();
        } else {
            return whiteAvailableCells.get(cell.getId()).getReversibleCells();
        }
    }

    public ArrayMap<Integer, CanPutCell> getAvailableCells(StoneColor currentStoneColor) {
        if (currentStoneColor.equals(StoneColor.BLACK)) {
            return blackAvailableCells;
        } else {
            return whiteAvailableCells;
        }
    }

    public boolean isAvailableCell(Cell cell, StoneColor currentStoneColor) {
        ArrayMap<Integer, CanPutCell> availableCells = getAvailableCells(currentStoneColor);
        for (CanPutCell availableCell : availableCells.values()) {
            if (availableCell.cell.getId() == cell.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPass(StoneColor stoneColor) {
        if (stoneColor.equals(StoneColor.BLACK)) {
            return blackAvailableCells.size() == 0;
        } else {
            return whiteAvailableCells.size() == 0;
        }
    }

    public boolean isGameEnd() {
        return blackAvailableCells.size() == 0 && whiteAvailableCells.size() == 0;
    }
}
