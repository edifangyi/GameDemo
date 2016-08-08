package com.example.fangyi.mygame.gamedmoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by FANGYI on 2016/8/3.
 */

public class GameDemoActivity extends AppCompatActivity {
    private GameUI gameUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //因为当前GameUI 继承SurfaceView 实现了SurfaceHolder.Callback
        gameUI = new GameUI(getApplicationContext());
        //所以在主线程中调用SurfaceView 和 SurfaceHolder.Callback
        setContentView(gameUI);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameUI.handlerTouch(event);//点击事件进行转移
        return super.onTouchEvent(event);
    }
}
