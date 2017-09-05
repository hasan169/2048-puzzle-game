package com.example.shago_000.puzzle;

/**
 * Created by shago_000 on 7/26/2015.
 */
public class Gameover {

    game Game;
    boolean update;
    int i,j;
    Gameover(game ob){
        Game=ob;
    }

    public boolean left() {
        update=false;
        for(i=1;i<=4;i++) {
            for (j = 2; j <= 4; j++) {
                if (Game.grid[i][j] != 0) {
                    if (Game.grid[i][j - 1]== 0) {
                        update = true;
                    } else if (Game.grid[i][j - 1] == Game.grid[i][j]) {
                            update = true;
                    }
                }
            }
        }
        return update;
    }
    public boolean right() {
        update=false;
        for (i = 1; i <= 4; i++) {
            for (j = 3; j >= 1; j--) {
                if (Game.grid[i][j]!= 0) {
                    if (Game.grid[i][j + 1] == 0) {
                        update = true;
                    } else if (Game.grid[i][j + 1] == Game.grid[i][j]) {
                        update = true;
                    }
                }
            }
        }
        return update;
    }
    public boolean up() {
        update=false;
        for (i = 1; i <= 4; i++) {
            for (j = 2; j <= 4; j++) {
                if (Game.grid[j][i]!= 0) {
                    if (Game.grid[j - 1][i] == 0) {
                        update = true;
                    } else if (Game.grid[j - 1][i]== Game.grid[j][i]) {
                        update = true;
                    }
                }
            }
        }
        return update;
    }
    public boolean down() {
        update=false;
        for(i=1;i<=4;i++){
            for (j = 3; j >= 1; j--) {
                if (Game.grid[j][i]!= 0) {
                    if (Game.grid[j + 1][i] == 0) {
                        update = true;
                    } else if (Game.grid[j + 1][i] == Game.grid[j][i]) {
                        update = true;
                    }
                }
            }
        }
        return update;
    }
}