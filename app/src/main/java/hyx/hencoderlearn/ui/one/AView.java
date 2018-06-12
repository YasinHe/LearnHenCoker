package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

import hyx.hencoderlearn.R;

/**
 * 第一课  Canvas
 * Created by HeYingXin on 2017/10/26.
 */
public class AView extends View{
    Paint paint = new Paint();

    public AView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    //--------------绘制一个圆
        //独有信息是指只有这个样式才有的信息，比如只有圆才有xy 半径，而颜色 空实心都是属于共有信息所以都在paint里面
        canvas.drawCircle(200,200,300,paint);//x y圆心坐标，半径  画笔
        /**
         *     Paint 类的几个最常用的方法。具体是：
         Paint.setStyle(Style style) 设置绘制模式
         Paint.setColor(int color) 设置颜色
         Paint.setStrokeWidth(float width) 设置线条宽度
         Paint.setTextSize(float textSize) 设置文字大小
         Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
         setStrokeCap(Paint.Cap cap)
         设置线头的形状。线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
         */
    //------------------会遮罩在图上(一般是用来在绘制前设置底色、绘制之后设置半透明蒙版)
        canvas.drawColor(Color.BLUE);//直接图成蓝色
        canvas.drawColor(Color.parseColor("#88880000"));//半透明红
        canvas.drawARGB(200,200,200,200);
        canvas.drawRGB(200,200,200);
        //改变画笔颜色
        paint.setColor(Color.RED);
        //画笔绘制模式(分为FIll   STROKE  FILL_AND_STROKE 指填充模式，画线模式，填充画线模式)
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//画线填充模式
        //设置画笔宽度（以像素为单位）
        paint.setStrokeWidth(20);
        //抗锯齿(让图像 文字的边缘更加平滑)不设置可能会有毛边现象
        /**
            关于锯齿现象的解释：锯齿的发生其实是由于图形分辨率过低，人眼看到像素颗粒，
            开不开启抗锯齿，图形边缘也是正常状态了，只是开抗锯齿会修改图形边缘处的像素颜色
            修改颜色从而造成平滑的感觉（改变边缘颜色，同时也就意味着图形颜色失真）
         */
        // paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
    //-----------------画矩形同理，但是RectF和Rect不一样  要注意一个是float一个是int
    //-----------------画点
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);//设置点大小
        paint.setStrokeCap(Paint.Cap.ROUND);//画一个圆点(设置点的形状)
        canvas.drawPoint(50,50,paint);
        //实际上setStrokeCap并不是专门设置点的形状，他是设置线条端点形状的，一般分为ROUND,BUTT,SQUARE 圆头，平头，方头
        //画多个点
        float[] pointPoints = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)  两个值为xy，分别表示一对值
        canvas.drawPoints(pointPoints, 2 /* 跳过两个数，即前两个 0 */,
                8 /* 一共绘制 8 个数（4 个点）*/, paint);
        canvas.drawPoints(pointPoints,paint);
    //----------------画椭圆
        paint.setColor(Color.CYAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //四个点abcd位置是  左边a y，上边b x，右边c x，下边d y
            canvas.drawOval(50,50,200,100,paint);//分别是上下左右四个点坐标，只能画横着或者竖着的椭圆，歪的需要几何变换
        }
        RectF rectF = new RectF(50,50,350,200);
        canvas.drawOval(rectF,paint);
    //-----------------画线
        paint.setColor(Color.MAGENTA);
        canvas.drawLine(50,50,400,400,paint);//从xy点到xy点一条直线，直线是不受画笔Style影响的
        //批量画线，可以写字
        float[] pointsline = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(pointsline, paint);
    //----------------画圆角矩形
        //分别是四条边坐标和横纵圆角半径
        paint.setColor(Color.DKGRAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);
            //(这里矩形的四个点意思是  A，b，c，d  A点100即左上角点对应的x轴100点，b是左上角顶点对应y轴100点，c是右上角点对应x，d是右下角对应y)
            //结果就是  width=right-left  height = buttom - top
            //链接  ：   http://blog.csdn.net/struggle323/article/details/50818320
        }
    //---------------椭圆描述圆弧
        paint.setColor(Color.YELLOW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //前四个参数描述一个弧形，startAngle是起始角度x轴正方向0度位置，顺时针为正逆时针为负，sweepAngle是弧形划过角度
            //是否连接圆心，连了是扇形  不连是弧形
            canvas.drawArc(200,100,800,500,-110,100,true,paint);
        }
    //---------------用路径画爱心 path类
        Path path = new Path(); // 初始化 Path 对象
        RectF rectF1 = new RectF(200, 200, 400, 400);
        path.addArc(rectF1, -225, 225);
        RectF rectF2 = new RectF(400, 200, 600, 400);
        path.arcTo(rectF2, -180, 225, false);
        path.lineTo(400, 542);
        canvas.drawPath(path, paint);
        //先画左边爱心半圆，然后右边爱心半圆，因为是arcTo path 所以此时已经连在一起了，就差往下拉拖长心
        /**
         * path画圆，前三个参数是圆的参数，第四个是画圆的路径方向，分为CW顺时针和CCW逆时针，区别在于当遇到paint的style
         * 不同的时候，图形出现自相交时  效果不一样
         */
        path.addCircle(100,100,50, Path.Direction.CW);
        //除了addPath之外，其他都是完整封闭图形，而xxxTo只是添加一条线
        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        path.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
        //贝塞尔曲线(前两个参数是控制点，后面两个是终点位置)
        path.quadTo(50,50,100,100);
        path.rQuadTo(50,50,100,100);
        //path.cubicTo();//三次贝塞尔
        //移动到目标位置,直接移动
        path.moveTo(200,100);
        //不使用当前位置作为弧线起点的画线（就是用来画弧线的,参数forceMoveTo 意思是
        //  要抬一下笔移动过去(无痕迹移动)，还是拖着笔过去（带着线过去））
        //path.arcTo();  addArc();  这都是非起点，直接换个地方开始
        //!!!!!!!!!!!   arcTo和addArc 区别是很关键的
        //直接画弧线，默认是抬着画笔过去的，是简化版的arcTo（）
        path.addArc(new RectF(),-90,90);
        //封闭图形
        path.close();
        //设置path的填充方式
        /**
         * 这里有四个字，EVEN_ODD WINDING （默认值）  INVERSE_EVEN_ODD  INVERSE_WINDING   后两个就是前两个反色
         * WINDING全填充     EVEN_ODD是交叉填充   全填充就是图全部涂满，交叉填充是交叉部分空白，不交叉的涂满
         *
         * ！！！所谓的交叉填充原则就是，在图形上面随便找一个点，这个点以射线任意方向出去，如果与图形外部
         * 相交2次则为偶数，单次为奇数，则奇数就要涂满，偶数就认为在图形外部  那就不涂
         *
         * ！！！而所谓的全填充也是看图形情况，遵循（非零环绕数原则）
         * 图形中所有线条都是有绘制方向的，同方向的两个圆，任意点射线出去如果遇到顺时针就+1 遇到逆时针-1；这个点任意方向射出去
         * 如果所交的图形点数不是0那就是内部，是0就是外部  是0就不填充，不是0就涂满（他的射线方向是不会影响结果的）
         *
         * 那么图形方向的控制，1：对于添加子图形 add时可以传入dir参数决定方向（CW CCW），而lineTo那线的方向就是图形方向了
         *
         * INVERSE就是反转过来的，就对了
         */
        path.setFillType(Path.FillType.EVEN_ODD);
    //Canvas画布还可以绘制图片Bitmap  left和top是位置
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher,null);
        canvas.drawBitmap(bitmap,50,50,paint);
        // ps还有个叫drawBitmapMesh的网格拉伸效果bitmap,就是像手揉捏照片一样
    //Canvas画布绘制文字  x y分别是起点坐标,Paint.setTextSize  可以设置文字大小
        canvas.drawText("贺应鑫",200,100,paint);

    }
}