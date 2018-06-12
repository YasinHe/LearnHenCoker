package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/** 第二课 Paint
 * Created by HeYingXin on 2017/10/26.
 */
public class BView extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path(); // 初始化 Path 对象

    public BView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        //1 绘制颜色，setColor，setARGB，setShader 着色器，一旦设置color和argb就无效了
        /**
         * 一般 Shader不会直接使用，而是使用渐变器Gradient，BitmapShader着色
         * 如果你想绘制圆形的 Bitmap，就别用 drawBitmap() 了，改用 drawCircle() + BitmapShader 就可以了（其他形状同理)
         * CLAMP会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式
         *
         *
         * ComposeShader 混合着色器,将两个Shader 合并为一个
         * !!!注意  ComposeShader() 在硬件加速下是不支持两个相同类型的 Shader 的,开了硬件加速  两个一样  会看不到
         * PorterDuff.Mode 是用来指定两个图像共同绘制时的颜色策略的,总共17种，分为 Alpha 合成和混合两种
         * 合成就是  两张着色器，谁在谁上面，怎么覆盖，是不是掏空 等等
         * 混合就是ps的混合模式，不是修改alpha通道，两图覆盖会改变颜色
         *
         *
         * 颜色过滤setColorFilter(ColorFilter colorFilter)。类似于射光，有色玻璃等（光照效果，去除色值等等）
         * ColorMatrix也是属于ColorFilter的一种，拍图之后可以设置饱和度，什么灰度效果 老照片 明亮  印染都可以做到
         * (内部通过3*4矩阵 对原图色值进行转换修改)
         *
         *
         * setXfermode(Xfermode xfermode) ，它处理的是「当颜色遇上 View」的问题
         * Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);(这个设置是在两个图层直接设置的需要写
         * 一张绘制之后调用  然后绘制第二张！！！！！ 而且使用之后应该setXfermode（null）)用完及时清除 Xfermode
         * 他的作用是设置给paint  可以实现PorterDuff.Mode效果
         * 注意！！！！！
         * 要使用Xfermode就必须使用离屏缓冲，因为实际绘制的时候，是一层图层设置到view，再要个图层绘制到同层view，实际
         * 出来的效果。离屏缓冲则是，在view之外  先把图层合成做好  再最后放到view上面去
         * 离屏缓冲两种方法  1：Canvas.saveLayer()  2：View.setLayerType()
         * int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
                xxxxxxxx
           canvas.restoreToCount(saved);//这个性能好一些

           View.setLayerType() 是直接把整个 View 都绘制在离屏缓冲中。
           setLayerType(LAYER_TYPE_HARDWARE) 是使用 GPU 来缓冲，
           setLayerType(LAYER_TYPE_SOFTWARE) 是直接直接用一个 Bitmap 来缓冲。

         *
         * 线条形状的方法： setStrokeWidth(width) 线宽
         * setStrokeCap(cap) 圆头
         * setStrokeJoint(join) 拐角形状
         * 和 setStrokeMiter(miter) 相交的补偿
         *
         *
         * 色彩优化:
         * 1:setDither(boolean dither)  设置图像抖动,32位效果不明显  16位可以体现，通过抖动  将色值放燥到周遭
         * 2：setFilterBitmap(boolean filter)  双线性过滤，一般放大的时候是最近邻查，这样会有马赛克，开双线性会平缓很多
         *
         *
         * 给图形设置轮廓：   setPathEffect(PathEffect effect)
         * 1:CornerPathEffect  拐角变成圆角
         * 2：DiscretePathEffect  线条偏离，无规则轮廓两个参数分别代表  拼接的每个线段的长度，偏离量
         * 3：DashPathEffect  虚线，：数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」，第三参数虚线的偏移量
         * 4：PathDashPathEffect，以path来添加虚线，PathDashPathEffect.Style 枚举了三种类型，分别是TRANSLATE：位移，ROTATE：旋转，MORPH：变体
         *  组合效果，advance 是两个相邻的 shape 段之间的间隔，phase 和 DashPathEffect 中一样，是虚线的偏移
         * 5：SumPathEffect，分别按照两种 PathEffect 分别对目标进行绘制
         * 6：ComposePathEffect，也是组合，与上面不同的是，他的组合是先完成一种PathEffect然后将图形进行下一个PathEffect(顺序不一样，结果是很不一样的)
         * 注意！！！  PathEffect在Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时 不支持硬件加速
         * PathDashPathEffect 对硬件加速的支持也有问题
         *
         *
         * setShadowLayer(float radius, float dx, float dy, int shadowColor)给绘制的内容后面加阴影
         * 清除阴影 clearShadowLayer()
         * 注意！！！  在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制
         *
         *
         * //设置面具过滤
         *  setMaskFilter(MaskFilter maskfilter)，与上面的setShadowLayer不同，他是放在绘制层上面的而不是下面
         *  1：BlurMaskFilter 模糊效果，参数分别是模糊范围和模糊类型分为四种
             NORMAL: 内外都模糊绘制
             SOLID: 内部正常绘制，外部模糊
             INNER: 内部模糊，外部不绘制
             OUTER: 内部不绘制，外部模糊（什么鬼？）
         *  2：EmbossMaskFilter  浮雕效果，它的构造方法
         *  EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 的参数里，
         *  direction 是一个 3 个元素的数组，指定了光源的方向；
         *  ambient 是环境光的强度，数值范围是 0 到 1；
         *  specular 是炫光的系数；
         *  blurRadius 是应用光线的范围
         *
         *
         *  //获得path的实际路径getFillPath(Path src, Path dst)，由于paint设置的
         *  线条宽度还有划线模式不同，实际绘制路径也就不同，并不是你设置的想象中的
         *  getTextPath获取文字的绘制路径，文字其实是转化为图形进行绘制的，所以文字也是有path的，
         *  获得到他之后，我们可以给文字设置下划线
         *
         *
         *  paint本身带的一些，初始化方法：
         *  1：reset()   重置，比new好
         *  2：set(Paint src) 复制属性
         *  3： setFlags(int flags)  批量设置多个flag
         *  paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);  =  paint.setAntiAlias(true); paint.setDither(true);
         *
         */
    }
}