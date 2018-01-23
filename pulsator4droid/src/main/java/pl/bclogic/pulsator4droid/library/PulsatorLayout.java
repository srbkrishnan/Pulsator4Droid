package pl.bclogic.pulsator4droid.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by booncol on 04.07.2016.
 *
 */
public class PulsatorLayout extends FrameLayout {
    public static final int INTERP_ACCELERATE = 1;
    public static final int INTERP_DECELERATE = 2;
    public static final int INTERP_ACCELERATE_DECELERATE = 3;

    private static final int DEFAULT_COLOR = Color.rgb(0, 116, 193);
    private static final int DEFAULT_DURATION = 2000;
    private static final boolean DEFAULT_START_FROM_SCRATCH = true;
    private static final int DEFAULT_INTERPOLATOR = INTERP_ACCELERATE;

    private int mDuration;
    private boolean mStartFromScratch;
    private int mColor;
    private int mInterpolator;

    private final List<View> mViews = new ArrayList<>();
    private AnimatorSet mAnimatorSet;
    private Paint mPaint;
    private float mOuterRadius;
    private float mInnerRadius;
    private float mCenterX;
    private float mCenterY;
    private boolean mIsStarted;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can access the current
     *                theme, resources, etc.
     */
    public PulsatorLayout(Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context The Context the view is running in, through which it can access the current
     *                theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     */
    public PulsatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a theme attribute.
     *
     * @param context The Context the view is running in, through which it can access the current
     *                theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style
     *                     resource that supplies default values for the view. Can be 0 to not look
     *                     for defaults.
     */
    public PulsatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // get attributes
        TypedArray attr = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Pulsator4Droid, 0, 0);
        mDuration = DEFAULT_DURATION;
        mStartFromScratch = DEFAULT_START_FROM_SCRATCH;
        mColor = DEFAULT_COLOR;
        mInterpolator = DEFAULT_INTERPOLATOR;

        try {

            mColor = attr.getColor(R.styleable.Pulsator4Droid_pulse_color, DEFAULT_COLOR);

        } finally {
            attr.recycle();
        }

        // create paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        // create views
        build();
    }

    /**
     * Start pulse animation.
     */
    public synchronized void start() {
        if (mAnimatorSet == null || mIsStarted) {
            return;
        }

        mAnimatorSet.start();

    }

    /**
     * Stop pulse animation.
     */
    public synchronized void stop() {
        if (mAnimatorSet == null || !mIsStarted) {
            return;
        }

        mAnimatorSet.end();
    }

    public synchronized boolean isStarted() {
        return (mAnimatorSet != null && mIsStarted);
    }



    /**
     * Get pulse duration.
     *
     * @return Duration of single pulse in milliseconds
     */
    public int getDuration() {
        return mDuration;
    }


    /**
     * Set single pulse duration.
     *
     * @param millis Pulse duration in milliseconds
     */
    public void setDuration(int millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        if (millis != mDuration) {
            mDuration = millis;
            reset();
            invalidate();
        }
    }

    /**
     * Gets the current color of the pulse effect in integer
     * Defaults to Color.rgb(0, 116, 193);
     * @return an integer representation of color
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Sets the current color of the pulse effect in integer
     * Takes effect immediately
     * Usage: Color.parseColor("<hex-value>") or getResources().getColor(R.color.colorAccent)
     * @param color : an integer representation of color
     */
    public void setColor(int color) {
        if (color != mColor) {
            this.mColor = color;

            if (mPaint != null) {
                mPaint.setColor(color);
            }
        }
    }

    /**
     * Get current interpolator type used for animating.
     *
     * @return Interpolator type as int
     */
    public int getInterpolator() {
        return mInterpolator;
    }

    /**
     * Set current interpolator used for animating.
     *
     * @param type Interpolator type as int
     */
    public void setInterpolator(int type) {
        if (type != mInterpolator) {
            mInterpolator = type;
            reset();
            invalidate();
        }
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        mCenterX = width * 0.5f;
        mCenterY = height * 0.5f;


    }
    public static float dp2px(Resources resource, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,   dp,resource.getDisplayMetrics());
    }

    /**
     * Remove all views and animators.
     */
    private void clear() {
        // remove animators
        stop();

        // remove old views
        for (View view : mViews) {
            removeView(view);
        }
        mViews.clear();
    }

    /**
     * Build pulse views and animators.
     */
    private void build() {
        // create views and animators
        mOuterRadius = dp2px(getResources(),23);
        mInnerRadius=dp2px(getResources(),13);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
       // layoutParams.addRule(CENTER_IN_PARENT);

        int repeatCount = ObjectAnimator.INFINITE;


            List<Animator> animators = new ArrayList<>();
            PulseView pulseView = new PulseView(getContext());
            pulseView.setScaleX(0);
            pulseView.setScaleY(0);
            pulseView.setAlpha(1);
            addView(pulseView, 0, layoutParams);
            mViews.add(pulseView);

            CircleView circleView= new CircleView(getContext());
            addView(circleView,1,layoutParams);
            mViews.add(circleView);

            long delay = mDuration /2;

            // setup animators
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(pulseView, "ScaleX", 0.9f,1f, 0f,1f);
            scaleXAnimator.setRepeatCount(repeatCount);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(delay);
            animators.add(scaleXAnimator);

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(pulseView, "ScaleY", 0.9f,1f, 0f,1f);
            scaleYAnimator.setRepeatCount(repeatCount);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(delay);
            animators.add(scaleYAnimator);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(pulseView, "Alpha", 0.1f, 0f, 1f,0f);
            alphaAnimator.setRepeatCount(repeatCount);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(delay);
            animators.add(alphaAnimator);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
        mAnimatorSet.setInterpolator(createInterpolator(mInterpolator));
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.addListener(mAnimatorListener);
    }

    /**
     * Reset views and animations.
     */
    private void reset() {
        boolean isStarted = isStarted();

        clear();
        build();

        if (isStarted) {
            start();
        }
    }

    /**
     * Create interpolator from type.
     *
     * @param type Interpolator type as int
     * @return Interpolator object of type
     */
    private static Interpolator createInterpolator(int type) {
        switch (type) {
            case INTERP_ACCELERATE:
                return new AccelerateInterpolator();
            case INTERP_DECELERATE:
                return new DecelerateInterpolator();
            case INTERP_ACCELERATE_DECELERATE:
                return new AccelerateDecelerateInterpolator();
            default:
                return new LinearInterpolator();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
    }

    private class PulseView extends View {

        public PulseView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mCenterX, mCenterY, mOuterRadius, mPaint);
        }

    }
    private class  CircleView extends View{


        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mCenterX,mCenterY, mInnerRadius,mPaint);
        }
    }

    private final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {
            mIsStarted = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mIsStarted = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            mIsStarted = false;
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }

    };

}
