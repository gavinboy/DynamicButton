package com.gavin.dynamicbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.renderscript.Sampler;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/**
 * 定义属性动画的开始值和结束值
 * Created by tom on 15/10/31.
 */
public class AnimatorBuilder {




    public static void startAnimator(final AnimatorParams params){
        ValueAnimator heightAnimator= ValueAnimator.ofInt(params.fromHeight,params.toHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp=params.mButton.getLayoutParams();
                lp.height=(Integer)animation.getAnimatedValue();
                params.mButton.setLayoutParams(lp);
            }
        });

        ValueAnimator widthAnimator=ValueAnimator.ofInt(params.fromWidth,params.toWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp=params.mButton.getLayoutParams();
                lp.width=(Integer)animation.getAnimatedValue();
                params.mButton.setLayoutParams(lp);
            }
        });

        CustomGradientDrawable drawable= (CustomGradientDrawable) params.mButton.getNormalDrawable();
        ObjectAnimator radiusAnimator=ObjectAnimator.ofFloat(drawable,"radius",params.fromCornerRadius,params.toCornerRadius);
        ObjectAnimator strokeWidthAnimator=ObjectAnimator.ofInt(drawable, "strokeWidth", params.fromStrokeWidth, params.toStrokeWidth);
        ObjectAnimator strokeColorAnimator=ObjectAnimator.ofInt(drawable,"strokeColor",params.fromStrokeColor,params.toStrokeColor);
        ObjectAnimator colorAnimaor=ObjectAnimator.ofInt(drawable,"color",params.fromColor,params.toColor);
        colorAnimaor.setEvaluator(new ArgbEvaluator());

        AnimatorSet animators=new AnimatorSet();
        animators.setDuration(params.duration);
        animators.play(radiusAnimator).with(strokeColorAnimator).with(strokeWidthAnimator).with(widthAnimator).with(heightAnimator).with(colorAnimaor);
        animators.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                params.mListener.onAnimatorEnd();
            }
        });

        animators.start();
    }

    public static class AnimatorParams{

        private float fromCornerRadius;
        private float toCornerRadius;

        private int fromHeight;
        private int toHeight;

        private int fromWidth;
        private int toWidth;

        private int fromColor;
        private int toColor;


        private int fromStrokeWidth;
        private int toStrokeWidth;

        private int fromStrokeColor;
        private int toStrokeColor;

        private long duration;


        private DynamicButton mButton;
        private AnimatorBuilder.AnimatorListener mListener;

        public AnimatorParams(DynamicButton mButton){
            this.mButton=mButton;
        }

        public static AnimatorParams build(DynamicButton mButton){
            return new AnimatorParams(mButton);
        }

        public AnimatorParams height(int fromHeight,int toHeight) {
            this.fromHeight = fromHeight;
            this.toHeight = toHeight;
            return this;
        }

        public AnimatorParams cornerRadius(float fromCornerRadius,float toCornerRadius){
            this.fromCornerRadius=fromCornerRadius;
            this.toCornerRadius=toCornerRadius;
            return this;
        }


        public AnimatorParams width(int fromWidth,int toWidth){
            this.fromWidth=fromWidth;
            this.toWidth=toWidth;
            return this;
        }

        public AnimatorParams strokeWidth(int fromStrokeWidth,int toStrokeWidth){
            this.fromStrokeWidth=fromStrokeWidth;
            this.toStrokeWidth=toStrokeWidth;
            return this;
        }


        public AnimatorParams strokeColor(int fromStrokeColor,int toStrokeColor){
            this.fromStrokeColor=fromStrokeColor;
            this.toStrokeColor=toStrokeColor;
            return this;
        }

        public AnimatorParams duration(long duration){
            this.duration=duration;
            return this;
        }

        public AnimatorParams listener(AnimatorListener listener){
            this.mListener=listener;
            return this;
        }

        public AnimatorParams color(int fromColor,int toColor){
            this.fromColor=fromColor;
            this.toColor=toColor;
            return this;
        }

    }


    public interface AnimatorListener{
        public void onAnimatorEnd();
    }
}
