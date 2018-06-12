package hyx.hencoderlearn.ui.one;

/**
 * 第七课：动画
 * Created by HeYingXin on 2017/11/7.
 */
public class FView {
    //关于属性动画

    /**
     * 一：ViewPropertyAnimator多属性动画（手动赋值）
     * 用方式：View.animate() 后跟 translationX() 等方法，动画会自动执行。
     * view.animate().translationX(500);
     * 对应的还有个bY，意思是增量，比如：
     * 例如，translationX(100) 表示用动画把 View 的 translationX 值渐变为 100，而 translationXBy(100)
     * 则表示用动画把 View 的 translationX 值渐变地增加 100
     * 对应的方法集合：https://user-gold-cdn.xitu.io/2017/9/6/94be145cf5c31d1970ea28e359d06d59?imageView2/0/w/1280/h/960/ignore-error/1
     */

    /**
     * 二：ObjectAnimator对象动画（通过内部的set get 自动赋值）
     * 使用方式：
       如果是自定义控件，需要添加 setter / getter 方法；
       用 ObjectAnimator.ofXXX() 创建 ObjectAnimator 对象；
       用 start() 方法执行动画。
     *
     */

    //他两内部实现都是通过ValueAnimator值动画,ObjectAnimator是他的子类

    /**
     * 通用的功能：
     * 1. setDuration(int duration) 设置动画时长
     * // imageView1: 500 毫秒
          imageView1.animate()
          .translationX(500)
          .setDuration(500);

     // imageView2: 2 秒
         ObjectAnimator animator = ObjectAnimator.ofFloat(
         imageView2, "translationX", 500);
         animator.setDuration(2000);
         animator.start();
     *
     * 2.  setInterpolator(Interpolator interpolator) 设置 Interpolator
     * // imageView1: 线性 Interpolator，匀速
          imageView1.animate()
          .translationX(500)
          .setInterpolator(new LinearInterpolator());

     // imageView: 带施法前摇和回弹的 Interpolator
         ObjectAnimator animator = ObjectAnimator.ofFloat(
         imageView2, "translationX", 500);
         animator.setInterpolator(new AnticipateOvershootInterpolator());
         animator.start();
     *
     * 分类：
     * 1. AccelerateDecelerateInterpolator 先加速再减速。这是默认的 Interpolator（正弦 / 余弦曲线）
     * 2. LinearInterpolator 匀速
     * 3. AccelerateInterpolator 持续加速（指数曲线）
     * 4. DecelerateInterpolator 持续减速到0
     * 5. AnticipateInterpolator 先回拉一下再进行正常动画轨迹。效果看起来有点像投掷物体或跳跃等动作前的蓄力。
     * 6. OvershootInterpolator 动画会超过目标值一些，然后再弹回来。效果看起来有点像你一屁股坐在沙发上后又被弹起来一点的感觉
     * 7. AnticipateOvershootInterpolator 上面这两个的结合版：开始前回拉，最后超过一些然后回弹。
     * 8. BounceInterpolator 在目标值处弹跳。有点像玻璃球掉在地板上的效果
     * 9. CycleInterpolator 它可以自定义曲线的周期，所以动画可以不到终点就结束，也可以到达终点后回弹，回弹的次数由曲线的周期决定
     * 10. PathInterpolator 自定义动画完成度 / 时间完成度曲线
     * 比如：
     * Path interpolatorPath = new Path();
     * // 匀速
     * interpolatorPath.lineTo(1, 1);
     * // 先以「动画完成度 : 时间完成度 = 1 : 1」的速度匀速运行 25%
     * interpolatorPath.lineTo(0.25f, 0.25f);
     * // 然后瞬间跳跃到 150% 的动画完成度
     * interpolatorPath.moveTo(0.25f, 1.5f);
     * // 再匀速倒车，返回到目标点
     * interpolatorPath.lineTo(1, 1);
     *
     * Android 5.0 （API 21）引入了三个新的 Interpolator 模型
     * 11. FastOutLinearInInterpolator 加速运动。（曲线公式是用的贝塞尔曲线），比AccelerateInterpolator初始位置要快
     * 12. FastOutSlowInInterpolator 先加速再减速。（用的是贝塞尔曲线）他的前期加速和后期减速都要更猛
     * 13. LinearOutSlowInInterpolator 持续减速 ,他的初始速度是会更高的
     */

    /**
     * 关于监听器:
     * 1:
     * ViewPropertyAnimator 用的是 setListener() 和 setUpdateListener() 方法，
     * 可以设置一个监听器，要移除监听器时通过 set[Update]Listener(null) 填 null 值来移除
     * 独有 withStartAction() 和 withEndAction() 方法，可以设置一次性的动画开始或结束的监听
     * 2:
     * ObjectAnimator 则是用 addListener() 和 addUpdateListener() 来添加一个或多个监听器
     * ，移除监听器则是通过 remove[Update]Listener() 来指定移除对象,
     * 还多了一个 addPauseListener() / removePauseListener() 的支持
     */

    /**
     * 注意:
     * 就算动画被取消，onAnimationEnd() 也会被调用。所以当动画被取消时，如果设置了
     * AnimatorListener，那么 onAnimationCancel() 和 onAnimationEnd() 都会被调用。
     * onAnimationCancel() 会先于 onAnimationEnd() 被调用
     *
     *
     * withEndAction() 设置的回调只有在动画正常结束时才会被调用，而在动画被取消时不会被执行
     * 而且他们是一次性的，就是一次性的再重用 ViewPropertyAnimator 来做别的动画，用它们设置的回调也不会再被调用
     * 但是listener可以重用
     */
}