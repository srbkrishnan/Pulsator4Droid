package pl.bclogic.pulsator4droid.sampleapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import pl.bclogic.pulsator4droid.demo.R;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class ImageViewActivity extends AppCompatActivity {

    private static final String TAG = ImageViewActivity.class.getName();
    RelativeLayout layout;
    private Target t;
    private TextView tv;
    private static float[][] hotpots= {
            {0.5f,0.5f}
    };
    public static int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,   dp,resource.getDisplayMetrics());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        layout= (RelativeLayout) findViewById(R.id.main_container);
        final ImageView iv=(ImageView)findViewById(R.id.image);
         tv= (TextView) findViewById(R.id.textView);

        Picasso.with(this).load(Uri.parse("http://static6.businessinsider.com/image/56e83f1252bcd044008b6769/17-male-style-icons-on-instagram-who-will-inspire-you-to-dress-better.jpg"))
                .into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Drawable drawable = iv.getDrawable();
                        //you should call after the bitmap drawn
                        Rect bounds = drawable.getBounds();
                        int width = bounds.width();
                        int height = bounds.height();
                        float[] f = new float[9];
                        iv.getImageMatrix().getValues(f);
                        int displayedWidth = (int) (width * f[Matrix.MSCALE_X]);
                        int displayedHeight = (int) (height * f[Matrix.MSCALE_Y]);
                        Log.d(TAG, "onSuccess: w : "+ displayedWidth +" h: "+displayedHeight + "SX " +f[Matrix.MSCALE_X] + " SY "+f[Matrix.MSCALE_Y]);
                        Log.d(TAG, "onSuccess: w : "+ width +" h: "+height);

                        RelativeLayout rl= new RelativeLayout(ImageViewActivity.this);
                        RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(displayedWidth,displayedHeight);
                        param.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                        rl.setLayoutParams(param);
                        rl.setGravity(Gravity.NO_GRAVITY);
                        //rl.setBackgroundColor(Color.parseColor("#A4000000"));
                        layout.addView(rl);
                        setupHotSpots(rl,displayedWidth,displayedHeight);
                    }

                    @Override
                    public void onError() {
                    }
                });
    }
    private void setupHotSpots(RelativeLayout rl, int displayedWidth, int displayedHeight) {
    for(float[] point : hotpots){
        PulsatorLayout pulse= new PulsatorLayout(this);
        pulse.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        PulsatorLayout.LayoutParams param= new PulsatorLayout.LayoutParams(dp2px(getResources(),50),dp2px(getResources(),50));
        param.leftMargin= (int) (displayedWidth*point[0]) -dp2px(getResources(),25);
        param.topMargin= (int) (displayedHeight*point[1])-dp2px(getResources(),25);
        pulse.setLayoutParams(param);
        rl.addView(pulse);
        pulse.start();
    }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }
}
