package com.tripdazzle.daycation.ui.activityselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.tripdazzle.daycation.R;

public class ActivityNumberIcon extends androidx.appcompat.widget.AppCompatImageView {

    private static final int[] STATE_ACTIVITY_1 = {R.attr.state_activity_1};
    private static final int[] STATE_ACTIVITY_2 = {R.attr.state_activity_2};
    private static final int[] STATE_ACTIVITY_3 = {R.attr.state_activity_3};

    private int activityNumber;

    public ActivityNumberIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.activityNumberIcon,
                -1, 0);
        activityNumber = a.getColor(R.styleable.activityNumberIcon_activityNumber, -1);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (activityNumber == 1) {
            mergeDrawableStates(drawableState, STATE_ACTIVITY_1);
            return drawableState;
        } else if (activityNumber == 2) {
            mergeDrawableStates(drawableState, STATE_ACTIVITY_2);
            return drawableState;
        } else if (activityNumber == 3) {
            mergeDrawableStates(drawableState, STATE_ACTIVITY_3);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);

        }
    }
}
