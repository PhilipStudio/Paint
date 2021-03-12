package com.philipstudio.paint.view;/*
//
// Project: Paint
// Created by ViettelStore on 3/11/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GeometryView extends View {

    Paint paint;
    final int colorPaint = Color.BLACK;

    public GeometryView(Context context) {
        super(context);
    }

    public GeometryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setUpPaint();
    }

    private void setUpPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(colorPaint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
}
