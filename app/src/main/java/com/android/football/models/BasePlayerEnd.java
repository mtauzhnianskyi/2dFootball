package com.android.football.models;

import android.graphics.Paint;

/**
 * Created by Mihail on 25.09.14.
 */
public abstract class BasePlayerEnd {

    private float endWidth;

    private float endHeight;

    private Paint endColor;

    public BasePlayerEnd(float w, float h, Paint endColor){
        endWidth = w;
        endHeight = h;
        this.endColor = endColor;
    }

    public float getEndWidth() {
        return endWidth;
    }

    public void setEndWidth(float endWidth) {
        this.endWidth = endWidth;
    }

    public float getEndHeight() {
        return endHeight;
    }

    public void setEndHeight(float endHeight) {
        this.endHeight = endHeight;
    }

    public Paint getEndColor() {
        return endColor;
    }

    public void setEndColor(Paint endColor) {
        this.endColor = endColor;
    }
}
