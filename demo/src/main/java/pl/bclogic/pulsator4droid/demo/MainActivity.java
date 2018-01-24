package pl.bclogic.pulsator4droid.demo;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    private PulsatorLayout mPulsator;
    private RelativeLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);
//        mPulsator.start();
        layout= (RelativeLayout) findViewById(R.id.relative);
        PulsatorLayout pulse= new PulsatorLayout(this);
        pulse.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        Log.d("DP to Pixel", "onCreate: "+dp2px(getResources(),50));

        PulsatorLayout.LayoutParams param= new PulsatorLayout.LayoutParams(dp2px(getResources(),50),dp2px(getResources(),50));
        param.leftMargin=dp2px(getResources(),25);
        param.topMargin=dp2px(getResources(),25);
/*        param.leftMargin=50;
        param.topMargin=50;*/
        pulse.setLayoutParams(param);

        layout.addView(pulse);
        pulse.start();


/*
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                PulsatorFrameLayout pulse= new PulsatorFrameLayout(MainActivity.this);
                int width  = layout.getMeasuredWidth();
                int height = layout.getMeasuredHeight();
                pulse.setPosition(width,height);
                layout.addView(pulse);
                pulse.start();

            }
        });*/


    }

    public static int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,   dp,resource.getDisplayMetrics());
    }


}
