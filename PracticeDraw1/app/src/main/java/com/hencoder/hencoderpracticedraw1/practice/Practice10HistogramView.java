package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLUE);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        paint.setColor(Color.WHITE);
        canvas.drawLine(50,10,50,200,paint);
        canvas.drawLine(50,200,350,200,paint);

        float l,t,r,b;
        l = 60;
        t = 195;
        r = 100;
        b = 200;
        paint.setColor(Color.GREEN);
        canvas.drawRect(l, t, r, b, paint);
        paint.setColor(Color.WHITE);
        float x = l + 5;
        float y = b + 10;
        canvas.drawText("Froyo", l + 5, b + 10, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(120, 100, 160, b, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("GB", l + 75, b +10, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(180, 50, 220, b, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("ics", l + 135, b +10, paint);
    }
}
