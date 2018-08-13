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
import android.os.Bundle;
import com.example.user.infowhirl2.World.WorldListener;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class MainMenu extends GLScreen {
    static int pos = 0;
    private float accelX;
    private float accelY;
    private float accelxD;
    private float accelxO;
    private float accelyD;
    private float accelyO;
    SpriteBatcher batcher = new SpriteBatcher(this.glGraphics, 100);
    Camera2D guiCam = new Camera2D(this.glGraphics, 320.0f, 480.0f);
    Rectangle postNewBounds;
    List<Rectangle> postedBounds = new ArrayList();
    Rectangle reloadBounds;
    WorldRenderer renderer;
    Rectangle screenBounds;
    Vector2 touchPoint;
    World world = new World(this.worldListener);
    WorldListener worldListener = new C02741();

    class C02741 implements WorldListener {
        C02741() {
        }

        public void jump() {
        }

        public void highJump() {
        }

        public void hit() {
        }

        public void coin() {
        }

        public void crack() {
        }
    }

    public MainMenu(Game game) {
        super(game);
        InfoWhirl.isHome = true;
        this.renderer = new WorldRenderer(this.glGraphics, this.batcher, this.world);
        this.screenBounds = new Rectangle(0.0f, 0.0f, 320.0f, 480.0f);
        this.postNewBounds = new Rectangle(256.0f, 0.0f, 64.0f, 64.0f);
        this.reloadBounds = new Rectangle(128.0f, 416.0f, 64.0f, 64.0f);
        if (InfoWhirl.getYyes().size() > 0) {
            for (int i = 0; i < InfoWhirl.getYyes().size(); i++) {
                this.postedBounds.add(new Rectangle((float) (((Integer) InfoWhirl.getXxes().get(i)).intValue() - 32), (float) (((Integer) InfoWhirl.getYyes().get(i)).intValue() - 32), 64.0f, 64.0f));
            }
        }
        this.touchPoint = new Vector2();
    }

    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            if (event.type == 1) {
                this.touchPoint.set((float) event.x, (float) event.y);
                this.guiCam.touchToWorld(this.touchPoint);
                if (OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
                    dispose();
                    this.game.setScreen(new SelectaLocaleScreen(this.game));
                    return;
                }
            } else {
                if (event.type == 0 && !OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
                    this.touchPoint.set((float) event.x, (float) event.y);
                    this.guiCam.touchToWorld(this.touchPoint);
                    if (InfoWhirl.getYyes().size() > 0) {
                        for (int b = 0; b < InfoWhirl.getYyes().size(); b++) {
                            if (OverlapTester.pointInRectangle((Rectangle) this.postedBounds.get(b), this.touchPoint)) {
                                setPos(b);
                                dispose();
                                this.game.setScreen(new MooScreen(this.game));
                                return;
                            }
                        }
                    }
                    this.accelxO = (float) event.x;
                    this.accelyO = (float) event.y;
                }
                if (event.type == 0 && OverlapTester.pointInRectangle(this.reloadBounds, this.touchPoint)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("HOBBY", InfoWhirl.u_hobby);
                    bundle.putString("COURSE", InfoWhirl.u_course);
                    bundle.putInt("TRINKETS", InfoWhirl.u_trinkets);
                    bundle.putInt("DATE", InfoWhirl.u_date);
                    bundle.putString("NAME", InfoWhirl.u_name);
                    bundle.putString("PASSKEY", InfoWhirl.u_pass);
                    Intent info = new Intent(this.glGame, InfoWhirl.class);
                    info.putExtras(bundle);
                    this.glGame.finish();
                    this.glGame.startActivity(info);
                }
                if (event.type == 2 && !OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
                    this.touchPoint.set((float) event.x, (float) event.y);
                    this.guiCam.touchToWorld(this.touchPoint);
                    if (OverlapTester.pointInRectangle(this.screenBounds, this.touchPoint)) {
                        this.accelxD = (float) event.x;
                        this.accelyD = (float) event.y;
                        this.accelX = this.accelxD - this.accelxO;
                        this.accelY = this.accelyD - this.accelyO;
                        this.guiCam.position = new Vector2((this.guiCam.frustumWidth / 2.0f) + this.accelX, (this.guiCam.frustumHeight / 2.0f) + this.accelY);
                        return;
                    }
                }
            }
        }
        this.world.update(deltaTime, this.game.getInput().getAccelX());
    }

    public void setPos(int po) {
        pos = po;
    }

    public static int getPos() {
        return pos;
    }

    public void present(float deltaTime) {
        GL10 gl = this.glGraphics.getGL();
        gl.glClear(16384);
        this.guiCam.setViewportAndMatrices();
        gl.glEnable(3553);
        this.guiCam.setViewportAndMatrices();
        this.batcher.beginBatch(Assets.background);
        this.batcher.drawSprite(160.0f, 240.0f, 640.0f, 960.0f, Assets.backgroundRegion);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.reload);
        this.batcher.drawSprite(this.accelX + 160.0f, this.accelY + 448.0f, 64.0f, 64.0f, Assets.reloadregion);
        this.batcher.endBatch();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.postNew);
        this.batcher.drawSprite(this.accelX + 288.0f, this.accelY + 32.0f, 64.0f, 64.0f, Assets.postnewRegion);
        this.postNewBounds.lowerLeft.x = this.accelX + 256.0f;
        this.postNewBounds.lowerLeft.y = this.accelY + 0.0f;
        this.reloadBounds.lowerLeft.x = this.accelX + 128.0f;
        this.reloadBounds.lowerLeft.y = this.accelY + 416.0f;
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.items);
        if (InfoWhirl.getYyes().size() > 0) {
            for (int i = 0; i < InfoWhirl.getYyes().size(); i++) {
                try {
                    Whirl whirlo = (Whirl) this.world.whirls.get(i);
                    if ((((Integer) InfoWhirl.getUpVotes().get(i)).intValue() - ((Integer) InfoWhirl.getDownvotes().get(i)).intValue()) / 2 < 2) {
                        dispose();
                        this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 64.0f, 64.0f, Assets.whirl.getKeyFrame(whirlo.stateTime, 0));
                    } else {
                        dispose();
                        this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 64.0f, 64.0f, Assets.superwhirl.getKeyFrame(whirlo.stateTime, 0));
                    }
                } catch (IndexOutOfBoundsException e) {
                    this.game.setScreen(new MainMenu(this.glGame));
                }
            }
        }
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