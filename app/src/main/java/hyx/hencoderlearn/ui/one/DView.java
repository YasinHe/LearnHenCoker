package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

import hyx.hencoderlearn.R;

import static android.R.attr.bitmap;

/**
 * 第四课：camera
 * Created by HeYingXin on 2017/11/6.
 */
public class DView extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path(); // 初始化 Path 对象
    Camera camera = new Camera();


    public DView(Context context) {
        super(context);
        //直接观看视频记录要点
        //1：操作画布之前，先save再折腾然后restore到调用save时的状态
        //2：调用画布的几何变换方法  平移，缩放，旋转，错切  连续调用时，顺序是反的，先平移在旋转，那代码应该先写旋转在写平移
        //3：几何变化的内部实现都是基于Matrix，所以也可以直接使用matrix然后set或者concat给canvas（pre ，post  往前插入往后往后插入）
        //4：canvas只能实现简单的几何变化，而更复杂的可以利用Camera，内部会转为Matrix（camera的旋转是以原点00为轴心的）
        //4：上面的轴心不支持更换，只能移动view去轴心，然后做动作，然后再平移回来(camara的setLocation可以把视图往三维三个方向挪动xyz)
        //5：camera的坐标轴和canvas不一样，camera是x右正，y是上正（和canvas反），z是面朝屏幕方向为反，穿过屏幕方向为正（拍照片方向）
        //6：camera存在一个虚拟的camera对象，工作原理是在-z轴上xy原点有一个camera点，往view上面做投射，所以几何变化之后
        //，就是把变换的投影投射到canvas，这个顺序需要反着写，因为camera的绘制顺序就是反的
        //7：记住一点，camera是可以平移的，但是往往平移之后调用的效果不是所要的，因为他是带着整个三维坐标轴平移的，平移之后投影最后到canvas的
        //效果，就会跟预计的效果不一样(他是变换之后才会做投影)
        //8：关于旋转方向问题，xyz三轴的旋转方向是没有规则的，可能早期openGl团队没有做好，x轴往y的负轴旋转，y是往x正轴旋转，z是往x负轴旋转

        /**
         * 范围裁切
         * canvas变换
         * matrix变换，自定义变换（很少）
         * camera三维变换
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left= 0,top= 0,right= 0,bottom= 0,x= 0,y = 0,centerX= 0,centerY = 0;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //范围裁切
        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(path);
        Point point1 = new Point(50,50);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        /**
         * canvas常见变换：
         * Canvas.translate(float dx, float dy) 参数里的 dx 和 dy 表示横向和纵向的位移
         * Canvas.rotate(float degrees, float px, float py) 旋转degrees 是旋转角度，
         * 单位是度（也就是一周有 360° 的那个单位），方向是顺时针为正向； px 和 py 是轴心的位置
         *
         * Canvas.scale(float sx, float sy, float px, float py) 放缩，参数里的 sx sy 是横向和纵向的放缩倍数； px py 是放缩的轴心
         * skew(float sx, float sy) 错切参数里的 sx 和 sy 是 x 方向和 y 方向的错切系数(0.x f,以原图为基准，往x y方向+-扩缩)
         */

        /**
         * Canvas.setMatrix(matrix)：用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，改用 Matrix 的变换
         *
         * Canvas.concat(matrix)：用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上 Matrix 中的变换
         *
         * Matrix 的自定义变换使用的是 setPolyToPoly(): 方法参数里，src 和 dst 是源点集合目标点集；
         * srcIndex 和 dstIndex 是第一个点的偏移；pointCount 是采集的点的个数（个数不能大于 4，因为大于 4 个点就无法计算变换了）。
         */

        /**
         * Camera.rotate*() 一共有四个方法:rotateX(deg) rotateY(deg) rotateZ(deg) rotate(x, y, z)
         */
        canvas.save();
        camera.save(); // 保存 Camera 的状态
        camera.rotateX(30); // 旋转 Camera 的三维空间(直接传入度数值)
        canvas.translate(centerX, centerY); // 旋转之后把投影移动回来(2222222222)
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-centerX, -centerY); // 旋转之前把绘制内容移动到轴心（原点）(1111111111)
        camera.restore(); // 恢复 Camera 的状态
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();
        //Canvas 的几何变换顺序是反的，所以要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面

        /**
         * Camera.setLocation(x, y, z) 设置虚拟相机的位置 在 Camera 中，一英寸72像素
         * 相机的默认位置是 (0, 0, -8)（英寸）。8 x 72 = 576，所以它的默认位置是 (0, 0, -576)（像素）
         * DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
           float newZ = - displayMetrics.density * 6;//屏幕密度，这个手机一dpi等于多少像素
         */

        //ObjectAnimator动画要给这个动画第二个参数起名字，他的名字比如是degree，那返回动画动作就是  setDegree

    }
}