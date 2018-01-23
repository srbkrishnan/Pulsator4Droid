package pl.bclogic.pulsator4droid.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by tkmajne on 1/22/18.
 */

public class Pulselayout extends RelativeLayout {

    int mColor;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private Paint mPaint;

    public Pulselayout( Context context, int color) {
        this(context,null);
        this.mColor=color;
    }

    public Pulselayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Pulselayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pulselayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        LayoutParams layoutParams= new LayoutParams(getDP(30),getDP(30));
        View circle = new CircleView(getContext());
        addView(circle,layoutParams);
    }
    public int getDP(int px){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int) ((px/displayMetrics.density)+0.5);
    }
    class CircleView extends View{

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mCenterX, mCenterY, 30.0f, mPaint);

        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        mCenterX = width * 0.5f;
        mCenterY = height * 0.5f;
        mRadius = 70.0f;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
