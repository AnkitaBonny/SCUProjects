package com.example.computer1.myarttherapy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

    protected Path newPath = new Path();
    protected Paint newPaint = new Paint();
    public DrawView(Context context) {
        super(context);
        init(null,0);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public DrawView(Context context, AttributeSet attrs, int styleAttr) {
        super(context, attrs, styleAttr);
        init(attrs,styleAttr);
    }

    public void init(AttributeSet attributeSet, int styleAttr) {
        newPaint.setColor(Color.BLACK);
        newPaint.setStyle(Paint.Style.STROKE);
        newPaint.setStrokeWidth(8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(newPath,newPaint);

    }

    @Override

    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                newPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                newPath.lineTo(x,y);
                break;

        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        newPath.reset();
        invalidate();

    }

}

