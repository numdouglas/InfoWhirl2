package com.example.user.infowhirl2;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input.TouchEvent;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.Vector2;
import android.content.Intent;
import android.os.Looper;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class SelectaLocaleScreen extends GLScreen {
    static int XPlace = 90;
    static int YPlace = 90;
    static InfoWhirl jool;
    static String s_course;
    static String s_hobby;
    float accelxD;
    float accelxO;
    float accelyD;
    float accelyO;
    SpriteBatcher batcher = new SpriteBatcher(this.glGraphics, 100);
    Camera2D guiCam = new Camera2D(this.glGraphics, 320.0f, 480.0f);
    Rectangle nextBounds = new Rectangle(256.0f, 0.0f, 64.0f, 64.0f);
    Rectangle screenBounds = new Rectangle(0.0f, 0.0f, 320.0f, 480.0f);
    Vector2 touchPoint = new Vector2();
    Rectangle whirlBounds = new Rectangle((float) XPlace, (float) YPlace, 64.0f, 64.0f);

    public SelectaLocaleScreen(Game game) {
        super(game);
        Looper.prepare();
        jool = new InfoWhirl();
        s_course = InfoWhirl.getU_course();
        s_hobby = InfoWhirl.getU_hobby();
        InfoWhirl.isHome = false;
    }

    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();
        int len = touchEvents.size();
        int i = 0;
        if (XPlace < 0) {
            XPlace = 0;
        }
        if (XPlace > 640) {
            XPlace = 320;
        }
        if (YPlace < 0) {
            YPlace = 0;
        }
        if (YPlace > 960) {
            YPlace = 480;
        }
        XPlace += (int) this.game.getInput().getAccelX();
        YPlace += (int) this.game.getInput().getAccelY();
        while (true) {
            int i2 = i;
            if (i2 < len) {
                TouchEvent event = (TouchEvent) touchEvents.get(i2);
                this.touchPoint.set((float) event.x, (float) event.y);
                this.guiCam.touchToWorld(this.touchPoint);
                if (event.type == 0) {
                    if (OverlapTester.pointInRectangle(this.nextBounds, this.touchPoint)) {
                        this.glGame.finish();
                        this.glGame.startActivity(new Intent(this.glGame, ToGo.class));
                    }
                    if (OverlapTester.pointInRectangle(this.whirlBounds, this.touchPoint)) {
                        XPlace += (int) this.game.getInput().getAccelX();
                        YPlace += (int) this.game.getInput().getAccelY();
                        return;
                    } else if (OverlapTester.pointInRectangle(this.screenBounds, this.touchPoint)) {
                        this.accelxO = (float) event.x;
                        this.accelyO = (float) event.y;
                        return;
                    }
                }
                if (event.type == 2) {
                    this.touchPoint.set((float) event.x, (float) event.y);
                    this.guiCam.touchToWorld(this.touchPoint);
                    if (OverlapTester.pointInRectangle(this.screenBounds, this.touchPoint)) {
                        this.accelxD = (float) event.x;
                        this.accelyD = (float) event.y;
                        float accelX = this.accelxD - this.accelxO;
                        float accelY = this.accelyD - this.accelyO;
                        this.guiCam.position = new Vector2((this.guiCam.frustumWidth / 2.0f) + accelX, (this.guiCam.frustumHeight / 2.0f) + accelY);
                        return;
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void present(float deltaTime) {
        GL10 gl = this.glGraphics.getGL();
        gl.glClear(16384);
        this.guiCam.setViewportAndMatrices();
        gl.glEnable(3553);
        this.batcher.beginBatch(Assets.background);
        this.batcher.drawSprite(160.0f, 240.0f, 640.0f, 960.0f, Assets.backgroundRegion);
        this.batcher.endBatch();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.items);
        this.batcher.drawSprite((float) XPlace, (float) YPlace, 64.0f, 64.0f, Assets.whirlS);
        this.batcher.drawSprite(288.0f, 32.0f, 64.0f, 64.0f, Assets.next);
        this.batcher.endBatch();
        gl.glDisable(3042);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}