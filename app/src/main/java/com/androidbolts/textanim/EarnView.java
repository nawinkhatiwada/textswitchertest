package com.androidbolts.textanim;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by brainovation on 6/23/17.
 */

public class EarnView extends RelativeLayout {
    private static long EARN_TIME = 4000;
    private long earnTime = EARN_TIME;

    private TextView tvTimer;
    private TextSwitcher tvValue;
    private TransitionDrawable timerTransition;
    private LayoutInflater inflater;
    private String startText = "";
    private String completeText = "";
    private CompletionListener completionListener;
    private Animation inAnimation;
    private Animation outAnimation;

    public EarnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this.inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.earn_view, this, true);
            this.tvTimer = (TextView) findViewById(R.id.timer);
            this.tvValue = (TextSwitcher) findViewById(R.id.value);
            tvValue.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    return inflater.inflate(R.layout.value_view, tvValue, false);
                }
            });
            inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_in);
            outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_out);
            tvValue.getLayoutTransition().setStartDelay(LayoutTransition.CHANGE_APPEARING, 800);
            tvValue.getLayoutTransition().setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 800);
            timerTransition = (TransitionDrawable) tvTimer.getBackground();
        }
    }

    public void setEarnTime(long timeInSeconds) {
        this.earnTime = timeInSeconds;
    }

    public void setText(String startText, String completeText) {
        this.startText = startText;
        this.completeText = completeText;
        tvValue.setText(startText);
        tvValue.setInAnimation(inAnimation);
        tvValue.setOutAnimation(outAnimation);
    }

    public void start() {
        final long startTime = System.currentTimeMillis() + earnTime;
        new CountDownTimer(earnTime, 500) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.valueOf(Math.abs(System.currentTimeMillis() - startTime) / 1000));
            }

            public void onFinish() {
                timerTransition.startTransition(500);
                tvTimer.setText("✓");
                tvValue.setText(completeText);
            }
        }.start();
    }

    public void reset() {
        tvValue.setText("Earn Something");
        start();
    }

    public interface CompletionListener {
        void onComplete();
    }
}
