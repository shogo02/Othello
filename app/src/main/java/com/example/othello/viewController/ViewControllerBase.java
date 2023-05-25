package com.example.othello.viewController;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.othello.MainActivity;

public abstract class ViewControllerBase implements ViewControllerInterface {
    protected final MainActivity mainActivity = MainActivity.getMainActivity();
    protected final Handler uiHandler = MainActivity.getUiHandler();


    protected void postViewSetText(TextView view, String text) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText(text);
            }
        });
    }

    protected void postViewSetHint(TextView view, String hint) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setHint(hint);
            }
        });
    }

    public void postMakeToast(String text) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        MainActivity.getMainActivity(),
                        text,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
