package com.example.othello.viewController;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.othello.MainActivity;

public abstract class ViewControllerBase implements ViewControllerInterface {
    protected final MainActivity mainActivity = MainActivity.getMainActivity();
    protected final Handler uiHandler = MainActivity.getUiHandler();


    protected void postViewSetText(TextView view, String text) {
        view.post(() -> view.setText(text));
    }

    protected void postViewSetHint(TextView view, String hint) {
        view.post(() -> view.setHint(hint));
    }

    public void postMakeToast(String text) {
        uiHandler.post(() -> Toast.makeText(
                MainActivity.getMainActivity(),
                text,
                Toast.LENGTH_SHORT
        ).show());
    }
}
