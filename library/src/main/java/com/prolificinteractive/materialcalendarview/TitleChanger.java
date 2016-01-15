package com.prolificinteractive.materialcalendarview;

import android.animation.Animator;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

class TitleChanger {

    public static final int DEFAULT_ANIMATION_DELAY = 400;
    public static final int DEFAULT_Y_TRANSLATION_DP = 20;

    private final TextView title;
    private TitleFormatter titleFormatter;

    private final int animDelay;
    private final int animDuration;
    private final int yTranslate;
    private final Interpolator interpolator = new DecelerateInterpolator(2f);

    private long lastAnimTime = 0;
    private CalendarDay previousMonth = null;

    public TitleChanger(TextView title) {
        this.title = title;

        Resources res = title.getResources();

        animDelay = DEFAULT_ANIMATION_DELAY;

        animDuration = res.getInteger(android.R.integer.config_shortAnimTime) / 2;

        yTranslate = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Y_TRANSLATION_DP, res.getDisplayMetrics()
        );
    }

    public void change(final CalendarDay currentMonth) {
        long currentTime = System.currentTimeMillis();

        if (currentMonth == null) {
            return;
        }

        if (TextUtils.isEmpty(title.getText()) || (currentTime - lastAnimTime) < animDelay) {
            doChange(currentTime, currentMonth, false);
        }

        if (currentMonth.equals(previousMonth) || currentMonth.getMonth() == previousMonth.getMonth()) {
            return;
        }

        doChange(currentTime, currentMonth, true);
    }

    private void doChange(final long now, final CalendarDay currentMonth, boolean animate) {

        title.animate().cancel();
        title.setTranslationY(0);
        title.setAlpha(1);
        lastAnimTime = now;

        final CharSequence newTitle = titleFormatter.format(currentMonth);

        if (!animate) {
            title.setText(newTitle);
        } else {
            final int yTranslation = yTranslate * (previousMonth.isBefore(currentMonth) ? 1 : -1);

            title.animate()
                    .translationY(yTranslation * -1)
                    .alpha(0)
                    .setDuration(animDuration)
                    .setInterpolator(interpolator)
                    .setListener(new AnimatorListener() {

                        @Override
                        public void onAnimationCancel(Animator animator) {
                            title.setTranslationY(0);
                            title.setAlpha(1);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            title.setText(newTitle);
                            title.setTranslationY(yTranslation);
                            title.animate()
                                    .translationY(0)
                                    .alpha(1)
                                    .setDuration(animDuration)
                                    .setInterpolator(interpolator)
                                    .setListener(new AnimatorListener())
                                    .start();
                        }
                    }).start();
        }

        previousMonth = currentMonth;
    }

    public TitleFormatter getTitleFormatter() {
        return titleFormatter;
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

    public void setPreviousMonth(CalendarDay previousMonth) {
        this.previousMonth = previousMonth;
    }
}
