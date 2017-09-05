package com.example.shago_000.puzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

/**
 * Created by shago_000 on 7/26/2015.
 */
public class score extends View {
    game Game;
    RectF score;
    RectF best;
    Paint p;
    Paint tx;
    float recsize;
    String str;
    Paint paintscore;
    Paint paint;
    float width;
    float canvas_width;
    boolean start=true;

    public score(Context context) {
        super(context);
        p=new Paint();
        paint=new Paint();
        tx=new Paint();
        paintscore=new Paint();
        score =new RectF();
        best=new RectF();
        paint.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.parseColor("#BBADA0"));
        p.setStyle(Paint.Style.FILL);
        paintscore.setColor(Color.parseColor("#FFFFFF"));
        paint.setColor(Color.parseColor("#EEE2CD"));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
    }
    public void object(game ob){
        Game=ob;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(start) {
            tx.setTextSize(Game.space * 6);
            tx.setColor(Color.parseColor("#776e65"));
            tx.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas_width = width = canvas.getWidth() / 2;
            width = (canvas_width - Game.space) / 2;
            score.set(canvas_width, 0, canvas_width + width + Game.space / 2, canvas.getHeight());
            best.set(canvas_width + width + Game.space, 0, canvas.getWidth(), canvas.getHeight());
            paint.setTextSize(Game.space + (Game.space / 2));
            paint.setTextAlign(Paint.Align.CENTER);
            paintscore.setTextSize(Game.space * 2);
            paintscore.setTextAlign(Paint.Align.CENTER);
            paintscore.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        }
        canvas.drawText("2048",0,score.height()-(Game.space/2),tx);
        canvas.drawRoundRect(score, 5, 5, p);
        canvas.drawRoundRect(best, 5, 5, p);
        canvas.drawText("SCORE", score.left + (score.width() / 2), score.top + Game.space * 2, paint);
        canvas.drawText("BEST", best.left + (best.width() / 2), best.top + Game.space * 2, paint);
        str=""+Game.score;
        recsize=score.width()/2;
        canvas.drawText("" + Game.score, score.left + recsize, score.top + Game.space * 4 + Game.space / 2, paintscore);
        canvas.drawText(""+game.best,best.left+recsize-Game.space/2,best.top+Game.space*4+Game.space/2,paintscore);
    }
}