package com.zlkj.dingdangwuyou.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;

/**
 * 解决文字和drawableleft和文字一起时不居中
 * Created by Botx on 2016/10/24.
 */

public class MDMRadioButton extends RadioButton {

    public MDMRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // TODO Auto-generated constructor stub
    }

    public MDMRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // TODO Auto-generated constructor stub
    }

    public MDMRadioButton(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取设置的图片
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            //第一个是left
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                //获取文字的宽度
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                int y = getWidth();
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
