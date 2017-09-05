package com.example.shago_000.puzzle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Random;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by shago_000 on 7/23/2015.
 */
public class game  extends  View implements View.OnTouchListener,GestureDetector.OnGestureListener{
    static int score=0;
    float grid_width;
    float grid_height;
    float space_size;
    static boolean win=false;
    static  boolean gameover=false;
    static  Random rnd;
    static  boolean turn;
    static boolean flag;
    float canvas_width;
    float canvas_height;
    int i,j;
    static int best;
    MainActivity mn;
    Paint p;
    boolean start =true;
    Bitmap btm[]=new Bitmap[13];
    RectF rectf[][]=new RectF[5][5];
    float space;
    Bitmap bm;
    RectF rec;
    final float round = 5;
    float box_width;
    float box_height;
    float box_xcor;
    static int grid[][]=new int[5][5];
    int block[][]=new int[5][5];
    float box_ycor;
    score scr;
    movement sw;
    Gameover gv;
    GestureDetector gestureDetector;
    public game(Context context) {
        super(context);
        rnd=new Random();
        p=new Paint();
        sw=new movement(this);
        sw.start();
        gv=new Gameover(this);
        gestureDetector=new GestureDetector(context,this);

    }
    public void set_object (score ob){
        scr=ob;
        sw.set_object(ob);
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(start) {
            rec=new RectF();
            canvas_width=canvas.getWidth();
            canvas_height=canvas.getHeight();
            p.setColor(Color.parseColor("#BBADA0"));
            p.setStyle(Paint.Style.FILL);
            grid_width = canvas_width;
            space_size = grid_width / 5;
            space = space_size / 12;
            space = space + (space / 2);
            space_size = space_size + (space_size - (space * 5)) / 4;
            space_size=space_size+(grid_width-((space_size*4)+(space*5)))/4;
            grid_height = space/2+grid_width;
            box_ycor = space+space/2;
            rectf[0][0] = new RectF();
            rectf[0][0].set(0, space/2, grid_width , grid_height);
            for (i = 1; i <= 4; i++) {
                box_xcor = space;
                box_height = box_ycor + space_size;
                for (j = 1; j <= 4; j++) {
                    box_width = space_size + box_xcor;
                    rectf[i][j] = new RectF();
                    rectf[i][j].set(box_xcor, box_ycor, box_width, box_height);
                    box_xcor = box_width + space;
                }
                box_ycor = box_ycor + space_size + space;
            }
            set_bitmap();
            if(turn) {
                gen_random();
                gen_random();
            }
            turn=false;
            start = false;
            scr.postInvalidate();

        }
        p.setColor(Color.parseColor("#BBADA0"));
        canvas.drawRoundRect(rectf[0][0], round, round, p);
        p.setColor(Color.parseColor("#CCC0B3"));
        for(i=1;i<=4;i++){
            for(j=1;j<=4;j++){
                canvas.drawRoundRect(rectf[i][j],round,round,p);
                if(grid[i][j]!=0){
                    find_bitmap(grid[i][j]);
                    canvas.drawBitmap(bm,null,rectf[i][j],null);
                    if(grid[i][j]==2048){
                       if(!win) {
                           win=true;
                           mn.set_dialogue2();
                       }
                    }
                }
            }
        }

        if(!gameover){
            if(!gv.up()){
                if(!gv.down()){
                    if(!gv.left()){
                        if(!gv.right()){
                            gameover=true;
                            mn.set_dialogue();
                        }
                    }
                }
            }
        }
        if(score>best){
            best = score;
        }
        if(turn) {
            gen_random();
            turn=false;
            set_value();
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) ;
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1){
        float dis=50;
        float velocity=50;
        try {
            float diffx = motionEvent1.getX() - motionEvent.getX();
            float diffy = motionEvent1.getY() - motionEvent.getY();
            set_value();
            if (Math.abs(diffx) > Math.abs(diffy)) {
                if (Math.abs(diffx) > dis && Math.abs(v) > velocity) {
                    if (diffx > 0) {
                        sw.direction="right";
                    } else{
                        sw.direction="left";
                    }
                    return true;
                }
            } else {
                if (Math.abs(diffy) > dis && Math.abs(v1) > velocity) {
                    if (diffy < 0) {
                        sw.direction="up";
                    } else {
                        sw.direction="down";
                    }
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }
    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
    public  void find_bitmap(int number){
        if(number==2){
            bm=btm[1];
        }
        if(number==4){
            bm=btm[2];
        }
        if(number==8){
            bm=btm[3];
        }
        if(number==16){
            bm=btm[4];
        }
        if(number==32){
            bm=btm[5];
        }
        if(number==64){
            bm=btm[6];
        }
        if(number==128){
            bm=btm[7];
        }
        if(number==256){
            bm=btm[8];
        }
        if(number==512){
            bm=btm[9];
        }
        if(number==1024){
            bm=btm[10];
        }
        if(number==2048){
            bm=btm[11];
        }
        if(number==4096){
            bm=btm[12];
        }
    }
    public void set_object(MainActivity m){
        mn=m;
    }
    private void set_bitmap(){
        btm[1]= BitmapFactory.decodeResource(getResources(), R.drawable.two);
        btm[2]=BitmapFactory.decodeResource(getResources(),R.drawable.four);
        btm[3]=BitmapFactory.decodeResource(getResources(),R.drawable.eight);
        btm[4]=BitmapFactory.decodeResource(getResources(),R.drawable.s_t);
        btm[5]=BitmapFactory.decodeResource(getResources(),R.drawable.t_t);
        btm[6]=BitmapFactory.decodeResource(getResources(),R.drawable.s_f);
        btm[7]=BitmapFactory.decodeResource(getResources(),R.drawable.o_t_e);
        btm[8]=BitmapFactory.decodeResource(getResources(),R.drawable.t_f_s);
        btm[9]=BitmapFactory.decodeResource(getResources(),R.drawable.f_o_t);
        btm[10]=BitmapFactory.decodeResource(getResources(),R.drawable.o_z_t_f);
        btm[11]=BitmapFactory.decodeResource(getResources(),R.drawable.t_z_f_e);
        btm[12]=BitmapFactory.decodeResource(getResources(),R.drawable.f_z_n_s);
        for(i=1;i<=12;i++){
            btm[i]=getRoundedCornerBitmap(btm[i]);
        }
    }
    static void gen_random(){
        int i,j,r,c;
        flag=true;
        for(i=1;i<=4;i++){
            for(j=1;j<=4;j++){
                if(grid[i][j]==0){
                    flag=false;
                    break;
                }
            }
        }
        if(flag){
            return;
        }
        while(true){
            r=rnd.nextInt(5-1)+1;
            for(i=1;i<=4;i++){
                if(grid[r][i]==0){
                    flag=true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        flag=false;
        while(true){
            c=rnd.nextInt(5-1)+1;
            for(i=1;i<=4;i++){
                if(grid[r][c]==0){
                    flag=true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        grid[r][c]=2;
    }
    public void set_value(){
        for(i=1;i<=4;i++){
            for(j=1;j<=4;j++){
                block[i][j]=0;
            }
        }
    }
    public  Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRoundRect(rectF, round, round, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}