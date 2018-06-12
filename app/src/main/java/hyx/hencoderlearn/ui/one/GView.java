package hyx.hencoderlearn.ui.one;

/**
 * 第八课 动画
 * Created by HeYingXin on 2017/11/7.
 */
public class GView {

    /**
     * 思考问题：
     *
     怎么针对特殊类型的属性来做属性动画；
     怎么针对复杂的属性关系来做属性动画。
     */


    /**
     * 首先，属性动画使用ofInt ofFloat 都是常有的，
     * 当需要对其他类型来做属性动画的时候，就需要用到 TypeEvaluator 了(Evaluator是可以自定义的（意思就是求值器）)
     */

    /**
     * HSV(色相，饱和度，明度) HSL（色相，饱和度，亮度）
     * 1:ArgbEvaluator(颜色渐变的动画)
     *ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
      animator.setEvaluator(new ArgbEvaluator());
      animator.start();
      如果api高于21
      ObjectAnimator animator = ObjectAnimator.ofArgb(view, "color", 0xffff0000, 0xff00ff00);
      animator.start();
     */

    /**
     * ofObject()
     * 借助于 TypeEvaluator，属性动画就可以通过 ofObject() 来对不限定类型的属性做动画了
     * 为目标属性写一个自定义的 TypeEvaluator,使用 ofObject() 来创建 Animator，并把自定义的 TypeEvaluator 作为参数填入
     *
     * 这个自定义怎么写，其实重写的三个参数   第一个是百分比（动画完成度）  第二个是开始值   第三个是结束值
     * 其实就是  结束值减去开始值  乘上百分比  就行
     */

    /**
     * 2:PropertyValuesHolder 同一个动画中改变多个属性
     * 同一个动画中会需要改变多个属性，例如在改变透明度的同时改变尺寸(比如ViewPropertyAnimator 可以连起来写)
     * PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1);
       PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1);
       PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1);

       ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3)
       animator.start();
     *
     */

    /**
     * PropertyValuesHolders.ofKeyframe() 把同一个属性拆分
     * 除了合并多个属性和调配多个动画，你还可以在 PropertyValuesHolder 的基础上更进一步，
     * 通过设置 Keyframe （关键帧），把同一个动画属性拆分成多个阶段。例如，你可以让一个进度增加到 100% 后再「反弹」回来
     *
     * // 在 0% 处开始
     Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
     // 时间经过 50% 的时候，动画完成度 100%
     Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
     // 时间见过 100% 的时候，动画完成度倒退到 80%，即反弹 20%
     Keyframe keyframe3 = Keyframe.ofFloat(1, 80);
     PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

     ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder);
     animator.start();
     *
     */

    /**
     * AnimatorSet 多个动画配合执行
     *
     * ObjectAnimator animator1 = ObjectAnimator.ofFloat(...);
     animator1.setInterpolator(new LinearInterpolator());
     ObjectAnimator animator2 = ObjectAnimator.ofInt(...);
     animator2.setInterpolator(new DecelerateInterpolator());

     AnimatorSet animatorSet = new AnimatorSet();
     // 两个动画依次执行
     animatorSet.playSequentially(animator1, animator2);
     animatorSet.start();
     *
     * // 两个动画同时执行
     animatorSet.playTogether(animator1, animator2);
     animatorSet.start();
     *
     * // 使用 AnimatorSet.play(animatorA).with/before/after(animatorB)
     // 的方式来精确配置各个 Animator 之间的关系
     animatorSet.play(animator1).with(animator2);
     animatorSet.play(animator1).before(animator2);
     animatorSet.play(animator1).after(animator2);
     animatorSet.start();
     *
     */

    /**
     * 总结：
     * 使用 PropertyValuesHolder 来对多个属性同时做动画；
     使用 AnimatorSet 来同时管理调配多个动画；
     PropertyValuesHolder 的进阶使用：使用 PropertyValuesHolder.ofKeyframe() 来把一个属性拆分成多段，执行更加精细的属性动画。
     额外简单说一下 ValuesAnimator。很多时候，你用不到它，只是在你使用一些第三方库的控件，而你想要做动画的属性却没有 setter / getter 方法的时候，会需要用到它
     */
}