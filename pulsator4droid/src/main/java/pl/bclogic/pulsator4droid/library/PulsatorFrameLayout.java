package pl.bclogic.pulsator4droid.library;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by tkmajne on 1/22/18.
 */

public class PulsatorFrameLayout extends RelativeLayout {
    private PulsatorLayout mPulsator;


    public PulsatorFrameLayout(Context context) {
        this(context,null);
    }

    public PulsatorFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PulsatorFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PulsatorFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(),R.layout.compound,this);
        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);

    }

    public void start() {
        // start pulsator
        mPulsator.start();
    }

    public void setPosition(int width, int height) {
        LayoutParams params= new LayoutParams(mPulsator.getLayoutParams());
        params.setMargins(width,height,getWidth()-width,getHeight()-height);
        mPulsator.setLayoutParams(params);
    }
}
