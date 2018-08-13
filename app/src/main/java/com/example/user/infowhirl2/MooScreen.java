package com.example.user.infowhirl2;

import GameHose.Camera2D;
import GameHose.Font;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input.TouchEvent;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.Vector2;
import android.os.AsyncTask;
import android.util.Log;
import com.example.user.infowhirl2.World.WorldListener;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

public class MooScreen extends GLScreen {
    static boolean MoreClicked = false;
    private static final String TAG_SUCCESS = "success";
    static boolean backclicked;
    private static String url_set_bucket = "http://10.42.0.1/infowhirl/set_bucket.php";
    private static String url_vote = "http://10.42.0.1/infowhirl/update_votes.php";
    static World world;
    int OpinionY;
    Rectangle backbounds;
    SpriteBatcher batcher;
    int downvotes;
    Camera2D guiCam;
    JSONParser jParser = new JSONParser();
    Rectangle nullyBounds;
    int nullys,bucketedU,bucketedD;
    int opinionX;
    WorldRenderer renderer;
    Vector2 touchPoint;
    int upvotes;
    Rectangle veryBounds;
    int verys;
    WorldListener worldListener;

    class UpdateBucket extends AsyncTask<String, String, String> {
        UpdateBucket() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> paramsV = new ArrayList();
            paramsV.add(new BasicNameValuePair("name", InfoWhirl.u_name));
            paramsV.add(new BasicNameValuePair("passkey", InfoWhirl.u_pass));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(InfoWhirl.u_date);
            paramsV.add(new BasicNameValuePair("last_voted", stringBuilder.toString()));
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(InfoWhirl.u_trinkets);
            paramsV.add(new BasicNameValuePair("trinkets", stringBuilder.toString()));
            try {
                if (MooScreen.this.jParser.makeHttpRequest(MooScreen.url_set_bucket, HttpPost.METHOD_NAME, paramsV).getInt(MooScreen.TAG_SUCCESS) == 1) {
                    Log.d("Opinion", "Bucket updated");
                } else {
                    Log.d("Opinion", "Ooops! No postman");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
        }
    }

    class UpdateVotes extends AsyncTask<String, String, String> {
        UpdateVotes() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MooScreen.this.verys);
            stringBuilder.append("");
            params.add(new BasicNameValuePair("V", stringBuilder.toString()));
            stringBuilder = new StringBuilder();
            stringBuilder.append(MooScreen.this.nullys);
            stringBuilder.append("");
            params.add(new BasicNameValuePair("N", stringBuilder.toString()));
            stringBuilder = new StringBuilder();
            stringBuilder.append(MooScreen.this.opinionX);
            stringBuilder.append("");
            params.add(new BasicNameValuePair("coords_x", stringBuilder.toString()));
            stringBuilder = new StringBuilder();
            stringBuilder.append(MooScreen.this.OpinionY);
            stringBuilder.append("");
            params.add(new BasicNameValuePair("coords_y", stringBuilder.toString()));
            try {
                if (MooScreen.this.jParser.makeHttpRequest(MooScreen.url_vote, HttpPost.METHOD_NAME, params).getInt(MooScreen.TAG_SUCCESS) == 1) {
                    Log.d("Opinion", "Now they've heard");
                } else {
                    Log.d("Opinion", "Ooops! No postman");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
        }
    }

