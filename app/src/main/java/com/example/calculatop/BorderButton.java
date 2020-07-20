package com.example.calculatop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class BorderButton extends View {
    private ShapeDrawable vertRect, horizRect;
    private Rect mRectSquare;
    private Paint mPaintSquare;
    public BorderButton(Context context) {
        super(context);
        init(null);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public BorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int width = this.getWidth();
        int height = this.getHeight();

        vertRect = new ShapeDrawable(new RectShape());
        vertRect.getPaint().setColor(Color.RED);
        vertRect.setBounds(0, 0, width/10, height);
        horizRect = new ShapeDrawable(new RectShape());
        horizRect.getPaint().setColor(Color.RED);
        horizRect.setBounds(0, 0, width, height/9);

    }

    public BorderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@NonNull AttributeSet set){
        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor( Color.RED );
        paint.setStrokeWidth( 1.5f );
        paint.setStyle( Paint.Style.STROKE );
        canvas.drawLine(0,0,0,getHeight(),paint);


    }

}
