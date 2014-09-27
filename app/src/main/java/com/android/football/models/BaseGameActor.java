package com.android.football.models;

import android.content.Context;
import android.util.DisplayMetrics;

import com.android.football.interfaces.IFigureDrawable;

import org.jbox2d.dynamics.Body;

/**
 * Created by Mihail on 25.09.14.
 */

public abstract class BaseGameActor implements IFigureDrawable {

    protected Body body;

    protected float dpHeight;

    protected float dpWidth;

    public BaseGameActor(Context context){
        super();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        dpHeight = displayMetrics.heightPixels;
        dpWidth = displayMetrics.widthPixels;
    }

    @Override
    public Body getFigureBody() {
        return body;
    }
}
