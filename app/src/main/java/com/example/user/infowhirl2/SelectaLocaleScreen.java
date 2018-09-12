package com.example.user.infowhirl2;

import Hose.Camera2D;
import Hose.GLScreen;
import Hose.Game;
import Hose.Input.TouchEvent;
import Hose.OverlapTester;
import Hose.Rectangle;
import Hose.SpriteBatcher;
import Hose.Vector2;
import android.content.Intent;
import android.os.Looper;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class SelectaLocaleScreen extends GLScreen {
    static int XPlace = 0;
    static int YPlace = 0;
    static InfoWhirl jool;
    static String s_course;
    static String s_hobby;
    SpriteBatcher batcher = new SpriteBatcher(this.glGraphics, 100);
    Camera2D guiCam = new Camera2D(this.glGraphics, InfoWhirl.getScreenWidth(), InfoWhirl.getScreenHeight());
    Rectangle nextBounds = new Rectangle(256.0f,0.0f, 64.0f, 64.0f);
    Rectangle screenBounds = new Rectangle(0.0f, 0.0f, InfoWhirl.getScreenWidth(), InfoWhirl.getScreenHeight());
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
        if (XPlace > (InfoWhirl.getScreenWidth()*2)) {
            XPlace = InfoWhirl.getScreenWidth();
        }
        if (YPlace < 0) {
            YPlace = 0;
        }
        if (YPlace > InfoWhirl.getScreenHeight()*2) {
            YPlace =InfoWhirl.getScreenHeight();
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
                        /*this.accelxO = (float) event.x;
                        this.accelyO = (float) event.y;*/
                        return;
                    }
                }
                if (event.type == 2) {
                    this.touchPoint.set((float) event.x, (float) event.y);
                    this.guiCam.touchToWorld(this.touchPoint);
                    if (OverlapTester.pointInRectangle(this.screenBounds, this.touchPoint)) {
                      /*  this.accelxD = (float) event.x;
                        this.accelyD = (float) event.y;
                        float accelX = this.accelxD - this.accelxO;
                        float accelY = this.accelyD - this.accelyO;
                        this.guiCam.position = new Vector2((this.guiCam.frustumWidth / 2.0f) + accelX, (this.guiCam.frustumHeight / 2.0f) + accelY);
                       */ return;
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
        this.guiCam.position = new Vector2((float) XPlace, (float) YPlace);
        gl.glEnable(3553);
        this.batcher.beginBatch(Assets.background);
        this.batcher.drawSprite(InfoWhirl.getScreenWidth()/2, InfoWhirl.getScreenHeight()/2, InfoWhirl.getScreenWidth()*4,       InfoWhirl.getScreenHeight()*4, Assets.backgroundRegion);
        this.batcher.endBatch();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.items);
        this.batcher.drawSprite((float) XPlace, (float) YPlace,64.0f, 64.0f, Assets.whirlS);
        this.batcher.drawSprite((float) XPlace+((InfoWhirl.getScreenWidth()/2)-32), (float) YPlace-((InfoWhirl.getScreenHeight()/2)-32), 64.0f, 64.0f, Assets.next);

        //Move the next button with the movement of the selector
        nextBounds.lowerLeft.x=(float) XPlace+((InfoWhirl.getScreenWidth()/2)-48);//-48
        nextBounds.lowerLeft.y=(float) YPlace-((InfoWhirl.getScreenHeight()/2)-16);//-16
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