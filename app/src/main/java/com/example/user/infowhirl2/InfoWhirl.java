package com.example.user.infowhirl2;

import GameHose.GLGame;
import GameHose.Screen;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class InfoWhirl extends GLGame {
    public static String PASS = "root";
    private static final String TAG_COURSE = "course";
    private static final String TAG_DATE = "last_voted";
    private static final String TAG_DETAILS = "details";
    private static final String TAG_EVENT = "event";
    private static final String TAG_HOBBY = "hobby";
    private static final String TAG_N = "N";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASS = "passkey";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TIME = "time";
    private static final String TAG_V = "V";
    private static final String TAG_VOTEB = "trinkets";
    private static final String TAG_WHIRLS = "members";
    private static final String TAG_X = "coords_x";
    private static final String TAG_Y = "coords_y";
    static int[] backo = new int[3];
    static List<String> detailsD = new ArrayList();
    static List<Integer> downvotes = new ArrayList();
    static List<String> events = new ArrayList();
    public static boolean isHome;
    static List<Integer> times = new ArrayList();
    static String u_course;
    static int u_date;
    static String u_hobby;
    static String u_name;
    static String u_pass;
    static int u_trinkets;
    static List<Integer> upvotes = new ArrayList();
    private static String url_all_products = "http://10.42.0.1/infowhirl/get_all_postings.php";
    private static String url_set_bucket = "http://10.42.0.1/infowhirl/set_bucket.php";
    private static String url_trash = "http://10.42.0.1/infowhirl/trash.php";
    static int vote_d = 0;
    static int vote_u = 0;
    static List<Integer> xxes = new ArrayList();
    static List<Integer> yyes = new ArrayList();
    static int zoomfactor = 0;
    int coordsx;
    int coordsy;
    String course;
    Date date = Calendar.getInstance().getTime();
    int datum_Today;
    String details;
    String event;
    String hobby;
    JSONParser jParser = new JSONParser();
    JSONArray members = null;
    String name;
    int nullifications;
    ProgressDialog pDialog;
    String passkey;
    int time;
    int time_now;
    int verifications;

    class LoadAllProducts extends AsyncTask<String, String, String> {

@Override
        protected String doInBackground(String... r1) {


    List<NameValuePair> params = new ArrayList<NameValuePair>();
    JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
    Log.d("All Locales:", json.toString());
    params.add(new BasicNameValuePair(TAG_COURSE, getU_course()));
    params.add(new BasicNameValuePair(TAG_HOBBY, getU_hobby()));
    params.add(new BasicNameValuePair(TAG_NAME, u_name));
    params.add(new BasicNameValuePair(TAG_PASS, u_pass));
    JSONObject jsonb = jParser.makeHttpRequest(url_set_bucket, "GET", params);
    JSONObject jsont = jParser.makeHttpRequest(url_trash, "GET", params);
    return  null;
}
@Override
        protected void onPreExecute() {
            super.onPreExecute();
            InfoWhirl.this.pDialog = new ProgressDialog(InfoWhirl.this);
            InfoWhirl.this.pDialog.setMessage("Loading locales. Please wait...");
            InfoWhirl.this.pDialog.setIndeterminate(false);
            InfoWhirl.this.pDialog.setCancelable(false);
            InfoWhirl.this.pDialog.show();
        }
@Override
        protected void onPostExecute(String file_url) {
            InfoWhirl.this.pDialog.dismiss();
            InfoWhirl.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(InfoWhirl.this.getApplicationContext(), "locales loaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public Screen getStartScreen() {
        return new MainMenu(this);
    }

    public void onCreate(Bundle m) {
        super.onCreate(m);
        Intent bundle = getIntent();
        u_course = bundle.getStringExtra("COURSE");
        u_hobby = bundle.getStringExtra("HOBBY");
        u_pass = bundle.getStringExtra("PASSKEY");
        u_name = bundle.getStringExtra("NAME");
        u_trinkets = bundle.getIntExtra("TRINKETS", 0);
        u_date = bundle.getIntExtra("DATE", 0);
        setU_course(u_course);
        setU_hobby(u_hobby);
        this.datum_Today = Integer.parseInt(this.date.toString().substring(8, 10));
        this.time_now = Integer.parseInt(this.date.toString().substring(11, 13));
        if (this.datum_Today != u_date) {
            u_trinkets = 10;
            u_date = this.datum_Today;
        }
        new LoadAllProducts().execute(new String[0]);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        Assets.load(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (isHome) {
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                setScreen(new MainMenu(this));
            }
        }
        if (keyCode == 24) {
            MainMenu p = new MainMenu(this);
            zoomfactor++;
            p.guiCam.zoom = (float) (((double) zoomfactor) * 0.1d);
            setScreen(p);
        }
        if (keyCode == 25 && zoomfactor > 1) {
            MainMenu p = new MainMenu(this);
            zoomfactor--;
            p.guiCam.zoom = (float) (((double) zoomfactor) * 0.1d);
            setScreen(p);
        }
        return true;
    }

    public void setVotes(int pos, int voteu, int voted) {
        upvotes.add(pos, Integer.valueOf(voteu));
        downvotes.add(pos, Integer.valueOf(voted));
    }

    public static void setPositional_Votes(int pos, int vote_u, int vote_d) {
        upvotes.set(pos, Integer.valueOf(vote_u));
        downvotes.set(pos, Integer.valueOf(vote_d));
    }

    public void setEventsString(int pos, String val) {
        events.add(pos, val);
    }

    public void setDetailsDString(int pos, String val) {
        detailsD.add(pos, val);
    }

    public void setXxes(int pos, int val) {
        xxes.add(pos, Integer.valueOf(val));
    }

    public void setYyes(int pos, int val) {
        yyes.add(pos, Integer.valueOf(val));
    }

    public void setU_hobby(String hobby) {
        u_hobby = hobby;
    }

    public void setU_course(String course) {
        u_course = course;
    }

    public static List<Integer> getXxes() {
        return xxes;
    }

    public static List<Integer> getYyes() {
        return yyes;
    }

    public void setTime(int pos, int val) {
        times.add(Integer.valueOf(val));
    }

    public static List<String> getDetailsString() {
        return detailsD;
    }

    public static List<String> getEventsString() {
        return events;
    }

    public static List<Integer> getTimes() {
        return times;
    }

    public static List<Integer> getUpVotes() {
        return upvotes;
    }

    public static List<Integer> getDownvotes() {
        return downvotes;
    }

    public static String getU_course() {
        return u_course;
    }

    public static String getU_hobby() {
        return u_hobby;
    }

    public static void setVoteBucket(int ups, int downs) {
        u_trinkets--;
        vote_u = ups;
        vote_d = downs;
    }

    public static int[] getVoteBucket() {
        backo[0] = vote_u;
        backo[1] = vote_d;
        backo[2] = u_trinkets;
        return backo;
    }

    public void onPause() {
        super.onPause();
    }
}