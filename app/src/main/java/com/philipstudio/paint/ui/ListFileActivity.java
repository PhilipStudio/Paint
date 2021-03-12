package com.philipstudio.paint.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.philipstudio.paint.MainActivity;
import com.philipstudio.paint.R;
import com.philipstudio.paint.adapter.ListFileAdapter;
import com.philipstudio.paint.util.LayoutUtil;

import java.io.File;
import java.util.ArrayList;

public class ListFileActivity extends AppCompatActivity {

    TextView txtNamePath;
    RecyclerView rVListFile;
    ImageView imgMenu, imgChangeLayout;

    LayoutUtil layoutUtil;
    boolean isChangeLayout;
    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Paint/";
    ListFileAdapter fileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_file);

        initView();

        txtNamePath.setText(path);
        imgChangeLayout.setOnClickListener(listener);
        imgMenu.setOnClickListener(listener);
        isChangeLayout = layoutUtil.getChangeLayout();
        setUpRecyclerViewListFile();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_view_change_layout:
                    imgChangeLayout.setImageResource(R.drawable.ic_baseline_notes);
                    isChangeLayout = layoutUtil.getChangeLayout();
                    layoutUtil.setChangeLayout(!isChangeLayout);
                    break;
                case R.id.image_view_menu:
                    Toast.makeText(ListFileActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void setUpRecyclerViewListFile(){
        isChangeLayout = layoutUtil.getChangeLayout();
        rVListFile.setHasFixedSize(true);
        if (!isChangeLayout){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rVListFile.setLayoutManager(linearLayoutManager);
        }
        else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            rVListFile.setLayoutManager(gridLayoutManager);
        }

        File file = new File(path);
        ArrayList<File> arrayList = getListFile(file);

        fileAdapter = new ListFileAdapter(arrayList, this);
        rVListFile.setAdapter(fileAdapter);

        fileAdapter.setOnItemFileClickListener(path -> {
            Intent intent = new Intent(ListFileActivity.this, MainActivity.class);
            intent.putExtra("path", path);
            startActivity(intent);
            finish();
        });
    }

    private ArrayList<File> getListFile(File directoryFile){
        ArrayList<File> fileArrayList = new ArrayList<>();
        File[] listFile = directoryFile.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.getName().endsWith(".jpg")) {
                    fileArrayList.add(file);
                }
            }
        }
        return fileArrayList;
    }

    public void onBack(View view){
        finish();
    }

    private void initView(){
        txtNamePath = findViewById(R.id.text_view_name_path);
        rVListFile = findViewById(R.id.recyclerview_list_file);
        imgMenu = findViewById(R.id.image_view_menu);
        imgChangeLayout = findViewById(R.id.image_view_change_layout);

        layoutUtil = new LayoutUtil(this);
    }
}