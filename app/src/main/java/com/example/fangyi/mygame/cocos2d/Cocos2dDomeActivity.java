package com.example.fangyi.mygame.cocos2d;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * Created by FANGYI on 2016/8/3.
 */

public class Cocos2dDomeActivity extends AppCompatActivity {

    private CCDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        //创建了程序中唯一的导演
        director = CCDirector.sharedDirector();
        director.attachInView(surfaceView);//开启线程

        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//设置游戏方向水平
        director.setDisplayFPS(true);//是否展示帧率
        director.setAnimationInterval(1.0f/30);//控制锁定帧率，向下锁定
        director.setScreenSize(1024, 615);//设置屏幕的大小，可以自动屏幕适配 开启线程之后

        CCScene ccScene = CCScene.node();//为了api和cocos-iphone 一致
        ccScene.addChild(new FirstLayer());//场景添加图层
        ccScene.addChild(new ActionLayer());//场景添加图层


        director.runWithScene(ccScene);//运行场景

    }

    /**
     * 导演生命周期和Activity声明周期绑定在一起
     */
    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        director.onPause();//没区别
        director.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();//游戏结束
    }
}
