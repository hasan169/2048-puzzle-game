package com.example.shago_000.puzzle;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class custom_dialogue extends Activity implements View.OnClickListener{

    SQLiteDatabase mydatabase;
    TextView text;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_custom_dialogue);
        text=(TextView)findViewById(R.id.dl);
        text.setOnClickListener(this);
        tx=(TextView)findViewById(R.id.score);
        String str=""+game.score;
        str="YOUR SCORE "+str+" POINTS";
        tx.setText(str);
        mydatabase = openOrCreateDatabase("best_score", MODE_PRIVATE, null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_dialogue, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void set_grid() {
        for(  int i = 1; i <= 4; i++ ){
            mydatabase.execSQL("UPDATE grid SET col1 = " + game.grid[i][1] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col2 = " + game.grid[i][2] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col3 = " + game.grid[i][3] + " WHERE row_no = "+ i + " ;");
            mydatabase.execSQL("UPDATE grid SET col4 = " + game.grid[i][4] + " WHERE row_no = "+ i + " ;");
        }
        mydatabase.execSQL("UPDATE player_score set score = 0;");
    }
    @Override
    public void onClick(View view) {
        for(int i=1;i<=4;i++){
            for(int j=1;j<=4;j++){
                game.grid[i][j]=0;
            }
        }
        game.score=0;
        game.gameover=false;
        game.gen_random();
        game.gen_random();
        set_grid();
        this.finish();
    }
}