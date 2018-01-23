package pl.bclogic.pulsator4droid.demo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import pl.bclogic.pulsator4droid.library.PulsatorFrameLayout;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout= (FrameLayout) findViewById(R.id.frame_layout);

        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(getDP(30),getDP(30));

        Pulselayout pL= new Pulselayout(this, Color.BLACK);

        layout.addView(pL,layoutParams);


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
    public int getDP(int px){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) ((px/displayMetrics.density)+0.5);
    }


}
