package com.example.othello;


import android.content.Context;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4ClassRunner.class)
public class UnitTestSample {
    Game game;
    Board board;
    BoardCheckService boardCheckService;

    Player player = Player.BLACK;

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
        board.putStone(board.getCell(33), Player.WHITE);
        board.putStone(board.getCell(34), Player.WHITE);
        board.putStone(board.getCell(35), Player.WHITE);
        board.putStone(board.getCell(41), Player.WHITE);
        board.putStone(board.getCell(43), Player.WHITE);
        board.putStone(board.getCell(49), Player.WHITE);
        board.putStone(board.getCell(50), Player.WHITE);
        board.putStone(board.getCell(51), Player.WHITE);

        // 更にその周囲を黒の石で囲む
        board.putStone(board.getCell(24), Player.BLACK);
        board.putStone(board.getCell(25), Player.BLACK);
        board.putStone(board.getCell(26), Player.BLACK);
        board.putStone(board.getCell(27), Player.BLACK);
        board.putStone(board.getCell(28), Player.BLACK);
        board.putStone(board.getCell(32), Player.BLACK);
        board.putStone(board.getCell(36), Player.BLACK);
        board.putStone(board.getCell(40), Player.BLACK);
        board.putStone(board.getCell(44), Player.BLACK);
        board.putStone(board.getCell(48), Player.BLACK);
        board.putStone(board.getCell(52), Player.BLACK);
        board.putStone(board.getCell(56), Player.BLACK);
        board.putStone(board.getCell(57), Player.BLACK);
        board.putStone(board.getCell(58), Player.BLACK);
        board.putStone(board.getCell(59), Player.BLACK);
        board.putStone(board.getCell(60), Player.BLACK);

        boardCheckService.check(board, player);
        ArrayList<Cell> reversibleCells =  boardCheckService.getReversibleCells(board.getCell(42), player);
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
    