package com.android.mycalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 *
 * @author Scorpio
 * @date 2018/5/31
 * QQ:751423471
 * phone:13982250340
 */

public class MyTextView extends AppCompatTextView {
    private Paint mPaint;
    public boolean isToday = false;

    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        }
    }
}
