package com.codecool.game;

import java.io.*;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import javax.lang.model.util.ElementFilter;
import java.lang.Thread;

import com.codecool.termlib.Color;
import com.codecool.termlib.Direction;
import com.codecool.termlib.Terminal;



public class Game {
    public static final int MAXROW = 24, MAXCOL = 80;
    public static char[][] grid = new char[MAXROW][MAXCOL];
    static Terminal t = new Terminal();
    static short frame = 1;
    static Thing car, roadSign, roadSign2;
    
    public static void main(String[] args) throws InterruptedException, IOException {
        //System.in.read();
        car = new Thing("resources/car.txt", 39, 15);
        roadSign = new Thing("resources/road_sign.txt", 39, -24);
        while (true) {
            if (frame == 2400) frame = 1;
            if (frame % 3 == 0) drawRoad();
            car.draw();
            Character input = tryToRead();
            if (input != null){
                if (input == 'd') {
                    car.move(Direction.FORWARD);
                }
                if (input == 'a') {
                    car.move(Direction.BACKWARD);
                }
                if (input == 'w') {
                    car.move(Direction.UP);
                }
                if (input == 's') {
                    car.move(Direction.DOWN);
                }
                if (input == 'q') {
                    System.exit(0);
                }  
            }
            drawGrid();
            Thread.sleep(10);
            frame++;
            //System.in.read();
        }
    }

    public static void updateGrid(int row, int col, char ch) {
        grid[row][col] = ch;
    }

    private static Character tryToRead() {
        try {
            if (System.in.available() > 0) {
                return (char) System.in.read();
            }
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
        return null;
    }
    
    public static void drawRoad(){
        int rt_frame = frame / 3;
        int toggle = rt_frame % 2;

        if (roadSign.posY < 0) {
            roadSign.move(Direction.DOWN);    
        } else {
            roadSign.moveTo(39, -23);
        }

        for(int i = 0;i < 24;i++){
            for (int j = 0;j < 80;j++){
                if (j==25 || j==55){
                    if(i % 2 == toggle){
                        updateGrid(i, j, 'w'); // 'w' for 'white'
                    }
                    else{
                        updateGrid(i, j, '\u0000');
                    }
                    
                }
                // else if (j == 40){
                //     if ((i >= (rt_frame % 24) && i < ((rt_frame + 4) % 24)) ||
                //         (i >= (rt_frame + 12) % 24 && i < (rt_frame + 16) % 24)){
                    
                //         updateGrid(i, j, 'w'); 
                //     }
                //     else{
                //         updateGrid(i, j, '\u0000');       
                //     }
                // }
            }
        }
    }
    public static void drawGrid(){
        t.moveTo(0, 0);
        for(int i = 0;i < 24;i++){
            for (int j = 0;j < 80;j++){
                if (grid[i][j] == 'w'){
                    t.setBgColor(Color.WHITE);
                    t.setChar(' ');
                    t.resetStyle();
                }
                else if( grid[i][j] == '\u0000'){ //default initialization value for char type
                    t.setChar(' ');                    
                }
                else{
                    t.setChar(grid[i][j]);
                }
            }
        }
    }

    //fills the grid with space characters, space (' ') means there is nothing here
    //the char default initalization value \u0000 could print anything
    public static void clearGrid(){
        for(int i = 0; i < 24; i++){
            for (int j = 0; j < 80; j++){
                grid[i][j] = '\u0000';
            }
        }
    }

    public static void gridMoving(){

    }
}

