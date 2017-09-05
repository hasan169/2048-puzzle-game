package com.example.shago_000.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class MainActivity extends Activity implements View.OnClickListener {

    game ob;
    score scr;
    LinearLayout ll;
    TextView tx;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.grid);
        ll.addView(ob = new game(ll.getContext()));
        ll = (LinearLayout) findViewById(R.id.lay);
        ll.addView(scr = new score(ll.getContext()));
        tx = (TextView) findViewById(R.id.text);
        tx.setOnClickListener(this);
        scr.object(ob);
        ob.set_object(scr);
        ob.set_object(this);
        mydatabase = openOrCreateDatabase("best_score", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS score(best int);");
        Cursor sql = mydatabase.rawQuery("Select * from score", null);
        if (sql.moveToFirst()) {
            game.best = sql.getInt(0);
        } else {
            mydatabase.execSQL("INSERT INTO score VALUES(0);");
        }
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS player_score(score int)");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS grid(row_no int,col1 int,col2 int,col3 int,col4 int);");
        sql = mydatabase.rawQuery("Select * from grid where row_no = 1", null);
        if (sql.moveToFirst()) {
            ob.turn = false;
            game.grid[1][1] = sql.getInt(1);
            game.grid[1][2] = sql.getInt(2);
            game.grid[1][3] = sql.getInt(3);
            game.grid[1][4] = sql.getInt(4);
            sql = mydatabase.rawQuery("Select * from grid where row_no = 2", null);
            sql.moveToFirst();
            game.grid[2][1] = sql.getInt(1);
            game.grid[2][2] = sql.getInt(2);
            game.grid[2][3] = sql.getInt(3);
            game.grid[2][4] = sql.getInt(4);
            sql = mydatabase.rawQuery("Select * from grid where row_no = 3", null);
            sql.moveToFirst();
            game.grid[3][1] = sql.getInt(1);
            game.grid[3][2] = sql.getInt(2);
            game.grid[3][3] = sql.getInt(3);
            game.grid[3][4] = sql.getInt(4);
            sql = mydatabase.rawQuery("Select * from grid where row_no = 4", null);
            sql.moveToFirst();
            game.grid[4][1] = sql.getInt(1);
            game.grid[4][2] = sql.getInt(2);
            game.grid[4][3] = sql.getInt(3);
            game.grid[4][4] = sql.getInt(4);
            sql = mydatabase.rawQuery("Select * from player_score", null);
            sql.moveToFirst();
            ob.score = sql.getInt(0);
        } else {
            mydatabase.execSQL("INSERT INTO player_score VALUES(0);");
            mydatabase.execSQL("INSERT INTO grid VALUES(1,0,0,0,0);");
            mydatabase.execSQL("INSERT INTO grid VALUES(2,0,0,0,0);");
            mydatabase.execSQL("INSERT INTO grid VALUES(3,0,0,0,0);");
            mydatabase.execSQL("INSERT INTO grid VALUES(4,0,0,0,0);");
            ob.turn = true;
        }
    }
    protected void onStop() {
        super.onStop();
        set_grid();
        set_bestscore();
    }
    public void set_bestscore() {
        mydatabase.execSQL("UPDATE score SET best = " + ob.best + ";");
    }
    public void set_grid() {
        for(  int i = 1; i <= 4; i++ ){
            mydatabase.execSQL("UPDATE grid SET col1 = " + ob.grid[i][1] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col2 = " + ob.grid[i][2] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col3 = " + ob.grid[i][3] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col4 = " + ob.grid[i][4] + " WHERE row_no = "+ i + " ;");
        }
        mydatabase.execSQL("UPDATE player_score set score = "+ ob.score +";");
    }
    public void set_dialogue(){
        Intent intent=new Intent(getApplicationContext(),custom_dialogue.class);
        startActivity(intent);
    }
    public void set_dialogue2(){
        Intent intent=new Intent(getApplicationContext(),win_dialogue.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        for(int i=1;i<=4;i++){
            for(int j=1;j<=4;j++){
                ob.grid[i][j]=0;
            }
        }
        ob.score=0;
        scr.postInvalidate();
        ob.gen_random();
        ob.gen_random();
        set_grid();
    }
}