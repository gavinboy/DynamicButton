package com.gavin.dynamicbutton;

import android.graphics.drawable.GradientDrawable;

/**
 * 主要是为了使用属性动画而设计的类
 * Created by tom on 15/10/31.
 */
public class CustomGradientDrawable extends GradientDrawable{

    private int mStrokeWidth;
    private int mStrokeColor;

    private float mRadius;
    private int mColor;

    public CustomGradientDrawable(){

    }

    public void setStrokeWidth(int mStrokeWidth){
        this.mStrokeWidth=mStrokeWidth;
        setStroke(mStrokeWidth,mStrokeColor);
    }

    public void setStrokeColor(int mStrokeColor){
        this.mStrokeColor=mStrokeColor;
        setStroke(mStrokeWidth,mStrokeColor);
    }

    public int getStrokeWidth(){
        return mStrokeWidth;
    }

    public int getStrokeColor(){
        return mStrokeColor;
    }

    public void setRadius(float mRadius){
        this.mRadius=mRadius;
        setCornerRadius(mRadius);
    }

    public float getRadius(){
        return mRadius;
    }

    public void setColor(int mColor){
        super.setColor(mColor);
        this.mColor=mColor;
    }

    public int getColor(){
        return mColor;
    }
}
