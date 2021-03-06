package com.xw.sample.slidinguppanellayout.demo3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.xw.repo.ISlidingUpPanel;
import com.xw.repo.SlidingUpPanelLayout;

import static com.xw.repo.SlidingUpPanelLayout.COLLAPSED;

/**
 * <p/>
 * Created by woxingxiao on 2017-07-10.
 */

public abstract class BaseWeatherPanelView_3 extends CardView implements ISlidingUpPanel<BaseWeatherPanelView_3> {

    protected static int MAX_RADIUS;

    protected int mExpendedHeight;
    protected int mFloor; // 由下至上第几层，最高三层
    protected int mPanelHeight;
    protected int mRealPanelHeight;
    @SlidingUpPanelLayout.SlideState
    protected int mSlideState = COLLAPSED;
    protected float mSlope; // 斜率

    protected WeatherModel_3 mWeather;

    public BaseWeatherPanelView_3(Context context) {
        this(context, null);
    }

    public BaseWeatherPanelView_3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseWeatherPanelView_3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        MAX_RADIUS = dp2px(16);
        setRadius(MAX_RADIUS);
        ViewCompat.setElevation(this, dp2px(16));
        setCardBackgroundColor(Color.TRANSPARENT);
    }

    public void setFloor(int floor) {
        mFloor = floor;
        mRealPanelHeight = 0;
    }

    public int getFloor() {
        return mFloor;
    }

    public void setPanelHeight(int panelHeight) {
        mPanelHeight = panelHeight;
    }

    public int getRealPanelHeight() {
        if (mRealPanelHeight == 0)
            mRealPanelHeight = mFloor * mPanelHeight;

        return mRealPanelHeight;
    }

    @NonNull
    @Override
    public BaseWeatherPanelView_3 getPanelView() {
        return this;
    }

    @Override
    public int getPanelExpandedHeight() {
        if (mExpendedHeight == 0)
            mExpendedHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        return mExpendedHeight;
    }

    @Override
    public int getPanelCollapsedHeight() {
        return getRealPanelHeight();
    }

    @Override
    @SlidingUpPanelLayout.SlideState
    public int getSlideState() {
        return mSlideState;
    }

    @Override
    public int getPanelTopBySlidingState(@SlidingUpPanelLayout.SlideState int slideState) {
        if (slideState == SlidingUpPanelLayout.EXPANDED) {
            return 0;
        } else if (slideState == COLLAPSED) {
            return getPanelExpandedHeight() - getPanelCollapsedHeight();
        } else if (slideState == SlidingUpPanelLayout.HIDDEN) {
            return getPanelExpandedHeight();
        }
        return 0;
    }

    @Override
    public void setSlideState(@SlidingUpPanelLayout.SlideState int slideState) {
        mSlideState = slideState;

        if (mSlideState != SlidingUpPanelLayout.EXPANDED) {
            mSlope = 0;
        }
    }

    @Override
    public void onSliding(@NonNull ISlidingUpPanel panel, int top, int dy, float slidedProgress) {
        if (panel != this) {
            int myTop = (int) (getPanelExpandedHeight() + getSlope(((BaseWeatherPanelView_3) panel).getRealPanelHeight()) * top);
            setTop(myTop);
        }
    }

    public float getSlope(int slidingViewRealHeight) {
        if (mSlope == 0) {
            mSlope = -1.0f * getRealPanelHeight() / (getPanelExpandedHeight() - slidingViewRealHeight);
        }

        return mSlope;
    }

    public abstract void setWeatherModel(WeatherModel_3 weather);

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        ss.mSavedSlideState = mSlideState;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mSlideState = ss.mSavedSlideState;
    }

    private static class SavedState extends BaseSavedState {
        int mSavedSlideState;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mSavedSlideState = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mSavedSlideState);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<BaseWeatherPanelView_3.SavedState>() {
                    @Override
                    public BaseWeatherPanelView_3.SavedState createFromParcel(Parcel in) {
                        return new BaseWeatherPanelView_3.SavedState(in);
                    }

                    @Override
                    public BaseWeatherPanelView_3.SavedState[] newArray(int size) {
                        return new BaseWeatherPanelView_3.SavedState[size];
                    }
                };
    }

    int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
