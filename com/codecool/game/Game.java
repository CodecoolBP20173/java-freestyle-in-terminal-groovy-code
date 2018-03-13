package com.codecool.game;
import com.codecool.termlib.Terminal;

public class Game {
    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.setBgColor(Color.YELLOW);
        t.setChar('X');
        t.clearScreen();
    }
}