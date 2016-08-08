package com.example.fangyi.mygame.map;

import android.view.MotionEvent;

import com.example.fangyi.mygame.R;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FANGYI on 2016/8/4.
 */

public class DemoLayer extends CCLayer {


    private CCTMXTiledMap map;
    private List<CGPoint> cgPointList;//地图中点的集合
    int postion = 0;//僵尸要走的第一个点
    private String filepath;
    private CCSprite sprite;//僵尸
    private CCParticleSystem system;


    public DemoLayer() {
        this.setIsTouchEnabled(true);//打开触摸开发
        init();
    }

    private void init() {
        //加载地图
        loadMap();
        //解析地图
        parserMap();
        //粒子系统
        loadParticle();
        //需要一个僵尸
        loadZombies();

    }

    /**
     * 粒子系统
     */
    private void loadParticle() {
        //创建粒子系统
        //创建的是雪花粒子系统
        system = CCParticleSnow.node();
//        CCParticleSystem system = CCParticleFire.node();//创建的是着火粒子系统
//        CCParticleSystem system = CCParticleSmoke.node();//创建的是冒烟粒子系统

        //设置雪花的样式
        system.setTexture(CCTextureCache.sharedTextureCache().addImage("f.png"));

        this.addChild(system, 1);

        //自定义粒子系统 使用ParticleDesigner，导出的文件放里面
//        CCParticleSystem ps = CCParticleSystemPoint.particleWithFile("f.plist");
//        ps.setPosition(0, winSize.height);
//        addChild(ps);
    }

    /**
     * 展示僵尸
     */
    private void loadZombies() {
        sprite = CCSprite.sprite("z_1_01.png");
        sprite.setPosition(cgPointList.get(0));
        sprite.setAnchorPoint(0.4f, 0.1f);
        sprite.setScale(0.65f);
        sprite.setFlipX(true);
        map.addChild(sprite);//通过地图添加僵尸，如果地图随着手指移动，那么僵尸随着地图移动

        animateZombies();
        moveToNext();

    }

    int speed = 40;//僵尸的速度

    /**
     * 僵尸移动
     */
    public void moveToNext() {
        postion++;
        if (postion < cgPointList.size()) {
            CGPoint cgPoint = cgPointList.get(postion);
            float t = CGPointUtil.distance(cgPointList.get(postion - 1), cgPoint) / speed;
            CCMoveTo moveTo = CCMoveTo.action(t, cgPoint);//匀速

            CCSequence ccSequence = CCSequence.actions(moveTo,
                    CCCallFunc.action(this, "moveToNext"));//调用当前某一对象的方法,方法是公有，反射

            sprite.runAction(ccSequence);
        } else {
            //雪停下来，移动完成了，
            system.stopSystem();//停止粒子系统
            sprite.stopAllActions();//停止所有动作
            //跳舞
            dance();
            //音乐
            music();
        }

    }

    /**
     * 声音引擎
     */
    private void music() {
        SoundEngine engine = SoundEngine.sharedEngine();
        //1.上下文，2.音乐资源 3.是否循环播放
        engine.playSound(CCDirector.theApp, R.raw.psy, true);
    }

    /**
     * 复杂的动作
     */
    private void dance() {
        sprite.setAnchorPoint(0.5f, 0.5f);
        CCJumpBy ccJumpBy = CCJumpBy.action(2, ccp(-20, 10), 10, 2);
        CCRotateBy ccRotateBy = CCRotateBy.action(1, 360);
        CCSpawn ccSpawn = CCSpawn.actions(ccJumpBy, ccRotateBy);

        CCSequence sequence = CCSequence.actions(ccSpawn, ccSpawn.reverse());
        CCRepeatForever forver = CCRepeatForever.action(sequence);
        sprite.runAction(forver);

    }

    /**
     * 帧动画，僵尸行走的样子
     */
    private void animateZombies() {
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        filepath = "z_1_%02d.png";//02d占位符，可以表示两位的证书，如果不足两位前面可以用0补足
        for (int i = 1; i <= 7; i++) {
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(filepath, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }

        //序列帧的播放
        //配置序列帧的信息 1：动作的名字（给程序员看的）2:每一帧播放的时间 单位秒 3：所有用到的帧
        CCAnimation animation = CCAnimation.animation("走路", 0.2f, frames);
        CCAnimate animate = CCAnimate.action(animation);
        //序列帧动作默认是永不停止的循环
        CCRepeatForever forever = CCRepeatForever.action(animate);
        sprite.runAction(forever);
    }


    /**
     * 加载地图
     */
    private void loadMap() {

        map = CCTMXTiledMap.tiledMap("map.tmx");//默认锚点0，0
        map.setAnchorPoint(0.5f, 0.5f);
        //因为修改了锚点，所以坐标也需要修改
        map.setPosition(map.getContentSize().width / 2, map.getContentSize().height / 2);
        this.addChild(map);
    }

    /**
     * 解析地图
     */
    private void parserMap() {
        cgPointList = new ArrayList<>();

        CCTMXObjectGroup objectGroup = map.objectGroupNamed("road");
        ArrayList<HashMap<String, String>> objects = objectGroup.objects;
        for (HashMap<String, String> object : objects) {
            int x = Integer.parseInt(object.get("x"));
            int y = Integer.parseInt(object.get("y"));
            CGPoint cgPoint = ccp(x, y);
            cgPointList.add(cgPoint);
        }
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        map.touchMove(event, map);//地图随着手指的移动而移动 如果该方法生效，必须保证地图的锚点在中间位置
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        //先把Android坐标系中的点 转换成 cocos2d坐标系中的点
        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        CGRect boudingBox = sprite.getBoundingBox();//获取精灵的矩形

        //判断点是否在矩形之中
        //参数1：矩形 参数2：点
        boolean containsPoint = CGRect.containsPoint(boudingBox, cgPoint);
        if (containsPoint) {
            this.onExit();//暂停
            this.getParent().addChild(new PauseLayer());//让场景添加新的图层
        }

        return super.ccTouchesBegan(event);
    }

    /**
     * 新的图层
     */
    private class PauseLayer extends CCLayer {

        private final CCSprite bilibili;

        public PauseLayer() {
            this.setIsTouchEnabled(true);

            bilibili = CCSprite.sprite("z_1_attack_01.png");
            //获取屏幕的尺寸
            CGSize winSize = CCDirector.sharedDirector().getWinSize();
            bilibili.setPosition(winSize.width / 2, winSize.height / 2);//在屏幕的中间
            this.addChild(bilibili);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {

        //先把Android坐标系中的点 转换成 cocos2d坐标系中的点
        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        CGRect boudingBox = bilibili.getBoundingBox();//获取精灵的矩形

        //判断点是否在矩形之中
        //参数1：矩形 参数2：点
        boolean containsPoint = CGRect.containsPoint(boudingBox, cgPoint);
        if (containsPoint) {
            this.removeSelf();//收回当前图层
            DemoLayer.this.onEnter();//游戏继续
        }
            return super.ccTouchesBegan(event);
        }
    }

}
