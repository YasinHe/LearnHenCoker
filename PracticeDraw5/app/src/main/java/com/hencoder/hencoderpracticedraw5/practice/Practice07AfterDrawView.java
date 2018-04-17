package com.hencoder.hencoderpracticedraw5.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

public class Practice07AfterDrawView extends AppCompatImageView {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice07AfterDrawView(Context context) {
        super(context);
    }

    public Practice07AfterDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice07AfterDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("TAG","00");
        super.draw(canvas);
        // 在 super.draw() 的下方插入绘制代码，让绘制内容盖住其他所有
        // 由于这期的重点是绘制代码的位置而不是绘制代码本身，所以直接给出绘制代码，你只要解除注释就好
        paint.setColor(Color.parseColor("#f44336"));
        canvas.drawRect(0, 40, 200, 120, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("New", 20, 100, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TAG","AA1");
        super.onDraw(canvas);
        Log.e("TAG","AA");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("TAG","BB1");
        super.dispatchDraw(canvas);
        Log.e("TAG","BB");
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.e("TAG","CC1");
        super.onDrawForeground(canvas);
        Log.e("TAG","CC");
    }

}