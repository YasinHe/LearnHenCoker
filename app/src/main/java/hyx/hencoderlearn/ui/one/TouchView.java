package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HeYingXin on 2018/6/11.
 * 触摸反馈
 */
public class TouchView extends View{
    /**
     * 触摸事件都是独立的，按下 移动 抬起，其中action_cancel比较特殊是一种非人为的结束
     */

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
        /**
         * 从down事件开始，会从手指点的最近view一个一个往下传递down事件（从外往里）
         * down往下传，直到遇到第一个最近的可以响应的view，这个事件会处理
         * retrun值，就是view的态度，我这个控件是否处理事件，一般只要down事件返回值是true
         * 那就是处理，move和up返回什么就不影响了。
         *
         * 相反：在onTouchEvent之前，android会从activity，root开始（从里往外）询问外层的view
         * 要不要拦截事件，拦截就是自己处理不给子view。也就是ViewGroup的Intercept，默认false不拦截，
         * 如果拦截的话那就会交给他的onTouchEvent直接处理，而这个事件之后的后续事件也全部由他处理不会再
         * 交给子view，也不会再调用Intercept而是直接给他的onTouchEvent。
         *
         * 如果全部是false，那就会执行onTouchEvent（从外往里，从上到下）
         *
         * Intercept和onTouchEvent区别：
         * onTouchEvent在down事件出现时，如果返回了false，那从此这个事件就跟这个控件没有关系了。
         * 而Intercept则是全程对事件流每个事件都监听，按自己的拦截想法，在需要的时候返回true，立即
         * 就可以获得处理能力。Intercept返回true的实现事件接管时候会额外对子view发送一个取消cancel
         *
         * 子view请求不让父view拦截，会挨个调用给所有的父view，仅限一轮事件。
         */
    }
}
