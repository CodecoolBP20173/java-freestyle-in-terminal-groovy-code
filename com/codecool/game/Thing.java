package com.codecool.game;

import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import com.codecool.termlib.Direction;

//a rectangle shaped thing represented with a multiarray of chars
public class Thing{
    char[][] cells; //character cells of the thing 
    int sizeX, sizeY; // size of thing
    int posX, posY; //position of top left cell of the thing in the global grid array

    //this constructor fills a thing with the data of importPix 
    Thing(char[][] importPix, int posX, int posY){
        sizeX = importPix[0].length;
        sizeY = importPix.length;
        this.posX = posX;
        this.posY = posY;
        cells = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                cells[y][x] = importPix[y][x];
            }
        }
    }

    Thing(String filePath, int posX, int posY) throws IOException {

        this.posX = posX;
        this.posY = posY;

        BufferedReader inputStream = null;
        List<List<Character>> importCells;
        List<Character> line;

        try {
            inputStream = new BufferedReader(new FileReader(filePath));
            importCells = new ArrayList<List<Character>>();
            line = new ArrayList<Character>();
            int c;
            while ((c = inputStream.read()) != -1) {
                if (c == '\n'){
                    importCells.add(line);
                    System.out.println(line);
                    line = new ArrayList<Character>(); //clearing is not good, same ref 
                }
                else{
                    line.add((char) c);
                }
            }
            
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        sizeX = importCells.get(0).size();
        sizeY = importCells.size();

        cells = new char[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                cells[y][x] = importCells.get(y).get(x);
            }
        }

    }
    
    //TODO: we should handle partly out of screen and collision here I think
    //update grid with the thing, "stamp" it on the grid
    public void draw(){
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                Game.grid[posY + y][posX + x] = cells[y][x];
            }
        }
    }

    //clears the thing's rectangle in the grid, we use this method before moving 
    public void clear(){
        for (int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++){
                Game.grid[posY + y][posX + x] = '\u0000';
            }
        }
    }

    public void move(Direction dir){
        clear(); //clear the rectangle where we are now
        if (dir == Direction.FORWARD){
            posX++;
        }
        else if (dir == Direction.BACKWARD){
            posX--;
        }
        else if (dir == Direction.UP){
            posY--;
        }
        else if (dir == Direction.DOWN){
            posY++;
        }
        draw();
    }
}