package com.example.user.infowhirl2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import GameHose.GLGame;
import GameHose.Screen;

public class InfoWhirl extends GLGame {
    static List <String> detailsD=new ArrayList<String>();
    DetailTaker smallG;
    static List<String> events=new ArrayList<String>();
    static List<Integer> times=new ArrayList<>();
    static List<Integer> votes=new ArrayList<>();
    int zoomfactor=0;


    public Screen getStartScreen() {
        return new MainMenu(this);}

    @Override
    public void onCreate(Bundle m) {
        super.onCreate(m);

            smallG = new DetailTaker(this);
            smallG.getPostings(ReLogger.namelog.getText().toString());

            for(int i=detailsD.size();i<DetailTaker.xxes.size();i++) {
                setDetailsDString(i, smallG.pollLocDetails(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)));
                setEventsString(i, smallG.pollLocEvents(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)));
                setTime(i, smallG.pollLocTimes(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)));
                smallG.enLive(smallG.pollLocVotes(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)),smallG.pollLocDetails(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)));
              setVotes(i,smallG.pollLocVotes(DetailTaker.xxes.get(i), DetailTaker.yyes.get(i)));
             }// Toast.makeText(this, getEventsString().get(1), Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);

        Assets.load(this);}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent reg=new Intent(this,ReLogger.class);
        finish();
        startActivity(reg);}
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP) {
            MainMenu p=new MainMenu(this);
            zoomfactor++;
            p.guiCam.zoom= (float) (zoomfactor*0.1);
            setScreen(p);
        }if (keyCode==KeyEvent.KEYCODE_VOLUME_DOWN&&zoomfactor>1){
            MainMenu p=new MainMenu(this);
            zoomfactor--;
            p.guiCam.zoom =(float)(zoomfactor*0.1);
        setScreen(p);}
        if(keyCode==KeyEvent.KEYCODE_BACK){finish();}return true;
    }
    public void setVotes(int pos,int vote){this.votes.add(vote);}

    public void setEventsString(int pos,String val){this.events.add(val);//this.events.set(pos,val);
        }



    public void setDetailsDString(int pos,String val){

        detailsD.add(val);
            //this.detailsD.set(pos,val);
        }

    public void setTime(int pos,int val){this.times.add(val);//this.times.set(pos,val);
    }
    public static List<String> getDetailsString(){
        return detailsD;}
    public static List<String> getEventsString(){
        return events;}
    public static List<Integer> getTimes(){return times;}
    public static  List<Integer> getVotes(){return votes;}
        @Override
        public void onPause() {
        super.onPause();}}