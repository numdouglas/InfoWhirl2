package com.example.user.infowhirl2;

import android.view.View;

import com.example.user.infowhirl2.World.WorldListener;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input;
import GameHose.SpriteBatcher;
import GameHose.Vector2;


/**
 * Created by user on 10/20/2017.
 */

public class WhorlScreen extends GLScreen {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    int state;

    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;

    int lastScore;
    String scoreString;
    public WhorlScreen(Game game) {
        super(game);
        state = GAME_READY;
        guiCam = new Camera2D(glGraphics, 320, 480);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        world = new World(worldListener);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        lastScore = 0;
        scoreString = "score: 0";
    }
    @Override
    public void update(float deltaTime) {

    }
    private void updateReady() {
        if(game.getInput().getTouchEvents().size() > 0) {
            state = GAME_RUNNING;
        }
    }
    private void updateRunning(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type != Input.TouchEvent.TOUCH_UP)
                continue;
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
        }
        world.update(deltaTime, game.getInput().getAccelX());
        if(world.score != lastScore) {
            lastScore = world.score;
            scoreString = "" + lastScore;
        }}

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
        switch(state) {
            case GAME_RUNNING:
                presentRunning();
                break;
        }
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void presentRunning() {
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
