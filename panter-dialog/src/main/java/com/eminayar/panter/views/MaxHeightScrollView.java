package com.eminayar.panter.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.eminayar.panter.R;

public class MaxHeightScrollView extends ScrollView {

private int maxHeight;
private final int defaultHeight = 200;

public MaxHeightScrollView(Context context) {
    super(context);
}

public MaxHeightScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
        init(context, attrs);
    }
}

public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (!isInEditMode()) {
        init(context, attrs);
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    if (!isInEditMode()) {
        init(context, attrs);
    }
}

private void init(Context context, AttributeSet attrs) {
    if (attrs != null) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
        //200 is a defualt value
        maxHeight = styledAttrs.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, defaultHeight);

        styledAttrs.recycle();
    }
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
}
}