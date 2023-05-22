package com.example.othello;


import android.content.Context;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.othello.game.board.Board;
import com.example.othello.game.board.BoardCheckService;
import com.example.othello.game.board.Cell;
import com.example.othello.game.Game;
import com.example.othello.constants.Turn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4ClassRunner.class)
public class UnitTestSample {
    Game game;
    Board board;
    BoardCheckService boardCheckService;

    Turn turn = Turn.BLACK;

    Context mockContext;

    @Before
    public void setUp() {
        mockContext = ApplicationProvider.getApplicationContext();

        board = new Board();
        boardCheckService = new BoardCheckService();
        game = new Game(new TextView(mockContext), new TextView(mockContext), board, boardCheckService);
        board.boardInit(game, new TableLayout(mockContext), mockContext);
    }

    @Test
    public void testCheckDirection() {
        // 42に黒の石を置くとし、全方向に白の石を置く
        board.putStone(board.getCell(33), Turn.WHITE);
        board.putStone(board.getCell(34), Turn.WHITE);
        board.putStone(board.getCell(35), Turn.WHITE);
        board.putStone(board.getCell(41), Turn.WHITE);
        board.putStone(board.getCell(43), Turn.WHITE);
        board.putStone(board.getCell(49), Turn.WHITE);
        board.putStone(board.getCell(50), Turn.WHITE);
        board.putStone(board.getCell(51), Turn.WHITE);

        // 更にその周囲を黒の石で囲む
        board.putStone(board.getCell(24), Turn.BLACK);
        board.putStone(board.getCell(25), Turn.BLACK);
        board.putStone(board.getCell(26), Turn.BLACK);
        board.putStone(board.getCell(27), Turn.BLACK);
        board.putStone(board.getCell(28), Turn.BLACK);
        board.putStone(board.getCell(32), Turn.BLACK);
        board.putStone(board.getCell(36), Turn.BLACK);
        board.putStone(board.getCell(40), Turn.BLACK);
        board.putStone(board.getCell(44), Turn.BLACK);
        board.putStone(board.getCell(48), Turn.BLACK);
        board.putStone(board.getCell(52), Turn.BLACK);
        board.putStone(board.getCell(56), Turn.BLACK);
        board.putStone(board.getCell(57), Turn.BLACK);
        board.putStone(board.getCell(58), Turn.BLACK);
        board.putStone(board.getCell(59), Turn.BLACK);
        board.putStone(board.getCell(60), Turn.BLACK);

        boardCheckService.check(board, turn);
        ArrayList<Cell> reversibleCells =  boardCheckService.getReversibleCells(board.getCell(42), turn);
        Assert.assertEquals(reversibleCells.size(), 8);
        for(Cell cell : reversibleCells) {
            if (
                    cell.getId() == 33 ||
                    cell.getId() == 34 ||
                    cell.getId() == 35 ||
                    cell.getId() == 41 ||
                    cell.getId() == 43 ||
                    cell.getId() == 49 ||
                    cell.getId() == 50 ||
                    cell.getId() == 51
            ) {
                assert true;
            } else {
                assert false;
            }
        }
    }
}
    