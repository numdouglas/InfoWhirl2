package com.example.user.infowhirl2;

import android.graphics.Rect;
import android.view.KeyEvent;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.Vector2;

/**
 * Created by user on 11/1/2017.
 */

public class MooScreen extends GLScreen {
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;
    WorldRenderer renderer;
    Whirl whirlo;
    World world;
    World.WorldListener worldListener;
    Rectangle backbounds,veryBounds,nullyBounds;
    DetailTaker detass;
    public MooScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        touchPoint = new Vector2();
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
        backbounds=new Rectangle(0,0,64,64);
        veryBounds=new Rectangle(288,260,32,32);
        nullyBounds=new Rectangle(288,164,32,32);
        worldListener = new World.WorldListener() {
            @Override
            public void jump() {

            }

            @Override
            public void highJump() {

            }

            @Override
            public void hit() {

            }

            @Override
            public void coin() {

            }

            @Override
            public void crack() {

            }
        };
        world = new World(worldListener);
        batcher = new SpriteBatcher(glGraphics, 100);
        renderer = new WorldRenderer(glGraphics, batcher, world);
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
                if(OverlapTester.pointInRectangle(backbounds, touchPoint)) {
                    game.setScreen(new MainMenu(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(veryBounds,touchPoint)){
                    world.whirls.get(MainMenu.getPos()).lives++;
                }
                if(OverlapTester.pointInRectangle(nullyBounds,touchPoint)){}
                world.whirls.get(MainMenu.getPos()).lives--;


            }}}

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        renderer.render();
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        batcher.drawSprite(32,32,64,64,Assets.back);
        batcher.endBatch();

        batcher.beginBatch(Assets.feelButtons);
        batcher.drawSprite(304,276,32,32,Assets.veryButton);
        batcher.drawSprite(304,180,32,32,Assets.nullyButton);
        batcher.endBatch();

        batcher.beginBatch(Assets.texs);try {
            Assets.font.drawText(batcher, InfoWhirl.getEventsString().get(MainMenu.getPos()), InfoWhirl.getEventsString().get(MainMenu.getPos()).length(), 480 - 20);
            Assets.font.drawText(batcher, InfoWhirl.getDetailsString().get(MainMenu.getPos()), InfoWhirl.getDetailsString().get(MainMenu.getPos()).length(), 480 - 80);

            Assets.font.drawText(batcher, "TIME:: " + InfoWhirl.getTimes().get(MainMenu.getPos()) + " Hrs", InfoWhirl.getDetailsString().get(MainMenu.getPos()).length(), 480 - 160);
        }catch (NullPointerException d){//activity must be given values
           Assets.font.drawText(batcher,"null",0,0);
            Assets.font.drawText(batcher,"null",0,0);
            Assets.font.drawText(batcher,"null",0,0);

        }batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);}
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