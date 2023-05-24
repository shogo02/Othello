package com.example.othello.game.board;
import com.example.othello.constants.Constants;
import com.example.othello.constants.EnumDirection;
import com.example.othello.constants.EnumStoneColor;

import java.util.ArrayList;

public class DirectionCheck {
    private final EnumDirection enumDirection;
    private final Cell currentCell;
    private final EnumStoneColor currentEnumStoneColor;

    private ArrayList<Cell> reversibleCells = new ArrayList<>();

    public DirectionCheck(Cell currentCell, EnumDirection enumDirection, EnumStoneColor currentEnumStoneColor) {
        this.currentCell = currentCell;
        this.enumDirection = enumDirection;
        this.currentEnumStoneColor = currentEnumStoneColor;
    }

    // 縦横斜めいずれかの方向に石を置けるかをループしながら調べる
    public void execute(Board board) {
        Cell baseCell = currentCell;
        Cell nextCell = board.getCell(currentCell.getId() + enumDirection.getNumber());

        boolean isReversible = false; // 裏返せる石があるかどうか

        while (true) {
            // nextCellがボード内にあるかを調べる
            if (nextCell == null || !isInBoard(baseCell.getId(), nextCell.getId())) {
                break;
            }

            // nextCellが自分と同じ色の石かを調べる
            if (nextCell.isSameState(currentEnumStoneColor)) {
                isReversible = reversibleCells.size() > 0;
                break;
            }

            // nextCellが空白かを調べる
            if (nextCell.isEmpty()) {
                break;
            }

            // 裏返せる石を格納する(暫定)
            reversibleCells.add(nextCell);

            // baseCellとnextCell次のセルに更新する
            baseCell = nextCell;
            nextCell = board.getCell(nextCell.getId() + enumDirection.getNumber());
        }

        reversibleCells = isReversible ? reversibleCells : new ArrayList<>();
    }


    // nextCellIdがボード内にあるかを調べる
    private boolean isInBoard(int baseCellId, int nextCellId) {
        int leftmostId = baseCellId - (baseCellId % Constants.BOARD_SIZE);
        int rightmostId = leftmostId + (Constants.BOARD_SIZE - 1);
        int topLeftmostId = leftmostId + EnumDirection.TOP.getNumber();
        int topRightmostId = rightmostId + EnumDirection.TOP.getNumber();
        int bottomLeftmostId = leftmostId + EnumDirection.BOTTOM.getNumber();
        int bottomRightmostId = rightmostId + EnumDirection.BOTTOM.getNumber();

        switch (this.enumDirection) {
            case TOP:
                return isInBoardTop(nextCellId);
            case BOTTOM:
                return isInBoardBottom(nextCellId);
            case LEFT:
                return isInBoardLeft(nextCellId, leftmostId);
            case RIGHT:
                return isInBoardRight(nextCellId, rightmostId);
            case TOP_LEFT:
                return isInBoardTop(nextCellId) && isInBoardLeft(nextCellId, topLeftmostId);
            case TOP_RIGHT:
                return isInBoardTop(nextCellId) && isInBoardRight(nextCellId, topRightmostId);
            case BOTTOM_LEFT:
                return isInBoardBottom(nextCellId) && isInBoardLeft(nextCellId, bottomLeftmostId);
            case BOTTOM_RIGHT:
                return isInBoardBottom(nextCellId) && isInBoardRight(nextCellId, bottomRightmostId);
        }
        return false;
    }

    private boolean isInBoardTop(int nextCellId) {
        return nextCellId >= 0;
    }

    private boolean isInBoardBottom(int nextCellId) {
        return nextCellId < Constants.BOARD_MAX_ID;
    }

    private boolean isInBoardLeft(int nextCellId, int leftmostId) {
        return nextCellId >= leftmostId;
    }

    private boolean isInBoardRight(int nextCellId, int rightmostId) {
        return nextCellId <= rightmostId;
    }

    // 裏返せるセルがあるか
    public boolean isReversible() {
        return reversibleCells.size() > 0;
    }

    public ArrayList<Cell> getReversibleCells() {
        return reversibleCells;
    }
}
