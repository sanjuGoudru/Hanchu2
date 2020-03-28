package com.example.hanchu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<Boolean> mImages=new ArrayList<>();
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mPaths=new ArrayList<>();
    private RecyclerView recyclerView;
    private File currentDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_view);
        Log.d(TAG, "TEST: onCreate: started");
        initData();
    }

    private void initData(){
        currentDir = new File(this.getExternalFilesDir(null).getAbsolutePath()+File.separator+"Hanchu");
        boolean dirCreated = currentDir.mkdir();
        if(dirCreated)
            Log.d(TAG, "TEST: onCreate: currentDir is created. Path:"+currentDir.getAbsolutePath());
        else
            Log.d(TAG, "TEST: onCreate: currentDir is not created. Path:"+currentDir.getAbsolutePath());
        fill(currentDir);
    }
    private void fill(File f) {
        //Log.d(TAG, "TEST: fill: Starting");
        File[]dirs = f.listFiles();
        //if(dirs==null){
        //    Log.d(TAG, "TEST: fill: dirs[] is null");
        //}else if(dirs.length==0){
        //    Log.d(TAG, "TEST: fill: dirs[] length is 0");
        //}else{
        //    Log.d(TAG, "TEST: fill: dirs[] is good");
        //}
        //Log.d(TAG, "TEST: fill: f name:"+f.getName());
        this.setTitle("Current Dir: "+f.getName());
        try{
            for(File ff: dirs)
            {
                mNames.add(ff.getName());
                mPaths.add(ff.getAbsolutePath());

                if(ff.isDirectory()){
                    mImages.add(false);
                }
                else
                {
                    mImages.add(true);
                }
            }
        }catch(Exception e)
        {
            Log.d(TAG, "TEST: fill: Exception:"+e.getMessage());
        }
        setRecyclerView();
    }
    private void setRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mImages,mNames,mPaths);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
