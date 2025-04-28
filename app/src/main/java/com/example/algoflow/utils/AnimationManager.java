package com.example.algoflow.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

import com.example.algoflow.algorithm_views.SortView;

public class AnimationManager {
    private final View view;
    private ValueAnimator animator;
    private boolean isAnimating = false;
    private float animationProgress = 0.0f;
    private int index1 = -1;
    private int index2 = -1;
    private int[] array; // Thêm tham chiếu đến mảng
    private final Object pauseLock; // Thêm tham chiếu đến pauseLock

    public AnimationManager(View view, int[] array, Object pauseLock) {
        this.view = view;
        this.array = array;
        this.pauseLock = pauseLock;
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

    // Các phương thức khác giữ nguyên
    public void pause() {
        if (isAnimating) {
            animator.pause();
        }
    }

    public void resume() {
        if (isAnimating) {
            animator.resume();
        }
    }

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

    public int getIndex2() {
        return index2;
    }

    public void animateSwap(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
        this.isAnimating = true;
        this.animationProgress = 0.0f;

        animator.removeAllListeners();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                int temp = array[index1];
                array[index1] = array[index2];
                array[index2] = temp;
                AnimationManager.this.index1 = -1;
                AnimationManager.this.index2 = -1;
                view.invalidate();
                synchronized (pauseLock) {
                    pauseLock.notify();
                }
            }
        });

        view.post(() -> animator.start());

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (view instanceof SortView) {
                    ((SortView) view).setSorting(false);
                }
            }
        }
    }

    public void animateShift(int fromIndex, int toIndex) {
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
                view.invalidate();
                synchronized (pauseLock) {
                    pauseLock.notify();
                }
            }
        });

        view.post(() -> animator.start());

        synchronized (pauseLock) {
            try {
                pauseLock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (view instanceof SortView) {
                    ((SortView) view).setSorting(false);
                }
            }
        }
    }
}