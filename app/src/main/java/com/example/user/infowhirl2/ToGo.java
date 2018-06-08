package com.example.user.infowhirl2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 10/29/2017.
 */

public class ToGo extends Activity implements View.OnClickListener {
static TextView event;
    TextView details,time;
 Button button;
    String user=ReLogger.namelog.getText().toString();
    DetailTaker smallG;

        @Override
public void onCreate(Bundle b){
            super.onCreate(b);
            setContentView(R.layout.form);
            smallG=new DetailTaker(this);
            //consider assigning
            event=findViewById(R.id.event);
            details=findViewById(R.id.details);
            button=findViewById(R.id.button);
            time=findViewById(R.id.time);
            button.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        smallG.addDetails(ReLogger.namelog.getText().toString(),event.getText().toString(),details.getText().toString(),Integer.parseInt(time.getText().toString()));
        Toast.makeText(this,"Now they know..."+SelectaLocaleScreen.XPlace+"...",Toast.LENGTH_SHORT).show();
        Intent po=new Intent(this,InfoWhirl.class);
        finish();
        startActivity(po);
    }
}
