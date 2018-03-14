package com.codecool.game;
import javax.lang.model.util.ElementFilter;
import java.io.*;
import com.codecool.termlib.Color;
import com.codecool.termlib.Direction;
import com.codecool.termlib.Terminal;

public class Game {
    static char[][] grid = new char[24][80];
    static char car = 'X';
    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.moveTo(79, 23);
        updateGrid(23, 79, car);
        while (true) {
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

}