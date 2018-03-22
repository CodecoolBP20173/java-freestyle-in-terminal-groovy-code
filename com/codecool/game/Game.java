package com.codecool.game;

import com.codecool.termlib.Direction;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    static int[][] blocks=new int[50][2];


    public static class Car {
        static int row = 23;
        static int col = 45;
        static int speed = 140;
    }

    public static class Obstacle {
        static int row = 0;
        static int col = 45;
        static char symbol = 'O';
    }

    public static void main(String[] args) throws InterruptedException {
        boolean b = false;
        updateGrid(Car.row, Car.col, car);
        updateGrid(Obstacle.row, Obstacle.col, Obstacle.symbol);
        while (true) {
            if (Car.speed > 50) {
                Car.speed--;
            }
            moveObstacle();

            Character input = tryToRead();
            if (input != null) {
                if (input == 'd') {
                    checkImpact();
                    updateGrid(Car.row, Car.col, ' ');
                    Car.col++;
                    updateGrid(Car.row, Car.col, 'X');
                }
                if (input == 'a') {
                    checkImpact();
                    updateGrid(Car.row, Car.col, ' ');
                    Car.col--;
                    updateGrid(Car.row, Car.col, 'X');
                }
                if (input == 'w') {
                    if (Car.speed > 0) {
                        Car.speed -= 20;
                    }
                }
                if (input == 's') {
                    if (Car.speed < 350) {
                        Car.speed += 20;
                    }
                }
                if (input == 'q') {
                    System.exit(0);
                }
            }
            moveObstacle();
            drawRoad(b);
            b = !b;
            drawGrid();
            Thread.sleep(Car.speed);
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

    public static void drawRoad(boolean b) {
        int toggle = b ? 1 : 0;
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 80; j++) {
                if (j == 25 | j == 55) {
                    if (i % 2 == toggle) {
                        updateGrid(i, j, '/');
                    } else {
                        updateGrid(i, j, '\\');
                    }

                } else {
                    if (grid[i][j] != 'X' && grid[i][j] != 'O') {
                        updateGrid(i, j, ' ');
                    }
                }
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


    public static void drawGrid() {
        t.moveTo(0, 0);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 80; j++) {
                t.setChar(grid[i][j]);
            }
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

    public static void checkImpact() {
        for (int i = 0; i < grid.length; i++) {
            if (Car.col == 26) {
                updateGrid(Car.row, Car.col, ' ');
                Car.col++;

            }
            if (Car.col == 54) {
                updateGrid(Car.row, Car.col, ' ');
                Car.col--;

            }

            if (Car.col == Obstacle.col && Car.row == Obstacle.row) {
                System.exit(5);
            }
        }
    }
}
