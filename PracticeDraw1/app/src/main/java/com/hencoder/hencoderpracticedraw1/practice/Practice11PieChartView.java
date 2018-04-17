package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.MAGENTA);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        paint.setTextSize(10);
        paint.setColor(Color.WHITE);
        canvas.drawText("Lollipop",5,10,paint);
        canvas.drawLine(65,10,125,10,paint);
        canvas.drawLine(125,10,135,20,paint);

        paint.setColor(Color.RED);
        RectF rectF1 = new RectF(120,10,220,110);
        canvas.drawArc(rectF1,-180,120,true,paint);

        paint.setTextSize(10);
        paint.setColor(Color.WHITE);
        canvas.drawText("Marshmallow",283,40,paint);
        canvas.drawLine(223,50,233,40,paint);
        canvas.drawLine(233,40,273,40,paint);

        paint.setColor(Color.BLACK);
        RectF rectF2 = new RectF(123,12,223,112);
        canvas.drawArc(rectF2,-60,60,true,paint);

        paint.setColor(Color.YELLOW);
        RectF rectF3 = new RectF(123,12,223,112);
        canvas.drawArc(rectF3,0,10,true,paint);

        paint.setColor(Color.BLUE);
        RectF rectF4 = new RectF(123,12,223,112);
        canvas.drawArc(rectF4,10,25,true,paint);

        paint.setColor(Color.WHITE);
        RectF rectF5 = new RectF(123,12,223,112);
        canvas.drawArc(rectF5,25,55,true,paint);

        paint.setColor(Color.CYAN);
        RectF rectF6 = new RectF(123,12,223,112);
        canvas.drawArc(rectF6,90,80,true,paint);
    }
}
