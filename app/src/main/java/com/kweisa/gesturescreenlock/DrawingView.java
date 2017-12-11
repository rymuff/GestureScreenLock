package com.kweisa.gesturescreenlock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;

public class DrawingView extends View {

    private HashSet<Point> pointSet;
    private ArrayList<Point> pointList;

    private Paint paint;
    private Path path = new Path();
    private boolean touchUp;
    private boolean showLine;

    private OnActionUpListener onActionUpListener = null;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(15);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        pointList = new ArrayList<>();
        pointSet = new HashSet<>();
        touchUp = false;
        showLine = true;
    }

    public void showLine(boolean t) {
        showLine = t;
    }

    public boolean isTouchUp() {
        return touchUp;
    }

    public ArrayList<Point> getGesture() {
        return pointList;
    }

    public void reset() {
        touchUp = false;
        pointSet = new HashSet<>();
        pointList = new ArrayList<>();
    }

    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (pointSet.add(new Point(x, y))) {
            pointList.add(new Point(x, y));
        }

        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                path = new Path();
                touchUp = true;
                if (onActionUpListener != null)
                    onActionUpListener.onActionUp(this);
                break;
            default:
                return false;
        }
        // Force a view to draw again
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (showLine) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return onTouch(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setOnActionUpListener(OnActionUpListener onActionUpListener) {
        this.onActionUpListener = onActionUpListener;
    }

}
