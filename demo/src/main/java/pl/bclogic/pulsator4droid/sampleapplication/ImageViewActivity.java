package pl.bclogic.pulsator4droid.sampleapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import pl.bclogic.pulsator4droid.demo.R;

public class ImageViewActivity extends AppCompatActivity {

    private static final String TAG = ImageViewActivity.class.getName();
    RelativeLayout layout;
    private Target t;
    private TextView tv;

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
                        rl.setGravity(Gravity.CENTER);
                        rl.setBackgroundColor(Color.parseColor("#A4000000"));
                        layout.addView(rl);
                    }

                    @Override
                    public void onError() {

                    }
                });
/*        t= new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width= layout.getWidth();
                int height= layout.getHeight();
                float scale= Math.min((float)width/(float)bitmap.getWidth() , (float)height/(float)bitmap.getHeight() );
                Log.d(TAG, "onBitmapLoaded: w : "+ width +" h: "+height);
                Log.d(TAG, "onBitmapLoaded: w : "+ bitmap.getWidth() +" h: "+bitmap.getHeight());
                tv.setText("onBitmapLoaded: Scale : "+scale);
//                RelativeLayout image_container= new RelativeLayout(ImageViewActivity.this);
//
//                RelativeLayout.LayoutParams param= new RelativeLayout.LayoutParams()
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: ");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d(TAG, "onPrepareLoad: ");

            }
        };*/



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }
}
