package com.example.user.infowhirl2;

/*import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailTaker {
    private static final String DB_NAME = "whirls";
    private static final int DB_VERSION = 3;
    static List<Integer> Ns = new ArrayList();
    static List<Integer> Vs = new ArrayList();
    public static int f13x;
    static List<Integer> xxes = new ArrayList();
    public static int f14y;
    static List<Integer> yyes = new ArrayList();
    private Calendar calo = Calendar.getInstance();
    private SQLiteDatabase db;

    public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DetailTaker.DB_NAME, null, 3);
   }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS  whirls(ACNAME TEXT,Passkey TEXT,Hobby TEXT,Course TEXT,Event TEXT,CoordsX INT,CoordsY INT,Details TEXT,TIME INT,V INT,N INT)";
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public DetailTaker(Context context) {
        this.db = new CustomSQLiteOpenHelper(context).getWritableDatabase();
    }

    public void createAccount(String acName, String passkey, String Hobby, String Course) {
        String query = new StringBuilder();
        query.append("INSERT INTO whirls(ACNAME,Passkey,Hobby,Course) VALUES ('");
        query.append(acName);
        query.append("','");
        query.append(passkey);
        query.append("','");
        query.append(Hobby);
        query.append("','");
        query.append(Course);
        query.append("');");
        this.db.execSQL(query.toString());
    }

    public String confirm(String name) {
        Cursor c = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Passkey FROM whirls WHERE ACNAME='");
        stringBuilder.append(name);
        stringBuilder.append("';");
        c = c.rawQuery(stringBuilder.toString(), null);
        c.moveToFirst();
        return c.getString(0);
    }

    public void addDetails(String AcName, String event, String details, int time) {
        Cursor b = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Course FROM whirls WHERE ACNAME='");
        stringBuilder.append(AcName);
        stringBuilder.append("'");
        b = b.rawQuery(stringBuilder.toString(), null);
        b.moveToFirst();
        String query = new StringBuilder();
        query.append("INSERT INTO whirls(Event,Details,TIME,Course,CoordsX,CoordsY,V,N) VALUES ('");
        query.append(event);
        query.append("','");
        query.append(details);
        query.append("',");
        query.append(time);
        query.append(",'");
        query.append(b.getString(0));
        query.append("',");
        query.append(SelectaLocaleScreen.XPlace);
        query.append(",");
        query.append(SelectaLocaleScreen.YPlace);
        query.append(",");
        query.append(0);
        query.append(",");
        query.append(0);
        query.append(")");
        query = query.toString();
        setX(SelectaLocaleScreen.XPlace);
        setY(SelectaLocaleScreen.YPlace);
        getPostings(AcName);
        this.db.execSQL(query);
    }

    public void vote(int Xpos, int Ypos, int V, int N) {
        String query = new StringBuilder();
        query.append("UPDATE whirls SET V=");
        query.append(V);
        query.append(",N=");
        query.append(N);
        query.append(" WHERE CoordsX=");
        query.append(Xpos);
        query.append(" AND CoordsY=");
        query.append(Ypos);
        this.db.execSQL(query.toString());
        getPostings(ReLogger.namelog.getText().toString());
    }

    public int[] countVotes(int xplace, int yplace) {
        Cursor v = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT V,N FROM WHIRLS WHERE CoordsX=");
        stringBuilder.append(xplace);
        stringBuilder.append(" AND CoordsY=");
        stringBuilder.append(yplace);
        try {
            if (!v.rawQuery(stringBuilder.toString(), null).moveToFirst()) {
                return null;
            }
            return new int[]{v.rawQuery(stringBuilder.toString(), null).getInt(0), v.rawQuery(stringBuilder.toString(), null).getInt(1)};
        } catch (SQLiteCantOpenDatabaseException e) {
            return null;
        }
    }

    public void getPostings(String name) {
        int currenttime = Integer.parseInt(this.calo.getTime().toString().substring(11, 13));
        Cursor b = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Course FROM whirls WHERE ACNAME='");
        stringBuilder.append(name);
        stringBuilder.append("'");
        b = b.rawQuery(stringBuilder.toString(), null);
        if (b.moveToFirst()) {
            Cursor c = this.db;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("SELECT  CoordsX,CoordsY,V,N FROM whirls WHERE Course='");
            stringBuilder2.append(b.getString(0));
            stringBuilder2.append("' AND TIME>=");
            stringBuilder2.append(currenttime);
            c = c.rawQuery(stringBuilder2.toString(), null);
            if (c.moveToFirst()) {
                xxes.add(0, Integer.valueOf(c.getInt(0)));
                yyes.add(0, Integer.valueOf(c.getInt(1)));
                Vs.add(0, Integer.valueOf(c.getInt(2)));
                Ns.add(0, Integer.valueOf(c.getInt(3)));
            }
            for (int i = 1; i < xxes.size(); i++) {
                if (c.moveToNext()) {
                    xxes.add(i, Integer.valueOf(c.getInt(0)));
                    yyes.add(i, Integer.valueOf(c.getInt(1)));
                    Vs.add(i, Integer.valueOf(c.getInt(2)));
                    Ns.add(i, Integer.valueOf(c.getInt(3)));
                }
            }
        }
    }

    public String pollLocDetails(int xplace, int yplace) {
        Cursor c = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Details FROM whirls WHERE CoordsX=");
        stringBuilder.append(xplace);
        stringBuilder.append(" AND CoordsY=");
        stringBuilder.append(yplace);
        stringBuilder.append("");
        c = c.rawQuery(stringBuilder.toString(), null);
        try {
            if (c.moveToFirst()) {
                return c.getString(0);
            }
            return null;
        } catch (SQLiteCantOpenDatabaseException e) {
            return null;
        }
    }

    public String pollLocEvents(int xplace, int yplace) {
        Cursor c = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT Event FROM whirls WHERE CoordsX=");
        stringBuilder.append(xplace);
        stringBuilder.append(" AND CoordsY=");
        stringBuilder.append(yplace);
        stringBuilder.append("");
        c = c.rawQuery(stringBuilder.toString(), null);
        try {
            if (c.moveToFirst()) {
                return c.getString(0);
            }
            return null;
        } catch (SQLiteCantOpenDatabaseException e) {
            return null;
        }
    }

    public int pollLocTimes(int xplace, int yplace) {
        Cursor c = this.db;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT TIME FROM whirls WHERE CoordsX=");
        stringBuilder.append(xplace);
        stringBuilder.append(" AND CoordsY=");
        stringBuilder.append(yplace);
        stringBuilder.append("");
        c = c.rawQuery(stringBuilder.toString(), null);
        try {
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        } catch (SQLiteCantOpenDatabaseException e) {
        }
    }

    public void setX(int X) {
        f13x = X;
    }

    public void setY(int Y) {
        f14y = Y;
    }

    public static int getX() {
        return f13x;
    }

    public static int getY() {
        return f14y;
    }
}*/