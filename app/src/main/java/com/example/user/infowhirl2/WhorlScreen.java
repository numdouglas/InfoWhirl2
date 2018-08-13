package com.example.user.infowhirl2;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input.TouchEvent;
import GameHose.SpriteBatcher;
import GameHose.Vector2;
import com.example.user.infowhirl2.World.WorldListener;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class WhorlScreen extends GLScreen {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    SpriteBatcher batcher = new SpriteBatcher(this.glGraphics, 1000);
    Camera2D guiCam = new Camera2D(this.glGraphics, 320.0f, 480.0f);
    int lastScore = 0;
    WorldRenderer renderer = new WorldRenderer(this.glGraphics, this.batcher, this.world);
    String scoreString = "score: 0";
    int state = 0;
    Vector2 touchPoint = new Vector2();
    World world = new World(this.worldListener);
    WorldListener worldListener;

    public WhorlScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
    }

    private void updateReady() {
        if (this.game.getInput().getTouchEvents().size() > 0) {
            this.state = 1;
        }
    }

    private void updateRunning(float deltaTime) {
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == 1) {
                this.touchPoint.set((float) event.x, (float) event.y);
                this.guiCam.touchToWorld(this.touchPoint);
            }
        }
        this.world.update(deltaTime, this.game.getInput().getAccelX());
        if (this.world.score != this.lastScore) {
            this.lastScore = this.world.score;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.lastScore);
            this.scoreString = stringBuilder.toString();
        }
    }

    public void present(float deltaTime) {
        GL10 gl = this.glGraphics.getGL();
        gl.glClear(16384);
        gl.glEnable(3553);
        this.renderer.render();
        this.guiCam.setViewportAndMatrices();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.items);
        if (this.state == 1) {
            presentRunning();
        }
        this.batcher.endBatch();
        gl.glDisable(3042);
    }

    private void presentRunning() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}