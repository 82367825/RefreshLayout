package com.zero.refreshlayout.library.theme.round;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

public class RoundSpreadView extends View {

    private static float FACTION_ROUND_TO_CENTER = 0.3f;

    private Round mCenterRound;
    private List<Round> mRoundList;

    private Paint mPaint;
    private boolean mIsParamsInit = false;
    private ValueAnimator mValueAnimator;

    public RoundSpreadView(Context context) {
        super(context);
        init();
    }

    public RoundSpreadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundSpreadView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mCenterRound = new Round(getWidth() / 2, getHeight() / 2,
                getWidth() / 2, getHeight() / 2, (int) (0.35f * getHeight()));
        mCenterRound.mCurX = mCenterRound.mStartX;
        mCenterRound.mCurY = mCenterRound.mStartY;
        mRoundList = new ArrayList<>();
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX),
                        (int)(mCenterRound.mCurY - FACTION_ROUND_TO_CENTER * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(0).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX + FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterRound.mCurY - FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(1).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX + FACTION_ROUND_TO_CENTER * getHeight()),
                        (int)(mCenterRound.mCurY),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(2).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX + FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterRound.mCurY + FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(3).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX),
                        (int)(mCenterRound.mCurY + FACTION_ROUND_TO_CENTER * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(4).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX - FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterRound.mCurY + FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(5).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX - FACTION_ROUND_TO_CENTER * getHeight()),
                        (int)(mCenterRound.mCurY),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(6).setSquareAnimDuration(0.3f));
        mRoundList.add(
                new Round(mCenterRound.mCurX, mCenterRound.mCurY,
                        (int)(mCenterRound.mCurX - FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(mCenterRound.mCurY - FACTION_ROUND_TO_CENTER * 0.5f * getHeight()),
                        (int)(0.1f * getHeight()))
                        .setSquareTotalCount(8).setSquareIndex(7).setSquareAnimDuration(0.3f));
        for (Round square : mRoundList) {
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
        mCenterRound.draw(canvas, mPaint);
        for (Round square : mRoundList) {
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
                mCenterRound.mLength = (int) (0.1f * getHeight() + 0.25f * getHeight() * (1 - value));
                for (Round square : mRoundList) {
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


    private class Round {
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

        public Round(int startX, int startY, int endX, int endY, int length) {
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
            canvas.drawCircle(mCurX, mCurY, mLength / 2, paint);
        }

        public void setCurFaction(float faction) {
            if (mActualStart == -1f || mActualEnd == -1f) {
                float perDelayStartTime = mSquareAnimDuration - ((mSquareTotalCount * mSquareAnimDuration - 1f) /
                        (mSquareTotalCount - 1));
                mActualStart = mSquareIndex * perDelayStartTime;
                mActualEnd = mActualStart + mSquareAnimDuration;
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

        public Round setSquareTotalCount(int squareTotalCount) {
            mSquareTotalCount = squareTotalCount;
            return this;
        }

        public Round setSquareIndex(int squareIndex) {
            mSquareIndex = squareIndex;
            return this;
        }

        public Round setSquareAnimDuration(float squareAnimDuration) {
            mSquareAnimDuration = squareAnimDuration;
            return this;
        }
    }
    
}
