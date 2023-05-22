package com.example.othello.game.board;
import com.example.othello.constants.Constants;
import com.example.othello.constants.Direction;
import com.example.othello.constants.Turn;

import java.util.ArrayList;

public class DirectionCheck {
    private final Direction direction;
    private final Cell currentCell;
    private final Turn currentTurn;

    private ArrayList<Cell> reversibleCells = new ArrayList<>();

    public DirectionCheck(Cell currentCell, Direction direction, Turn currentTurn) {
        this.currentCell = currentCell;
        this.direction = direction;
        this.currentTurn = currentTurn;
    }

    // 縦横斜めいずれかの方向に石を置けるかをループしながら調べる
    public void execute(Board board) {
        Cell baseCell = currentCell;
        Cell nextCell = board.getCell(currentCell.getId() + direction.getNumber());

        boolean isReversible = false; // 裏返せる石があるかどうか

        while (true) {
            // nextCellがボード内にあるかを調べる
            if (nextCell == null || !isInBoard(baseCell.getId(), nextCell.getId())) {
                break;
            }

            // nextCellが自分と同じ色の石かを調べる
            if (nextCell.isSameState(currentTurn)) {
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
            nextCell = board.getCell(nextCell.getId() + direction.getNumber());
        }

        reversibleCells = isReversible ? reversibleCells : new ArrayList<>();
    }


    // nextCellIdがボード内にあるかを調べる
    private boolean isInBoard(int baseCellId, int nextCellId) {
        int leftmostId = baseCellId - (baseCellId % Constants.BOARD_SIZE);
        int rightmostId = leftmostId + (Constants.BOARD_SIZE - 1);
        int topLeftmostId = leftmostId + Direction.TOP.getNumber();
        int topRightmostId = rightmostId + Direction.TOP.getNumber();
        int bottomLeftmostId = leftmostId + Direction.BOTTOM.getNumber();
        int bottomRightmostId = rightmostId + Direction.BOTTOM.getNumber();

        switch (this.direction) {
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
