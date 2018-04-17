package com.hencoder.hencoderpracticedraw3.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice13GetTextBoundsView extends View {
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text1 = "A";
    String text2 = "a";
    String text3 = "J";
    String text4 = "j";
    String text5 = "Â";
    String text6 = "â";
    String[] texts = new String[]{"A","a","J","j","Â","â"};
    int top = 200;
    int bottom = 400;

    public Practice13GetTextBoundsView(Context context) {
        super(context);
    }

    public Practice13GetTextBoundsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice13GetTextBoundsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(Color.parseColor("#E91E63"));
        paint2.setTextSize(160);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, top, getWidth() - 50, bottom, paint1);

        // 使用 Paint.getTextBounds() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让文字精准地居中，分毫不差


        //这个策略就是提前绘制出矩形，通过矩形的上下距离和除以二就是这个字的平均线位置。分别偏移每个字符，得到结果

        Rect bound = new Rect();
        float[] offSetY = new float[6];
        paint2.getTextBounds(texts[0],0,texts[0].length(),bound);
        offSetY[0] = -(bound.bottom+bound.top)/2;
        paint2.getTextBounds(texts[1],0,texts[1].length(),bound);
        offSetY[1] = -(bound.bottom+bound.top)/2;
        paint2.getTextBounds(texts[2],0,texts[2].length(),bound);
        offSetY[2] = -(bound.bottom+bound.top)/2;
        paint2.getTextBounds(texts[3],0,texts[3].length(),bound);
        offSetY[3] = -(bound.bottom+bound.top)/2;
        paint2.getTextBounds(texts[4],0,texts[4].length(),bound);
        offSetY[4] = -(bound.bottom+bound.top)/2;
        paint2.getTextBounds(texts[5],0,texts[5].length(),bound);
        offSetY[5] = -(bound.bottom+bound.top)/2;


        int middle = (top + bottom) / 2;
        canvas.drawText(text1, 100, middle+offSetY[0], paint2);
        canvas.drawText(text2, 200, middle+offSetY[1], paint2);
        canvas.drawText(text3, 300, middle+offSetY[2], paint2);
        canvas.drawText(text4, 400, middle+offSetY[3], paint2);
        canvas.drawText(text5, 500, middle+offSetY[4], paint2);
        canvas.drawText(text6, 600, middle+offSetY[5], paint2);
    }
}