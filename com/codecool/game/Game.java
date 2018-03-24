package com.codecool.game;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Thread;
import com.codecool.termlib.Color;
import com.codecool.termlib.Direction;
import com.codecool.termlib.Terminal;

public class Game {
    public static final int MAXROW = 24, MAXCOL = 80;
    public static char[][] grid = new char[MAXROW][MAXCOL];
    static Terminal t = new Terminal();
    static short frame = 1;
    static int speed = 140;
    static int[][] blocks=new int[50][2];
    static Thing car, roadSign;

    public static class Obstacle {
        static int row = 0;
        static int col = 45;
        static char symbol = 'O';
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        car = new Thing("resources/car.txt", 39, 15);
        roadSign = new Thing("resources/road_sign.txt", 39, -24);
        //System.in.read();
        updateGrid(Obstacle.row, Obstacle.col, Obstacle.symbol);
        while (true) {
            if (frame == 2400) frame = 1;
            if (frame % 3 == 0) drawRoad();

            if (speed > 50) {
                speed--;
            }
            
            moveObstacle();
            car.draw();
            
            Character input = tryToRead();
            if (input != null) {
                if (input == 'd') {
                    checkImpact();
                    car.move(Direction.FORWARD);
                }
                if (input == 'a') {
                    checkImpact();
                    car.move(Direction.BACKWARD);
                }
                if (input == 'w') {
                    if (speed > 0) {
                        speed -= 20;
                    }
                }
                if (input == 's') {
                    if (speed < 350) {
                        speed += 20;
                    }
                }
                if (input == 'q') {
                    System.exit(0);
                }
            }
            drawGrid();
            Thread.sleep(speed);
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
                        updateGrid(i, j, 'w'); // 'w' for 'white', drawGrid function will set a white bg block for every 'w'
                    }
                    else{
                        updateGrid(i, j, '\u0000');
                    }
                    
                }
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
    public static void clearGrid(){
        for(int i = 0; i < 24; i++){
            for (int j = 0; j < 80; j++){
                grid[i][j] = ' ';
            }
        }
    }

    public static void moveObstacle(){
        
        if (Obstacle.row < 23) {
            Obstacle.row++;
            updateGrid(Obstacle.row , Obstacle.col, Obstacle.symbol);
            updateGrid(Obstacle.row -1, Obstacle.col, ' ');
        }
        if (Obstacle.row == 23) {
            checkImpact();
            updateGrid(Obstacle.row, Obstacle.col, ' ');
            Obstacle.row = 0;
            Random rnd = new Random();
            int n = 25 + (int)(Math.random() * ((55-25)+1));
            Obstacle.col = n;
        }
    } 


    public static void blockElements(){
        Random rand = new Random();
        for (int i = 0; i<50; i++){
            int n = 25 + (int)(Math.random() * ((55 - 25) + 1));
            blocks[i][0]=15;
            blocks[i][1]=n;
        }
    }
    
    public static void checkImpact() {
            if (car.posX == 26) {
                car.move(Direction.FORWARD);
                
            }
            else if (car.posX == 54) {
                car.move(Direction.BACKWARD);
            }
            
            if (car.posX <= Obstacle.col <= car.posX + car.sizeX 
                && car.posY == Obstacle.row) {
                System.exit(0);
            }
    }
    
    public static void timer(){
        int setTime=0;
        char r = (char) setTime;
        updateGrid(10,10,'T');
        updateGrid(10,11,'I');
        updateGrid(10,12,'M');
        updateGrid(10,13,'E');
        updateGrid(10,14,':');
        updateGrid(10,15, r);
    }
}
