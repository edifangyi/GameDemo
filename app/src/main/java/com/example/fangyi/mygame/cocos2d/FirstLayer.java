package com.example.fangyi.mygame.cocos2d;

import android.view.MotionEvent;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * 图层
 * Created by FANGYI on 2016/8/3.
 */

public class FirstLayer extends CCLayer {

    private CCSprite sprite;

    public FirstLayer() {
        setIsTouchEnabled(true);//打开图层触摸事件开关
        init();
    }


    /**
     * 按下的事件,现在叫 TouchesBegan
     * <p>
     *
     *     .
     * setIsTouchEnabled(true);//打开图层触摸事件开关
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        //先把Android坐标系中的点 转换成 cocos2d坐标系中的点
        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        CGRect boudingBox = sprite.getBoundingBox();//获取精灵的矩形

        //判断点是否在矩形之中
        //参数1：矩形 参数2：点
        boolean containsPoint = CGRect.containsPoint(boudingBox, cgPoint);
        if (containsPoint) {
            sprite.setFlipX(true);
        }
//        this.getChildByTag(10);//通过tag标签找对应的孩子
        return super.ccTouchesBegan(event);
    }


    /**
     * 移动的方法
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        return super.ccTouchesMoved(event);
    }

    /**
     * 抬起的方法
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        return super.ccTouchesEnded(event);
    }

    /**
     * 触动取消
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesCancelled(MotionEvent event) {
        return super.ccTouchesCancelled(event);
    }

    public void init() {
        //背景
        CCSprite bg = CCSprite.sprite("bbg_arena.jpg");
        bg.setAnchorPoint(0, 0);
        this.addChild(bg, 0);//优先级：如果第二个参数越大，默认显示的越靠上面，如果一样大，谁先添加，谁在下面

        //接收的参数 就是精灵显示的图片
        sprite = CCSprite.sprite("z_1_attack_01.png");

        /**
         * Cocos2d 的坐标就是正常坐标，左下角为(0,0)点
         */
        sprite.setAnchorPoint(0, 0);//设置锚点
        sprite.setPosition(200, 200);//设置坐标
        sprite.setScale(1.5);//缩放比例，X，Y轴变2倍
//        sprite.setFlipX(true);//水平反转
//        sprite.setFlipY(true);//垂直反转
//        sprite.setOpacity(150);//设置不透明度，值越大，越不透明 0-255
//        sprite.setVisible(false);//设置不可显示
        //把精力添加到图层上
        this.addChild(sprite);
//        this.addChild(sprite, z);//优先级
//        this.addChild(sprite, 1, 10);//参数3：标签
    }
}
