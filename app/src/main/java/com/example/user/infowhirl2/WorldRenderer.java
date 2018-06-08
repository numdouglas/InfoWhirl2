package com.example.user.infowhirl2;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Animation;
import GameHose.Camera2D;
import GameHose.GLGraphics;
import GameHose.SpriteBatcher;
import GameHose.TextureRegion;

/**
 * Created by user on 10/20/2017.
 */

public class WorldRenderer {
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;

    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, 320, 480);
        this.batcher = batcher;
    }

    public void render() {
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }

    public void renderBackground() {//background will always be rendered so i follow camera
        GL10 gl = glGraphics.getGL();
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 640, 960, Assets.backgroundRegion);//the front two args are the middlepoint of the image
        batcher.endBatch();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.postNew);
        batcher.drawSprite(288,32,64,64,Assets.postnewRegion);//coordinates begin from bottom left axis
        batcher.endBatch();
    }

    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        renderWhirl();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderWhirl() {
        TextureRegion keyFrame;
        for(int i=0;i<InfoWhirl.events.size();i++){
            Whirl whirlo=world.whirls.get(i);

        keyFrame = Assets.whirl.getKeyFrame(
                  whirlo.stateTime, Animation.ANIMATION_LOOPING);
               batcher.drawSprite(DetailTaker.xxes.get(i),DetailTaker.yyes.get(i),64,64,keyFrame);
      //  batcher.drawSprite(world.whirl.position.x, world.whirl.position.y,  1, 1, keyFrame);
    }}
}