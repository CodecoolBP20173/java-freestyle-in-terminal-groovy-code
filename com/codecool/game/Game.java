package com.codecool.game;
import com.codecool.termlib.*;

public class Game {
    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.setBgColor(Color.YELLOW);
        t.setColor(Color.BLACK);
        t.clearScreen();
        t.moveTo(10, 10);
        t.setChar('X');
        t.moveCursor(Direction.DOWN, 2);
        t.setChar('Y');
        t.resetStyle();
    }
}