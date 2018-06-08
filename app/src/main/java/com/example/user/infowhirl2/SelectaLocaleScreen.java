package com.example.user.infowhirl2;

import android.content.Intent;
import android.os.Looper;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input;
import GameHose.KeyboardHandler;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.Vector2;

/**
 * Created by user on 10/20/2017.
 */

public class SelectaLocaleScreen extends GLScreen {
    Camera2D guiCam;
    static int XPlace=90,YPlace=90;
    SpriteBatcher batcher;

    float accelxO,accelyO,accelxD,accelyD;
    static InfoWhirl jool;
    Rectangle nextBounds,whirlBounds,screenBounds;
    Vector2 touchPoint;

    public SelectaLocaleScreen(Game game){
        super(game);

        Looper.prepare();
        jool=new InfoWhirl();
        guiCam = new Camera2D(glGraphics, 320, 480);
        nextBounds = new Rectangle(
                256, 0, 64, 64);
        screenBounds=new Rectangle(0,0,320,480);
        whirlBounds=new Rectangle(XPlace,YPlace,64,64);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 100);
    }






    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        if(XPlace<0){XPlace=0;}
        if(XPlace>640){XPlace=320;}
        if(YPlace<0){YPlace=0;}
        if(YPlace>960){YPlace=480;}
        XPlace=XPlace+(int)game.getInput().getAccelX();
        YPlace=YPlace+(int)game.getInput().getAccelY();

        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                if (OverlapTester.pointInRectangle(nextBounds,touchPoint)) {
                    Intent togo=new Intent(glGame,ToGo.class);
                    glGame.finish();
                    glGame.startActivity(togo);

                }

                    if (OverlapTester.pointInRectangle(whirlBounds,touchPoint)) {
                    XPlace=XPlace+(int)game.getInput().getAccelX();
                    YPlace=YPlace+(int)game.getInput().getAccelY();
                    return;
                }
                if (OverlapTester.pointInRectangle(screenBounds, touchPoint)) {
                    accelxO=event.x;
                    accelyO=event.y;
                    return;

                }}
            if(event.type == Input.TouchEvent.TOUCH_DRAGGED) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                if (OverlapTester.pointInRectangle(screenBounds, touchPoint)) {
                    accelxD=event.x;
                    accelyD=event.y;
                    float accelX=accelxD-accelxO;
                    float accelY=accelyD-accelyO;

                    guiCam.position = new Vector2((guiCam.frustumWidth/2)+accelX , (guiCam.frustumHeight/2)+accelY);
                    return;


                }}}}

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 640, 960, Assets.backgroundRegion);
        batcher.endBatch();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        batcher.drawSprite(XPlace, YPlace, 64, 64, Assets.whirlS);
        batcher.drawSprite(288,32,64,64,Assets.next);
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
