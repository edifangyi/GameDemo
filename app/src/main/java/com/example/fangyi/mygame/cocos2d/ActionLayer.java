package com.example.fangyi.mygame.cocos2d;

import android.support.annotation.NonNull;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseIn;
import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.interval.CCBezierBy;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.ccColor3B;

/**
 * Created by FANGYI on 2016/8/4.
 */

public class ActionLayer extends CCLayer {
    public ActionLayer() {
        init();
    }

    private void init() {
        moveTo();
        moveBy();
        jumpBy();
        scaleBy();
        rotateBy();
        rotateTo();
        bezierBy();
        fadeIn();
        fadeOut();
        easeIn();
        easeOut();
        tint();
        blink();
        jumpBy_rotateBy();
    }


    /**
     * 跳跃+反转动作
     */
    private void jumpBy_rotateBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(100, 100);
        sprite.setAnchorPoint(0.5f, 0.5f);

        //1：时间 秒 2：目的地 3：（跳墙）高出的高度 4：跳跃的次数
        CCJumpBy ccJumpBy = CCJumpBy.action(4, ccp(400, 100), 100, 2);
        CCRotateBy ccRotateBy = CCRotateBy.action(2, 360);

        //并行动作
        CCSpawn ccSpawn = CCSpawn.actions(ccJumpBy, ccRotateBy);//并行起来了,跳跃的过程中伴随着旋转

        CCSequence sequence = CCSequence.actions(ccSpawn, ccSpawn.reverse());//sequence 跳上去，跳回来（伴随着旋转）

