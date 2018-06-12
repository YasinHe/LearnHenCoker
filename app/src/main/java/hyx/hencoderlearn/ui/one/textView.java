package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by HeYingXin on 2017/11/10.
 */
public class textView extends View{
    Path path = new Path();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float currentProgress = 0.3f;

    public textView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        path.moveTo(0,getHeight()/2+150);
//        path.lineTo(getWidth(),getHeight()/2-150);
//        path.rLineTo(getWidth(),getWidth());
//        path.lineTo(0,getHeight());
//        path.close();
//        canvas.clipPath(path);
//        canvas.drawColor(Color.BLACK);

       /* canvas.save();
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        int roundWidth = getHeight();
        //先画全进度条
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(getHeight());
        canvas.drawLine(roundWidth,centerY,getWidth()-roundWidth,centerY,paint);
        canvas.restore();
        //再来真的进度条，根据进度做动画
        canvas.save();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        canvas.scale(0.062f,1.0f,0,0);
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rectF,getHeight()*8,getHeight()*8,paint);
        canvas.restore();*/


        canvas.save();
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        int roundWidth = getHeight();
        //先画全进度条
//        paint.reset();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.WHITE);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setStrokeWidth(getHeight());
//        canvas.drawLine(roundWidth,centerY,getWidth()-roundWidth,centerY,paint);
//        canvas.restore();
        //再来真的进度条，根据进度做动画
        canvas.save();
        canvas.scale(currentProgress,1.0f,0,0);
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        canvas.drawRoundRect(rectF,roundWidth*(1/2*(1/currentProgress)),roundWidth*(1/2*(1/currentProgress)),paint);
        canvas.restore();
    }
}