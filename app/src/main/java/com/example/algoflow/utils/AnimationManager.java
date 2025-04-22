package com.example.algoflow.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimationManager {
    private final View view;
    private ValueAnimator animator;
    private boolean isAnimating = false;
    private float animationProgress = 0.0f;
    private int index1 = -1;
    private int index2 = -1;

    public AnimationManager(View view) {
        this.view = view;
        initAnimator();
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            animationProgress = (float) animation.getAnimatedValue();
            view.invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                index1 = -1;
                index2 = -1;
                view.invalidate();
            }
        });
    }

    // animation swap
    public void animateSwap(int index1, int index2, Runnable onComplete) {
        this.index1 = index1;
        this.index2 = index2;
        this.isAnimating = true;
        this.animationProgress = 0.0f;

        animator.removeAllListeners();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                AnimationManager.this.index1 = -1;
                AnimationManager.this.index2 = -1;
                onComplete.run();
                view.invalidate();
            }
        });

        view.post(() -> animator.start());
    }

    // pause animation
    public void pause() {
        if (isAnimating) {
            animator.pause();
        }
    }

    // resume animation
    public void resume() {
        if (isAnimating) {
            animator.resume();
        }
    }

    // cancel animation
    public void cancel() {
        if (isAnimating) {
            animator.cancel();
            isAnimating = false;
            index1 = -1;
            index2 = -1;
            view.invalidate();
        }
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public float getAnimationProgress() {
        return animationProgress;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2(){
        return index2;
    }

    // animation shift
    public void animateShift(int fromIndex, int toIndex, Runnable onComplete) {
        this.index1 = fromIndex;
        this.index2 = toIndex;
        this.isAnimating = true;
        this.animationProgress = 0.0f;

        animator.removeAllListeners();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                AnimationManager.this.index1 = -1;
                AnimationManager.this.index2 = -1;
                onComplete.run();
                view.invalidate();
            }
        });

        view.post(() -> animator.start());
    }
}