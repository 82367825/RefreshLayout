package com.zero.refreshlayout.library.theme.square;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzewu
 * @date 2017/9/5
 */

public class SquareSpreadView extends View {
    
    private static float FACTION_SQUARE_TO_CENTER = 0.3f;
    
    private Square mCenterSquare;
    private List<Square> mSquareList;
    
    private Paint mPaint;
    private boolean mIsParamsInit = false;
    private ValueAnimator mValueAnimator;
    
    public SquareSpreadView(Context context) {
        super(context);
        init();
    }

    public SquareSpreadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareSpreadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
    }
    
    private void initParams() {
        mCenterSquare = new Square(getWidth() / 2, getHeight() / 2, 
                getWidth() / 2, getHeight() / 2, (int) (0.35f * getHeight()));
        mCenterSquare.mCurX = mCenterSquare.mStartX;
        mCenterSquare.mCurY = mCenterSquare.mStartY;
        mSquareList = new ArrayList<>();
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX), 
                        (int)(mCenterSquare.mCurY - FACTION_SQUARE_TO_CENTER * getHeight()), 
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(0).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX + FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()), 
                        (int)(mCenterSquare.mCurY - FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(1).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX + FACTION_SQUARE_TO_CENTER * getHeight()),
                        (int)(mCenterSquare.mCurY),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(2).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX + FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterSquare.mCurY + FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(3).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX),
                        (int)(mCenterSquare.mCurY + FACTION_SQUARE_TO_CENTER * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(4).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX - FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterSquare.mCurY + FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(5).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX - FACTION_SQUARE_TO_CENTER * getHeight()),
                        (int)(mCenterSquare.mCurY),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(6).setSquareAnimDuration(0.3f));
        mSquareList.add(
                new Square(mCenterSquare.mCurX, mCenterSquare.mCurY,
                        (int)(mCenterSquare.mCurX - FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterSquare.mCurY - FACTION_SQUARE_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(7).setSquareAnimDuration(0.3f));
        for (Square square : mSquareList) {
            square.setCurFaction(0f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsParamsInit) {
            initParams();
            mIsParamsInit = true;
        }
        mCenterSquare.draw(canvas, mPaint);
        for (Square square : mSquareList) {
            square.draw(canvas, mPaint);
        }
    }
    
    public void startAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCenterSquare.mLength = (int) (0.1f * getHeight() + 0.25f * getHeight() * (1 - value));
                for (Square square : mSquareList) {
                    square.setCurFaction(value);
                }
                invalidate();
            }
        });
    }
    
    public void stopAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        initParams();
        invalidate();
    }
    
    public void setColor(int color) {
        mPaint.setColor(color);
    }
    
    
    private class Square {
        private int mStartX;
        private int mEndX;
        private int mStartY;
        private int mEndY;
        
        private int mCurX;
        private int mCurY;
        
        private int mSquareTotalCount;
        private int mSquareIndex;
        private float mSquareAnimDuration;
        
        private float mActualStart = -1f;
        private float mActualEnd = -1f;
        
        
        private int mLength;
        
        public Square(int startX, int startY, int endX, int endY, int length) {
            mStartX = startX;
            mStartY = startY;
            mEndX = endX;
            mEndY = endY;
            mLength = length;
        }
        
        public int getLength() {
            return mLength;
        }

        public void setLength(int length) {
            mLength = length;
        }
        
        public void draw(Canvas canvas, Paint paint) {
            int save = canvas.save();
            canvas.rotate(45, mCurX, mCurY);
            Rect rect = new Rect(mCurX - mLength / 2, mCurY - mLength / 2,
                    mCurX + mLength / 2, mCurY + mLength / 2);
            canvas.drawRect(rect, paint);
            canvas.restoreToCount(save);
        }
        
        public void setCurFaction(float faction) {
            if (mActualStart == -1f || mActualEnd == -1f) {
                float perDelayStartTime = mSquareAnimDuration - ((mSquareTotalCount * mSquareAnimDuration - 1f) / 
                        (mSquareTotalCount - 1));
                mActualStart = mSquareIndex * perDelayStartTime;
                mActualEnd = mActualStart + mSquareAnimDuration;

                Log.d("RefreshLayout", mSquareIndex + " : " + mActualStart);
            }
            float actualFaction = 0f;
            if (faction >= mActualStart && faction <= mActualEnd) {
                actualFaction = (faction - mActualStart) / mSquareAnimDuration;
            } else if (faction < mActualStart) {
                actualFaction = 0f;
            } else if (faction > mActualEnd) {
                actualFaction = 1f;
            }
            mCurX = (int) (mStartX + (mEndX - mStartX) * actualFaction);
            mCurY = (int) (mStartY + (mEndY - mStartY) * actualFaction);
        }

        public Square setSquareTotalCount(int squareTotalCount) {
            mSquareTotalCount = squareTotalCount;
            return this;
        }
        
        public Square setSquareIndex(int squareIndex) {
            mSquareIndex = squareIndex;
            return this;
        }
        
        public Square setSquareAnimDuration(float squareAnimDuration) {
            mSquareAnimDuration = squareAnimDuration;
            return this;
        }
    }
    
}
