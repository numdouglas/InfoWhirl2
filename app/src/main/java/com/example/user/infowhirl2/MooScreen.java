package com.example.user.infowhirl2;

import Hose.Camera2D;
import Hose.Font;
import Hose.GLScreen;
import Hose.Game;
import Hose.Input.TouchEvent;
import Hose.OverlapTester;
import Hose.Rectangle;
import Hose.SpriteBatcher;
import Hose.Vector2;
import android.os.AsyncTask;
import android.util.Log;
import com.example.user.infowhirl2.World.WorldListener;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class MooScreen extends GLScreen {
    static boolean MoreClicked = false;
    private static final String TAG_SUCCESS = "success";
    static boolean backclicked;

    private static String url_set_bucket = "http://192.168.137.1/infowhirl/set_bucket.php";
    private static String url_vote = "http://192.168.137.1/infowhirl/update_votes.php";
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

    class UpdateBucket extends AsyncTask<String, String, String> { UpdateBucket() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> paramsV = new ArrayList();
            paramsV.add(new BasicNameValuePair("name", InfoWhirl.u_name));
            paramsV.add(new BasicNameValuePair("passkey", InfoWhirl.u_pass));
            paramsV.add(new BasicNameValuePair("last_seen",""+InfoWhirl.u_date));
            paramsV.add(new BasicNameValuePair("trinkets", ""+InfoWhirl.getVoteBucket()[2]));

            try {
                JSONObject backo=jParser.makeHttpRequest(url_set_bucket, "POST", paramsV);
                if (backo.getInt(TAG_SUCCESS) == 1) {
                    Log.d("Opinion", backo.toString());
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
//This class will update the votes in the database when the user casts it via the background activity
    class UpdateVotes extends AsyncTask<String, String, String> {
        UpdateVotes() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("V", ""+verys));

            params.add(new BasicNameValuePair("N", ""+nullys));

            params.add(new BasicNameValuePair("coords_x", ""+opinionX));
            params.add(new BasicNameValuePair("coords_y", ""+OpinionY));
            try {
                JSONObject vota=jParser.makeHttpRequest(MooScreen.url_vote, "POST", params);
                if (vota.getInt(MooScreen.TAG_SUCCESS) == 1) {
                    Log.d("Opinion", vota.toString());
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

        public void upvote() {
        }

        public void downvote() {
        }
    }

    class C02762 implements WorldListener {
        C02762() {
        }

        public void upvote() {
        }

        public void downvote() {
        }

    }

    public MooScreen(Game game) {
        super(game);
        MoreClicked = true;
        this.guiCam = new Camera2D(this.glGraphics, InfoWhirl.getScreenWidth(), InfoWhirl.getScreenHeight());
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
                //if back button is pressed, go back to the mainscreen
                if (OverlapTester.pointInRectangle(this.backbounds, this.touchPoint)) {
                    this.game.setScreen(new MainScreen(this.game));
                    return;
                    //if the upvote button is pressed increase the votes in the database and the lifecycles votes arrray
                } else if (OverlapTester.pointInRectangle(this.veryBounds, this.touchPoint) && InfoWhirl.getVoteBucket()[2] > 0) {
                    bucketedU = InfoWhirl.getVoteBucket()[0] + 1;
                    bucketedD = InfoWhirl.getVoteBucket()[1];
                    this.upvotes = ((Integer) InfoWhirl.getUpVotes().get(MainScreen.getPos())).intValue() + 1;
                    this.downvotes = ((Integer) InfoWhirl.getDownvotes().get(MainScreen.getPos())).intValue();
                    //set the lifecycle array of upvotes and downvotes
                    InfoWhirl.setVoteBucket(bucketedU, bucketedD);
                    InfoWhirl.setPositional_Votes(MainScreen.getPos(), this.upvotes, this.downvotes);
                    update(0.0f);
                    vote(((Integer) InfoWhirl.xxes.get(MainScreen.getPos())).intValue(), ((Integer) InfoWhirl.yyes.get(MainScreen.getPos())).intValue(), this.upvotes, this.downvotes);
                    return;

                    //if the downvote button is pressed increase the votes in the database and the lifecycles votes array
                } else if (OverlapTester.pointInRectangle(this.nullyBounds, this.touchPoint) && InfoWhirl.getVoteBucket()[2] > 0) {
                    bucketedU = InfoWhirl.getVoteBucket()[0];
                    bucketedD = InfoWhirl.getVoteBucket()[1] + 1;
                    this.downvotes = ((Integer) InfoWhirl.getDownvotes().get(MainScreen.getPos())).intValue() + 1;
                    this.upvotes = ((Integer) InfoWhirl.getUpVotes().get(MainScreen.getPos())).intValue();
                    InfoWhirl.setVoteBucket(bucketedU, bucketedD);
                    InfoWhirl.setPositional_Votes(MainScreen.getPos(), this.upvotes, this.downvotes);
                    update(0.0f);
                    vote(((Integer) InfoWhirl.xxes.get(MainScreen.getPos())).intValue(), ((Integer) InfoWhirl.yyes.get(MainScreen.getPos())).intValue(), this.upvotes, this.downvotes);
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
        this.batcher.drawSprite(160.0f, 240.0f, InfoWhirl.getScreenWidth(), InfoWhirl.getScreenHeight(), Assets.table);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.items);
        this.batcher.drawSprite(32.0f, 32.0f, 64.0f, 64.0f, Assets.back);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.feelButtons);
        this.batcher.drawSprite(304.0f, 276.0f, 32.0f, 32.0f, Assets.veryButton);
        this.batcher.drawSprite(304.0f, 180.0f, 32.0f, 32.0f, Assets.nullyButton);
        this.batcher.endBatch();
        this.batcher.beginBatch(Assets.texs);
        try {//Draw the glyphs(text) for the title
            Assets.font.drawText(this.batcher, (String) InfoWhirl.getEventsString().get(MainScreen.getPos()), World.WORLD_WIDTH, 460.0f);
            //For loop to draw the details stringstream, this will automatically perform a word wrap on reaching the end of the
            //screen
            for(int i = 0; i<=(Integer)InfoWhirl.getDetailsString().get(MainScreen.getPos()).length()/18; i++) {
                //If this is the first of more than one index fill line one to the end
                if (i == 0&&i !=(Integer)InfoWhirl.getDetailsString().get(MainScreen.getPos()).length() /18) {
                    Assets.font.drawText(this.batcher, (String) InfoWhirl.getDetailsString().get(MainScreen.getPos()).substring(0, 18), 15.0f, 400.0f);
                }
                //If this is the second to second last of the index fill the line to the end
                if ((i < (Integer)InfoWhirl.getDetailsString().get(MainScreen.getPos()).length() /18)&&i!=0) {
                    Assets.font.drawText(this.batcher, (String) InfoWhirl.getDetailsString().get(MainScreen.getPos()).substring((i * 18), 18 * (i + 1)), 15.0f, 400.0f-(i*30));
                }
                //If this is the remaining or original line draw the glyphs to the postion the last indez glyph reaches
                if (i ==(Integer)InfoWhirl.getDetailsString().get(MainScreen.getPos()).length() /18) {
                    Assets.font.drawText(this.batcher, (String) InfoWhirl.getDetailsString().get(MainScreen.getPos()).substring((i * 18), InfoWhirl.getDetailsString().get(MainScreen.getPos()).length()), 15.0f, 400.0f-(i*30));

                }

            }
            Font font = Assets.font;
            SpriteBatcher spriteBatcher = this.batcher;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TIME:: ");
            stringBuilder.append(InfoWhirl.getTimes().get(MainScreen.getPos()));
            stringBuilder.append(" Hrs");
            font.drawText(spriteBatcher, stringBuilder.toString(), 4.0f, 120.0f);
            font = Assets.font;
            spriteBatcher = this.batcher;
            stringBuilder = new StringBuilder();
            stringBuilder.append(InfoWhirl.getUpVotes().get(MainScreen.getPos()));
            font.drawText(spriteBatcher, stringBuilder.toString(), 250.0f, 270.0f);
            font = Assets.font;
            spriteBatcher = this.batcher;
            stringBuilder = new StringBuilder();
            stringBuilder.append(InfoWhirl.getDownvotes().get(MainScreen.getPos()));
            font.drawText(spriteBatcher, stringBuilder.toString(), 250.0f, 190.0f);
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
//The method will set the field values and increase the votes while decreasing those the active user has left to cast
    public void vote(int Xpos, int Ypos, int V, int N) {
        this.opinionX = Xpos;
        this.OpinionY = Ypos;
        this.verys = V;
        this.nullys = N;
        new UpdateVotes().execute(new String[0]);
        new UpdateBucket().execute(new String[0]);
    }
}