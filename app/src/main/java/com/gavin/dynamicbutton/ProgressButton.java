package com.gavin.dynamicbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * 在{@link DynamicButton}的基础上添加进度条功能
 * Created by tom on 15/11/7.
 */
public class ProgressButton extends DynamicButton{
    public static final String TAG="ProgressButton";
    /**默认进度的最大值*/
    public static final float MAX_PROGRESS=100;
    /**默认进度的最小值*/
    public static final float MIN_PROGRESS=0;
    /**默认的完成进度颜色*/
    public static final int DEFAULT_PROGRESS_COLOR=0XFF09F768;
    /**当前进度*/
    private float progress=0;
    /**画进度的画笔*/
    private Paint mCompletePaint;
    /**完成进度对应的矩形*/
    private RectF mProgressRect=new RectF();
    /**进度监听*/
    private ProgressListener mProgressListener;
    /**进度条的最大进度*/
    private float mMaxProgress=MAX_PROGRESS;
    /**进度条的最小值*/
    private float mMinProgress=MIN_PROGRESS;
    /**进度条颜色*/
    private int mProgressColor=DEFAULT_PROGRESS_COLOR;


    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initView(Context mContext,AttributeSet attrs,int defStyleAttr){
        super.initView(mContext,attrs,defStyleAttr);
        TypedArray array=mContext.obtainStyledAttributes(attrs,R.styleable.ProgressButton,defStyleAttr,0);
        mMaxProgress=array.getFloat(R.styleable.ProgressButton_pbtn_maxvalue,MAX_PROGRESS);
        mMinProgress=array.getFloat(R.styleable.ProgressButton_pbtn_minvalue,MIN_PROGRESS);
        progress=array.getFloat(R.styleable.ProgressButton_pbtn_progress,0);
        mProgressColor=array.getColor(R.styleable.ProgressButton_pbtn_progresscolor,DEFAULT_PROGRESS_COLOR);
        array.recycle();
        mCompletePaint = new Paint();
        mCompletePaint.setColor(mProgressColor);
        mCompletePaint.setStyle(Paint.Style.FILL);
        mCompletePaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mProgressRect.bottom=this.getHeight();
    }

    /**
     * 获取当前进度
     * @return
     */
    public float getProgress(){
        return progress;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress > mMinProgress && progress <= mMaxProgress) {
            this.progress = progress;
            mProgressRect.right = (int) (mWidth * (progress / mMaxProgress));
            invalidate();
            if(progress==mMaxProgress && mProgressListener!=null){
                mProgressListener.onComplete(this);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.progress>mMinProgress && this.progress<=mMaxProgress){
            mProgressRect.bottom=this.getHeight();
            canvas.drawRoundRect(mProgressRect, 10, 10, mCompletePaint);
        }
        //当进度完成后,进度清零
        if(this.progress==mMaxProgress){
            this.progress=0;
        }
    }

    public void setProgressListener(ProgressListener listener){
        this.mProgressListener=listener;
    }

    public static interface ProgressListener{
        public void onComplete(ProgressButton button);
    }
}
