package com.philipstudio.paint.util;/*
//
// Project: Paint
// Created by ViettelStore on 3/13/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class BrushUtil {
    private SharedPreferences preferences;

    public BrushUtil(Context context){
        preferences = context.getSharedPreferences("brush", Context.MODE_PRIVATE);
    }

    public void setBrushSize(float size){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("size", size);
        editor.apply();
    }

    public void setBrushColor(String color){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("color", color);
        editor.apply();
    }

    public float getBrushSize(){
        return preferences.getFloat("size", 2.0f);
    }

    public String getBrushColor(){
        return preferences.getString("color", Integer.toHexString(Color.BLACK));
    }
}
