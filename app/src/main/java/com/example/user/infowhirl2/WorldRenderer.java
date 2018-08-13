package com.example.user.infowhirl2;

import GameHose.Camera2D;
import GameHose.GLGraphics;
import GameHose.SpriteBatcher;
import javax.microedition.khronos.opengles.GL10;

public class WorldRenderer {
    SpriteBatcher batcher;
    Camera2D cam;
    GLGraphics glGraphics;
    World world;

    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, 320.0f, 480.0f);
        this.batcher = batcher;
    }

    public void render() {
        this.cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }

    public void renderBackground() {
        GL10 gl = this.glGraphics.getGL();
        this.batcher.beginBatch(Assets.background);
        this.batcher.drawSprite(160.0f, 240.0f, 640.0f, 960.0f, Assets.backgroundRegion);
        this.batcher.endBatch();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.postNew);
        this.batcher.drawSprite(288.0f, 32.0f, 64.0f, 64.0f, Assets.postnewRegion);
        this.batcher.endBatch();
    }

    public void renderObjects() {
        GL10 gl = this.glGraphics.getGL();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.items);
        renderWhirl();
        this.batcher.endBatch();
        gl.glDisable(3042);
    }

    private void renderWhirl() {
        for (int i = 0; i < InfoWhirl.getXxes().size(); i++) {
            this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 64.0f, 64.0f, Assets.whirl.getKeyFrame(((Whirl) this.world.whirls.get(i)).stateTime, 0));
        }
    }
}