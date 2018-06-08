package com.example.user.infowhirl2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 11/3/2017.
 */

public class Registrar extends Activity implements View.OnClickListener{
    TextView name,passkey,course,hobby;
    Button register;
    DetailTaker smallG;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        smallG=new DetailTaker(this);
        setContentView(R.layout.registerform);
        name=findViewById(R.id.namereg);
        passkey=findViewById(R.id.passreg);
        course=findViewById(R.id.course);
        hobby=findViewById(R.id.hobby);
        register=findViewById(R.id.registernew);
        register.setOnClickListener(this);
    }
        @Override
        public void onClick(View v) {
            smallG.createAccount(name.getText().toString(),passkey.getText().toString(),hobby.getText().toString(),course.getText().toString());
            Toast.makeText(Registrar.this,"Duly registered!! "+name.getText(),Toast.LENGTH_SHORT).show();
        Intent login=new Intent(Registrar.this,ReLogger.class);
        finish();
        startActivity(login);}
    }