package com.example.fangyi.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fangyi.mygame.cocos2d.Cocos2dDomeActivity;
import com.example.fangyi.mygame.gamedmoe.GameDemoActivity;
import com.example.fangyi.mygame.map.MapDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
    }

    private Button btnGamedome;
    private Button btnCocos2d;
    private Button btnMap;
    private Button btnStudy;

    private void assignViews() {
        btnGamedome = (Button) findViewById(R.id.btn_gamedome);
        btnCocos2d = (Button) findViewById(R.id.btn_cocos2d);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnStudy = (Button) findViewById(R.id.btn_study);

        btnGamedome.setOnClickListener(this);
        btnCocos2d.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gamedome:
                startActivity(new Intent(this, GameDemoActivity.class));
                break;
            case R.id.btn_cocos2d:
                startActivity(new Intent(this, Cocos2dDomeActivity.class));
                break;
            case R.id.btn_map:
                startActivity(new Intent(this, MapDemoActivity.class));
                break;
        }
    }
}
