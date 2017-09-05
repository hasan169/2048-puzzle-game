package com.example.shago_000.puzzle;

import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by shago_000 on 7/23/2015.
 */
public class movement extends Thread{

    game Game;
    score scr;
    boolean running=false;
    boolean update;
    int duration=32;
    int i,j,k;
    String direction;

    movement(game ob) {
        Game = ob;
        running=true;
    }
    public void set_object(score ob){
        scr=ob;
    }
    public void left() {
        for(i=1;i<=4;i++) {
            for (j = 2; j <= 4; j++) {
                if (Game.grid[i][j] != 0) {
                    if (Game.grid[i][j - 1]== 0) {
                        Game.grid[i][j - 1]= Game.grid[i][j];
                        Game.grid[i][j] = 0;
                        update = true;
                    } else if (Game.grid[i][j - 1] == Game.grid[i][j]) {
                        if (Game.block[i][j] == 0 && Game.block[i][j - 1] == 0) {
                            Game.block[i][j - 1] = 1;
                            Game.grid[i][j - 1] = Game.grid[i][j] + Game.grid[i][j - 1];
                            Game.grid[i][j] = 0;
                            Game.block[i][j] = 0;
                            update = true;
                            Game.score=Game.score+Game.grid[i][j-1];
                            scr.postInvalidate();
                        }
                    }
                }
            }
        }
    }
    public void right() {
        for (i = 1; i <= 4; i++) {
            for (j = 3; j >= 1; j--) {
                if (Game.grid[i][j]!= 0) {
                    if (Game.grid[i][j + 1] == 0) {
                        Game.grid[i][j + 1] = Game.grid[i][j];
                        Game.grid[i][j]= 0;
                        update = true;
                    } else if (Game.grid[i][j + 1] == Game.grid[i][j]) {
                        if (Game.block[i][j] == 0 && Game.block[i][j + 1] == 0) {
                            Game.block[i][j + 1] = 1;
                            Game.grid[i][j + 1]= Game.grid[i][j] + Game.grid[i][j + 1];
                            Game.grid[i][j]= 0;
                            Game.block[i][j] = 0;
                            update = true;
                            Game.score=Game.score+Game.grid[i][j+1];
                            scr.postInvalidate();
                        }
                    }
                }
            }
        }
    }
    public void up() {
        for (i = 1; i <= 4; i++) {
            for (j = 2; j <= 4; j++) {
                if (Game.grid[j][i]!= 0) {
                    if (Game.grid[j - 1][i] == 0) {
                        Game.grid[j - 1][i] = Game.grid[j][i];
                        Game.grid[j][i] = 0;
                        update = true;
                    } else if (Game.grid[j - 1][i]== Game.grid[j][i]) {
                        if (Game.block[j][i] == 0 && Game.block[j - 1][i] == 0) {
                            Game.block[j - 1][i] = 1;
                            Game.grid[j - 1][i] = Game.grid[j][i] + Game.grid[j - 1][i];
                            Game.grid[j][i] = 0;
                            Game.block[j][i] = 0;
                            update = true;
                            Game.score=Game.score+Game.grid[j-1][i];
                            scr.postInvalidate();
                        }
                    }
                }
            }
        }
    }
    public void down() {
        for(i=1;i<=4;i++){
            for (j = 3; j >= 1; j--) {
                if (Game.grid[j][i]!= 0) {
                    if (Game.grid[j + 1][i] == 0) {
                        Game.grid[j + 1][i] = Game.grid[j][i];
                        Game.grid[j][i] = 0;
                        update = true;
                    } else if (Game.grid[j + 1][i] == Game.grid[j][i]) {
                        if (Game.block[j][i] == 0 && Game.block[j + 1][i] == 0) {
                            Game.block[j + 1][i] = 1;
                            Game.grid[j + 1][i] = Game.grid[j][i]+ Game.grid[j + 1][i];
                            Game.grid[j][i] = 0;
                            Game.block[j][i] = 0;
                            update = true;
                            Game.score=Game.score+Game.grid[j+1][i];
                            scr.postInvalidate();
                        }
                    }
                }
            }
        }
    }
    @Override
    public void run() {
        while (running) {
            if(direction=="right"){
                update=false;
                for (k=1;k<=4;k++){
                    right();
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(update){
                    direction="";
                    Game.turn=true;
                }
            }
           else if(direction=="left"){
                update=false;
                for (k=1;k<=4;k++){
                    left();
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(update){
                    direction="";
                    Game.turn=true;
                }
            }
           else if(direction=="up"){
                update=false;
                for (k=1;k<=4;k++){
                    up();
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(update){
                    direction="";
                    Game.turn=true;
                }
            }
            if(direction=="down"){
                update=false;
                for (k=1;k<=4;k++){
                    down();
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(update){
                    direction="";
                    Game.turn=true;
                }
            }
        }
    }
}