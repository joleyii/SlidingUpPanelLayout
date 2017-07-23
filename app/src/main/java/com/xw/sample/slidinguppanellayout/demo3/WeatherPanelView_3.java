package com.xw.sample.slidinguppanellayout.demo3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xw.repo.ISlidingUpPanel;
import com.xw.repo.SlidingUpPanelLayout;
import com.xw.sample.slidinguppanellayout.R;

/**
 * <p/>
 * Created by woxingxiao on 2017-07-10.
 */

public class WeatherPanelView_3 extends BaseWeatherPanelView_3 implements View.OnClickListener {
    LinearLayout panel_content_layout;

    public WeatherPanelView_3(Context context) {
        this(context, null);
    }

    public WeatherPanelView_3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherPanelView_3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.content_weather_panel_view3, this, true);
        panel_content_layout = (LinearLayout) findViewById(R.id.panel_content_layout);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setSlideState(@SlidingUpPanelLayout.SlideState int slideState) {
        super.setSlideState(slideState);

    }

    @Override
    public void onSliding(@NonNull ISlidingUpPanel panel, int top, int dy, float slidedProgress) {
        super.onSliding(panel, top, dy, slidedProgress);

        if (dy < 0) { // 向上

        } else { // 向下

        }
    }

    @Override
    public void setWeatherModel(WeatherModel_3 weather) {
        mWeather = weather;
        if (weather == null)
            return;
    }

    public void setBackColor(Drawable d) {
        panel_content_layout.setBackground(d);
    }

}
