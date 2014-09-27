package com.android.football.interfaces;

import android.graphics.Canvas;

import org.jbox2d.dynamics.Body;

/**
 * Created by Mihail on 25.09.14.
 */
public interface IFigureDrawable {

    public void draw(Canvas canvas, Body body);

    public Body getFigureBody();
}
