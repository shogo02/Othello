package com.example.othello.game.board;

import static com.example.othello.constants.EnumStoneColor.*;

import android.util.ArrayMap;

import com.example.othello.constants.EnumDirection;
import com.example.othello.constants.EnumStoneColor;

import java.util.ArrayList;

// 置けるセルと裏返るセルを格納するクラス
public class BoardCheckService {
    // プレイヤー黒が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> blackAvailableCells = new ArrayMap<>();

    // プレイヤー白が置けるセルを配列で格納
    private ArrayMap<Integer, CanPutCell> whiteAvailableCells = new ArrayMap<>();


    public void check(Board board, EnumStoneColor currentEnumStoneColor) {
        clearAvairableCells(currentEnumStoneColor);
        for (Cell cell : board.getBoardMapValues()) {
            if (!cell.isEmpty()) {
                continue;
            }

            // 裏返せる石を格納する配列
            ArrayMap<EnumDirection, ArrayList<Cell>> reversibleCells = new ArrayMap<>();

            // 8方向を探索
            for (EnumDirection enumDirection : EnumDirection.values()) {
                DirectionCheck directionCheck = new DirectionCheck(cell, enumDirection, currentEnumStoneColor);
                directionCheck.execute(board);
                if(directionCheck.isReversible()) {
                    reversibleCells.put(enumDirection, directionCheck.getReversibleCells());
                }
            }

            if (reversibleCells.size() > 0) {
                CanPutCell canPutCell = new CanPutCell(cell, reversibleCells);

                // 探索結果を格納
                setAvailableCells(cell, canPutCell, currentEnumStoneColor);
            }
        }
    }

    public void bothPlayerCheck(Board board) {
        check(board, BLACK);
        check(board, WHITE);
    }

    private void clearAvairableCells(EnumStoneColor enumStoneColor) {
        if (enumStoneColor.equals(BLACK)) {
            blackAvailableCells = new ArrayMap<>();
        } else {
            whiteAvailableCells = new ArrayMap<>();
        }
    }

    private void setAvailableCells(Cell cell, CanPutCell canPutCells, EnumStoneColor currentEnumStoneColor) {
        if (currentEnumStoneColor.equals(BLACK)) {
            blackAvailableCells.put(cell.getId(), canPutCells);
        } else {
            whiteAvailableCells.put(cell.getId(), canPutCells);
        }
    }

    public ArrayList<Cell> getReversibleCells(Cell cell, EnumStoneColor currentEnumStoneColor) {
        if (currentEnumStoneColor.equals(BLACK)) {
            return blackAvailableCells.get(cell.getId()).getReversibleCells();
        } else {
            return whiteAvailableCells.get(cell.getId()).getReversibleCells();
        }
    }

    public ArrayMap<Integer, CanPutCell> getAvailableCells(EnumStoneColor currentEnumStoneColor) {
        if (currentEnumStoneColor.equals(BLACK)) {
            return blackAvailableCells;
        } else {
            return whiteAvailableCells;
        }
    }

    public boolean isAvailableCell(Cell cell, EnumStoneColor currentEnumStoneColor) {
        ArrayMap<Integer, CanPutCell> availableCells = getAvailableCells(currentEnumStoneColor);
        for (CanPutCell availableCell : availableCells.values()) {
            if (availableCell.cell.getId() == cell.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPass(EnumStoneColor enumStoneColor) {
        if (enumStoneColor.equals(BLACK)) {
            return blackAvailableCells.size() == 0;
        } else {
            return whiteAvailableCells.size() == 0;
        }
    }

    public boolean isGameEnd() {
        return blackAvailableCells.size() == 0 && whiteAvailableCells.size() == 0;
    }
}
