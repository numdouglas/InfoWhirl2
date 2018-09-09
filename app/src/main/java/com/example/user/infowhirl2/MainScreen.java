package com.example.user.infowhirl2;

import Hose.AndroidInput;
import Hose.Camera2D;
import Hose.GLScreen;
import Hose.Game;
import Hose.Input.TouchEvent;
import Hose.OverlapTester;
import Hose.Rectangle;
import Hose.SpriteBatcher;
import Hose.Vector2;
import android.content.Intent;
import android.os.Bundle;

import com.example.user.infowhirl2.World.WorldListener;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class MainScreen extends GLScreen {
    static int pos = 0;
    int zoomfactor=10;
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

        public void upvote(){}
        public void downvote(){}
    }
//constructor
    public MainScreen(Game game) {
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



//handle events on the screen

    public void update(float deltaTime) {


//zoomin, pass an increased value to place the camera closer
        if(glGame.getInput().isKeyPressed(24)){
            zoomfactor++;
            guiCam.zoom = (float) (((double) zoomfactor) * 0.1d);
            }
//zoomout  pass an increased value to place the camera further
        if(glGame.getInput().isKeyPressed(25) && zoomfactor > 1){
            zoomfactor--;
            guiCam.zoom = (float) (((double) zoomfactor) * 0.1d);
        }


/*Load the pool of touch events. These will be loaded into ana array and the oldest automatically emptied by the recycler
whn the array is full. This dynamic property is what enables the ability of this Hose.TouchEvent class to handle multitouch
and drag events
 */
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = (TouchEvent) touchEvents.get(i);
            //Hnadle the action where a finger touches and then leaves the touchscreen
            if (event.type == TouchEvent.TOUCH_UP) {
                this.touchPoint.set((float) event.x, (float) event.y);
                this.guiCam.touchToWorld(this.touchPoint);
                if (OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
                    dispose();
                    this.game.setScreen(new SelectaLocaleScreen(this.game));
                    return;
                }
            } else {
                //Handle the action where a finger touches the screen and stays
                if (event.type == TouchEvent.TOUCH_DOWN && !OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
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
                //Handle the event where a finger touches a screen and stays and is within the bounds of the reload button
                if (event.type == TouchEvent.TOUCH_DOWN && OverlapTester.pointInRectangle(this.reloadBounds, this.touchPoint)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("HOBBY", InfoWhirl.u_hobby);
                    bundle.putString("COURSE", InfoWhirl.u_course);
                    bundle.putInt("TRINKETS", InfoWhirl.u_trinkets);
                    bundle.putInt("DATE", InfoWhirl.u_date);
                    bundle.putString("NAME", InfoWhirl.u_name);
                    bundle.putString("PASSKEY", InfoWhirl.u_pass);
                    Intent info = new Intent(this.glGame, InfoWhirl.class);
                    info.putExtras(bundle);
                    dispose();
                    this.glGame.finish();
                    this.glGame.startActivity(info);
                }
                //Handle the event where a finger touches a screen and stays and is within the bounds of the postNew button
                if (event.type == TouchEvent.TOUCH_DRAGGED && !OverlapTester.pointInRectangle(this.postNewBounds, this.touchPoint)) {
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
//set the position of the whirl in the array
    public void setPos(int po) {
        pos = po;
    }

    public static int getPos() {
        return pos;
    }
//update the screen
    public void present(float deltaTime) {
        GL10 gl = this.glGraphics.getGL();
        gl.glClear(16384);
        this.guiCam.setViewportAndMatrices();
        gl.glEnable(3553);
        this.guiCam.setViewportAndMatrices();
        this.batcher.beginBatch(Assets.background);
        this.batcher.drawSprite(160.0f, 240.0f, 640.0f, 960.0f, Assets.backgroundRegion);
        this.batcher.endBatch();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.reload);
        this.batcher.drawSprite(this.accelX+ 160.0f, (this.accelY +((this.zoomfactor-10)*16))+ 448.0f, 64.0f, 64.0f, Assets.reloadregion);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.postNew);
        this.batcher.drawSprite(this.accelX + 288.0f+(((this.zoomfactor-10)*16)), this.accelY + 32.0f-(((this.zoomfactor-10)*16)), 64.0f, 64.0f, Assets.postnewRegion);
       /*The zoomfactor is at an original value of 10, we'll decrease it by 10 and multiply by the distance variance
       factor of 16
        */
        this.postNewBounds.lowerLeft.x = this.accelX + 256.0f+((this.zoomfactor-10)*16);
        this.postNewBounds.lowerLeft.y = this.accelY + 0.0f-((this.zoomfactor-10)*16);
        this.reloadBounds.lowerLeft.x = this.accelX + 128.0f;
        this.reloadBounds.lowerLeft.y = this.accelY + 416.0f+((this.zoomfactor-10)*16);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.birdy);
        if (InfoWhirl.getYyes().size() > 0) {
            int avg_votes;
//render the representation of the whirls based on popularity. Behavior varies based on upvotes and downvotes
            for (int i = 0; i < InfoWhirl.getYyes().size(); i++) {
                try {
                    Whirl whirlo = (Whirl) this.world.whirls.get(i);
                    avg_votes=Math.round(((Integer) InfoWhirl.getUpVotes().get(i)).intValue() - ((Integer) InfoWhirl.getDownvotes().get(i)).intValue() / 2);

                    if (avg_votes<= 2 || ((Integer) InfoWhirl.getUpVotes().get(i)).intValue()<=7) {
                        dispose();
                        this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 50.0f, 50.0f, Assets.whirl.getKeyFrame(whirlo.stateTime, 0));
                    }
                    if((avg_votes>2)&&((Integer) InfoWhirl.getUpVotes().get(i)).intValue()>7&&((Integer) InfoWhirl.getUpVotes().get(i)).intValue()<=20){
                        dispose();
                        this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 50.0f, 50.0f, Assets.superwhirl.getKeyFrame(whirlo.stateTime, 0));
                    }
                    if((avg_votes>4)&&((Integer) InfoWhirl.getUpVotes().get(i)).intValue()>20){
                        dispose();
                        this.batcher.drawSprite((float) ((Integer) InfoWhirl.getXxes().get(i)).intValue(), (float) ((Integer) InfoWhirl.getYyes().get(i)).intValue(), 50.0f, 50.0f, Assets.ultimatewhirl.getKeyFrame(whirlo.stateTime, 0));
                    }
                } catch (IndexOutOfBoundsException e) {
                    this.game.setScreen(new MainScreen(this.glGame));
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