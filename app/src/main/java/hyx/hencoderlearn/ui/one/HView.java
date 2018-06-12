package hyx.hencoderlearn.ui.one;

/**
 * Created by HeYingXin on 2017/11/8.
 */
public class HView {
    //硬件加速

    /**
     * 所谓硬件加速，指的是把某些计算工作交给专门的硬件来做，而不是和普通的计算工作一样交给 CPU 来处理。
     * 这样不仅减轻了 CPU 的压力，而且由于有了「专人」的处理，这份计算工作的速度也被加快了。这就是「硬件加速」
     *
     * 而对于 Android 来说，硬件加速有它专属的意思：在 Android 里，
     * 硬件加速专指把 View 中绘制的计算工作交给 GPU 来处理。进一步地再明确一下，
     * 这个「绘制的计算工作」指的就是把绘制方法中的那些 Canvas.drawXXX() 变成实际的像素这件事
     */

    /**
     * 在硬件加速关闭的时候:
     * 绘制原理就是把draw方法中，需要绘制的内容 比如canvas.xxx方法，内容交给cpu转为实际像素信息，
     * 再还给draw，让他显示到屏幕上去
     * (在硬件加速关闭时，绘制内容会被 CPU 转换成实际的像素，然后直接渲染到屏幕)
     *
     * 开启硬件加速：
     * cpu把任务交给GPU，然后由gpu来计算并将他们显示到屏幕
     *
     * 在硬件加速开启时，CPU 做的事只是把绘制工作转换成 GPU 的操作，这个工作量相对来说是非常小的
     */

    /**
     * 加速原因:
     * 本来由 CPU 自己来做的事，分摊给了 GPU 一部分，自然可以提高效率；
     * 相对于 CPU 来说，GPU 自身的设计本来就对于很多常见类型内容的计算（例如简单的圆形、简单的方形）具有优势；
     * 由于绘制流程的不同，硬件加速在界面内容发生重绘的时候绘制流程可以得到优化，避免了一些重复操作，从而大幅提升绘制效率。
     *
     * 所谓绘制流程优化是指：
     * cpu会把绘制内容转换为像素，然后显示到屏幕，这个实际像素其实就是bitmap，当界面某个view更新，
     * 结果就是整个重新计算bitmap像素，这个view的全家view都要重绘，所以工作量非常大。
     * 而gpu则是把绘制内容转换为gpu操作记录，比如我要改变哪个view刷新，那么操作这个view对应的GPU
     * 部分操作内容就可以了，其他view不需要动。
     */

    /**
     * 但是！！！！
     * 硬件加速不只是好处，也有它的限制：受到 GPU 绘制方式的限制，Canvas 的有些方法在硬件加速开启式会失效或无法正常工作。
     * 对应的api支持：https://user-gold-cdn.xitu.io/2017/9/18/263905e233af4c17e9888a3d5bf15f32?imageView2/0/w/1280/h/960/ignore-error/1
     */

    /**
     * 硬件加速的开关
     * view.setLayerType(LAYER_TYPE_SOFTWARE, null);
     * (当它的参数为 LAYER_TYPE_SOFTWARE 的时候，可以「顺便」把硬件加速关掉而已)
     *
     * setLayerType() 这个方法，它的作用其实就是名字里的意思：设置 View Layer 的类型。
     * 所谓 View Layer，又称为离屏缓冲（Off-screen Buffer），
     * 它的作用是单独启用一块地方来绘制这个 View ，
     * 而不是使用软件绘制的 Bitmap 或者通过硬件加速的 GPU。
     * 这块「地方」可能是一块单独的 Bitmap，也可能是一块 OpenGL 的纹理
     * （texture，OpenGL 的纹理可以简单理解为图像的意思），
     * 具体取决于硬件加速是否开启.当设置了 View Layer 的时候，它的绘制会被缓存下来，
     * 而且缓存的是最终的绘制结果，而不是像硬件加速那样只是把 GPU 的操作保存下来再交给 GPU 去计算。
     * 通过这样更进一步的缓存方式，View 的重绘效率进一步提高了：
     * 只要绘制的内容没有变，那么不论是 CPU 绘制还是 GPU 绘制，它们都不用重新计算，
     * 而只要只用之前缓存的绘制结果就可以了.
     *
     * 基于这样的原理，在进行移动、旋转等（无需调用 invalidate()）的属性动画的时候开启
     * Hardware Layer 将会极大地提升动画的效率，因为在动画过程中 View 本身并没有发生改变，
     * 只是它的位置或角度改变了，而这种改变是可以由 GPU 通过简单计算就完成的，
     * 并不需要重绘整个 View。所以在这种动画的过程中开启 Hardware Layer，
     * 可以让本来就依靠硬件加速而变流畅了的动画变得更加流畅.
     * view.setLayerType(LAYER_TYPE_HARDWARE, null);
     * 在属性动画onAnimationEnd时：view.setLayerType(LAYER_TYPE_NONE, null);
     * ViewPropertyAnimator：
     * view.animate().withLayer(); // withLayer() 可以自动完成上面这段代码的复杂操作
     *但是！！！！！！这种方式不适用于基于自定义属性绘制的动画！！！！！！！！
     *
     * 另外，由于设置了 View Layer 后，View 在初次绘制时以及每次 invalidate() 后重绘时
     * ，需要进行两次的绘制工作（一次绘制到 Layer，一次从 Layer 绘制到显示屏），
     * 所以其实它的每次绘制的效率是被降低了的。
     */

    /**
     * 关于硬件加速的结论：
     * 参数为 LAYER_TYPE_SOFTWARE 时，使用软件来绘制 View Layer，绘制到一个 Bitmap，并顺便关闭硬件加速；
     * 参数为 LAYER_TYPE_HARDWARE 时，使用 GPU 来绘制 View Layer，绘制到一个 OpenGL texture（如果硬件加速关闭，那么行为和 VIEW_TYPE_SOFTWARE 一致）；
     * 参数为 LAYER_TYPE_NONE 时，关闭 View Layer。
     * View Layer 可以加速无 invalidate() 时的刷新效率，但对于需要调用 invalidate() 的刷新无法加速。
     * View Layer 绘制所消耗的实际时间是比不使用 View Layer 时要高的，所以要慎重使用。
     */
}