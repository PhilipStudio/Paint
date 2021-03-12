package com.philipstudio.paint;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.philipstudio.paint.ui.ListFileActivity;
import com.philipstudio.paint.ui.SettingActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;

public class MainActivity extends AppCompatActivity implements ColorPickerView.OnColorChangedListener {

    PhotoEditorView photoEditorView;
    ImageView imgUndo, imgBrush, imgEarse, imgTools, imgMenu, imgLayers, imgColor,
            imgRedo, imgChecked, imgSetting, imgGeometry;
    LinearLayout linearLayoutColor, layoutTools;
    BottomSheetBehavior<LinearLayout> bottomSheetBehaviorColor;
    ColorPickerView colorPickerView;
    EditText edtHex, edtR, edtG, edtB, edtA;
    Button btnRed500, btnPink500, btnPurple500, btnDeepPurple500, btnIndigo500, btnBlue500, btnLightBlue500, btnCyan500,
            btnTeal500, btnGreen500, btnLightGreen500, btnLime500, btnYellow500, btnAmber500, btnOrange500, btnBrown500;

    PhotoEditor photoEditor;
    GestureDetector gestureDetector;
    String color = "#FF000000";
    float size = 2.0f;
    int SWIPE_THRESHOLD = 100;
    int SWIPE_VELOCITY_THRESHOLD = 100;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        setUpPhotoEditor();

