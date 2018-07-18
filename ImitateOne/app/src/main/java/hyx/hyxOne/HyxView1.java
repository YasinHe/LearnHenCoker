package hyx.hyxOne;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by HeYingXin on 2017/11/9.
 */
public class HyxView1 extends View{

    /**
     * 思路，动画分成三步骤
     * 1:右边先翘起啦
     * 2:转个圈
     * 3:上边翘起来
     * 总结：右边先掀起来，然后旋转画布保持右边掀起来   旋转之后的样子再反旋转回来  就形成了转圈样式。
     */
    /**
     * 目前遇到的坑：
     * 1：不能限制外围layout大小，以限制的方式会导致内部绘制不完整，界面效果达不到
     * (比如限制了外围大小，而图片又已经变换到最大，再进行3d变换，变换效果无法扩充  导致看起来怪怪的)
     * 2：path裁切位置不对
     * 3：
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private AnimatorSet animationSet = new AnimatorSet();
    private ObjectAnimator objectAnimator1;
    private ObjectAnimator objectAnimator2;
    private ObjectAnimator objectAnimator3;
    private float rightRaise = 0;//1号动画  绕着y轴起来
    private float allRaise = 0;//2号动画  绕着z轴起来
    private float topRaise = 0;//3号动画
    private Bitmap bitmap;
    private Camera camera;
    private Rect rect;
    private Path clipPath1;
    private Path clipPath2;
    private Rect rectAll;
    private float newZ;

    public HyxView1(Context context) {
        super(context);
    }

    public HyxView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HyxView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HyxView1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.flip_board);
        objectAnimator1 = ObjectAnimator.ofFloat(this,"rightRaise",0,-45).setDuration(2000);
        objectAnimator1.setInterpolator(new LinearInterpolator());

        objectAnimator2 = ObjectAnimator.ofFloat(this,"allRaise",0,270).setDuration(2000);
        objectAnimator2.setInterpolator(new LinearInterpolator());

        objectAnimator3 = ObjectAnimator.ofFloat(this, "topRaise", 0, 30);
        objectAnimator3.setInterpolator(new LinearInterpolator());
        objectAnimator3.setDuration(500);
        objectAnimator3.setStartDelay(500);

        camera = new Camera();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        newZ = - displayMetrics.density * 6;//屏幕密度，这个手机一dpi等于多少像素
        camera.setLocation(0,0,newZ);

        clipPath1 = new Path();
        clipPath2 = new Path();
    }

    public float getRightRaise() {
        return rightRaise;
    }

    public void setRightRaise(float rightRaise) {
        this.rightRaise = rightRaise;
        invalidate();
    }

    public float getAllRaise() {
        return allRaise;
    }

    public void setAllRaise(float allRaise) {
        this.allRaise = allRaise;
        invalidate();
    }

    public float getTopRaise() {
        return topRaise;
    }

    public void setTopRaise(float topRaise) {
        this.topRaise = topRaise;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(LAYER_TYPE_HARDWARE, null);
        animationSet.playSequentially(objectAnimator1,objectAnimator2,objectAnimator3);
//        animationSet.play(objectAnimator2);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rightRaise = 0;
                        allRaise = 0;
                        topRaise = 0;
                        animationSet.start();
                    }
                },500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setLayerType(LAYER_TYPE_NONE, null);
        animationSet.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
  /*      int width = getWidth();
        int height = getHeight();
        int centerX = width/2;
        int centerY = height/2;
        rectAll = new Rect(centerX-200,centerY-200,centerX+200,centerY+200);
        //动画动作一：
        if(rightRaise>-45) {
//            //首先画左边半边
            canvas.save();
            rect = new Rect(0, 0, centerX, height);
            canvas.clipRect(rect);
            canvas.drawBitmap(bitmap, null, rectAll, paint);
            canvas.restore();
            //再来右半边
            canvas.save();
            rect = new Rect(centerX, 0, width, height);
            canvas.clipRect(rect);
            camera.save();
            camera.setLocation(0, 0, newZ);
            camera.rotateY(rightRaise);
            canvas.translate(centerX, centerY);
            camera.applyToCanvas(canvas);
            canvas.translate(-centerX, -centerY);
            camera.restore();
            camera.save();
            canvas.drawBitmap(bitmap, null, rectAll, paint);
            canvas.restore();
        }else if(allRaise<270){
            canvas.save();
            clipPath1.reset();
            clipPath2.reset();
            int singleX = centerX/45;
            int singleY = centerY/45;
            float size = 0;
            if(allRaise<45){
                size = allRaise;
                clipPath1.moveTo(centerX-(singleX*size),0);
                clipPath1.lineTo(centerX+singleX*size,height);
                clipPath1.rLineTo(0,height);
                clipPath1.lineTo(0,0);
                clipPath1.close();

                clipPath2.moveTo(centerX-(singleX*size),0);
                clipPath2.lineTo(centerX+singleX*size,height);
                clipPath2.rLineTo(width,height);
                clipPath2.lineTo(width,0);
                clipPath2.close();
            }else if(allRaise>=45&&allRaise<90){
                size = allRaise-45;
                clipPath1.moveTo(0,singleY*size);
                clipPath1.lineTo(width,height-singleY*size);
                clipPath1.rLineTo(width,height);
                clipPath1.lineTo(0,height);
                clipPath1.close();

                clipPath2.moveTo(0,singleY*size);
                clipPath2.lineTo(width,height-singleY*size);
                clipPath2.rLineTo(width,0);
                clipPath2.lineTo(0,0);
                clipPath2.close();
            }else if(allRaise>=90&&allRaise<135){
                size = allRaise-90;
                clipPath1.moveTo(0,centerY+singleY*size);
                clipPath1.lineTo(width,centerY-singleY*size);
                clipPath1.rLineTo(width,height);
                clipPath1.lineTo(0,height);
                clipPath1.close();

                clipPath2.moveTo(0,centerY+singleY*size);
                clipPath2.lineTo(width,centerY-singleY*size);
                clipPath2.rLineTo(width,0);
                clipPath2.lineTo(0,0);
                clipPath2.close();
            }else if(allRaise>=135&&allRaise<180){
                size = allRaise-135;
                clipPath1.moveTo(singleX*size,height);
                clipPath1.lineTo(0,width-singleX*size);
                clipPath1.rLineTo(width,0);
                clipPath1.lineTo(width,height);
                clipPath1.close();

                clipPath2.moveTo(singleX*size,height);
                clipPath2.lineTo(0,width-singleX*size);
                clipPath2.rLineTo(0,0);
                clipPath2.lineTo(0,height);
                clipPath2.close();
            }else if(allRaise>=180&&allRaise<225){
                size = allRaise-180;
                clipPath1.moveTo(centerX+singleX*size,height);
                clipPath1.lineTo(centerX-singleX*size,0);
                clipPath1.rLineTo(width,0);
                clipPath1.lineTo(width,height);
                clipPath1.close();

                clipPath2.moveTo(centerX+singleX*size,height);
                clipPath2.lineTo(centerX-singleX*size,0);
                clipPath2.rLineTo(0,0);
                clipPath2.lineTo(0,height);
                clipPath2.close();
            }else if(allRaise>=225&&allRaise<270){
                size = allRaise-225;
                clipPath1.moveTo(width,height-singleY*size);
                clipPath1.lineTo(0,singleY*size);
                clipPath1.rLineTo(0,0);
                clipPath1.lineTo(width,0);
                clipPath1.close();

                clipPath2.moveTo(width,height-singleY*size);
                clipPath2.lineTo(0,singleY*size);
                clipPath2.rLineTo(0,height);
                clipPath2.lineTo(width,height);
                clipPath2.close();
            }
            //首先画不动的半边
            canvas.clipPath(clipPath2);
            canvas.drawBitmap(bitmap, null, rectAll, paint);
            canvas.restore();
            //再来另外动的半边
            canvas.save();
            canvas.clipPath(clipPath1);
            camera.save();
            camera.setLocation(0, 0, newZ);
            if(allRaise<45) {
                camera.rotateY(-45);
            }else if(allRaise>=45&&allRaise<135){
                camera.rotateX(-45);
            }else if(allRaise>=135&&allRaise<225){
                camera.rotateY(45);
            }else{
                camera.rotateX(45);
            }
            canvas.translate(centerX, centerY);
            camera.applyToCanvas(canvas);
            canvas.translate(-centerX, -centerY);
            camera.restore();
            camera.save();
            canvas.drawBitmap(bitmap, null, rectAll, paint);
            canvas.restore();
        }*/

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //---能动的半边
        //先旋转位置，然后裁切下来   再用camera把裁切部分的旋转
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-allRaise);
        camera.rotateY(rightRaise);//0到-45------------------------------
        camera.applyToCanvas(canvas);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(0, -centerY, centerX, centerY);//--------------00点右下 能动部分-----------------
        canvas.rotate(allRaise);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();



        //---不能动的半边
        canvas.save();//1
        camera.save();//1
        canvas.translate(centerX, centerY);//1
        canvas.rotate(-allRaise);//1
        //此时的canvas的坐标系已经旋转，所以这里是rotateY(本来应该是x  就是上面往下翻的部分)
        camera.rotateY(topRaise);//-----------------------0 -30
        camera.applyToCanvas(canvas);//1
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY);//-----------00点左边不能动部分--------------
        canvas.rotate(allRaise);//1
        canvas.translate(-centerX, -centerY);//1
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}