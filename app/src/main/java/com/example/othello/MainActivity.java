package com.example.othello;


import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;

import com.example.othello.game.Game;
import com.example.othello.viewController.BoardViewControllerBase;
import com.example.othello.viewController.GameViewControllerBase;

public class MainActivity extends AppCompatActivity {
    private static MainActivity MAIN_ACTIVITY;

    private static Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAIN_ACTIVITY = this;

        // UIスレッドのハンドラを設定
        uiHandler = new Handler(Looper.getMainLooper());

        // ダークモードを無効化
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);

        // ゲームビューの初期化
        GameViewControllerBase gameViewController = new GameViewControllerBase();
        gameViewController.init();

        // ボードビューの初期化
        BoardViewControllerBase boardViewController = new BoardViewControllerBase();
        boardViewController.init();

        // ゲームの初期化
        Game game = new Game(gameViewController, boardViewController);
        game.init();

        // ゲームスタート
        game.startGame();
    }

    public static MainActivity getMainActivity() {
        return MAIN_ACTIVITY;
    }

    public static Handler getUiHandler() {
        return uiHandler;
    }
}