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
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "http://10.42.0.1/infowhirl/register_new.php";
    TextView course;
    TextView hobby;
    TextView inputName;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    TextView passkey;
    Button register;

    class C02231 implements OnClickListener {
        C02231() {
        }

        public void onClick(View view) {
            new CreateNewProduct().execute(new String[0]);
        }
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        class C02241 implements Runnable {
            C02241() {
            }

            public void run() {
                Toast.makeText(Registrar.this.getApplicationContext(), "Registration complete", 0);
            }
        }

        CreateNewProduct() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Registrar.this.pDialog = new ProgressDialog(Registrar.this);
            Registrar.this.pDialog.setMessage("Creating Product..");
            Registrar.this.pDialog.setIndeterminate(false);
            Registrar.this.pDialog.setCancelable(true);
            Registrar.this.pDialog.show();
        }

        protected String doInBackground(String... args) {
            String name = Registrar.this.inputName.getText().toString();
            String passk = Registrar.this.passkey.getText().toString();
            String hobb = Registrar.this.hobby.getText().toString();
            String cours = Registrar.this.course.getText().toString();
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("passkey", passk));
            params.add(new BasicNameValuePair("hobby", hobb));
            params.add(new BasicNameValuePair("course", cours));
            JSONObject json = Registrar.this.jsonParser.makeHttpRequest(Registrar.url_create_product, HttpPost.METHOD_NAME, params);
            Log.d("Create Response", json.toString());
            try {
                if (json.getInt(Registrar.TAG_SUCCESS) == 1) {
                    Registrar.this.startActivity(new Intent(Registrar.this.getApplicationContext(), ReLogger.class));
                    Registrar.this.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            Registrar.this.pDialog.dismiss();
            Registrar.this.runOnUiThread(new C02241());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerform);
        this.inputName = (TextView) findViewById(R.id.namereg);
        this.passkey = (TextView) findViewById(R.id.passreg);
        this.course = (TextView) findViewById(R.id.course);
        this.hobby = (TextView) findViewById(R.id.hobby);
        this.register = (Button) findViewById(R.id.registernew);
        this.register.setOnClickListener(new C02231());
    }
}