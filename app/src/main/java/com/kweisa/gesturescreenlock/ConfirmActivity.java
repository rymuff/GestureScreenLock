package com.kweisa.gesturescreenlock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ConfirmActivity extends AppCompatActivity {
    private ArrayList<Point> setupGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();

        //noinspection unchecked
        setupGesture = (ArrayList<Point>) intent.getSerializableExtra("gesture");
        Point start = (Point) intent.getSerializableExtra("start");
        Point stop = (Point) intent.getSerializableExtra("stop");

        DrawingView drawingView = findViewById(R.id.confirm_view);
        drawingView.printRandomLine(start, stop);
        drawingView.setOnActionUpListener(new OnActionUpListener() {
            @Override
            void onActionUp(DrawingView drawingView) {
                if (GestureChecker.check(drawingView.getGesture(), setupGesture)) {
                    Toast.makeText(getApplicationContext(), "Gestures Matched", Toast.LENGTH_SHORT).show();
                    save(setupGesture);
                } else {
                    Toast.makeText(getApplicationContext(), "Gestures Do Not Match", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    private void save(ArrayList<Point> gesture) {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("gesture.key", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(gesture);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
