package com.codecool.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.lang.model.util.ElementFilter;

import java.io.*;
import com.codecool.termlib.Color;
import com.codecool.termlib.Terminal;

public class Game {
    public static void main(String[] args) {
        while (true) {
            Terminal t = new Terminal();
            t.clearScreen();
            Character d = tryToRead();
            if (d == null)
                continue;
            if (d == 'd') {
                t.moveTo(30,30);
                t.setChar('X');
            }
            if (d == 'p') {
                System.exit(0);
            }
        }
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