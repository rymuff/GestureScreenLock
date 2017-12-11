package com.kweisa.gesturescreenlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        DrawingView drawingView = findViewById(R.id.setup_view);
        drawingView.setOnActionUpListener(new OnActionUpListener() {
            @Override
            public void onActionUp(DrawingView drawingView) {
                ArrayList<Point> gesture = drawingView.getGesture();
                Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                intent.putExtra("gesture", gesture);
                startActivity(intent);
                finish();
            }
        });
    }
}
