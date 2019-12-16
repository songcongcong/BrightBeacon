package com.example.retrofitmvplibrary.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.annotations.Nullable;


/**
 * 作者：宋聪聪 on 2019/6/21.
 */

public class MyCircleView extends View {

    public MyCircleView(Context context) {
        super(context);
    }
    //有style方法時需要使用这个构造方法
    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//重写draw方法，用于画圆
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //实例化画笔对象
        Paint paint = new Paint();
        //给画笔设置颜色
        paint.setColor(Color.RED);
        //设置画笔属性
//        paint.setStyle(Paint.Style.FILL); //实心圆
        paint.setStyle(Paint.Style.STROKE);//空心圆
        //设置画笔粗细
        paint.setStrokeWidth(8);

        /**
         * 开始绘制
         * 四个参事
         * 1、圆心的X坐标
         * 2、圆心的Y坐标
         * 3、圆的半径
         * 4、定义的画笔
         */
        canvas.drawCircle(getWidth()/2,getHeight()/2,200,paint);
    }
}