    class C02751 implements WorldListener {
        C02751() {
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

    class C02762 implements WorldListener {
        C02762() {
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

    public MooScreen(Game game) {
        super(game);
        MoreClicked = true;
        this.guiCam = new Camera2D(this.glGraphics, 320.0f, 480.0f);
        this.touchPoint = new Vector2();
        this.worldListener = new C02751();
        world = new World(this.worldListener);
        this.backbounds = new Rectangle(0.0f, 0.0f, 64.0f, 64.0f);
        this.veryBounds = new Rectangle(288.0f, 260.0f, 32.0f, 32.0f);
        this.nullyBounds = new Rectangle(288.0f, 164.0f, 32.0f, 32.0f);
        this.worldListener = new C02762();
        this.batcher = new SpriteBatcher(this.glGraphics, 100);
        this.renderer = new WorldRenderer(this.glGraphics, this.batcher, world);
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
                if (OverlapTester.pointInRectangle(this.backbounds, this.touchPoint)) {
                    this.game.setScreen(new MainMenu(this.game));
                    return;
                } else if (OverlapTester.pointInRectangle(this.veryBounds, this.touchPoint) && InfoWhirl.getVoteBucket()[2] > 0) {
                    bucketedU = InfoWhirl.getVoteBucket()[0] + 1;
                    bucketedD = InfoWhirl.getVoteBucket()[1];
                    this.upvotes = ((Integer) InfoWhirl.getUpVotes().get(MainMenu.getPos())).intValue() + 1;
                    this.downvotes = ((Integer) InfoWhirl.getDownvotes().get(MainMenu.getPos())).intValue();
                    InfoWhirl.setVoteBucket(bucketedU, bucketedD);
                    InfoWhirl.setPositional_Votes(MainMenu.getPos(), this.upvotes, this.downvotes);
                    update(0.0f);
                    vote(((Integer) InfoWhirl.xxes.get(MainMenu.getPos())).intValue(), ((Integer) InfoWhirl.yyes.get(MainMenu.getPos())).intValue(), this.upvotes, this.downvotes);
                    return;
                } else if (OverlapTester.pointInRectangle(this.nullyBounds, this.touchPoint) && InfoWhirl.getVoteBucket()[2] > 0) {
                    bucketedU = InfoWhirl.getVoteBucket()[0];
                    bucketedD = InfoWhirl.getVoteBucket()[1] + 1;
                    this.downvotes = ((Integer) InfoWhirl.getDownvotes().get(MainMenu.getPos())).intValue() + 1;
                    this.upvotes = ((Integer) InfoWhirl.getUpVotes().get(MainMenu.getPos())).intValue();
                    InfoWhirl.setVoteBucket(bucketedU, bucketedD);
                    InfoWhirl.setPositional_Votes(MainMenu.getPos(), this.upvotes, this.downvotes);
                    update(0.0f);
                    vote(((Integer) InfoWhirl.xxes.get(MainMenu.getPos())).intValue(), ((Integer) InfoWhirl.yyes.get(MainMenu.getPos())).intValue(), this.upvotes, this.downvotes);
                    return;
                }
            }
        }
    }

    private void setUps(int ups) {
        this.upvotes = ups;
    }

    private void setDowns(int Downs) {
        this.downvotes = Downs;
    }

    private int getUps() {
        return this.upvotes;
    }

    private int getDowns() {
        return this.downvotes;
    }

    public void present(float deltaTime) {
        GL10 gl = this.glGraphics.getGL();
        gl.glClear(16384);
        gl.glEnable(3553);
        this.renderer.render();
        this.guiCam.setViewportAndMatrices();
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        this.batcher.beginBatch(Assets.mat);
        this.batcher.drawSprite(160.0f, 240.0f, 640.0f, 960.0f, Assets.table);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.items);
        this.batcher.drawSprite(32.0f, 32.0f, 64.0f, 64.0f, Assets.back);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.feelButtons);
        this.batcher.drawSprite(304.0f, 276.0f, 32.0f, 32.0f, Assets.veryButton);
        this.batcher.drawSprite(304.0f, 180.0f, 32.0f, 32.0f, Assets.nullyButton);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.texs);
        try {
            Assets.font.drawText(this.batcher, (String) InfoWhirl.getEventsString().get(MainMenu.getPos()), World.WORLD_WIDTH, 460.0f);
            Assets.font.drawText(this.batcher, (String) InfoWhirl.getDetailsString().get(MainMenu.getPos()), 15.0f, 400.0f);
            Font font = Assets.font;
            SpriteBatcher spriteBatcher = this.batcher;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TIME:: ");
            stringBuilder.append(InfoWhirl.getTimes().get(MainMenu.getPos()));
            font.drawText(spriteBatcher, stringBuilder.toString(), 4.0f, 320.0f);
            font = Assets.font;
            spriteBatcher = this.batcher;
            stringBuilder = new StringBuilder();
            stringBuilder.append("                           ");
            stringBuilder.append(InfoWhirl.getUpVotes().get(MainMenu.getPos()));
            font.drawText(spriteBatcher, stringBuilder.toString(), 3.0f, 270.0f);
            font = Assets.font;
            spriteBatcher = this.batcher;
            stringBuilder = new StringBuilder();
            stringBuilder.append("                           ");
            stringBuilder.append(InfoWhirl.getDownvotes().get(MainMenu.getPos()));
            font.drawText(spriteBatcher, stringBuilder.toString(), 3.0f, 190.0f);
        } catch (NullPointerException e) {
            NullPointerException d = e;
            Assets.font.drawText(this.batcher, "null", 0.0f, 0.0f);
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

    public void vote(int Xpos, int Ypos, int V, int N) {
        this.opinionX = Xpos;
        this.OpinionY = Ypos;
        this.verys = V;
        this.nullys = N;
        new UpdateVotes().execute(new String[0]);
        new UpdateBucket().execute(new String[0]);
    }
}