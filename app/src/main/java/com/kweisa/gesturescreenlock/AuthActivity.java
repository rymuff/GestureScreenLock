package com.kweisa.gesturescreenlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AuthActivity extends AppCompatActivity {
    private int step;
    private ArrayList<Point> gesture1, gesture2;
    private DrawingView gview;
    private TextView tip;
    private boolean wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        step = 1;
        gview = (DrawingView) findViewById(R.id.setup_view);
        tip = (TextView) findViewById(R.id.tip);

        wait = false;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AuthActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (gview.isTouchUp() && !wait) {
                            wait = true;
                            if (step == 1) {
                                gesture1 = gview.getGesture();
                                tip.setText("Draw your gesture again");
                                step++;
                                gview.reset();
                                gesture2 = load();
                                if (GestureChecker.check(gesture1, gesture2)) {
                                    tip.setText("");
                                    step = 1;
                                    Toast.makeText(getApplicationContext(), "Gesture matched!", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    tip.setText("Draw your gesture");
                                    step = 1;
                                    Toast.makeText(getApplicationContext(), "Gestures don't match", Toast.LENGTH_SHORT).show();
                                }
                                gview.reset();
                            }
                            wait = false;
                        }
                    }
                });
            }
        }, 0, 300);
    }

    private ArrayList<Point> load() {
        ArrayList<Point> gesture;
        try {
            FileInputStream fos = getApplicationContext().openFileInput("gesture.key");
            ObjectInputStream os = new ObjectInputStream(fos);
            gesture = (ArrayList<Point>) os.readObject();
            os.close();
            return gesture;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
