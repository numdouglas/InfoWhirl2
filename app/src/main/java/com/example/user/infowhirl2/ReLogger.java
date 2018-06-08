package com.example.user.infowhirl2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 11/2/2017.
 */

public class ReLogger extends Activity{
    static TextView namelog;
            TextView passkey,newGuy;
    Button sublog;
    DetailTaker smallG;
    @Override
    public void onCreate(Bundle stateb){

        smallG=new DetailTaker(this);
        super.onCreate(stateb);

        setContentView(R.layout.loginform);
        namelog=findViewById(R.id.name);
        passkey=findViewById(R.id.passkeylog);
        newGuy=findViewById(R.id.register);
        sublog=findViewById(R.id.login);
        Loggier loggier=new Loggier();
        NewGuy newGuyB=new NewGuy();
        newGuy.setOnClickListener(newGuyB);
        sublog.setOnClickListener(loggier);
    }

    class Loggier implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(smallG.confirm(namelog.getText().toString()).equals(passkey.getText().toString())){
                Intent home=new Intent(ReLogger.this,InfoWhirl.class);
                finish();
                startActivity(home);
            }
        }
    }
    class NewGuy implements View.OnClickListener{
        @Override
        public void onClick(View v){
       Intent reg=new Intent(ReLogger.this,Registrar.class);
        startActivity(reg);}
    }
}
