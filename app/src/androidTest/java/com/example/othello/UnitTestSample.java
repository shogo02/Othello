package com.example.othello;


import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.othello.game.board.Board;
import com.example.othello.game.board.BoardCheckService;
import com.example.othello.game.board.Cell;
import com.example.othello.game.Game;
import com.example.othello.constants.StoneColor;

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

    GameViewController gameViewController;

    StoneColor stoneColor = StoneColor.BLACK;

    Context mockContext;

    @Before
    public void setUp() {
//        mockContext = ApplicationProvider.getApplicationContext();
//
//        boardCheckService = new BoardCheckService();
//
//        board = new Board(boardCheckService);
//        game = new Game(new ViewController()); // TODO 後で直す
//        board.init(viewController);
    }

    @Test
    public void testCheckDirection() {
        // 42に黒の石を置くとし、全方向に白の石を置く
        board.putStone(board.getCell(33), StoneColor.WHITE);
        board.putStone(board.getCell(34), StoneColor.WHITE);
        board.putStone(board.getCell(35), StoneColor.WHITE);
        board.putStone(board.getCell(41), StoneColor.WHITE);
        board.putStone(board.getCell(43), StoneColor.WHITE);
        board.putStone(board.getCell(49), StoneColor.WHITE);
        board.putStone(board.getCell(50), StoneColor.WHITE);
        board.putStone(board.getCell(51), StoneColor.WHITE);

        // 更にその周囲を黒の石で囲む
        board.putStone(board.getCell(24), StoneColor.BLACK);
        board.putStone(board.getCell(25), StoneColor.BLACK);
        board.putStone(board.getCell(26), StoneColor.BLACK);
        board.putStone(board.getCell(27), StoneColor.BLACK);
        board.putStone(board.getCell(28), StoneColor.BLACK);
        board.putStone(board.getCell(32), StoneColor.BLACK);
        board.putStone(board.getCell(36), StoneColor.BLACK);
        board.putStone(board.getCell(40), StoneColor.BLACK);
        board.putStone(board.getCell(44), StoneColor.BLACK);
        board.putStone(board.getCell(48), StoneColor.BLACK);
        board.putStone(board.getCell(52), StoneColor.BLACK);
        board.putStone(board.getCell(56), StoneColor.BLACK);
        board.putStone(board.getCell(57), StoneColor.BLACK);
        board.putStone(board.getCell(58), StoneColor.BLACK);
        board.putStone(board.getCell(59), StoneColor.BLACK);
        board.putStone(board.getCell(60), StoneColor.BLACK);

        boardCheckService.check(board, stoneColor);
        ArrayList<Cell> reversibleCells =  boardCheckService.getReversibleCells(board.getCell(42), stoneColor);
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
    