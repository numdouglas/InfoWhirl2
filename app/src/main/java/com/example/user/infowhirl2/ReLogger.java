package com.example.user.infowhirl2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReLogger extends Activity {
    private static final String TAG_COURSE = "course";
    private static final String TAG_DATUM = "last_seen";
    private static final String TAG_HOBBY = "hobby";
    private static final String TAG_NAME = "name";
    private static final String TAG_PID = "passkey";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_VOTES = "trinkets";
    private static final String TAG_WHIRLS = "members";
    static String course;
    static int date;
    static String hobby;
    static String name;
    static TextView namelog;
    static String pass;
    static int trinkets;
    private static String url_all_details = "http:///*Insert the server's ip adress here, I used my computer's ip address in this case*//infowhirl/confirm_details.php";
    JSONParser jParser = new JSONParser();
    TextView newGuy;
    private ProgressDialog pDialog;
    TextView passkey;
    JSONArray pivots = null;
    ArrayList<HashMap<String, String>> productsList;
    Button sublog;
//The class that will confirm a users details and move to a new screen if correct. This is done via background activity
    //due to the erratic nature of the internet
    class LogEmIn extends AsyncTask<String, String, String> {

        class C02221 implements Runnable {
            C02221() {
            }

            public void run() {
                Toast.makeText(ReLogger.this.getApplicationContext(), "Confirmation complete", Toast.LENGTH_SHORT);
            }
        }

        LogEmIn() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ReLogger.this.pDialog = new ProgressDialog(ReLogger.this);
            ReLogger.this.pDialog.setMessage("Checking up. Please wait...");
            ReLogger.this.pDialog.setIndeterminate(false);
            ReLogger.this.pDialog.setCancelable(false);
            ReLogger.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            ReLogger.name = ReLogger.namelog.getText().toString();
            ReLogger.pass = ReLogger.this.passkey.getText().toString();
            InfoWhirl.PASS = ReLogger.pass;
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair(ReLogger.TAG_NAME, ReLogger.name));
            params.add(new BasicNameValuePair(ReLogger.TAG_PID, ReLogger.pass));
            JSONObject json = jParser.makeHttpRequest(ReLogger.url_all_details, "GET", params);
            Log.d("Members confirmed: ", json.toString());
            try {
                if (json.getInt(ReLogger.TAG_SUCCESS) == 1) {
                    ReLogger.this.pivots = json.getJSONArray(ReLogger.TAG_WHIRLS);
                        JSONObject c = ReLogger.this.pivots.getJSONObject(0);
                        ReLogger.hobby = c.getString(ReLogger.TAG_HOBBY);

                        ReLogger.course = c.getString(ReLogger.TAG_COURSE);
                        ReLogger.trinkets = c.getInt(ReLogger.TAG_VOTES);
                        ReLogger.date = c.getInt(ReLogger.TAG_DATUM);

                    Bundle bundle = new Bundle();
                    bundle.putString("HOBBY", ReLogger.hobby);
                    bundle.putString("COURSE", ReLogger.course);
                    bundle.putInt("TRINKETS", ReLogger.trinkets);
                    bundle.putInt("DATE", ReLogger.date);
                    bundle.putString("NAME", ReLogger.name);
                    bundle.putString("PASSKEY", ReLogger.pass);
                    Intent home = new Intent(ReLogger.this, InfoWhirl.class);
                    home.putExtras(bundle);
                    ReLogger.this.startActivity(home);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            ReLogger.this.pDialog.dismiss();
            ReLogger.this.runOnUiThread(new C02221());
        }
    }

    class Loggier implements OnClickListener {
        Loggier() {
        }

        public void onClick(View v) {
            new LogEmIn().execute();
        }
    }

    class NewGuy implements OnClickListener {
        NewGuy() {
        }

        public void onClick(View v) {
            ReLogger.this.startActivity(new Intent(ReLogger.this, Registrar.class));
        }
    }

    public void onCreate(Bundle stateb) {
        super.onCreate(stateb);
        setContentView(R.layout.loginform);
        namelog = (TextView) findViewById(R.id.name);
        this.passkey = (TextView) findViewById(R.id.passkeylog);
        this.newGuy = (TextView) findViewById(R.id.register);
        this.sublog = (Button) findViewById(R.id.login);
        Loggier loggier = new Loggier();
        this.newGuy.setOnClickListener(new NewGuy());
        this.sublog.setOnClickListener(loggier);
    }
}