        CCRepeatForever forever = CCRepeatForever.action(sequence);//永不停止循环
        sprite.runAction(forever);
    }

    /**
     * 颜色闪烁
     */
    private void blink() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 0);

        //1:闪烁时间 2：闪烁次数
        CCBlink blink = CCBlink.action(1, 3);
        sprite.runAction(blink);
    }

    /**
     * 颜色渐变
     */
    private void tint() {
        //专门显示文字的精灵
        //1：显示的内存 2：字体的样式 3：字体的大小
        CCLabel label = CCLabel.labelWithString("ヾ(≧O≦)〃嗷~嗷嗷~", "hkbd.ttf", 24);
        label.setColor(ccc3(50, 0, 255));
        label.setPosition(200,200);
        this.addChild(label);

        ccColor3B c = ccColor3B.ccc3(100,255,-100);
        //1：时间 2：变化后的颜色
        CCTintBy by = CCTintBy.action(1, c);
        CCTintBy reverse = by.reverse();
        CCSequence sequence = CCSequence.actions(by, reverse);
        CCRepeatForever forever = CCRepeatForever.action(sequence);
        label.runAction(forever);
    }

    /**
     * 加速度有关系的
     */
    private void easeIn() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 0);

        CCMoveBy ccMoveBy = CCMoveBy.action(5, CCNode.ccp(700, 0));
        //起步越来越快 2：加速度
        CCEaseIn ccEaseIn = CCEaseIn.action(ccMoveBy, 3);//让移动按照有一定加速度移动
        sprite.runAction(ccEaseIn);
    }

    /**
     * 加速度有关系的
     */
    private void easeOut() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 0);

        CCMoveBy ccMoveBy = CCMoveBy.action(5, CCNode.ccp(700, 0));
        //速度越来越慢,刹车 2：加速度
        CCEaseOut ccEaseOut = CCEaseOut.action(ccMoveBy, 3);//让移动按照有一定加速度移动
        sprite.runAction(ccEaseOut);
    }

    /**
     * 淡出
     */
    private void fadeOut() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(550, 400);
        CCFadeOut fadeOut = CCFadeOut.action(5);
        sprite.runAction(fadeOut);
    }

    /**
     * 淡出
     */
    private void fadeIn() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(500, 400);
        CCFadeIn fadeIn = CCFadeIn.action(5);
        sprite.runAction(fadeIn);

    }


    /**
     * 贝塞尔曲线 动作
     */
    private void bezierBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 0);

        CCBezierConfig ccBezierConfig = new CCBezierConfig();
        ccBezierConfig.controlPoint_1 = ccp(300, 300);
        ccBezierConfig.controlPoint_2 = ccp(500, 300);
        ccBezierConfig.endPosition = ccp(450, 300);
        CCBezierBy ccBezierBy = CCBezierBy.action(2, ccBezierConfig);//偷懒的做法
        sprite.runAction(ccBezierBy);
    }

    /**
     * 旋转的动作 To
     */
    private void rotateTo() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(500, 300);

        CCRotateTo ccRotateTo = CCRotateTo.action(2, 270);//偷懒的做法
        CCRepeatForever forever = CCRepeatForever.action(ccRotateTo);//永不停止循环
        sprite.runAction(forever);
    }


    /**
     * 旋转的动作 By
     */
    private void rotateBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(500, 200);

        //2:旋转的角度，顺时针
        CCRotateBy ccRotateBy = CCRotateBy.action(2, 270);
        CCRotateBy reverse = ccRotateBy.reverse();//相反的动作
        CCSequence sequence = CCSequence.actions(ccRotateBy, reverse);//sequence 串行动作
        CCRepeatForever forever = CCRepeatForever.action(sequence);//永不停止循环
        sprite.runAction(forever);
    }

    /**
     * 缩放动作
     */
    private void scaleBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 300);

        //1：时间 秒 2：缩放比例
        CCScaleBy ccScaleBy = CCScaleBy.action(0.5f, 0.5f); //基于锚点缩放
        CCScaleBy reverse = ccScaleBy.reverse();//相反的动作
        CCSequence sequence = CCSequence.actions(ccScaleBy, reverse);//sequence 串行动作
        CCRepeatForever forever = CCRepeatForever.action(sequence);//永不停止循环
        sprite.runAction(forever);
    }

    /**
     * 跳跃动作
     */
    private void jumpBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 200);

        //1：时间 秒 2：目的地 3：（跳墙）高出的高度 4：跳跃的次数
        CCJumpBy ccJumpBy = CCJumpBy.action(2, ccp(400, 100), 100, 2);
        CCJumpBy reverse = ccJumpBy.reverse();//相反的动作
        CCSequence sequence = CCSequence.actions(ccJumpBy, reverse);//sequence 串行动作
        CCRepeatForever forever = CCRepeatForever.action(sequence);//永不停止循环
        sprite.runAction(forever);
    }

    /**
     * 移动 By
     * 有相反动作
     */
    private void moveBy() {
        CCSprite sprite = getCcSprite();
        sprite.setPosition(0, 100);
        //参数1 移动的时间 单位 秒， 参数2 移动的坐标的改变
        CCMoveBy ccMoveBy = CCMoveBy.action(5, CCNode.ccp(700, 0));
        CCMoveTo reverse = ccMoveBy.reverse();//相反的动作
        CCSequence sequence = CCSequence.actions(ccMoveBy, reverse);//sequence 串行动作

        sprite.runAction(sequence);
    }

    /**
     * 移动 To
     * 没有相反动作
     */
    private void moveTo() {
        CCSprite sprite = getCcSprite();

        //参数1 移动的时间 单位 秒， 参数2 移动到指定目的地
        CCMoveTo ccMoveTo = CCMoveTo.action(5, CCNode.ccp(700, 0));
        sprite.runAction(ccMoveTo);
    }

    /**
     * 创建一个精灵
     *
     * @return
     */
    @NonNull
    private CCSprite getCcSprite() {
        CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
        sprite.setAnchorPoint(0, 0);
        this.addChild(sprite);
        return sprite;
    }

    @NonNull
    private CCSprite getHeartSprite() {
        CCSprite sprite = CCSprite.sprite("heart.png");
        sprite.setAnchorPoint(0, 0);
        this.addChild(sprite);
        return sprite;
    }
}
