package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect

        paint.setPathEffect(new CornerPathEffect(10));//曲线
        // 第一处：CornerPathEffect
        canvas.drawPath(path, paint);

        canvas.save();
        canvas.translate(500, 0);
        paint.setPathEffect(new DiscretePathEffect(20,10));//线条偏离
        // 第二处：DiscretePathEffect
        canvas.drawPath(path, paint);
        canvas.restore();//恢复画布到之前的状态

        canvas.save();//保存恢复的话状态
        canvas.translate(0, 200);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 7, 15, 10},0));
        // 第三处：DashPathEffect
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);
        paint.setColor(Color.BLACK);
        Path path1 = new Path();
        path1.moveTo(0,0);
        path1.lineTo(20, -30);
        path1.lineTo(40, 0);
        path1.close();
        paint.setPathEffect(new PathDashPathEffect(path1,40,0, PathDashPathEffect.Style.MORPH));
        // 第四处：PathDashPathEffect
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        DiscretePathEffect discretePathEffect = new DiscretePathEffect(20,10);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{10, 3, 5, 3},3);
        paint.setPathEffect(new SumPathEffect(discretePathEffect,dashPathEffect));
        // 第五处：SumPathEffect
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);
        DiscretePathEffect discretePathEffect2 = new DiscretePathEffect(20,10);
        DashPathEffect dashPathEffect2 = new DashPathEffect(new float[]{20, 7, 15, 10},3);
        paint.setPathEffect(new ComposePathEffect(dashPathEffect2,discretePathEffect2));
        // 第六处：ComposePathEffect
        canvas.drawPath(path, paint);
        canvas.restore();


        //当先偏离再虚线时，出现的结果是直接虚线的，和上面先虚线再偏离不一样
        canvas.save();
        canvas.translate(0, 550);
        DiscretePathEffect discretePathEffect3 = new DiscretePathEffect(20,10);
        DashPathEffect dashPathEffect3 = new DashPathEffect(new float[]{20, 7, 15, 10},3);
        paint.setPathEffect(new ComposePathEffect(discretePathEffect3,dashPathEffect3));
        // 第七处：ComposePathEffect
        canvas.drawPath(path, paint);
        canvas.save();


        canvas.restore();

        paint.setPathEffect(null);
    }
}
