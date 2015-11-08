package com.gavin.dynamicbutton;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="MainActivity";

    private DynamicButton mButton;
    private DynamicButton mButtonTwo;
    private ProgressButton mProgressButton;
    private int value=1;
    private Timer mTimer=new Timer();
    int progress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton=(DynamicButton)this.findViewById(R.id.btnMorph1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = value + 1;
                if (value % 2 == 1) {
                    morphToSquare(mButton, 500);
                    Log.d("yzy", "morphToSquare..");
                } else {
                    morphToSuccess2(mButton);
                    Log.d("yzy", "morphToSuccess..");
                }

            }
        });
        morphToSquare(mButton, 0);

        mButtonTwo=(DynamicButton)this.findViewById(R.id.btnMorph2);
        mButtonTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                morphToSuccess(mButtonTwo);
            }
        });
        morphToSquare(mButtonTwo,0);

        mProgressButton=(ProgressButton)this.findViewById(R.id.btnMorph3);
        mProgressButton.setProgressListener(new ProgressButton.ProgressListener() {
            @Override
            public void onComplete(ProgressButton button) {
                morphToSuccess(button);
            }
        });
        mProgressButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    morphToProgress(mProgressButton);

                    mTimer.schedule(new TimerTask(){
                        @Override
                        public void run() {
                            progress++;
                            if(progress>ProgressButton.MAX_PROGRESS){
                                mTimer.cancel();
                            }
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressButton.setProgress(progress);
                                    Log.d(TAG,"progress:"+progress);
                                }
                            });
                        }
                    },1000,100);


            }
        });
        morphToSquare(mProgressButton, 0);

    }

    private void morphToSquare(final DynamicButton btnMorph, long duration) {
        DynamicButton.PropertyParam square = DynamicButton.PropertyParam.build()
                .duration(duration)
                .setCornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .setWidth(dimen(R.dimen.mb_width_100))
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.mb_blue))
                .setPressedColor(color(R.color.mb_blue_dark))
                .text(getString(R.string.mb_button));
        btnMorph.startChange(square);
    }



    private void morphToSuccess(final DynamicButton btnMorph) {
        DynamicButton.PropertyParam circle = DynamicButton.PropertyParam.build()
                .duration(500)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(dimen(R.dimen.mb_height_56))
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.mb_green))
                .icon(drawable(R.drawable.ic_done))
                .setPressedColor(color(R.color.mb_green_dark));

        btnMorph.startChange(circle);
    }

    private void morphToSuccess2(final DynamicButton btnMorph) {
        DynamicButton.PropertyParam circle = DynamicButton.PropertyParam.build()
                .duration(500)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(dimen(R.dimen.mb_width_120))
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.mb_green))
                .icon(drawable(R.drawable.ic_done))
                .text("Success")
                .setPressedColor(color(R.color.mb_green_dark));

        btnMorph.startChange(circle);
    }

    public void morphToProgress(final ProgressButton btnMorph){
        DynamicButton.PropertyParam progress=DynamicButton.PropertyParam.build()
                .duration(500)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(dimen(R.dimen.mb_width_200))
                .setHeight(dimen(R.dimen.mb_height_8))
                .setColor(color(R.color.gray))
                .setStrokeColor(color(R.color.blue))
                .setStrokeWidth(0)
                .setPressedColor(R.color.blue_dark);

        btnMorph.startChange(progress);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    public Drawable drawable(int resId){
        return getResources().getDrawable(resId);
    }
}
