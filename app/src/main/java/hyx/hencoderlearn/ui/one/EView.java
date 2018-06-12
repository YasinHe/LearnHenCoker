package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * 第五课：绘制流程
 * Created by HeYingXin on 2017/11/7.
 */
public class EView extends View{
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path(); // 初始化 Path 对象
    Camera camera = new Camera();
    public EView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 把绘制内容： super.onDraw() 的下面
         * 把绘制代码写在 super.onDraw() 的下面，由于绘制代码会在原有内容绘制结束之后才执行，所以绘制内容就会盖住控件原来的内容
         *
         * 写在 super.onDraw() 的上面
         * 由于绘制代码会执行在原有内容的绘制之前，所以绘制的内容会被控件的原内容盖住，等于绘制一个背景强调色这样的
         *
         * dispatchDraw()：绘制子 View 的方法():
         * 例如，你继承了一个 LinearLayout，重写了它的 onDraw() 方法,你的目的是希望通过这个layout做自定义，做无遮盖
         * 视图效果，然后你在onDraw重写效果，实现之后，你把这个自定义layout放在xml做扩展，写了子view之后，onDraw的内容就不见了
         * 造成这种情况的原因是 Android 的绘制顺序：在绘制过程中，每一个 ViewGroup 会先调用自己的 onDraw() 来绘制完自己的主体之后再去绘制它的子 View
         * 这样就是onDraw先绘制，然后才是子View，子View就会盖住onDraw
         * ！！！！注：虽然 View 和 ViewGroup 都有 dispatchDraw() 方法，不过由于 View 是没有子 View 的，
         * 所以一般来说 dispatchDraw() 这个方法只对 ViewGroup（以及它的子类）有意义！！！！！
         * 所以结论：在绘制过程中，每个 View 和 ViewGroup 都会先调用 onDraw() 方法来绘制主体，再调用 dispatchDraw() 方法来绘制子 View
         * 只要重写 dispatchDraw()，并在 super.dispatchDraw() 的下面写上你的绘制代码，这段绘制代码就会发生在子 View 的绘制之后，从而让绘制内容盖住子 View 了
         *
         */

        /**
         * 绘制过程简述
         *
         背景:通常一个 LinearLayout 只有背景和子 View，那么它会先绘制背景再绘制子 View
         主体（onDraw()）:它的前景也会在主体之后进行绘制。需要注意，前景的支持是在 Android 6.0（也就是 API 23）才加入的
         子 View（dispatchDraw()）
         滑动边缘渐变和滑动条  :第 4、5 两步——滑动边缘渐变和滑动条以及前景，这两部分被合在一起放在了 onDrawForeground() 方法里
         前景
         *结论：
         *背景：它的绘制发生在一个叫 drawBackground() 的方法里，但这个方法是 private 的，不能重写（android:background 属性以及 Java 代码的 View.setBackgroundXxx() 方法）
         *主体：onDraw
         *子View：dispatchDraw
         *滑动边缘渐变和滑动条：可以通过 xml 的 android:scrollbarXXX 系列属性或 Java 代码的 View.setXXXScrollbarXXX() 系列方法来设置
         *前景：可以通过 xml 的 android:foreground 属性或 Java 代码的 View.setForeground() 方法来设置。
         * 而重写 onDrawForeground() 方法，并在它的 super.onDrawForeground() 方法的上面或下面插入绘制代码，
         * 则可以控制绘制内容和滑动边缘渐变、滑动条以及前景的遮盖关系
         */

        /**
         * 关于api23引入的前景：onDrawForeground()
         * 在 onDrawForeground() 中，会依次绘制滑动边缘渐变、滑动条和前景
         * 把绘制代码写在了 super.onDrawForeground() 的下面，绘制代码会在滑动边缘渐变、滑动条和前景之后被执行，那么绘制内容将会盖住滑动边缘渐变、滑动条和前景
         * 比如： android:foreground="#88000000"  super.onDrawForeground(canvas);后面加绘制图片，图片在前景绘制之后再绘制
         * 如果写在super.onDrawForeground()前面，那效果就会反过来了
         */

        /**
         *  draw() 总调度方法：
         *  drawBackground(Canvas); // 绘制背景（不能重写）
            onDraw(Canvas); // 绘制主体
            dispatchDraw(Canvas); // 绘制子 View
            onDrawForeground(Canvas); // 绘制滑动相关和前景
         * onDraw() dispatchDraw() onDrawForeground() 这三个方法在 draw() 中被依次调用，
         * 因此它们的遮盖关系也就像前面所说的——dispatchDraw() 绘制的内容盖住 onDraw() 绘制的内容；
         * onDrawForeground() 绘制的内容盖住 dispatchDraw() 绘制的内容。
         * 而在它们的外部，则是由 draw() 这个方法作为总的调度。所以，你也可以重写 draw() 方法来做自定义的绘制
         * 1：写在 super.draw() 的下面
         由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的下面，
         那么这段代码会在其他所有绘制完成之后再执行，也就是说，它的绘制内容会盖住其他的所有绘制内容
         * 2：写在 super.draw() 的上面
         同理，由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的上面，
         那么这段代码会在其他所有绘制之前被执行，所以这部分绘制内容会被其他所有的内容盖住，包括背景。是的，背景也会盖住它
         （这个看起来没用，其实不是的，比如editText 的background已经是最背景的了，想在背景前画背景就可以用这个办法）
         */

        /**
         * 最后注意要点：
         * 1：出于效率的考虑，ViewGroup 默认会绕过 draw() 方法，换而直接执行 dispatchDraw()，以此来简化绘制流程，
         * 所以如果你自定义了某个 ViewGroup 的子类（比如 LinearLayout）并且需要在它的除 dispatchDraw()
         * 以外的任何一个绘制方法内绘制内容，你可能会需要调用 View.setWillNotDraw(false),就会说onDraw根本不会执行
         * 2：如果绘制代码既可以写在 onDraw() 里，也可以写在其他绘制方法里，那么优先写在 onDraw() ，因为 Android 有相关的优化
         * ，可以在不需要重绘的时候自动跳过 onDraw() 的重复执行，以提升开发效率。享受这种优化的只有 onDraw() 一个方法
         *  流程如下：
         *  https://user-gold-cdn.xitu.io/2017/8/14/82e9383685be9ddae82b656176e69cf1?imageView2/0/w/1280/h/960/ignore-error/1
         */

        /**
         * 断点view的绘制流程大致是：
         * draw前 ->ondraw前 ->后 -> dispatchDraw前 ->后 ->onDrawForeground前 -> 后  -> draw后
         */
    }
}