package com.example.user.infowhirl2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

public class ToGo extends Activity implements OnClickListener {
    private static final String TAG_COURSE = "course";
    private static final String TAG_CX = "coords_x";
    private static final String TAG_CY = "coords_y";
    private static final String TAG_DETAILS = "details";
    private static final String TAG_EVENT = "event";
    private static final String TAG_HOBBY = "hobby";
    private static final String TAG_PASS = "passkey";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TIME = "time";
    static TextView event;
    private static String url_update_product = "http://10.42.0.1/infowhirl/post_locale.php";
    Button button;
    String course;
    TextView details;
    String hobby;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    TextView time;

    class UpdateLocales extends AsyncTask<String, String, String> {
        UpdateLocales() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ToGo.this.pDialog = new ProgressDialog(ToGo.this);
            ToGo.this.pDialog.setMessage("Saving product ...");
            ToGo.this.pDialog.setIndeterminate(false);
            ToGo.this.pDialog.setCancelable(true);
            ToGo.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            ToGo.this.course = SelectaLocaleScreen.s_course;
            ToGo.this.hobby = SelectaLocaleScreen.s_hobby;
            String name = ToGo.event.getText().toString();
            String detas = ToGo.this.details.getText().toString();
            String chrono = ToGo.this.time.getText().toString();
            String coords_x = ""+SelectaLocaleScreen.XPlace;
            String coords_y=""+SelectaLocaleScreen.YPlace;

            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair(ToGo.TAG_COURSE, ToGo.this.course));
            params.add(new BasicNameValuePair(ToGo.TAG_HOBBY, ToGo.this.hobby));
            params.add(new BasicNameValuePair("event", name));
            params.add(new BasicNameValuePair(ToGo.TAG_DETAILS, detas));
            params.add(new BasicNameValuePair(ToGo.TAG_TIME, chrono));
            params.add(new BasicNameValuePair(ToGo.TAG_CX, coords_x));
            params.add(new BasicNameValuePair(ToGo.TAG_CY, coords_y));
            try {
                if (ToGo.this.jsonParser.makeHttpRequest(ToGo.url_update_product, HttpPost.METHOD_NAME, params).getInt(ToGo.TAG_SUCCESS) == 1) {
                    Intent home = new Intent(ToGo.this, InfoWhirl.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("COURSE", ToGo.this.course);
                    bundle.putString("HOBBY", ToGo.this.hobby);
                    bundle.putInt("TRINKETS", ReLogger.trinkets);
                    bundle.putInt("DATE", ReLogger.date);
                    bundle.putString("NAME", ReLogger.name);
                    bundle.putString("PASSKEY", ReLogger.pass);
                    home.putExtras(bundle);
                    ToGo.this.startActivity(home);
                    ToGo.this.finish();
                } else {
                    Toast.makeText(ToGo.this.getApplicationContext(), "Some details are wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            ToGo.this.pDialog.dismiss();
        }
    }

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.form);
        event = (TextView) findViewById(R.id.event);
        this.details = (TextView) findViewById(R.id.details);
        this.button = (Button) findViewById(R.id.button);
        this.time = (TextView) findViewById(R.id.time);
        this.button.setOnClickListener(this);
        InfoWhirl.isHome = false;
    }

    public void onClick(View v) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Now they know...");
        stringBuilder.append(SelectaLocaleScreen.XPlace);
        stringBuilder.append("...");
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        new UpdateLocales().execute(new String[0]);
    }
}