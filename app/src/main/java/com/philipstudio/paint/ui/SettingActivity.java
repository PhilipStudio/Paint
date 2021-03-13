package com.philipstudio.paint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.philipstudio.paint.R;

public class SettingActivity extends AppCompatActivity {

    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        btnOk.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.youtube.com/channel/UCH6tg43n_FG5xZHlCfN360A"));
            startActivity(intent);
        });
    }

    private void initView(){
        btnOk = findViewById(R.id.button_ok);
    }
}