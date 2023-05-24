package com.example.othello.viewController;

import android.widget.TextView;

import com.example.othello.MainActivity;

public abstract class ViewControllerBase implements ViewControllerInterface {
    protected final MainActivity mainActivity = MainActivity.getMainActivity();

    public void postViewSetText(TextView view, String text) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText(text);
            }
        });
    }

    public void postViewSetHint(TextView view, String hint) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setHint(hint);
            }
        });
    }
}
