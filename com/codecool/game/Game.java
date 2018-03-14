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


    public static void main(String[] args) throws InterruptedException {
        boolean b = false;
        t.moveTo(79, 23);
        updateGrid(23, 79, car);
        while (true) {
            grid(b);
            b = !b;
            Thread.sleep(500);
            //System.out.print(b);
            t.moveTo(0,0);
            Character d = tryToRead();
            if (d == null)
                continue;
            if (d == 'd') {
                t.setChar(' ');
                t.moveCursor(Direction.FORWARD, 1);
                t.setChar(car);
            }
            if (d == 'a') {
                t.setChar(' ');
                t.moveCursor(Direction.BACKWARD, 1);
                t.setChar(car);
            }
            if (d == 'p') {
                System.exit(0);
            }
            
        }
    }

    public static void updateGrid(int y, int x, char ch) {
        grid[y][x] = ch;
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
    
    public static void grid(boolean b){
        Terminal t = new Terminal();
        int toggle = b ? 1 : 0;
        int [][] board= new int [24][80];
        for(int i = 0;i<board.length;i++){
            for (int j = 0;j<80;j++){
                if (j==25 | j==55){
                    if(i % 2 == toggle){
                        System.out.print("/");
                    }
                    else{
                        System.out.print('\\');
                    }
                    
                }else{
                    System.out.print(" ");
                }
            }
            t.moveCursor(Direction.BACKWARD,80);
            System.out.println();
        }
    }
    public static void gridMoving(){
        
    }
}

