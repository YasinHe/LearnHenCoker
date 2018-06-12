package hyx.hencoderlearn.ui.one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import java.util.Locale;

/**
 * 第三课：Text
 * Created by HeYingXin on 2017/11/2.
 */
public class CView extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path(); // 初始化 Path 对象

    public CView(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        /**
         * 这个x y的位置是在文字左下角 往左边一点点的位置
         * 因为这个y点，是指的重心对齐，不同文字的高低都是不一样的，不是顶部底部对齐，而是重心基本线对齐，而x也要往左一点，因为实际文字
         * 都有间距宽度
         */
        canvas.drawText("贺应鑫",100,100,paint);
        /**
         * drawTextRun()  这个是绘制阿拉伯文之类的东西的
         */

        /**
         * 这个意思就是按照path去绘制文字，记住path拐角一定最好用圆角，别用直角
         * 参数中：hOffset 和 vOffset。它们是文字相对于 Path 的水平偏移量和竖直偏移量，
         * 利用它们可以调整文字的位置。例如你设置 hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素
         */
        canvas.drawTextOnPath("Hello HeCoder", path, 0, 0, paint);

        /**
         * Canvas.drawText()他是能绘制单行文字，第一不能超出屏幕又不会换行，第二加\n没用，只会加出空格
         * 这个时候可以使用StaticLayout，他是text.layout的子类而不是view
         *   width 是文字区域的宽度，文字到达这个宽度后就会自动换行；
             align 是文字的对齐方向；
             spacingmult 是行间距的倍数，通常情况下填 1 就好；
             spacingadd 是行间距的额外增加值，通常情况下填 0 就好；
             includeadd 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。
           专门应对多行绘制，下面案例中  text1会根据600的长度进行换行，下面的会直接根据\n换行
         */
        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        StaticLayout staticLayout1 = new StaticLayout(text1, new TextPaint(), 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
        StaticLayout staticLayout2 = new StaticLayout(text2, new TextPaint(), 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        canvas.save();//(掉sava之前要先restore或者之前没有任何操作也可以直接save)，他就是成对出现的，sava保存现在这样，restore回复save的样子
        canvas.translate(50, 100);
        staticLayout1.draw(canvas);
        canvas.translate(0, 200);
        staticLayout2.draw(canvas);
        canvas.restore();//用来恢复Canvas旋转、缩放等之后的状态
        /**
         * 使用paint设置textSize或者 设置字体
         */
        String text = "贺应鑫AbCd";
        paint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text, 100, 150, paint);
        paint.setTypeface(Typeface.SERIF);
        canvas.drawText(text, 100, 300, paint);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));
        canvas.drawText(text, 100, 450, paint);
        /**
         * 是否设置为  伪粗体，就是Bold的加粗效果(就是绘制的时候程序，把字体描粗了)
         */
        paint.setFakeBoldText(false);
        canvas.drawText(text, 100, 150, paint);
        paint.setFakeBoldText(true);
        canvas.drawText(text, 100, 230, paint);
        //是否添加删除线
        paint.setStrikeThruText(true);
        canvas.drawText(text, 100, 150, paint);
        //是否加下划线
        paint.setUnderlineText(true);
        canvas.drawText(text, 100, 150, paint);
        //设置倾斜度
        paint.setTextSkewX(-0.5f);
        canvas.drawText(text, 100, 150, paint);
        //设置文字横向缩放，就是字体的拉长拉瘦
        paint.setTextScaleX(1);
        canvas.drawText(text, 100, 150, paint);
        paint.setTextScaleX(0.8f);
        canvas.drawText(text, 100, 230, paint);
        paint.setTextScaleX(1.2f);
        canvas.drawText(text, 100, 310, paint);
        //设置字符的间距(实际上就是0间距，也有一个基本距离  这个是没办法的，也就是x的距离)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setLetterSpacing(0.2f);
        }
        canvas.drawText(text, 100, 150, paint);
        //用css的方式设置text字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setFontFeatureSettings("smcp"); // 设置 "small caps"
        }
        canvas.drawText("Hello HenCoder", 100, 150, paint);
        //设置对齐方式，在一个基准线  左边对齐，中心对齐  和右边对齐,x值无论多少  会以对齐为算
        int textHeight = 10;
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 500, 150, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, 500, 150 + textHeight, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text, 500, 150 + textHeight * 2, paint);
        //设置语言地域（不同地域语言同样文字  但是写法就是不一样）,api24还加入了多语言支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            paint.setTextLocale(Locale.CHINA); // 简体中文
            canvas.drawText(text, 150, 150, paint);
            paint.setTextLocale(Locale.TAIWAN); // 繁体中文
            canvas.drawText(text, 150, 150 + textHeight, paint);
            paint.setTextLocale(Locale.JAPAN); // 日语
            canvas.drawText(text, 150, 150 + textHeight * 2, paint);
        }
        //以下  目前都几乎没什么用
        //设置是否启用字体的 hinting （字体微调）,这个由于很多设备使用的是矢量图做text，可以让大文字尺寸也可以
        //圆润，但是小尺寸就会变得很失真,setElegantTextHeight(boolean elegant)  避免一些字太高，做适配
        //setSubpixelText(boolean subpixelText) 开启字体的次像素级的抗锯齿（）

        /**
         * 接下来的测量文字尺寸
         * paint.getFontSpacing() 获取文字推荐的行间距，就是高度间距，用这个可以做文字同高度换行
         */
        canvas.drawText(text, 100, 150, paint);
        canvas.drawText(text, 100, 150 + paint.getFontSpacing(), paint);
        canvas.drawText(text, 100, 150 + paint.getFontSpacing() * 2, paint);
        /**
         * ！！！！不管是怎么获取高宽，只需要输入text就行，不需要先绘制出来文字，直接用对应的paint去调取
         * FontMetircs getFontMetrics()
         * FontMetrics 是个比较专业的工具，ascent, descent, top, bottom, leading六个数值分别代表六条线
         * baseline基准线
         * ascent / descent  普通字符顶部和底部的范围线
         * top / bottom  任何字形的字符顶部和底部范围线
         * leading  这个是上面一行字的buttom 和这一行的top的距离
         * ascent 和 descent 这两个值还可以通过 Paint.ascent() 和 Paint.descent() 来快捷获取
         */
        //获取文字的显示范围  getTextBounds(String text, int start, int end, Rect bounds)等于最后给这个文字范围画了一个矩形框
        Rect bounds = new Rect();
        float offsetX = 100;
        float offsetY = 100;
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, offsetX, offsetY, paint);
        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.left += offsetX;
        bounds.top += offsetY;
        bounds.right += offsetX;
        bounds.bottom += offsetY;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);
        //测量文字的宽度并返回(getTextBounds测量的是人可见宽度，而measureText测的是实际占用的宽度)
        //这两个宽度是不一样的，就像是x的值一样，实际占用绘制的区域，是会要大一些的
        canvas.drawText(text, offsetX, offsetY, paint);
        float textWidth = paint.measureText(text);
        canvas.drawLine(offsetX, offsetY, offsetX + textWidth, offsetY, paint);
        //获取每个字符的宽度getTextWidths(String text, float[] widths)，填入一个数组接收
        /**
         * int breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth)
         * breakText() 的返回值是截取的文字个数（如果宽度没有超限，则是文字的总个数）。参数中， text 是要测量的文字；measureForwards
         * 表示文字的测量方向，true 表示由左往右测量；maxWidth 是给出的宽度上限
         * 在宽度上线的范围内测量
         */
        //光标测量text宽度（start 和 contextStart 都是 0， end contextEnd 和 offset 都等于 text.length()。在这种情况下，它是等价于 measureText(text) 的）
        //如果出现表情符号，那么不管设置怎样的offset，他都不会返回错，卡在表情中间，表情中间是没有位置的，虽然表情占很多符位
        int length = text.length();
        float advance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            advance = paint.getRunAdvance(text, 0, length, 0, length, false, length);
        }
        canvas.drawText(text, offsetX, offsetY, paint);
        canvas.drawLine(offsetX + advance, offsetY - 50, offsetX + advance, offsetY + 10, paint);
        // getOffsetForAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance)
        //给出一个位置的像素值，计算出文字中最接近这个位置的字符偏移量（即第几个字符最接近这个坐标）。
        //tart end 是文字的起始和结束坐标；contextStart contextEnd 是上下文的起始和结束坐标；isRtl 是文字方向；advance 是给出的位置的像素值
        //getOffsetForAdvance() 配合上 getRunAdvance() 一起使用，就可以实现「获取用户点击处的文字坐标」的需求

        // hasGlyph(String string)  检查指定的字符串中是否是一个单独的字形 (glyph）
    }
}