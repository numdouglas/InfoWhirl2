package com.example.user.infowhirl2;

import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Animation;
import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.TextureRegion;
import GameHose.Vector2;

/**
 * Created by user on 10/20/2017.
 */

public class MainMenu extends GLScreen {
    Camera2D guiCam;
    static int pos=0;
    float accelxO,accelyO,accelxD,accelyD;
    SpriteBatcher batcher;
    List<Rectangle>postedBounds=new ArrayList<Rectangle>();
    Rectangle postNewBounds,screenBounds;
    Vector2 touchPoint;
    World world;
    World.WorldListener worldListener;
    WorldRenderer renderer;

    public MainMenu(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        batcher = new SpriteBatcher(glGraphics, 100);
        worldListener = new World.WorldListener() {
            public void jump() {
            }
            public void highJump() {

            }
            public void hit() {

            }
            public void coin() {

            }

            @Override
            public void crack() {

            }

        };
        world=new World(worldListener);

        renderer=new WorldRenderer(glGraphics,batcher,world);
        screenBounds=new Rectangle(0,0,320,480);
        postNewBounds = new Rectangle(256,0, 64, 64);//axes start from bottomleft
        if (DetailTaker.xxes.size()>0){
        for (int i=0;i<DetailTaker.xxes.size();i++) {
            postedBounds.add(new Rectangle(DetailTaker.xxes.get(i) - 32, DetailTaker.yyes.get(i) - 32, 64, 64));
        }   }touchPoint = new Vector2();
    }
    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();

        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                if (OverlapTester.pointInRectangle(postNewBounds, touchPoint)) {
                    game.setScreen(new SelectaLocaleScreen(game));
                    return;}

            }else{
           if(event.type == Input.TouchEvent.TOUCH_DOWN&&!OverlapTester.pointInRectangle(postNewBounds,touchPoint)) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                if(DetailTaker.xxes.size()>0){
                for (int b=0;b<DetailTaker.xxes.size();b++){
               if (OverlapTester.pointInRectangle(postedBounds.get(b), touchPoint)) {
                   setPos(b);//pos to be used in Mooscreens query
                   game.setScreen(new MooScreen(game));
                   return;
               }}}
                accelxO=event.x;
                accelyO=event.y;}


            if(event.type == Input.TouchEvent.TOUCH_DRAGGED&&!OverlapTester.pointInRectangle(postNewBounds,touchPoint)) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                if (OverlapTester.pointInRectangle(screenBounds, touchPoint)) {
                    accelxD=event.x;
                    accelyD=event.y;
                    float accelX=accelxD-accelxO;
                    float accelY=accelyD-accelyO;

                    guiCam.position = new Vector2((guiCam.frustumWidth/2)+accelX , (guiCam.frustumHeight/2)+accelY);
                    return;


      }}}} world.update(deltaTime, game.getInput().getAccelX());}
    public void setPos(int po){
        pos=po;
    }
    public static int getPos(){return pos;}
    @Override
    public void present(float deltaTime) {
        TextureRegion keyFrame;
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
      //  renderer.render();
        guiCam.setViewportAndMatrices();
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 640, 960, Assets.backgroundRegion);//the front two args are the middlepoint of the image
        batcher.endBatch();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.postNew);
        batcher.drawSprite(288,32,64,64,Assets.postnewRegion);//coordinates begin from bottom left axis
        batcher.endBatch();
        batcher.beginBatch(Assets.items);
        if (DetailTaker.xxes.size()>0){

        for(int i=0;i<InfoWhirl.events.size();i++) {
            Whirl whirlo = world.whirls.get(i);
            if(whirlo.lives<2){
            keyFrame = Assets.whirl.getKeyFrame(whirlo.stateTime,
                    Animation.ANIMATION_LOOPING);
            batcher.drawSprite(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i), 64, 64, keyFrame);
            }else {
                keyFrame = Assets.superwhirl.getKeyFrame(whirlo.stateTime,
                        Animation.ANIMATION_LOOPING);
                batcher.drawSprite(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i), 64, 64, keyFrame);
            }}}
        batcher.endBatch();
        //gl.glDisable(GL10.GL_BLEND);
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