        Intent intent = getIntent();
        if (intent != null) {
            String path = intent.getStringExtra("path");
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                photoEditorView.getSource().setImageURI(Uri.fromFile(file));
            }
        }

        imgEarse.setOnClickListener(listener);
        imgBrush.setOnClickListener(listener);
        imgRedo.setOnClickListener(listener);
        imgUndo.setOnClickListener(listener);
        imgColor.setOnClickListener(listener);
        imgChecked.setOnClickListener(listener);
        imgMenu.setOnClickListener(listener);
        btnBlue500.setOnClickListener(listener);
        btnIndigo500.setOnClickListener(listener);
        btnLightBlue500.setOnClickListener(listener);
        btnPink500.setOnClickListener(listener);
        btnPurple500.setOnClickListener(listener);
        btnRed500.setOnClickListener(listener);
        btnCyan500.setOnClickListener(listener);
        btnDeepPurple500.setOnClickListener(listener);
        btnTeal500.setOnClickListener(listener);
        btnGreen500.setOnClickListener(listener);
        btnLightGreen500.setOnClickListener(listener);
        btnLime500.setOnClickListener(listener);
        btnYellow500.setOnClickListener(listener);
        btnAmber500.setOnClickListener(listener);
        btnOrange500.setOnClickListener(listener);
        btnBrown500.setOnClickListener(listener);
        imgGeometry.setOnClickListener(listener);

        colorPickerView.setOnColorChangedListener(this);

        layoutTools.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(MainActivity.this, "X: " + event.getRawX() + ", Y: " + event.getRawY(), Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
            }
            return true;
        });

        photoEditorView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_view_earse:
                    photoEditor.brushEraser();
                    break;
                case R.id.image_view_brush:
                    imgChecked.setVisibility(View.GONE);
                    setUpDrawing(color, size);
                    break;
                case R.id.image_view_undo:
                    photoEditor.undo();
                    break;
                case R.id.image_view_redo:
                    photoEditor.redo();
                    break;
                case R.id.image_view_color:
                case R.id.image_view_checked:
                    bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
                    break;
                case R.id.image_view_menu:
                    showPopUpMenu();
                    break;
                case R.id.image_view_setting:
                    Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_md_blue500:
                    setColor(getColor(R.color.md_blue_500));
                    break;
                case R.id.button_md_cyan500:
                    setColor(getColor(R.color.md_cyan_500));
                    break;
                case R.id.button_md_deep_purple500:
                    setColor(getColor(R.color.md_deep_purple_500));
                    break;
                case R.id.button_md_indigo500:
                    setColor(getColor(R.color.md_indigo_500));
                    break;
                case R.id.button_md_light_blue500:
                    setColor(getColor(R.color.md_light_blue_500));
                    break;
                case R.id.button_md_red500:
                    setColor(getColor(R.color.md_red_500));
                    break;
                case R.id.button_md_pink500:
                    setColor(getColor(R.color.md_pink_500));
                    break;
                case R.id.button_md_teal500:
                    setColor(getColor(R.color.md_teal_500));
                    break;
                case R.id.button_md_green500:
                    setColor(getColor(R.color.md_green_500));
                    break;
                case R.id.button_md_light_green500:
                    setColor(getColor(R.color.md_light_green_500));
                    break;
                case R.id.button_md_lime500:
                    setColor(getColor(R.color.md_lime_500));
                    break;
                case R.id.button_md_orange500:
                    setColor(getColor(R.color.md_orange_500));
                    break;
                case R.id.button_md_amber500:
                    setColor(getColor(R.color.md_amber_500));
                    break;
                case R.id.button_md_yellow500:
                    setColor(getColor(R.color.md_yellow_500));
                    break;
                case R.id.button_brown500:
                    setColor(getColor(R.color.md_brown_500));
                    break;
            }
        }
    };

    private void setColor(int color) {
        bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
        imgChecked.setVisibility(View.VISIBLE);
        imgChecked.setBackgroundColor(color);
        photoEditor.setBrushColor(color);
        colorPickerView.setColor(color);
        edtHex.setText(Integer.toHexString(color).toUpperCase());
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = (color) & 0xFF;
        edtR.setText(String.valueOf(red));
        edtG.setText(String.valueOf(green));
        edtB.setText(String.valueOf(blue));
    }

    private void setUpPhotoEditor() {
        Typeface mTypeFace = ResourcesCompat.getFont(this, R.font.roboto_medium);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(mTypeFace)
                .build();

        setUpDrawing(color, size);
    }

    private void setUpDrawing(String color, float size) {
        photoEditor.clearAllViews();
        photoEditor.setBrushDrawingMode(true);
        photoEditor.setBrushColor(Color.parseColor(color));
        photoEditor.setBrushSize(size);
    }

    @SuppressLint("NonConstantResourceId")
    private void showPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(this, imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_app, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.new_file:
                    setUpDrawing(color, size);
                    break;
                case R.id.open_file:
                    Intent openIntent = new Intent(MainActivity.this, ListFileActivity.class);
                    startActivity(openIntent);
                    break;
                case R.id.save_file:
                    showDialogSaveFile();
                    break;
                case R.id.setting:
                    Intent settingIntent = new Intent(this, SettingActivity.class);
                    startActivity(settingIntent);
                    break;
            }
            return false;
        });
    }

    private void showDialogSaveFile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Có phải bạn muốn lưu dự án này ?");
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Có", (dialog, which) -> Dexter.withContext(MainActivity.this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            String nameFile = System.currentTimeMillis() + ".jpg";
                            saveFileDraw(nameFile);
                        } else {
                            Toast.makeText(MainActivity.this, "Bạn cần cấp quyền để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        Toast.makeText(MainActivity.this, "Bạn cần cấp quyền để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                    }
                }).check());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveFileDraw(String name) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Paint/";
        File directoryFile = new File(path);
        if (!directoryFile.exists()) {
            directoryFile.mkdir();
        }

        File file = new File(directoryFile, name);
        try {
            file.createNewFile();
            SaveSettings saveSettings = new SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(true)
                    .build();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            photoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                @Override
                public void onSuccess(@NonNull String imagePath) {
                    Toast.makeText(MainActivity.this, imagePath, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        photoEditorView = findViewById(R.id.photo_editor_view);
        imgUndo = findViewById(R.id.image_view_undo);
        imgTools = findViewById(R.id.image_view_tools);
        imgBrush = findViewById(R.id.image_view_brush);
        imgMenu = findViewById(R.id.image_view_menu);
        imgLayers = findViewById(R.id.image_view_layers);
        imgEarse = findViewById(R.id.image_view_earse);
        imgRedo = findViewById(R.id.image_view_redo);
        imgColor = findViewById(R.id.image_view_color);
        linearLayoutColor = findViewById(R.id.linear_layout_color);
        colorPickerView = findViewById(R.id.color_picker_view);
        edtR = findViewById(R.id.edit_text_red);
        edtA = findViewById(R.id.edit_text_alpha);
        edtB = findViewById(R.id.edit_text_blue);
        edtG = findViewById(R.id.edit_text_green);
        edtHex = findViewById(R.id.edit_text_hex);
        layoutTools = findViewById(R.id.layout_tools);
        imgChecked = findViewById(R.id.image_view_checked);
        imgChecked.setVisibility(View.GONE);
        imgMenu = findViewById(R.id.image_view_menu);
        imgSetting = findViewById(R.id.image_view_setting);
        imgGeometry = findViewById(R.id.image_view_geometry);

        btnBlue500 = findViewById(R.id.button_md_blue500);
        btnCyan500 = findViewById(R.id.button_md_cyan500);
        btnDeepPurple500 = findViewById(R.id.button_md_deep_purple500);
        btnRed500 = findViewById(R.id.button_md_red500);
        btnPurple500 = findViewById(R.id.button_md_purple500);
        btnPink500 = findViewById(R.id.button_md_pink500);
        btnLightBlue500 = findViewById(R.id.button_md_light_blue500);
        btnIndigo500 = findViewById(R.id.button_md_indigo500);

        btnTeal500 = findViewById(R.id.button_md_teal500);
        btnGreen500 = findViewById(R.id.button_md_green500);
        btnLightGreen500 = findViewById(R.id.button_md_light_green500);
        btnLime500 = findViewById(R.id.button_md_lime500);
        btnYellow500 = findViewById(R.id.button_md_yellow500);
        btnAmber500 = findViewById(R.id.button_md_amber500);
        btnOrange500 = findViewById(R.id.button_md_orange500);
        btnBrown500 = findViewById(R.id.button_brown500);

        bottomSheetBehaviorColor = BottomSheetBehavior.from(linearLayoutColor);
        bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_HIDDEN);

        gestureDetector = new GestureDetector(this, new MyGesture());
    }

    @Override
    public void onColorChanged(int newColor) {
        setColor(newColor);
    }

    class MyGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(MainActivity.this, "Left to Right", Toast.LENGTH_SHORT).show();
            }
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(MainActivity.this, "Right to Left", Toast.LENGTH_SHORT).show();
            }
            if (e2.getY() - e1.getY() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(MainActivity.this, "Top to Bottom : " + e2.getY() + ", " + e1.getY(), Toast.LENGTH_SHORT).show();
            }
            if (e1.getY() - e2.getY() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                Toast.makeText(MainActivity.this, "Bottom to Top: " + e2.getY() + ", " + e1.getY(), Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}