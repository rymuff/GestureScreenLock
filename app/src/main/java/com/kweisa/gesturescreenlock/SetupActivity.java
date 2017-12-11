package com.kweisa.gesturescreenlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.SecureRandom;
import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {
    private Point start;
    private Point stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        DrawingView drawingView = findViewById(R.id.setup_view);
        SecureRandom secureRandom = new SecureRandom();

        start = new Point(secureRandom.nextInt(1080), secureRandom.nextInt(1080));
        stop = new Point(secureRandom.nextInt(1080), secureRandom.nextInt(1080));

        Log.i("Start", start.toString());
        Log.i("stop", stop.toString());

        drawingView.printRandomLine(start, stop);
        drawingView.setOnActionUpListener(new OnActionUpListener() {
            @Override
            public void onActionUp(DrawingView drawingView) {
                ArrayList<Point> gesture = drawingView.getGesture();
                Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                intent.putExtra("gesture", gesture);
                intent.putExtra("start", start);
                intent.putExtra("stop", stop);
                startActivity(intent);
                finish();
            }
        });
    }
}
