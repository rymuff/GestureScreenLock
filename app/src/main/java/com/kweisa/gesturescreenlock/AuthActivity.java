package com.kweisa.gesturescreenlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class AuthActivity extends AppCompatActivity {
    private ArrayList<Point> gesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        gesture = load();
        if (gesture == null) {
            Toast.makeText(getApplicationContext(), "Gesture Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }

        DrawingView drawingView = findViewById(R.id.auth_view);
        drawingView.setOnActionUpListener(new OnActionUpListener() {
            @Override
            void onActionUp(DrawingView drawingView) {
                if (GestureChecker.check(gesture, drawingView.getGesture())) {
                    Toast.makeText(getApplicationContext(), "Gesture Matched", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Gesture Dose Not Match", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private ArrayList<Point> load() {
        ArrayList<Point> gesture = null;

        try {
            FileInputStream fos = getApplicationContext().openFileInput("gesture.key");
            ObjectInputStream os = new ObjectInputStream(fos);

            //noinspection unchecked
            gesture = (ArrayList<Point>) os.readObject();

            os.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return gesture;
    }
}