package com.codecool.game;

import com.codecool.termlib.Direction;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.ElementFilter;
import java.lang.Thread;


import java.io.*;
import com.codecool.termlib.Color;
import com.codecool.termlib.Direction;
import com.codecool.termlib.Terminal;



public class Game {
    static char[][] grid = new char[24][80];
    static Terminal t = new Terminal();
    static char car = 'X';

    public static class Car{
        static int row = 23;
        static int col = 45;
    }

    public static void main(String[] args) throws InterruptedException {
        boolean b = false;
        updateGrid(Car.row, Car.col, car);
        while (true) {
            Character input = tryToRead();
            if (input != null){
                if (input == 'd') {
                    updateGrid(Car.row, Car.col, ' ');
                    Car.col++;
                    updateGrid(Car.row, Car.col, 'X');
                }
                if (input == 'a') {
                    updateGrid(Car.row, Car.col, ' ');
                    Car.col--;
                    updateGrid(Car.row, Car.col, 'X');
                }
                if (input == 'q') {
                    System.exit(0);
                }  
            }
            drawRoad(b);
            b = !b;
            drawGrid();
            Thread.sleep(200);
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
    
    public static void drawRoad(boolean b){
        int toggle = b ? 1 : 0;
        for(int i = 0;i < 24;i++){
            for (int j = 0;j < 80;j++){
                if (j==25 | j==55){
                    if(i % 2 == toggle){
                        updateGrid(i, j, '/');
                    }
                    else{
                        updateGrid(i, j, '\\');
                    }
                    
                }else{
                    if (grid[i][j] != 'X'){
                        updateGrid(i, j, ' ');
                    }
                }
            }
        }
    }

    public static void drawGrid(){
        t.moveTo(0, 0);
        for(int i = 0;i < 24;i++){
            for (int j = 0;j < 80;j++){
                t.setChar(grid[i][j]);
            }
        }
    }

    public static void gridMoving(){

    }
}

