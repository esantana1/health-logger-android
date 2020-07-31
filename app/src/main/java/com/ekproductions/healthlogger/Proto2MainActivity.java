package com.ekproductions.healthlogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Proto2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proto2_main);

        RecyclerView list = (RecyclerView) findViewById(R.id.breakfastList);

    }
}
