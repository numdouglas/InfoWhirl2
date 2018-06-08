package com.example.user.infowhirl2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 10/9/2017.
 */

public class DetailTaker {
    private static final String DB_NAME = "whirls";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    public static int x,y;
    static List<Integer> xxes=new ArrayList<Integer>();
    static List<Integer> yyes=new ArrayList<Integer>();
    Calendar calo = Calendar.getInstance();
    public DetailTaker(Context context){

        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
// Get a writable database
        db = helper.getWritableDatabase();}
    public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS  whirls(ACNAME TEXT,Passkey TEXT,Hobby TEXT,Course TEXT,Event TEXT,CoordsX INT,CoordsY INT,Details TEXT,TIME INT,V INT,N INT);";
            db.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }}
    public void createAccount(String acName, String passkey, String Hobby, String Course){
        String query="INSERT INTO whirls(ACNAME,Passkey,Hobby,Course) VALUES ('"+acName+"','"+passkey+"','"+Hobby+"','"+Course+"');";
        db.execSQL(query);
    }
    public void enLive(int votes,String details){
        String query="UPDATE whirls SET V="+votes+" WHERE DETAILS='"+details+"'";
        db.execSQL(query);
    }
    public  String  confirm(String name){

        Cursor c=db.rawQuery("SELECT Passkey FROM whirls WHERE ACNAME='"+name+"';",null);
        c.moveToFirst();
        return c.getString(0);}
/*
        public void addLocale(String name){
            String query="UPDATE whirls SET CoordsX="+SelectaLocaleScreen.XPlace+",CoordsY="+SelectaLocaleScreen.YPlace+
                    ",V="+0+",N="+0+" WHERE ACNAME='"+name+"'";
            setX(SelectaLocaleScreen.XPlace);
            setY(SelectaLocaleScreen.YPlace);
            db.execSQL(query);
        }*/
        public  void addDetails(String AcName,String event,String details,int time){

            Cursor b=db.rawQuery("SELECT Course FROM whirls WHERE ACNAME='"+AcName+"'",null);
            b.moveToFirst();
            String query="INSERT INTO whirls(Event,Details,Course,CoordsX,CoordsY,V,N) VALUES ('"+event+"','"+details+"','"+b.getString(0)+"',"+SelectaLocaleScreen.XPlace+","+SelectaLocaleScreen.YPlace+","+0+","+0+")";
            setX(SelectaLocaleScreen.XPlace);
            setY(SelectaLocaleScreen.YPlace);
            db.execSQL(query);}
            /*
        public String getEvent(){
            Cursor c=db.rawQuery("SELECT Event FROM whirls WHERE ACNAME='"+ReLogger.namelog+"'",null);
            c.moveToFirst();
            return c.getString(0);}*/
    public void getPostings(String name){
        int currenttime=Integer.parseInt(calo.getTime().toString().substring(11,13));

        Cursor b=db.rawQuery("SELECT Course FROM whirls WHERE ACNAME='"+name+"'",null);
        b.moveToFirst();
        Cursor c=db.rawQuery("SELECT  CoordsX,CoordsY FROM whirls WHERE Course='"+b.getString(0)+"' AND TIME>="+currenttime,null);
        if(c.moveToFirst()){
      /*  exes[0][0]=c.getInt(0);
        exes[0][1]=c.getInt(1);*/
        xxes.add(0,c.getInt(0));
        yyes.add(0,c.getInt(1));}
        for(int i=1;i<xxes.size();i++){
        if (c.moveToNext()){
            xxes.add(i,c.getInt(0));
            yyes.add(i,c.getInt(1));}

/*            exes[i][0]=c.getInt(0);
        exes[i][1]=c.getInt(1);*/}
    }


    public String pollLocDetails(int xplace,int yplace){
        Cursor c=db.rawQuery("SELECT Details FROM whirls WHERE CoordsX="+xplace+" AND CoordsY="+yplace+"",null);
        if(c.moveToFirst())
        return c.getString(0);

        return null;
    }

    public String pollLocEvents(int xplace,int yplace){
        Cursor c=db.rawQuery("SELECT Event FROM whirls WHERE CoordsX="+xplace+" AND CoordsY="+yplace+"",null);
        if(c.moveToFirst())
            return c.getString(0);

        return null;
    }

    public int pollLocVotes(int xplace,int yplace){
      Cursor c=db.rawQuery("SELECT V FROM whirls WHERE CoordsX="+xplace+" AND CoordsY="+yplace,null);
      if(c.moveToFirst())return c.getInt(0);
        return 0;
    }
    public int pollLocTimes(int xplace,int yplace){

        Cursor c=db.rawQuery("SELECT TIME FROM whirls WHERE CoordsX="+xplace+" AND CoordsY="+yplace+"",null);
        if(c.moveToFirst())
            return c.getInt(0);

        return 00;
    }

        public void setX(int X){
            this.x=X;
        }
        public void setY(int Y){
            this.y=Y;
        }
        public static int getX(){
        return x;}
    public static int getY(){
        return y;}
}