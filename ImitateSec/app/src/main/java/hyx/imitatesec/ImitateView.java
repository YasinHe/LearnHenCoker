package hyx.imitatesec;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HeYingXin on 2017/11/13.
 */
public class ImitateView extends View{

    /**
     * 先移动再裁剪，先裁剪
     */
    private boolean isUnPrise = true;//false为点赞
    private int count = 1001;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float yOffset;
    private float xOffset;
    private Rect rect;
    private Bitmap bitmap1;//原始
    private Bitmap bitmap2;//帽子
    private Bitmap bitmap3;//之后
    private ObjectAnimator objectAnimatorPrise;
    private float priseSize,unPriseSize;
    private ObjectAnimator objectAnimatorUnPrise;


    public ImitateView(Context context) {
        super(context);
    }

    public ImitateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImitateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImitateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_messages_like_unselected);
        bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_messages_like_selected_shining);
        bitmap3 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_messages_like_selected);
        paint.setTextSize(32);
        paint.setColor(Color.GRAY);
        rect = new Rect();
        objectAnimatorPrise = ObjectAnimator.ofFloat(this,"priseSize",0,1).setDuration(1000);
        objectAnimatorUnPrise = ObjectAnimator.ofFloat(this,"unPriseSize",0,1).setDuration(1000);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUnPrise){
                    isUnPrise = false;
                    ++count;
                    objectAnimatorPrise.start();
                }else{
                    isUnPrise = true;
                    --count;
                    objectAnimatorUnPrise.start();
                }
            }
        });
    }

    public float getUnPriseSize() {
        return unPriseSize;
    }

    public void setUnPriseSize(float unPriseSize) {
        this.unPriseSize = unPriseSize;
        invalidate();
    }

    public float getPriseSize() {
        return priseSize;
    }

    public void setPriseSize(float priseSize) {
        this.priseSize = priseSize;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateSize(count);
        int width = getWidth();
        int height = getHeight();
        int x;
        int y;

        String counts = count+"";
        if(isUnPrise){//取消赞
            //过程式
            y = height/2 - bitmap1.getHeight()/2;
            x = (int) (width/2-bitmap1.getWidth()/2-xOffset);
            canvas.drawBitmap(bitmap1,x,y,paint);
            canvas.drawText(counts,0,counts.length(),x+bitmap1.getWidth(),height/2+yOffset,paint);
        }else{//点赞
            int currentSize = count;
            int lastSize = count;
            --lastSize;
            calculateSize(count);
            //过程式(原图形先变小，然后淡入第二张图  放大 然后开始显示灯光线  线完成，缩小第二张图)，文字就是纯滚动
            /**
             * 首先绘制左边动画
             */
            if(priseSize<=0.2f){
                y = height/2 - bitmap1.getHeight()/2;
                x = (int) (width/2-bitmap1.getWidth()/2-xOffset);
                //图片先会缩小,并且最右文字已经开始往上移动
                canvas.save();
                canvas.scale(1-priseSize,1-priseSize,x+bitmap1.getHeight()/2,y+bitmap1.getWidth()/2);
                canvas.drawBitmap(bitmap1,x,y,paint);
                canvas.restore();
            }else if(priseSize<=0.5f){
                y = height/2 - bitmap3.getHeight()/2;
                x = (int) (width/2-bitmap3.getWidth()/2-xOffset);
                //第二张图形出现，并且是逐渐加深颜色，加宽高
                canvas.save();
                canvas.scale(0.4f+priseSize,0.4f+priseSize,x+bitmap3.getHeight()/2,y+bitmap3.getWidth()/2);
                paint.setAlpha((int) (priseSize * 255));
                canvas.drawBitmap(bitmap3,x,y,paint);
                paint.setAlpha(255);
                canvas.restore();
            } else if(priseSize<=0.8f){
                y = height/2 - bitmap3.getHeight()/2;
                x = (int) (width/2-bitmap3.getWidth()/2-xOffset);
                //第二张图形出现，并且是逐渐加深颜色，加宽高
                canvas.save();
                canvas.scale(0.4f+priseSize,0.4f+priseSize,x+bitmap3.getHeight()/2,y+bitmap3.getWidth()/2);
                canvas.drawBitmap(bitmap3,x,y,paint);
                canvas.restore();
                //灯光也开始出现
                canvas.save();
                canvas.scale(priseSize,priseSize,x+bitmap2.getWidth()/2,y);
                canvas.drawBitmap(bitmap2,x+2
                        ,y+bitmap3.getHeight()/2-bitmap2.getHeight()-5,paint);
                canvas.restore();
            }else{
                y = height / 2 - bitmap3.getHeight() / 2;
                x = (int) (width / 2 - bitmap3.getWidth() / 2 - xOffset);
                canvas.drawBitmap(bitmap3, x, y, paint);
                canvas.drawBitmap(bitmap2,x+2
                        ,y+bitmap3.getHeight()/2-bitmap2.getHeight()-5,paint);
            }

            //右边动画
            //绘制文字部分
            canvas.save();
            float[] xyLast = printTextSize(lastSize);
            String counts1 = lastSize+"";
            float[] xy = printTextSize(currentSize);
            String counts2 = currentSize+"";
            int fixedCount = 0;
            for(int i=0;i< counts2.length();i++){
                if(counts2.length()>i&&counts1.length()>i) {
                    if (counts2.charAt(i) != counts1.charAt(i)) {
                        ++fixedCount;
                    }
                }
            }
            canvas.clipRect(x+bitmap1.getWidth(),height/2-xyLast[1]*2,x+bitmap1.getWidth()+xyLast[0]*2+10,
                    height/2+xyLast[1]);//+xy[1]
//            paint.setShader(new RadialGradient(((height/2+xyLast[1])-(height/2-xyLast[1]*2))/2,
//                    ((x+bitmap1.getWidth()+xyLast[0]*2+10)-(x+bitmap1.getWidth()))/2,
//                    ((x+bitmap1.getWidth()+xyLast[0]*2+10)-(x+bitmap1.getWidth()))/4,
//                    Color.parseColor("#E91E63"),
//                    Color.parseColor("#2196F3"), Shader.TileMode.CLAMP));
            canvas.translate(0,-((xyLast[1]*2+12)*priseSize));
            canvas.drawText(counts1,0,counts1.length(),x+bitmap1.getWidth(),height/2+xyLast[1],paint);
            //两行字之间做10 大小的隔开
            canvas.drawText(counts2,0,counts2.length(),x+bitmap1.getWidth(),height/2+xyLast[1]*2+xy[1]+10,paint);
//            paint.reset();
//            paint.setTextSize(32);
//            paint.setColor(Color.GRAY);
            canvas.restore();
            canvas.save();
        }

    }

    private void calculateSize(int size){
        String counts = size+"";
        paint.getTextBounds(counts,0,counts.length(),rect);
        yOffset = - (rect.top+rect.bottom) / 2;
        xOffset = (rect.right-rect.left) / 2;
    }

    private float[] printTextSize(int size){
        String counts = size+"";
        Rect rect = new Rect();
        paint.getTextBounds(counts,0,counts.length(),rect);
        float[] floats = new float[2];
        floats[0] = (rect.right-rect.left) / 2;
        floats[1] =  - (rect.top+rect.bottom) / 2;
        return floats;
    }

}