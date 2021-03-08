package com.philipstudio.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class MainActivity extends AppCompatActivity {

    PhotoEditorView photoEditorView;
    ImageView imgUndo, imgBrush, imgEarse, imgTools, imgMenu, imgLayers, imgColors;

    PhotoEditor photoEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        setUpPhotoEditor();

    }

    private void setUpPhotoEditor(){
        Typeface mTypeFace = ResourcesCompat.getFont(this, R.font.roboto_medium);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(mTypeFace)
                .build();

        photoEditor.setBrushDrawingMode(true);
        photoEditor.setBrushColor(Color.parseColor("#00000000"));
    }

    private void initView(){
        photoEditorView = findViewById(R.id.photo_editor_view);
        imgUndo = findViewById(R.id.image_view_undo);
        imgTools = findViewById(R.id.image_view_tools);
        imgBrush = findViewById(R.id.image_view_brush);
        imgMenu = findViewById(R.id.image_view_menu);
        imgLayers = findViewById(R.id.image_view_layers);
    }
}